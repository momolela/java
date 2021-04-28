package com.momolela.proxy.javassistdynamicproxy;

import com.momolela.proxy.javassistdynamicproxy.pojo.FieldsInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.MethodInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.ParameterInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.WebServiceInfo;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomWSProxyFactory {

    private static final AtomicInteger INCREMENT = new AtomicInteger(0);
    private WebServiceInfo webServiceInfo;
    private List<MethodInfo> methodInfos;
    private List<FieldsInfo> fieldsInfos;

    public CustomWSProxyFactory(WebServiceInfo webServiceInfo, List<MethodInfo> methodInfos, List<FieldsInfo> fieldsInfos) {
        this.webServiceInfo = webServiceInfo;
        this.methodInfos = methodInfos;
        this.fieldsInfos = fieldsInfos;
    }

    /**
     * 获取代理类对象
     *
     * @return
     * @throws Exception
     */
    public Object getProxy() throws Exception {

        ClassPool classPool = ClassPool.getDefault();
        classPool.insertClassPath(new ClassClassPath(CustomWSClass.class));

        // 创建自定义代理类
        CtClass customClassProxy = classPool.makeClass("CustomWSClassProxy$" + INCREMENT.incrementAndGet());
        try {
            // 设置父类
            customClassProxy.setSuperclass(classPool.get("java.lang.Object"));
            // 设置属性
            if (fieldsInfos != null && !fieldsInfos.isEmpty()) {
                for (FieldsInfo fieldsInfo : fieldsInfos) {
                    buildFieldsInClass(classPool, customClassProxy, fieldsInfo);
                }
            }
            ClassFile classFile = customClassProxy.getClassFile();
            ConstPool constPool = classFile.getConstPool();
            // 设置类注解
            classAnnotation(constPool, classFile);
            // 创建方法
            if (methodInfos != null && !methodInfos.isEmpty()) {
                for (MethodInfo methodInfo : methodInfos) {
                    CtMethod ctMethod = createMethod(methodInfo, customClassProxy, classPool);
                    // 方法添加注解
                    methodAnnotation(ctMethod, methodInfo, constPool);
                    List<ParameterInfo> parameterInfos = methodInfo.getParameterInfos();
                    if (parameterInfos != null && !parameterInfos.isEmpty()) {
                        // 添加参数注解
                        paramAnnotation(constPool, ctMethod, parameterInfos);
                    }
                }
            }
            Class clazz = customClassProxy.toClass();
            soapBindingAno(clazz);
            Constructor cons = clazz.getConstructor();
            return cons.newInstance();
        } catch (CannotCompileException e) {
            throw new IllegalArgumentException("创建代理类时编译异常", e);
        } catch (NotFoundException e) {
            throw new IllegalArgumentException("创建代理类时类未找到", e);
        }
    }

    /**
     * 创建类属性
     *
     * @param classPool
     * @param customClassProxy
     * @param fieldInfo
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    private void buildFieldsInClass(ClassPool classPool, CtClass customClassProxy, FieldsInfo fieldInfo) throws CannotCompileException, NotFoundException {
        CtClass fieldType = classPool.get(fieldInfo.getFieldClass()); // 字段类型
        String fieldName = fieldInfo.getFieldName(); // 字段名称
//        String modifiers = fieldInfo.getModifiers();// 字段修饰符
        CtField ctField = new CtField(fieldType, fieldName, customClassProxy);
        ctField.setModifiers(Modifier.PRIVATE);// 设置访问修饰符
//        customClassProxy.addField(fieldName, CtField.Initializer.constant("sunzj")); // 添加 name 字段，赋值为 sunzj
        customClassProxy.addField(ctField); // 添加name字段
        // setName getName方法
        customClassProxy.addMethod(CtNewMethod.setter("set" + captureName(fieldName), ctField));
        customClassProxy.addMethod(CtNewMethod.getter("get" + captureName(fieldName), ctField));
    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    private String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 添加类注解
     *
     * @param constPool
     * @param ccFile
     */
    private void classAnnotation(ConstPool constPool, ClassFile ccFile) {
        AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        // 设置 @WebService
        Annotation webServiceAno = new Annotation("javax.jws.WebService", constPool);
        if (webServiceInfo != null) {
            if (webServiceInfo.getNamespace() != null && !"".equals(webServiceInfo.getNamespace())) {
                webServiceAno.addMemberValue("targetNamespace", new StringMemberValue(webServiceInfo.getNamespace(), constPool));
            }
            if (webServiceInfo.getPortName() != null && !"".equals(webServiceInfo.getPortName())) {
                webServiceAno.addMemberValue("serviceName", new StringMemberValue(webServiceInfo.getServiceName(), constPool));
            }
            if (webServiceInfo.getServiceName() != null && !"".equals(webServiceInfo.getServiceName())) {
                webServiceAno.addMemberValue("portName", new StringMemberValue(webServiceInfo.getPortName(), constPool));
            }
        }
        bodyAttr.addAnnotation(webServiceAno);
        // 设置 @SOAPBinding
        Annotation SOAPBindingAno = new Annotation("javax.jws.soap.SOAPBinding", constPool);
//        SOAPBindingAno.addMemberValue("style", new EnumMemberValue("", constPool)); // 改为单独设置
        bodyAttr.addAnnotation(SOAPBindingAno);
        ccFile.addAttribute(bodyAttr);
    }

    /**
     * 创建方法
     *
     * @param methodInfo
     * @param ctClass
     * @param classPool
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    private CtMethod createMethod(MethodInfo methodInfo, CtClass ctClass, ClassPool classPool) throws NotFoundException, CannotCompileException {
        CtClass returnType = classPool.get(methodInfo.getReturnType());

        CtClass[] parameters = createCtClass(methodInfo, classPool);
        // 参数：
        // 1：返回类型
        // 2：方法名称
        // 3：传入参数类型
        // 4：所属类 CtClass
        CtMethod ctMethod = new CtMethod(returnType, methodInfo.getMethodName(), parameters, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC); // 可以根据修饰符自行修改
        // 方法体
        StringBuffer body = buildMethodBody(classPool, methodInfo);
        ctMethod.setBody(body.toString());
        ctClass.addMethod(ctMethod);
        return ctMethod;
    }

    /**
     * 获取方法参数列表类型
     *
     * @param methodInfo
     * @param classPool
     * @return
     * @throws NotFoundException
     */
    private CtClass[] createCtClass(MethodInfo methodInfo, ClassPool classPool) throws NotFoundException {
        if (methodInfo.getParameterInfos() == null || methodInfo.getParameterInfos().isEmpty())
            return new CtClass[0];
        CtClass[] ctClasses = new CtClass[methodInfo.getParameterInfos().size()];
        for (int i = 0; i < ctClasses.length; i++) {
            ParameterInfo parameterInfo = methodInfo.getParameterInfos().get(i);
            CtClass ccStringType = classPool.get(parameterInfo.getClassName());
            ctClasses[i] = ccStringType;
        }
        return ctClasses;
    }

    /**
     * 通用的方法，调用接入
     *
     * @return
     */
    private StringBuffer buildMethodBody(ClassPool classPool, MethodInfo methodInfo) {
        // 导入
        classPool.importPackage("java.util.*;");

        StringBuffer bodyStringBuffer = new StringBuffer();
        for (int i = 1; i < methodInfo.getParameterInfos().size() + 1; i++) {
            bodyStringBuffer.append("\n System.out.println($" + i + ");");
        }
        StringBuffer methodBody = new StringBuffer();
        methodBody.append("{");
        methodBody.append(bodyStringBuffer);
        methodBody.append("\n}");
        return methodBody;
    }

    /**
     * 添加方法注解{@WebMethod(operationName="")}
     *
     * @param ctMethod
     * @param methodInfo
     * @param constPool
     */
    private void methodAnnotation(CtMethod ctMethod, MethodInfo methodInfo, ConstPool constPool) {
        AnnotationsAttribute methodAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation methodAno = new Annotation("javax.jws.WebMethod", constPool);
        methodAno.addMemberValue("operationName", new StringMemberValue(methodInfo.getMethodName(), constPool));
        methodAttr.addAnnotation(methodAno);
        ctMethod.getMethodInfo().addAttribute(methodAttr);
    }

    /**
     * 添加属性注解{@WebParam(name="")}
     *
     * @param constPool
     * @param ctMethod
     * @param parameterInfos
     */
    private void paramAnnotation(ConstPool constPool, CtMethod ctMethod, List<ParameterInfo> parameterInfos) {
        ParameterAnnotationsAttribute parameterAttribute = new ParameterAnnotationsAttribute(
                constPool, ParameterAnnotationsAttribute.visibleTag);
        Annotation[][] paramArrays = new Annotation[parameterInfos.size()][1];
        for (int i = 0; i < parameterInfos.size(); i++) {
            ParameterInfo parameterInfo = parameterInfos.get(i);
            Annotation paramAno = new Annotation("javax.jws.WebParam", constPool);
            paramAno.addMemberValue("name", new StringMemberValue(parameterInfo.getParamName(), constPool));
            paramArrays[i][0] = paramAno;
        }
        parameterAttribute.setAnnotations(paramArrays);
        ctMethod.getMethodInfo().addAttribute(parameterAttribute);
    }

    /**
     * 单独设置{SOAPBinding.style}属性
     *
     * @param clazz
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void soapBindingAno(Class clazz) throws NoSuchFieldException, IllegalAccessException {
        SOAPBinding soapBinding = (SOAPBinding) clazz.getAnnotation(SOAPBinding.class);
        if (soapBinding != null) {
            InvocationHandler handler = Proxy.getInvocationHandler(soapBinding);
            Field field = handler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);
            Map memberMethods = (Map) field.get(handler);
            memberMethods.put("style", SOAPBinding.Style.RPC);
        }
    }

}

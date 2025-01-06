package com.momolela.proxy.jdkdynamicproxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author sunzj
 */
public class ArithmeticCalculatorLoggingProxy {

    private ArithmeticCalculator target;

    public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
        super();
        this.target = target;
    }

    public ArithmeticCalculator getLoggingProxy() {
        ArithmeticCalculator arithmeticCalculatorProxy = null;
        // 获取类加载器，和对应的类类型
        ClassLoader classLoader = this.target.getClass().getClassLoader();
        Class[] interfaces = {ArithmeticCalculator.class};

        // 实现函数式接口 InvocationHandler
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                System.out.println("这是在方法【" + methodName + "】调用前打印，参数是：" + Arrays.asList(args));
                Object result = method.invoke(target, args);
                System.out.println("这是在方法【" + methodName + "】调用后打印，结果是：" + result);
                return result;
            }
        };

        // 生成代理并且返回
        arithmeticCalculatorProxy = (ArithmeticCalculator) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        return arithmeticCalculatorProxy;
    }
}

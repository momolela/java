package com.momolela.proxy.cglibdynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> clazz) {
        // 字节码增强的一个类
        Enhancer enhancer = new Enhancer();

        // 当要代理的是一个接口时
        enhancer.setInterfaces(new Class[]{clazz});
//        // 当要代理的是一个接口实现类时，用这种方式，设置父类
//        enhancer.setSuperclass(clazz);

        // 设置回调类，回调当前接口实现类的  方法
        enhancer.setCallback(this);
        // 创建代理类
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("动态代理-前置增强（通知）===== 方法是：" + method.getName());

        // 当要代理的是一个接口时，因为具体的实现是未知的，要自己实现
        System.out.println("当要代理的是一个接口时，因为具体的实现是未知的，要自己实现");
//        // 当要代理的是一个接口实现类时，用这种方式，调用具体接口实现类的方法
//        Object result = methodProxy.invokeSuper(obj, args);

        System.out.println("动态代理-后置增强（通知）===== 方法是：" + method.getName());

        // 当要代理的是一个接口时
        return null;
//        // 当要代理的是一个接口实现类时
//        return result;
    }
}

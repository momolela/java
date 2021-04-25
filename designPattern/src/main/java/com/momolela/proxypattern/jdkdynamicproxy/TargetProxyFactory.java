package com.momolela.proxypattern.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 1、创建一个生成目标代理的工厂类，实现 InvocationHandler 接口，和静态代理不同的是，静态代理实现的是目标接口
public class TargetProxyFactory implements InvocationHandler {

    // 2、拥有目标接口的引用
    private Object targetInterFace;

    // 3、提供形参为目标接口的构造函数，并赋值给引用
    public TargetProxyFactory(Object targetInterFace) {
        this.targetInterFace = targetInterFace;
    }

    // 4、利用 jdk 的 Proxy 提供获取目标代理类的方法
    public <T> T getTargetProxy(Class interfaces) {
        // 3个参数，classLoader, 接口集合, InvocationHandler
        return (T) Proxy.newProxyInstance(this.targetInterFace.getClass().getClassLoader(), new Class<?>[]{interfaces}, this);
    }

    // 5、重写 invoke 的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理-前置增强（通知）");
        // 通过反射调用实际接口实现的方法，并返回结果
        Object result = method.invoke(targetInterFace, args);
        System.out.println("动态代理-后置增强（通知）");
        return result;
    }
}

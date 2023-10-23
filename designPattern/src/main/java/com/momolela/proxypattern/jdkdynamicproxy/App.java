package com.momolela.proxypattern.jdkdynamicproxy;

/**
 * @author sunzj
 */
public class App {
    public static void main(String[] args) {
        HelloServiceImpl helloServiceImpl = new HelloServiceImpl();
        // 创建动态代理工厂类，传入要代理的类
        TargetProxyFactory proxyClass = new TargetProxyFactory(helloServiceImpl);
        // 通过工厂类获取代理类对象，这里只支持接口的代理
        HelloService targetProxy = proxyClass.getTargetProxy(HelloService.class);
        // 通过代理类对象调用方法进行增强
        targetProxy.sayHello();
    }
}

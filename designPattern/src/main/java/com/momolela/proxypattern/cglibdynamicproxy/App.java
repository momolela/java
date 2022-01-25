package com.momolela.proxypattern.cglibdynamicproxy;

public class App {
    public static void main(String[] args) {
        TargetProxy targetProxy = new TargetProxy();
//        HelloService proxy = targetProxy.getProxy(HelloService.class); // 代理接口
        HelloService proxy = targetProxy.getProxy(HelloServiceImpl.class); // 代理接口实现类
        proxy.sayHello();
    }
}

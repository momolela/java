package com.momolela.proxypattern.jdkstaticproxy;

public class App {
    public static void main(String[] args) {
        // 创建一个普通接口实现类对象，里面有自己实现的方法
        HelloServiceImpl helloServiceImpl = new HelloServiceImpl();
        // 将创建的普通接口实现类对象传入到代理类，返回代理对象
        HelloService helloService = new TargetProxy(helloServiceImpl);
        // 通过代理对象调用就可以实现方法增强
        helloService.sayHello();
    }
}

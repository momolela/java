package com.momolela.proxypattern.cglibdynamicproxy;

/**
 * @author sunzj
 */
public class App {
    public static void main(String[] args) {
        TargetProxy targetProxy = new TargetProxy();

        // 代理接口
        // HelloService proxy = targetProxy.getProxy(HelloService.class);

        // 代理接口实现类
        HelloService proxy = targetProxy.getProxy(HelloServiceImpl.class);
        proxy.sayHello();
    }
}

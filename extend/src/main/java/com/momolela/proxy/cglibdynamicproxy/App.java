package com.momolela.proxy.cglibdynamicproxy;


import com.sun.xml.internal.ws.spi.ProviderImpl;

public class App {
    public static void main(String[] args) {
//         TargetProxy targetProxy = new TargetProxy();
//         HelloService proxy = targetProxy.getProxy(HelloService.class); // 代理接口
// //        HelloService proxy = targetProxy.getProxy(HelloServiceImpl.class); // 代理接口实现类
//         proxy.sayHello();


        TargetProxy targetProxy4WS = new TargetProxy();
        ProviderImpl proxy = targetProxy4WS.getProxy(ProviderImpl.class);// 代理接口
        proxy.createAndPublishEndpoint("http://10.0.38.178:8788/hello", new HelloServiceImpl());
    }
}

package com.momolela.proxypattern.jdkdynamicproxy;

/**
 * @author sunzj
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

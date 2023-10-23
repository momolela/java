package com.momolela.proxypattern.jdkstaticproxy;

/**
 * @author sunzj
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

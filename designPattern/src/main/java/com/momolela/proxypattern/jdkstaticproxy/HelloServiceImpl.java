package com.momolela.proxypattern.jdkstaticproxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

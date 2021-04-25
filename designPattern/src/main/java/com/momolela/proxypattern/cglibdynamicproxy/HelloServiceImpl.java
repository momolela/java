package com.momolela.proxypattern.cglibdynamicproxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

package com.momolela.proxy.jdkstaticproxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

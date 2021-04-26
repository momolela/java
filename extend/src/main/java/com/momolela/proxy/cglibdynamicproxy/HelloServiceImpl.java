package com.momolela.proxy.cglibdynamicproxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

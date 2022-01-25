package com.momolela.proxy.jdkdynamicproxy1;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

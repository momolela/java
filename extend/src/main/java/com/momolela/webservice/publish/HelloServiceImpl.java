package com.momolela.webservice.publish;

import javax.jws.WebService;

@WebService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "hello " + name;
    }
}

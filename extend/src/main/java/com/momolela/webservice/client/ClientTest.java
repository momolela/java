package com.momolela.webservice.client;

import com.momolela.webservice.server.HelloServiceImpl;
import com.momolela.webservice.server.HelloServiceImplService;

public class ClientTest {
    public static void main(String[] args) {
        HelloServiceImplService helloServiceImplService = new HelloServiceImplService();
        HelloServiceImpl helloServiceImplPort = helloServiceImplService.getHelloServiceImplPort();
        String s = helloServiceImplPort.sayHello("hufy");
        System.out.println(s);
    }
}

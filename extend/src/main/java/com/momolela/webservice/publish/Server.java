package com.momolela.webservice.publish;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        // 简单的发布服务
        Endpoint.publish("http://127.0.0.1:8080/hello", new HelloServiceImpl());
        System.out.println("服务启动了...");
    }
}

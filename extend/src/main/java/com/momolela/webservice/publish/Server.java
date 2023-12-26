package com.momolela.webservice.publish;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        // 简单的发布服务
        Endpoint.publish("http://10.0.38.179:8788/hello", new HelloServiceImpl());
        System.out.println("服务启动了...");
    }
}

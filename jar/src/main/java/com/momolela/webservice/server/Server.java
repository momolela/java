package com.momolela.webservice.server;

import com.momolela.util.NetUtil;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        String ipAddress = NetUtil.getIpAddress();
        String wsUrl = "http://" + ipAddress + ":9000/hello";
        Endpoint.publish(wsUrl, new HelloServiceImpl());
        System.out.println("webservice 服务启动了，地址是：" + wsUrl + "?wsdl");
    }
}

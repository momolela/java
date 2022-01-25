package com.momolela.webservice.server;

import javax.xml.ws.Endpoint;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Server {
    public static void main(String[] args) {
        String ipAddress = getIpAddress();
        String wsUrl = "http://" + ipAddress + ":9000/hello";
        Endpoint.publish(wsUrl, new HelloServiceImpl());
        System.out.println("webservice 服务启动了，地址是：" + wsUrl + "?wsdl");
    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress = null;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        inetAddress = inetAddresses.nextElement();
                        if (inetAddress != null && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("IP 地址获取失败：" + e.toString());
        }
        return "";
    }
}

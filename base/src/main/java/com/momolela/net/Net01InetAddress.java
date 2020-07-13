package com.momolela.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Net01InetAddress {
    public static void main(String[] args) throws UnknownHostException {
        getLocalHost();
        getRemoteHost();
    }

    private static void getLocalHost() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());
        System.out.println("hostName：" + localHost.getHostName());
        System.out.println("hostAddress：" + localHost.getHostAddress());
    }

    private static void getRemoteHost() throws UnknownHostException {
        InetAddress[] inetAddresses = InetAddress.getAllByName("www.baidu.com");
        for (InetAddress inetAddress : inetAddresses) {
            System.out.println("baidu hostName：" + inetAddress.getHostName());
            System.out.println("baidu hostAddress：" + inetAddress.getHostAddress());
        }
    }
}

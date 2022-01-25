package com.momolela.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Net02UDPSocketSend {

    public static void main(String[] args) throws IOException {
//        UDPSend1();
        UDPSend2();
    }

    private static void UDPSend1() throws IOException {
        DatagramSocket socket = new DatagramSocket(); // 创建 DatagramSocket
        byte[] buff = "hello sunzj".getBytes(); // 准备要传递的数据 DatagramSocket
        DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length, InetAddress.getByName("127.0.0.1"), 10000); // 创建 DatagramPacket 数据包，包含 InetAddress 和指定端口
        socket.send(datagramPacket); // 发送
        socket.close(); // 关闭资源
    }

    private static void UDPSend2() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); // 控制台输入来发送
        DatagramSocket socket = new DatagramSocket();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("886")) { // 发送 886 关闭
                break;
            }
            byte[] bytes = line.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 10000); // 这里有个东西实现群聊，xxx.xxx.xxx.0（代表网络段地址），xxx.xxx.xxx.255（这个网络段的广播地址） 是两个特殊地址，用广播地址，可以给这个网络段的所有机器发信息
            socket.send(datagramPacket);
        }
        socket.close();
    }

}

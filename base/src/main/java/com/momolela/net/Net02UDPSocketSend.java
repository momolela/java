package com.momolela.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Net02UDPSocketSend {

    public static void main(String[] args) throws IOException {
        UDPSend();
    }

    private static void UDPSend() throws IOException {
        DatagramSocket socket = new DatagramSocket(); // 创建 DatagramSocket
        byte[] buff = "hello sunzj".getBytes(); // 准备要传递的数据 DatagramSocket
        DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length, InetAddress.getByName("127.0.0.1"), 10000); // 创建 DatagramPacket 数据包，包含 InetAddress 和指定端口
        socket.send(datagramPacket); // 发送
        socket.close(); // 关闭资源
    }

}

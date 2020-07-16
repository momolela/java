package com.momolela.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Net03UDPSocketReceive {

    public static void main(String[] args) throws IOException {
        UDPReceive();
    }

    private static void UDPReceive() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(10000); // 创建 DatagramSocket，指定本程序的 port
        byte[] buff = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length); // 准备数据包 datagramPacket
        datagramSocket.receive(datagramPacket); // 接收数据放到 datagramPacket
        String hostAddress = datagramPacket.getAddress().getHostAddress();
        byte[] data = datagramPacket.getData();
        String dataStr = new String(data, 0, data.length);
        int port = datagramPacket.getPort();
        System.out.println(hostAddress + ":" + port + " data[" + dataStr + "]"); // 打印 datagramPacket 对象中获取的数据信息
        datagramSocket.close(); // 关闭资源
    }

}

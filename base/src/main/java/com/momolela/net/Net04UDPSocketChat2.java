package com.momolela.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Net04UDPSocketChat2 {

    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocketSend = new DatagramSocket();
        DatagramSocket datagramSocketReceive = new DatagramSocket(10001);
        new Thread(new Send2(datagramSocketSend)).start();
        new Thread(new Receive2(datagramSocketReceive)).start();
    }

}

class Send2 implements Runnable {

    private DatagramSocket datagramSocket;

    public Send2(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("886")) { // 发送 886 关闭
                    break;
                }
                byte[] bytes = line.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 10000); // 这里有个东西实现群聊，xxx.xxx.xxx.0（代表网络段地址），xxx.xxx.xxx.255（这个网络段的广播地址） 是两个特殊地址，用广播地址，可以给这个网络段的所有机器发信息
                datagramSocket.send(datagramPacket);
            }
            datagramSocket.close();
        } catch (Exception e) {
            throw new RuntimeException("发送消息失败");
        }
    }
}

class Receive2 implements Runnable {

    private DatagramSocket datagramSocket;

    public Receive2(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buff = new byte[1024 * 64]; // 发送方最多 64K 发一次，这里可以定义成 64K 去接
                DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length); // 准备数据包 datagramPacket
                datagramSocket.receive(datagramPacket); // 接收数据放到 datagramPacket ，有数据就接收，没数据就阻塞
                String hostAddress = datagramPacket.getAddress().getHostAddress();
                byte[] data = datagramPacket.getData();
                String dataStr = new String(data, 0, data.length);
                int port = datagramPacket.getPort();
                System.out.println(hostAddress + ":" + port + " data[" + dataStr + "]"); // 打印 datagramPacket 对象中获取的数据信息
            }
        } catch (Exception e) {
            throw new RuntimeException("发送消息失败");
        }
    }
}

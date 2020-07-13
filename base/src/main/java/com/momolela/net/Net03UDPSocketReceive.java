package com.momolela.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Net03UDPSocketReceive {

    public static void main(String[] args) throws IOException {
        UDPReceive();
    }

    private static void UDPReceive() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(10000);
        byte[] buff = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
        datagramSocket.receive(datagramPacket);
        String hostAddress = datagramPacket.getAddress().getHostAddress();
        byte[] data = datagramPacket.getData();
        String dataStr = new String(data, 0, data.length);
        int port = datagramPacket.getPort();
        System.out.println(hostAddress + ":" + port + " data[" + dataStr + "]");
        datagramSocket.close();
    }

}

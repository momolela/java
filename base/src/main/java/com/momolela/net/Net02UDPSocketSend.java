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
        DatagramSocket socket = new DatagramSocket();
        byte[] buff = "hello sunzj".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length, InetAddress.getByName("127.0.0.1"), 10000);
        socket.send(datagramPacket);
        socket.close();
    }

}

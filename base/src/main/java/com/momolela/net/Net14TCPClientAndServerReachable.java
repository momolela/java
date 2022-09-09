package com.momolela.net;

import java.io.IOException;
import java.net.*;

public class Net14TCPClientAndServerReachable {

    public static void main(String[] args) throws Exception {
        InetAddress localInetAddress = InetAddress.getByName("192.168.1.3"); // 本机 ip
        InetAddress remoteInetAddress = InetAddress.getByName("180.101.49.12"); // 远程 ip
        boolean reachable = isReachable(localInetAddress, remoteInetAddress, 80, 10000);
    }

    static boolean isReachable(InetAddress localInetAddress, InetAddress remoteInetAddress, int port, int timeout) {
        boolean isReachable = false;
        Socket socket = null;
        try {
            socket = new Socket();
            // 端口号设置为 0 表示在本地挑选一个可用端口进行连接
            SocketAddress localSocketAddress = new InetSocketAddress(localInetAddress, 0);
            socket.bind(localSocketAddress);
            InetSocketAddress endpointSocketAddress = new InetSocketAddress(remoteInetAddress, port);
            socket.connect(endpointSocketAddress, timeout);
            System.out.println("SUCCESS - connection established! Local: " + localInetAddress.getHostAddress() + " remote: " + remoteInetAddress.getHostAddress() + " port " + port);
            isReachable = true;
        } catch (IOException e) {
            System.out.println("FAILRE - CAN not connect! Local: " + localInetAddress.getHostAddress() + " remote: " + remoteInetAddress.getHostAddress() + " port " + port);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error occurred while closing socket..");
                }
            }
        }
        return isReachable;
    }

}

package com.momolela.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Net06TCPServer {
    public static void main(String[] args) throws IOException {
        TCPSocketRead();
    }

    private static void TCPSocketRead() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(10001, 3); // 指定只能建立3个连接，backlog
        ServerSocket serverSocket = new ServerSocket(10001);
        while (true) {
            Socket socket = serverSocket.accept();// 阻塞，没有数据就等，如果有数据，会使用对应的客户端对象，利用该对象的读取流读取数据
            InputStream inputStream = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = inputStream.read(buff); // 读取来自客户端的数据
            System.out.println("来自客户端的数据 【" + new String(buff, 0, len, "utf-8") + "】");
            OutputStream outputStream = socket.getOutputStream(); // 也会使用对应的客户端对象，利用该对象的写入流写数据
            String message = "";
            outputStream.write(message.getBytes()); // 向客户端返回数据
            socket.close(); // 关闭 socket 连接
//        serverSocket.close(); // 关闭服务端
        }
    }
}

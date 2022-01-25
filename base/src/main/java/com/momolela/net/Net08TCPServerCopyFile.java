package com.momolela.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Net08TCPServerCopyFile {
    public static void main(String[] args) throws IOException {
        TCPServerCopyFile();
    }

    private static void TCPServerCopyFile() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10001);
        Socket socket = serverSocket.accept(); // 阻塞式的获取客户端传递的数据

        // 将客户端的数据写入到新的文件
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new FileWriter("server.txt"), true);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            out.println(line);
        }

        // 向客户端响应成功数据
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println("文件 copy 成功");

        // 关闭资源
        out.close();
        socket.close();
        serverSocket.close();
    }
}

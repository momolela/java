package com.momolela.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Net11TCPCustomServerForBrowser {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10002);
        Socket socket = serverSocket.accept();

        System.out.println("请求的ip：" + socket.getInetAddress().getHostAddress());

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("<div style='color:red;font-size:14px;'>hello</div>");
        socket.close();
        serverSocket.close();
    }
}

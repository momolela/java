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
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new FileWriter("server.txt"), true);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            out.println(line);
        }

        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println("上传成功");

        out.close();
        socket.close();
        serverSocket.close();
    }
}

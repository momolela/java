package com.momolela.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Net07TCPServerDemo {
    public static void main(String[] args) throws IOException {
        TCPServerDemo();
    }

    private static void TCPServerDemo() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        Socket socket = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            bufferedWriter.write(line.toUpperCase());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        socket.close();
        serverSocket.close();
    }
}

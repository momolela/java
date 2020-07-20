package com.momolela.net;

import java.io.*;
import java.net.Socket;

public class Net08TCPClientCopyFile {
    public static void main(String[] args) throws IOException {
        TCPClientCopyFile();
    }

    private static void TCPClientCopyFile() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("test.txt"));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            out.println(line);
        }

        socket.shutdownOutput();

        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = bufferedReader1.readLine();
        System.out.println(s);

        bufferedReader.close();
        socket.close();
    }
}

package com.momolela.net;

import java.io.*;
import java.net.Socket;

public class Net07TCPClientDemo {
    public static void main(String[] args) throws IOException {
        TCPClientDemo();
    }

    private static void TCPClientDemo() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001);
        // 建立 socket 相关流，和键盘输入流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        while ((line = bufferedIn.readLine()) != null) {
            if ("over".equals(line)) {
                break;
            }
            // 像服务端传递输入的文字
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            // 读取服务端传回的文字
            String s = bufferedReader.readLine();
            System.out.println("server：" + s);
        }
        bufferedIn.close();
        socket.close();
    }
}

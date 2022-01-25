package com.momolela.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Net10TCPClientLogin {
    public static void main(String[] args) throws IOException {
        TCPClientLogin();
    }

    private static void TCPClientLogin() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001);

        BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        for (int i = 0; i < 3; i++) {
            String line = bufferedIn.readLine();
            if (line == null) {
                break;
            }
            printWriter.println(line);

            String info = bufferedReader.readLine();
            System.out.println("info：" + info);
            if (info.contains("欢迎")) {
                break;
            }
        }

        bufferedIn.close();
        socket.close();
    }
}

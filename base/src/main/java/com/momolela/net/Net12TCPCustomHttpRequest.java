package com.momolela.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Net12TCPCustomHttpRequest {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.println("GET /myweb/index.html HTTP/1.1");
        out.println("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        out.println("Accept-Encoding: gzip, deflate, br");
        out.println("Accept-Language: zh-CN,zh;q=0.9,en;q=0.8");
        out.println("Connection: keep-alive");
        out.println("Host: 127.0.0.1:8080");
        out.println();
        out.println();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        socket.close();
    }

}

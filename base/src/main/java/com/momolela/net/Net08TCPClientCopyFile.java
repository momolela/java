package com.momolela.net;

import java.io.*;
import java.net.Socket;

public class Net08TCPClientCopyFile {
    public static void main(String[] args) throws IOException {
        TCPClientCopyFile();
    }

    private static void TCPClientCopyFile() throws IOException {
        // 创建客户端 socket
        Socket socket = new Socket("127.0.0.1", 10001);

        // 读取 test.txt 的文件，将内容写入到服务端
        BufferedReader bufferedReader = new BufferedReader(new FileReader("test.txt"));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // 用 PrintWriter ，支持自动刷入的字符流
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            out.println(line);
        }

        // 告诉服务端，我写完了，相当于一个结束标识，这个很重要，不然会卡住；
        // 当然也可以自己定义结束语，如 “over” ，但是如果文件里也有 over，就会提前结束
        socket.shutdownOutput();

        // 读取服务端的响应数据
        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = bufferedReader1.readLine();
        System.out.println(s);

        // 关闭资源
        bufferedReader.close();
        socket.close();
    }
}

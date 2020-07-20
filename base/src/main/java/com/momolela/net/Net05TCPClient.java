package com.momolela.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Net05TCPClient {
    public static void main(String[] args) throws IOException {
        TCPSocketWrite();
    }

    private static void TCPSocketWrite() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001); // client 联通了 server 后，会把 client 带入到 server，所以带入了输入输出流，不会串消息
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("服务端你好 ...".getBytes()); // 向服务端发送数据
        InputStream inputStream = socket.getInputStream();
        byte[] buff = new byte[1024];
        int len = inputStream.read(buff); // 读取服务端返回的数据
        System.out.println("来自服务端的数据 【" + new String(buff, 0, len) + "】");
        socket.close();
    }
}

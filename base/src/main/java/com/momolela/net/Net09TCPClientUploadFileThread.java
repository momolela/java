package com.momolela.net;

import java.io.*;
import java.net.Socket;

public class Net09TCPClientUploadFileThread {
    public static void main(String[] args) throws IOException {
        TCPClientUploadFileThread();
    }

    private static void TCPClientUploadFileThread() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001); // 创建客户端 socket ，指定要连的 ip 端口

        // 获取输出流，将图片写入到服务端
        OutputStream outputStream = socket.getOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("test.png"));
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = bufferedInputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, len);
        }

        // 告诉服务端，我写完了，相当于一个结束标识，这个很重要，不然会卡住
        socket.shutdownOutput();

        // 读取服务端的响应数据
        InputStream inputStream = socket.getInputStream();
        byte[] buffRead = new byte[1024];
        int read = inputStream.read(buffRead);
        System.out.println(new String(buffRead, 0, read));

        // 关闭资源
        socket.close();
        bufferedInputStream.close();
    }
}

package com.momolela.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收客户端上传文件的线程，支持多客户端并发上传
 */
class UploadThread implements Runnable {

    private Socket socket;

    public UploadThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int count = 1;
        try {
            // 创建文件，将客户端上传的文件保存
            File file = new File("test-upload(" + count + ").png");
            while (file.exists()) {
                file = new File("test-upload(" + (count++) + ").png");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, len);
            }

            // 向客户端响应数据
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("图片上传成功...".getBytes());

            // 关闭资源
            socket.close();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("图片上传失败...");
        }

    }
}

public class Net09TCPServerUploadFileThread {
    public static void main(String[] args) throws IOException {
        TCPServerUploadFileThread();
    }

    private static void TCPServerUploadFileThread() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10001);

        // 一直循环接收客户端的上传数据
        while (true) {
            Socket socket = serverSocket.accept(); // 因为这里 accept() 是阻塞的，不会因为 while(true) 导致崩溃
            new Thread(new UploadThread(socket)).start(); // 开启多线程，不至于阻塞，支持客户端并发上传
        }

//        serverSocket.close(); // 上面是循环读取，这里就没必要关闭资源了
    }
}

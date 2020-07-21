package com.momolela.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class UserLoginThread implements Runnable {
    private Socket socket;

    public UserLoginThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 3; i++) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader bufferedReaderUser = new BufferedReader(new FileReader("user.txt"));

                String name = bufferedReader.readLine();
                if (name == null) {
                    break;
                }

                String user = null;
                boolean flag = false;
                while ((user = bufferedReaderUser.readLine()) != null) {
                    if (user.equals(name)) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    System.out.println(name + "，已经登录");
                    printWriter.println(name + "，欢迎光临");
                    break;
                } else {
                    System.out.println(name + "，在尝试登录");
                    printWriter.println(name + "，用户不存在");
                }
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException("校验失败");
        }
    }
}

public class Net10TCPServerLogin {
    public static void main(String[] args) throws IOException {
        TCPServerLogin();
    }

    private static void TCPServerLogin() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10001);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new UserLoginThread(socket)).start();
        }
    }
}

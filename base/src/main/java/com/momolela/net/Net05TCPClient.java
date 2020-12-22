package com.momolela.net;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Net05TCPClient {
    public static void main(String[] args) throws Exception {
        TCPSocketWrite(new String[]{"100", "500", "10.8.2.162", "8393"});
    }

    private static void TCPSocketWrite(String[] args) {
        try {
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                TimeUnit.MILLISECONDS.sleep(Integer.parseInt(args[1]));
                Socket socket = new Socket();
                long s = System.currentTimeMillis();
                socket.connect(new InetSocketAddress(args[2], Integer.parseInt(args[3])));
                System.out.println(i + ":" + (System.currentTimeMillis() - s));
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(("服务端你好 ..." + i).getBytes());
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Socket socket = new Socket("127.0.0.1", 10001); // client 联通了 server 后，会把 client 带入到 server，所以带入了输入输出流，不会串消息
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write("服务端你好 ...".getBytes()); // 向服务端发送数据
//        InputStream inputStream = socket.getInputStream();
//        byte[] buff = new byte[10];
//        int len = inputStream.read(buff); // 读取服务端返回的数据
//        System.out.println("来自服务端的数据 【" + new String(buff, 0, len) + "】");
//        socket.close();


//        Socket socket = new Socket("10.8.2.56", 60023); // client 联通了 server 后，会把 client 带入到 server，所以带入了输入输出流，不会串消息
//        OutputStream outputStream = socket.getOutputStream();
//        String message = "POST /10.0.38.85:8399 HTTP/1.1\nContent-Type:application/as-xml;\nContent-Length:353\nConnection: Close\n\n<BSXml><MsgHeader><Organization>1</Organization><Sender>ECIS</Sender><ServiceType>service</ServiceType><MsgType>ODS_07010001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>4889</seq><method>phportGetWristbands</method><params><hospitalCode>cy01</hospitalCode><hospitalName>创业智慧医院 </hospitalName></params></as></MsgBody></BSXml>";
//        message = message.replaceAll("\\n", "\r\n");
//        outputStream.write(message.getBytes("GB2312")); // 向服务端发送数据
//        InputStream inputStream = socket.getInputStream();
//        byte[] buff = new byte[4096]; // 能读取多少数据取决于这个数组的长度
//        int len = inputStream.read(buff); // 读取服务端返回的数据
//        System.out.println("来自服务端的数据 【" + new String(buff, 0, len) + "】");
//        socket.close();


        // 使用 jdk 原生的 socket 方式调用
//        String message = "POST /10.0.38.85:8399 HTTP/1.1\nContent-Type:application/as-xml;\nContent-Length:353\nConnection: Close\n\n<BSXml><MsgHeader><Organization>1</Organization><Sender>ECIS</Sender><ServiceType>service</ServiceType><MsgType>ODS_07010001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>4889</seq><method>phportGetWristbands</method><params><hospitalCode>cy01</hospitalCode><hospitalName>创业智慧医院 </hospitalName></params></as></MsgBody></BSXml>";
//        message = message.replaceAll("\\n", "\r\n");
//        Socket socket = new Socket("10.8.2.56", 60023);

//        String message = "你好";
//        Socket socket = new Socket("127.0.0.1", 10001);
//        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write(message.getBytes("GB2312")); // 向服务端发送数据
//        // 改用缓冲区一行一行读取
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String line = null;
//        StringBuffer resultSB = new StringBuffer();
//        while ((line = bufferedReader.readLine()) != null) {
//            resultSB.append(line + "\r\n");
//        }
//        bufferedReader.close();
//        socket.close();
//        System.out.println(resultSB.toString());
    }
}

package com.momolela.base64ToFile;

import sun.misc.BASE64Decoder;

import java.io.*;

public class App {

    /**
     * @param args
     * args[0] base64 文件路径
     * args[1] 要输出文件的路径
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // args[0], eg: "D:/base64File.txt"
        File file = new File(args[0]);
        FileReader fr = new FileReader(file);
        // 缓冲区创建的时候，一定要有流，因为缓冲区就是对流的包装，提高效率
        BufferedReader bufferedReader = new BufferedReader(fr);
        // bufferedReader.readLine() 是一行一行的去读，当返回 null 的时候 说明文件读完了，其实 readLine() 是边读边存，读完一行整体返回
        StringBuffer sb = new StringBuffer();
        String base64CodeLine = null;
        while ((base64CodeLine = bufferedReader.readLine()) != null) {
            sb.append(base64CodeLine);
            if (System.getProperty("os.name").indexOf("Windows") !=-1) {
                sb.append("\r\n");
            } else {
                sb.append("\n");
            }
        }
        // 关闭缓冲区资源，也关闭了流资源
        bufferedReader.close();
        // args[1] eg: "D:\\hai-cnode-3.2.war"
        decoderBase64File(sb.toString(), args[1]);
    }

    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
}

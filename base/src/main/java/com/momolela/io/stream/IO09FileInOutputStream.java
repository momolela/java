package com.momolela.io.stream;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IO09FileInOutputStream {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("test.txt");
        /**
         * 字节流往文件中写入内容，不需要先写入内存缓冲区，所以也不用 flush ，就直接写入到文件中了
         * 字符流要先写入内存缓冲区，是因为做编码转换，而字节流不用
         */
        fileOutputStream.write("sunzjlhufy\r\nhaha".getBytes());
        fileOutputStream.close();

        /**
         * 第一种读取方式
         */
        FileInputStream fileInputStream1 = new FileInputStream("test.txt");
        int num1 = 0;
        while ((num1 = fileInputStream1.read()) != -1) {
            System.out.print((char) num1);
        }
        System.out.println("\r\n第一种读取方式结束");
        fileInputStream1.close();

        /**
         * 第二种读取方式
         */
        FileInputStream fileInputStream2 = new FileInputStream("test.txt");
        byte[] buf2 = new byte[1024];
        int num2 = 0;
        while ((num2 = fileInputStream2.read(buf2)) != -1) {
            System.out.println(new String(buf2, 0, num2));
        }
        System.out.println("第二种读取方式结束");
        fileInputStream2.close();

        /**
         * 第三种读取方式，如果文件大，内存可能会崩
         */
        FileInputStream fileInputStream3 = new FileInputStream("test.txt");
        byte[] buf3 = new byte[fileInputStream3.available()];
        fileInputStream3.read(buf3);
        System.out.println(new String(buf3));
        System.out.println("第三种读取方式结束");
        fileInputStream3.close();
    }

}

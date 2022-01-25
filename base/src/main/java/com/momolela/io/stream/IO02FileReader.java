package com.momolela.io.stream;

import java.io.FileReader;
import java.io.IOException;

public class IO02FileReader {
    public static void main(String[] args) throws IOException {
        /**
         * 一个字符一个字符挨个读取
         * reader.read(); 也是一个字符一个字符挨个读取，会一直读取，读取返回的是 int ，要转换成 char 显示
         * reader.read(); 返回-1的时候，说明读完了
         */
        FileReader reader = new FileReader("test.txt");
        int ch = 0;
        while ((ch = reader.read()) != -1) {
            System.out.print((char) ch);
        }
        reader.close();
        System.out.println("\r\n读取结束了");

        /**
         * 通过字符数组进行读取
         * reader1.read(buf) 是将字符先读取到字符数组中
         * 因为 buf 的长度为 1024 ，所以一次读取 1024 个字符填满字符数组
         * 知道返回 -1 ，读取结束
         */
        FileReader reader1 = new FileReader("test.txt");
        char[] buf = new char[1024];
        int num = 0;
        while ((num = reader1.read(buf)) != -1) {
            System.out.print(buf);
        }
        reader1.close();
        System.out.println("\r\n读取结束了");
    }
}

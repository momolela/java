package com.momolela.io.stream;

import java.io.*;

public class IO10ImgCopyByBufferedStream {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("test.png"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test-copy.png"));

        long s1 = System.currentTimeMillis();
        int num = 0;
        while ((num = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(num);
        }
        System.out.println("拷贝文件花费了：" + (System.currentTimeMillis() - s1) + " ms");

        bufferedOutputStream.close();
        bufferedInputStream.close();
    }
}

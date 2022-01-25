package com.momolela.io.stream;

import java.io.*;

public class IO11CustomBufferedInputStream {
    private byte[] buff = new byte[1024];
    int pos = 0, count = 0;
    private InputStream in;

    public IO11CustomBufferedInputStream(InputStream in) {
        this.in = in;
    }

    public int myRead() throws IOException {
        if (count == 0) {
            count = in.read(buff);
            if (count < 0) {
                return -1;
            }
            pos = 0;
            byte b = buff[pos];
            count--;
            pos++;
            return b & 255;
        } else if (count > 0) {
            byte b = buff[pos];
            count--;
            pos++;
            return b & 255;
        }
        return -1;
    }

    public void myClose() throws IOException {
        in.close();
    }

    public static void main(String[] args) throws IOException {
        IO11CustomBufferedInputStream customBufferedInputStream = new IO11CustomBufferedInputStream(new FileInputStream("test.png"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test-copy.png"));

        long s1 = System.currentTimeMillis();
        int read = 0;
        while ((read = customBufferedInputStream.myRead()) != -1) {
            bufferedOutputStream.write(read);
        }
        System.out.println("拷贝文件花费了：" + (System.currentTimeMillis() - s1) + " ms");

        bufferedOutputStream.close();
        customBufferedInputStream.myClose();
    }
}

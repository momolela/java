package com.momolela.io.stream;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道流不建议放在同一个线程，因为容易死锁
 */
public class IO19PipedStream {

    public static void main(String[] args) throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        Thread write = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始写入数据，等待6秒");
                    Thread.sleep(6000);
                    out.write("piped come ...".getBytes());
                    out.close();
                } catch (Exception e) {
                    throw new RuntimeException("管道输出流失败");
                }
            }
        });

        PipedInputStream in = new PipedInputStream();
        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buff = new byte[1024];
                    System.out.println("读取前，没有数据阻塞");
                    int len = in.read();
                    System.out.println("读取后，阻塞结束");
                    String s = new String(buff, 0, len);
                    System.out.println(s);
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("管道读取流失败");
                }
            }
        });

        in.connect(out);

        write.start();
        read.start();
    }
}

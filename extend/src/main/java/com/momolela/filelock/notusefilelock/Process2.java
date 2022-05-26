package com.momolela.filelock.notusefilelock;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Process2 {
    public static void main(String[] args) throws Exception {
        File file = new File("extend/src/main/java/com/momolela/filelock/file.lock");
        if (file.exists()) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
            FileChannel fileChannel = randomAccessFile.getChannel();
            System.out.println("进程 2 开始读文件的时间：" + LocalTime.now());
            for (int i = 0; i < 10; i++) {
                // 这里直接读文件的大小
                System.out.println("文件大小为：" + randomAccessFile.length());
                // 这里等待 1 秒
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("结束读文件的时间：" + LocalTime.now());
            fileChannel.close();
            randomAccessFile.close();
        }
    }
}

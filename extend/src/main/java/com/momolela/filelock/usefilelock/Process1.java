package com.momolela.filelock.usefilelock;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Process1 {
    public static void main(String[] args) throws Exception {
        File file = new File("extend/src/main/java/com/momolela/filelock/file.lock");
        if (file.exists()) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
            FileChannel fileChannel = randomAccessFile.getChannel();
            FileLock lock = fileChannel.lock();
            System.out.println("进程 1 开始写内容：" + LocalTime.now());
            for (int i = 1; i <= 10; i++) {
                randomAccessFile.writeChars("sunzj_" + i);
                // 等待两秒
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println("进程 1 完成写内容：" + LocalTime.now());
            fileChannel.close();
            randomAccessFile.close();
            lock.release();
        }
    }
}

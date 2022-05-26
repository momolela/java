package com.momolela.filelock.usefilelock;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Process2 {

    public static void main(String[] args) throws Exception {
        readNotUseLock();
    }

    /**
     * 注意查看打印时间，Process2 的打印时间是在 Process1 文件锁释放之后才打印的，说明文件锁确实是可以解决多进程访问同一个文件的并发安全问题
     */
    private static void readUseLock() throws Exception {
        File file = new File("extend/src/main/java/com/momolela/filelock/file.lock");
        if (file.exists()) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
            FileChannel fileChannel = randomAccessFile.getChannel();
            FileLock lock = fileChannel.lock();
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
            lock.release();
        }
    }

    /**
     * 注意查看打印时间，Process2 的打印时间是在 main 方法执行后就开始打印的，说明文件锁在 Process1 进程中开启，但是不妨碍其他进程进行读取，只是不能写入而已
     */
    private static void readNotUseLock() throws Exception {
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

package com.momolela.thread;

import java.util.concurrent.TimeUnit;

/**
 * interrupt 两阶段终止的实现
 * 开启一个线程，
 * 线程 while (true) 中先判断当前线程的打断的状态，如果打断了，料理后事，并跳出 while (true) 结束监控线程
 * 如果没有打断，线程 while (true) 中一直做：每隔 2s 监控一次 CPU 数据
 * 主线程中，先开启线程，隔 10s 后打断线程
 * 因为打断线程只是会到 catch (InterruptedException e) 中，当前线程的打断状态还是 false
 * 所以在 catch (InterruptedException e) 中，需要再次做当前线程的 interrupt() 调用，调用后当前线程的打断状态变为 true
 * 然后再次进入循环，由于 currentThread.isInterrupted() 为 true
 * 结果 break; 退出循环
 */
public class Thread17DoubleInterrupt {

    private static Thread monitor;

    public static void start() {
        monitor = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            while (true) {
                if (currentThread.isInterrupted()) {
                    System.out.println("料理后事...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("监控 CPU 数据中...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    currentThread.interrupt();
                }
            }
        }, "t1");
        monitor.start();
    }

    public static void stop() {
        monitor.interrupt();
    }

    public static void main(String[] args) throws Exception {
        start();
        TimeUnit.SECONDS.sleep(10);
        stop();
    }
}

package com.momolela.thread;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程的使用
 * 通过 setDaemon(true) 可以将线程设置为守护线程
 * 当所有其他在运行的线程都结束，守护线程会强制结束
 * JVM 中的垃圾回收器就是一个守护线程，自己的线程没有了，整个 JVM 就停止运行了
 * tomcat 中的 Acceptor 和 Poller 两个也是守护线程，分别负责请求的接收和分发
 */
public class Thread18DaemonThread {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 RUNNING");
            }
        }, "t1");
        // t1.setDaemon(true); // 如果 t1 设置为守护线程，主线程结束，t1 也会强制结束；如果 t1 不设置为守护线程，主线程结束，t1 还是会继续执行
        t1.start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("main stop");
    }
}

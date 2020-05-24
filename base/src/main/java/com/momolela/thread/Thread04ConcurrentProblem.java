package com.momolela.thread;

/**
 * 当多个线程要同时处理公共资源ticket，可能出现并发安全问题
 * 解决办法：对多条操作共享数据的语句，只能让一个线程都执行完，才能让其他线程执行；java提供了同步代码块
 */
class Thread04ConcurrentProblemDemo implements Runnable {
    private int ticket = 100;
    Object object = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(1000); // 当这里线程睡1s的时候，多个线程都在这里即将要同时处理公共资源ticket，可能出现并发安全问题
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " sale " + ticket--);
                } else {
                    break;
                }
            }

        }
    }
}

public class Thread04ConcurrentProblem {
    public static void main(String[] args) {
        Thread04ConcurrentProblemDemo thread04ConcurrentProblemDemo = new Thread04ConcurrentProblemDemo(); // 相同的资源
        // 相同资源分4个线程运行
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
    }
}

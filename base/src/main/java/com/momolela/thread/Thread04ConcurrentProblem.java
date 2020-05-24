package com.momolela.thread;

/**
 * 当多个线程要同时处理公共资源ticket，可能出现并发安全问题
 * 解决办法：对多条操作共享数据的语句，只能让一个线程都执行完，才能让其他线程执行；java提供了同步代码块
 * 对象如同锁，持有锁的线程可以在同步代码块中执行，没有持有锁的线程，即使有cpu的执行权，也不能在同步代码块中执行
 * 使用同步代码块的前提：1、必须有两个或者两个以上的线程；2、必须是多个线程使用同一个锁
 * 好处：解决了多线程中的安全问题
 * 弊端：多个线程都需要判断，消耗资源
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

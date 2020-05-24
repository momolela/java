package com.momolela.thread;

/**
 * 同步嵌套同步造成死锁，我需要获取你的对象锁，你需要获取我的对象锁
 */
class Thread05DeadLockDemo implements Runnable {
    boolean flag;

    public Thread05DeadLockDemo(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (MyLock.lockA) {
                System.out.println("if lockA");
                synchronized (MyLock.lockB) {
                    System.out.println("if lockB");
                }
            }
        } else {
            synchronized (MyLock.lockB) {
                System.out.println("else lockB");
                synchronized (MyLock.lockA) {
                    System.out.println("else lockA");
                }
            }
        }
    }
}

class MyLock {
    public static Object lockA = new Object();
    public static Object lockB = new Object();
}

public class Thread05DeadLock {
    public static void main(String[] args) {
        Thread05DeadLockDemo thread05DeadLockDemo1 = new Thread05DeadLockDemo(true);
        Thread05DeadLockDemo thread05DeadLockDemo2 = new Thread05DeadLockDemo(false);
        new Thread(thread05DeadLockDemo1).start();
        new Thread(thread05DeadLockDemo2).start();
    }
}

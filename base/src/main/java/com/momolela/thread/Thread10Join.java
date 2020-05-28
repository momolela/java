package com.momolela.thread;

class Thread10JoinDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " run " + i);
        }
    }
}

/**
 * 当线程A执行到了线程B的join()方法时，A就会等待，等线程B全执行完了，线程A才会执行
 * join()可以用来临时加入线程执行
 */
public class Thread10Join {
    public static void main(String[] args) {
        Thread10JoinDemo thread10JoinDemo = new Thread10JoinDemo();
        Thread t1 = new Thread(thread10JoinDemo);
        Thread t2 = new Thread(thread10JoinDemo);
        t1.start();
//        try {
//            t1.join(); // t1要主线程的CPU执行权，主线程交出执行权，等t1执行完，主线程才继续执行。这个状态只有t1（运行）和主线程（阻塞）
//        } catch (InterruptedException e) {
//            e.printStackTrace(); // 为了防止因为阻塞，导致线程挂了，提供了InterruptedException异常，只需要线程调用interrupt()即可
//        }
        t2.start();
        try {
            t1.join(); // t1要主线程的CPU执行权，主线程交出执行权，等t1执行完，主线程才继续执行。这个状态有t1（运行）和t2（运行）和主线程（阻塞）
        } catch (InterruptedException e) {
            e.printStackTrace(); // 为了防止因为阻塞，导致线程挂了，提供了InterruptedException异常，只需要线程调用interrupt()即可
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " run " + i);
        }
    }
}

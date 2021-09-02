package com.momolela.thread;

import java.util.concurrent.TimeUnit;

public class Thread15SixState {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> { // 线程创建了，没有 start ，所以是 NEW 状态
            System.out.println("running ...");
        }, "t1");

        Thread t2 = new Thread(() -> { // 线程一直在执行中，所以是 RUNNABLE 状态
            while (true) {

            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> { // 线程在主线程停 5s 后，会停止所以是 TERMINATED 状态
            System.out.println("t3 running ...");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> { // 一直在 sleep，所以是 TIMED_WAITING 状态
            synchronized (Thread15SixState.class) {
                try {
                    TimeUnit.SECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> { // 一直在等待 t2 线程执行完成，但是 t2 一直在运行，所以是 WAITING 状态
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> { // 一直在等待 Thread15SixState.class 锁，cpu 没有给执行权，所以是 BLOCKED 状态
            synchronized (Thread15SixState.class) {
                try {
                    TimeUnit.SECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t1 State " + t1.getState());
        System.out.println("t2 State " + t2.getState());
        System.out.println("t3 State " + t3.getState());
        System.out.println("t4 State " + t4.getState());
        System.out.println("t5 State " + t5.getState());
        System.out.println("t6 State " + t6.getState());
    }

}

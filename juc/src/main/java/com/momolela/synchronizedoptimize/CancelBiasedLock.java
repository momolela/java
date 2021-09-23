package com.momolela.synchronizedoptimize;

import org.openjdk.jol.info.ClassLayout;


/**
 * 撤销偏向锁
 */
public class CancelBiasedLock {
    static class Dog {

    }

    public static void main(String[] args) throws InterruptedException {
        Dog d = new Dog();
        new Thread(() -> {
            synchronized (d) {
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            synchronized (CancelBiasedLock.class) {
                CancelBiasedLock.class.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (CancelBiasedLock.class) {
                try {
                    CancelBiasedLock.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                System.out.println(ClassLayout.parseInstance(d).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(d).toPrintable());
        }, "t2").start();
    }
}

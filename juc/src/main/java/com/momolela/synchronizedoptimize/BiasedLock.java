package com.momolela.synchronizedoptimize;

import org.openjdk.jol.info.ClassLayout;

public class BiasedLock {
    static class A {
        int a = 0;
        boolean f = false;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000); // 这里为什么要延迟5s，因为偏向是会有延迟的
        HeavyWeightLock.A a = new HeavyWeightLock.A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable(a)); // 第一个打印：101 ，上面延迟 5s，已经默认进入了偏向锁的状态
        Thread t1 = new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " 进入偏向锁状态");
                System.out.println(ClassLayout.parseInstance(a).toPrintable(a)); // 第二个打印：101 ，上面延迟 5s，已经默认进入了偏向锁的状态
            }
        }, "t1");
        t1.start();
        t1.join(); // 保证 t1 线程执行结束后执行 main 线程，模拟交替运行，此时会发生锁升级，变成轻量锁

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100); // 保证 main 线程先获取 a 锁，然后当前线程 t2 再竞争 a 锁
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " 进入重量锁状态");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable(a)); // 第四个打印：010
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        t2.start();

        synchronized (a) {
            System.out.println(Thread.currentThread().getName() + " 进入轻量锁状态");
            System.out.println(ClassLayout.parseInstance(a).toPrintable(a)); // 第三个打印：000
            Thread.sleep(2000);
        }
    }
}

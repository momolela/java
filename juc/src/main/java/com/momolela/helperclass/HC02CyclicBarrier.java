package com.momolela.helperclass;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class HC02CyclicBarrier {
    public static void main(String[] args) {
        testWithRunnable();
    }

    /**
     * 初始化 CyclicBarrier 没有可选 Runnable 的时候
     */
    public static void testWithoutRunnable() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 2000));

                    int randomInt = new Random().nextInt(500);
                    System.out.println("hello " + randomInt);

                    cyclicBarrier.await();

                    System.out.println("world " + randomInt);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 初始化 CyclicBarrier 有可选 Runnable 的时候
     */
    public static void testWithRunnable() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("汇总1 ...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("汇总2 ...");
        });
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 2000));

                    int randomInt = new Random().nextInt(500);
                    System.out.println("hello " + randomInt);

                    cyclicBarrier.await();

                    System.out.println("world " + randomInt);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}

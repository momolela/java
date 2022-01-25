package com.momolela.thread;

import java.util.concurrent.TimeUnit;

public class Thread13Volatile {
    public static void main(String[] args) throws InterruptedException {
        F f = new F();

        Runnable run = () -> {
            int i = 0;
            while (i < 50) {
                i++;
                f.num = f.num + 1;
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ====== " + f.num);
            }
        };

        new Thread(run).start();
        new Thread(run).start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println(f.num);
    }
}

class F {
    public volatile Integer num = 0;
}

package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutor01Base {

    private static AtomicInteger threadNum = new AtomicInteger();

    private static ThreadPoolExecutor threadPool;

    public static void main(String[] args) {
        base();
    }

    public static void base() {
        threadPool = new ThreadPoolExecutor(0, 60, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "threadPool-" + threadNum.getAndIncrement());
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "-" + i);
                }
            }
        });
    }

}

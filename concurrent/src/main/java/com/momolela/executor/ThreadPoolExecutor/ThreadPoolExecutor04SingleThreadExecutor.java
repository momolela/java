package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SingleThreadExecutor适用于需要保证顺序地执行各个任务；
 * 并且在任意时间点，不会有多个线程是活动的应用场景
 * 创建使用单个线程
 * SingleThreadExecutor 被定以后，无法修改，做到了真正的 Single，不像 ExecutorService executorService = Executors.newFixedThreadPool(2); 能修改
 */
public class ThreadPoolExecutor04SingleThreadExecutor {
    public static void main(String[] args) {

        // return new FinalizableDelegatedExecutorService
        //             (new ThreadPoolExecutor(1, 1,
        //                                     0L, TimeUnit.MILLISECONDS,
        //                                     new LinkedBlockingQueue<Runnable>())); // 无界队列，会在队列一直等待
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

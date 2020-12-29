package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolExecutor 是 Executor 框架的核心实现类
 * 参数如下：
 * int corePoolSize：核心线程数
 * int maximumPoolSize：最大线程数
 * long keepAliveTime：线程池中线程的闲置最大时间
 * TimeUnit unit：线程池中线程的闲置最大时间的单位
 * BlockingQueue<Runnable> workQueue：任务队列
 * ThreadFactory threadFactory：创建线程的工具类，可以自定义线程名称
 * RejectedExecutionHandler handler：拒绝策略
 */
public class ThreadPoolExecutor01Base {

    private static AtomicInteger threadNum = new AtomicInteger();

    private static ThreadPoolExecutor threadPool;

    public static void main(String[] args) {
        base();
    }

    public static void base() {
        threadPool = new ThreadPoolExecutor(5, 60, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "threadPool-" + threadNum.getAndIncrement());
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 6; i++) {
            System.out.println("threadPool activeCount：" + threadPool.getActiveCount());
            System.out.println("threadPool poolSize：" + threadPool.getPoolSize());
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

}

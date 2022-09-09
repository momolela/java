package com.momolela.jdk8.completablefuture;

import java.util.concurrent.*;

public class CompletableFuture01CreateAsyncTask {
    public static void main(String[] args) throws Exception {
        // createAsyncTaskByFuture();
        // createAsyncTaskByCFSupplyAsync();
        // createAsyncTaskByCFRunAsync();
    }

    /**
     * supplyAsync 创建带返回值的异步任务
     * 相当于 ExecutorService submit(Callable<T> task) 方法
     * 可以指定执行异步任务的 Executor 实现，
     * 如果不指定，默认使用 ForkJoinPool.commonPool()，
     * 如果机器是单核的，则默认使用ThreadPerTaskExecutor，该类是一个内部类，每次执行execute都会创建一个新线程
     *
     * @throws Exception e
     */
    public static void createAsyncTaskByCFSupplyAsync() throws Exception {
        // ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务，有返回值
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    if (false) {
                        throw new RuntimeException("test");
                    } else {
                        System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                        return 1.2;
                    }
                }
                // , pool
        );
        System.out.println("main thread start,time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("run result->" + cf.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * 创建无返回值的异步任务
     * 相当于 ExecutorService submit(Runnable task) 方法
     * 可以指定执行异步任务的 Executor 实现，
     * 如果不指定，默认使用 ForkJoinPool.commonPool()，
     * 如果机器是单核的，则默认使用ThreadPerTaskExecutor，该类是一个内部类，每次执行execute都会创建一个新线程
     *
     * @throws Exception e
     */
    public static void createAsyncTaskByCFRunAsync() throws Exception {
        // ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 创建异步执行任务，无返回值
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
                    System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    if (false) {
                        throw new RuntimeException("test");
                    } else {
                        System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                        // return 1.2; // runAsync 中使用 return 会报错
                    }
                }
                // , executorService
        );
        System.out.println("main thread start,time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("run result->" + cf.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    public static void createAsyncTaskByFuture() throws Exception {
        // 创建异步执行任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Double> cf = executorService.submit(() -> {
            System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        System.out.println("main thread start,time->" + System.currentTimeMillis());
        // 等待子任务执行完成，如果已完成则直接返回结果
        // 如果 if (false) 改为 if (true) 执行任务异常，则 get 方法会把之前捕获的异常重新抛出
        // 很多博客说使用不带等待时间限制的 get 方法时，如果子线程执行异常了会导致主线程长期阻塞，这其实是错误的。
        // 子线程执行异常时其异常会被捕获，然后修改任务的状态为异常结束并唤醒等待的主线程，get 方法判断任务状态发生变更，就终止等待了，并抛出异常，可参考《Java8 AbstractExecutorService 和 FutureTask 源码解析》中 FutureTask 的实现。
        System.out.println("run result->" + cf.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }
}



package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool是一个会根据需要去创建新线程的线程池
 * 适用于执行很多的短期异步任务的小程序，或者是负载较轻的服务器
 */
public class ThreadPoolExecutor02CachedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(); // 内部就是 ThreadPoolExecutor
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("thread1:" + i);
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("thread2:" + i);
                }
            }
        });
        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("thread1 and thread2 is shutdown");
                break;
            }
        }
    }


}

package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool是一个会根据需要去创建新线程的线程池
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("thread1:" + i);
                }
            }
        });
        executorService.submit(new Runnable() {
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

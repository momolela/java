package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool 是一个会根据需要去创建新线程的线程池
 * 适用于执行很多的短期异步任务的小程序，或者是负载较轻的服务器
 * 适用场景：快速处理大量耗时较短的任务，如Netty的NIO接受请求时，可使用
 */
public class ThreadPoolExecutor02CachedThreadPool {

    public static void main(String[] args) {

        // 内部就是 ThreadPoolExecutor
        // return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        //                                       60L, TimeUnit.SECONDS,
        //                                       new SynchronousQueue<Runnable>()); // 也就是它将任务交给线程而不需要保留，没有线程会创建新的线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("thread1:" + i);
            }
        });
        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("thread2:" + i);
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

package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.*;
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
 * RejectedExecutionHandler handler：拒绝策略，当ThreadPoolExecutor已经关闭或ThreadPoolExecutor已经饱和时（达到了最大线程池大小且工作队列已满），execute()方法将要调用的Handler
 */
public class ThreadPoolExecutor01Base {

    private static AtomicInteger threadNum = new AtomicInteger();

    private static ThreadPoolExecutor threadPool;

    public static void main(String[] args) throws InterruptedException {
        base();
    }

    public static void base() throws InterruptedException {


        /**
         * 流程：
         * 1、创建线程
         * 2、如果创建的线程数 <= corePoolSize，就继续创建
         * 3、如果创建的线程数 > corePoolSize，就丢到 BlockingQueue
         * 4、BlockingQueue 满了，就继续创建线程执行
         * 5、如果创建的线程数 <= maximumPoolSize，就继续创建
         * 6、如果创建的线程数 > maximumPoolSize，进入拒绝策略
         * 7、没有任务执行，线程数在空闲 keepAliveTime 后，会自动释放线程
         */
        // corePoolSize 核心线程数，这个太大会导致等待任务中的线程资源太多
        // maximumPoolSize 最大线程数
        // keepAliveTime 线程空闲后的存活时间
        // TimeUnit 线程空闲后的存活时间单位
        // BlockingQueue 线程等待队列，这个太大会导致很多任务在队列中等待，也不能实现最高性能
        // ThreadFactory 线程创建工厂
        // rejectedExecutionHandler 拒绝策略
        threadPool = new ThreadPoolExecutor06CustomThreadPoolExecutor(5,
                190,
                5,
                TimeUnit.SECONDS,

                new ArrayBlockingQueue<>(10), // 数组有界队列，先进先出
                // new LinkedBlockingQueue<>(10), // 链表有界/无界队列 new LinkedBlockingQueue<>(),，先进先出，吞吐量比 ArrayBlockingQueue 高
                // new SynchronousQueue(false), // 一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于 LinkedBlockingQueue，也就是它将任务交给线程而不需要保留，没有线程会创建新的线程；Executors.newCachedThreadPool 内部就是用的它
                // new PriorityBlockingQueue<>(10), // 一个具有优先级的无限阻塞队列

                r -> {
                    Thread thread = new Thread(r, "threadPool-" + threadNum.getAndIncrement());
                    return thread;
                },

                new ThreadPoolExecutor.CallerRunsPolicy() // 交给主线程执行，不会丢失但是容易阻塞
                // new ThreadPoolExecutor.AbortPolicy() // 会拒绝执行，并且抛出异常（默认）
                // new ThreadPoolExecutor.DiscardOldestPolicy() // 丢弃队列里最近的一个任务，并执行当前任务，不会抛出异常
                // new ThreadPoolExecutor.DiscardPolicy() // 直接丢弃当前的线程，不会抛出异常
                // new CustomRejectedExecutionHandler // 自定义拒绝策略，可以先记录后补充
        );

        // threadPool.prestartCoreThread(); // 预先开启一个
        // threadPool.prestartAllCoreThreads(); // 预先开启所有的核心线程
        // threadPool.setCorePoolSize(5);
        // threadPool.setMaximumPoolSize(190);
        // threadPool.setKeepAliveTime(5, TimeUnit.SECONDS);
        // threadPool.getQueue();
        // threadPool.remove(Runnable);
        // threadPool.purge();

        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            System.out.println("main thread start - " + i + " - " + System.currentTimeMillis());

            // execute 只能提交 Runnable，没有返回值；执行任务时遇到异常会直接抛出；
            // submit 可以提交 Runnable，没有返回值；也可以提交 Callable，返回 Future；执行任务遇到异常不会抛出，只有使用 Future.get() 获取返回值时，才会抛出异常
            threadPool.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            });
            // threadPool.submit(Runnable | Callable);

            System.out.println("main thread end - " + i + " - " + System.currentTimeMillis());
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println(System.currentTimeMillis() - start);

    }


    /**
     * 自定义的拒绝策略
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            new Thread(r).start();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

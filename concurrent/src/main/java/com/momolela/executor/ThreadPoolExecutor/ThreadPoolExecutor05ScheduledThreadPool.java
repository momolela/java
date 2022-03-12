package com.momolela.executor.ThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor05ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService services = Executors.newScheduledThreadPool(1);

        // scheduleWithFixedDelay
        // 是以上一个任务结束时开始计时，5秒过去后，立即执行
        // 这里会间隔 10 + 5 秒 打印
        services.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 10, 5, TimeUnit.SECONDS);

        // scheduleAtFixedRate
        // 是以上一个任务开始的时间计时，5秒过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行
        // 这里会间隔 10 秒
        // services.scheduleAtFixedRate(() -> {
        //     System.out.println(Thread.currentThread().getName());
        //     try {
        //         TimeUnit.SECONDS.sleep(10);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }, 10, 5, TimeUnit.SECONDS);

        // schedule
        // 特定时间延时后执行一次 Runnable ｜ Callable
        // services.schedule(() -> {}, 5, TimeUnit.SECONDS);
    }

}

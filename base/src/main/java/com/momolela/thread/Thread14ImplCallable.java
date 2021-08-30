package com.momolela.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 配合 Callable 的方式创建线程
 * 可以返回结果
 * 可以抛出异常
 * 通过 .get() 可以阻塞获取线程结果
 */
public class Thread14ImplCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // lambda 的写法
        // FutureTask futureTask = new FutureTask(
        //         (Callable<String>) () -> "Callable return result"
        // );
        FutureTask futureTask = new FutureTask<>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Callable return result";
                    }
                }
        );
        new Thread(futureTask, "CallableThread").start();

        // Callable 在外部类的写法
        CallableOut callableOut = new CallableOut();
        FutureTask<String> futureTaskOut = new FutureTask<String>(callableOut);
        new Thread(futureTaskOut, "CallableThreadOut").start();

        // 通过 .get() 可以阻塞获取线程结果
        String result = (String) futureTask.get();
        System.out.println(result);
        // 通过 .get() 可以阻塞获取线程结果
        String resultOut = (String) futureTaskOut.get();
        System.out.println(resultOut);
    }
}


class CallableOut implements Callable {

    @Override
    public String call() throws Exception {
        return "Callable return result out";
    }
}

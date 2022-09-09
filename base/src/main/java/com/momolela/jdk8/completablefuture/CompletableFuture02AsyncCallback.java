package com.momolela.jdk8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class CompletableFuture02AsyncCallback {
    public static void main(String[] args) throws Exception {
        // asyncCallbackByThenApplyOrAsync();
        // asyncCallbackByThenAcceptOrRun();
        // asyncCallbackByExceptionally();
        // asyncCallbackByWhenComplete();
        // asyncCallbackByHandle();
    }

    /**
     * thenApply 和 thenApplyAsync 实现异步回调
     * thenApply 表示某个任务执行完成后执行的动作，即回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中
     * thenApply 是由执行 job1 的线程立即执行 job2，即两个 job 都是同一个线程执行的
     * thenApplyAsync 是将 job2 提交到线程池中异步执行，实际执行 job2 的线程可能是另外一个线程
     * thenApply 和 thenApplyAsync 都有返回值
     *
     * @throws Exception e
     */
    public static void asyncCallbackByThenApplyOrAsync() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        // cf 关联的异步任务的返回值作为方法入参，传入到 thenApply 的方法中
        // thenApply 这里实际创建了一个新的 CompletableFuture 实例
        CompletableFuture<String> cf2 = cf.thenApply((result) -> {
            // CompletableFuture<String> cf2 = cf.thenApplyAsync((result) -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return "test:" + result;
        });
        System.out.println("main thread start cf.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("run result->" + cf.get());
        System.out.println("main thread start cf2.get(),time->" + System.currentTimeMillis());
        System.out.println("run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * thenAccept 同 thenApply 接收上一个任务的返回值作为参数，但是无返回值；
     * thenRun 的方法没有入参，也没有返回值
     *
     * @throws Exception e
     */
    public static void asyncCallbackByThenAcceptOrRun() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        // cf 关联的异步任务的返回值作为方法入参，传入到 thenApply 的方法中
        CompletableFuture cf2 = cf.thenApply((result) -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return "test:" + result;
        }).thenAccept((result) -> { // 接收上一个任务的执行结果作为入参，但是没有返回值
            System.out.println(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(result);
            System.out.println(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
        }).thenRun(() -> { // 无入参，也没有返回值
            System.out.println(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("thenRun do something");
            System.out.println(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });
        System.out.println("main thread start cf.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("run result->" + cf.get());
        System.out.println("main thread start cf2.get(),time->" + System.currentTimeMillis());
        // cf2 等待最后一个 thenRun 执行完成
        System.out.println("run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * exceptionally 执行异常时，将抛出的异常作为入参传递给回调方法
     * exceptionally 执行正常时，回调方法接收的是上个任务的正常返回结果
     *
     * @throws Exception e
     */
    public static void asyncCallbackByExceptionally() throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) { // 这里的 true 可以改为 false 然后执行查看结果
                throw new RuntimeException("test");
            } else {
                System.out.println(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        }, pool);
        // cf 执行异常时，将抛出的异常作为入参传递给回调方法
        CompletableFuture<Double> cf2 = cf.exceptionally((param) -> {
            System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("error stack trace->");
            param.printStackTrace();
            System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            return -1.1;
        });
        // cf 正常执行时执行的逻辑，如果执行异常则不调用此逻辑
        CompletableFuture cf3 = cf.thenAccept((param) -> {
            System.out.println(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("param->" + param);
            System.out.println(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
        });
        System.out.println("main thread start,time->" + System.currentTimeMillis());
        // 等待子任务执行完成,此处无论是 job2 和 job3 都可以实现 job2 退出，主线程才退出，如果是 cf，则主线程不会等待 job2 执行完成自动退出了
        // cf2.get 时，没有异常，但是依然有返回值，就是 cf 的返回值
        // cf2.get 时，没有异常，是等待 cf3 执行完成后才返回的
        System.out.println("run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * whenComplete 是当某个任务执行完成后执行的回调方法，会将执行结果或者执行期间抛出的异常传递给回调方法，
     * 如果是正常执行则异常为 null，回调方法对应的 CompletableFuture 的 result 和该任务一致，
     * 如果该任务正常执行，则 get 方法返回执行结果，
     * 如果是执行异常，则 get 方法抛出异常。
     *
     * @throws Exception e
     */
    public static void asyncCallbackByWhenComplete() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (false) {
                throw new RuntimeException("test");
            } else {
                System.out.println(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        // cf 执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为 null
        CompletableFuture<Double> cf2 = cf.whenComplete((a, b) -> {
            System.out.println(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (b != null) {
                System.out.println("error stack trace->");
                b.printStackTrace();
            } else {
                System.out.println("run success,result->" + a);
            }
            System.out.println(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
        });
        // 等待子任务执行完成
        System.out.println("main thread start wait,time->" + System.currentTimeMillis());
        // 如果 cf 是正常执行的，cf2.get 的结果就是 cf 执行的结果
        // 如果 cf 是执行异常，则 cf2.get 会抛出异常
        System.out.println("run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * 跟 whenComplete 基本一致，区别在于 handle 的回调方法有返回值，
     * 且 handle 方法返回的 CompletableFuture 的 result 是回调方法的执行结果或者回调方法执行期间抛出的异常，与原始 CompletableFuture 的 result 无关了。
     *
     * @throws Exception e
     */
    public static void asyncCallbackByHandle() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + "job1 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                System.out.println(Thread.currentThread() + "job1 exit,time->" + System.currentTimeMillis());
                return 1.2;
            }
        });
        // cf 执行完成后会将执行结果和执行过程中抛出的异常传入回调方法，如果是正常执行的则传入的异常为 null
        CompletableFuture<String> cf2 = cf.handle((a, b) -> {
            System.out.println(Thread.currentThread() + "job2 start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (b != null) {
                System.out.println("error stack trace->");
                b.printStackTrace();
            } else {
                System.out.println("run succ,result->" + a);
            }
            System.out.println(Thread.currentThread() + "job2 exit,time->" + System.currentTimeMillis());
            if (b != null) {
                return "run error";
            } else {
                return "run success";
            }
        });
        // 等待子任务执行完成
        System.out.println("main thread start wait,time->" + System.currentTimeMillis());
        // get 的结果是 cf2 的返回值，跟 cf 没关系了
        System.out.println("run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }
}

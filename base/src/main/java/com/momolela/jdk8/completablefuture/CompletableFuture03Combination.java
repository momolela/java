package com.momolela.jdk8.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture03Combination {
    public static void main(String[] args) throws Exception {
        // combinationByThenCombineOrThenAcceptBothOrRunAfterBoth();
        // combinationByApplyToEitherOrAcceptEitherOrRunAfterEither();
        // combinationByThenCompose();
        // combinationByAllOfOrAnyOf();
    }

    /**
     * 这三个方法都是将两个 CompletableFuture 组合起来，只有这两个都正常执行完了才会执行某个任务，
     * 区别在于，thenCombine 会将两个任务的执行结果作为方法入参传递到指定方法中，且该方法有返回值；
     * thenAcceptBoth 同样将两个任务的执行结果作为方法入参，但是无返回值；
     * runAfterBoth 没有入参，也没有返回值。
     * 注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。测试用例如下
     * 运行解析如下：
     * job1 和 job2 几乎同时运行，
     * job2 比 job1 先执行完成，等 job1 退出后，
     * job3 和 job4 几乎同时开始运行，job4 先退出，等 job3 执行完成，
     * job5 开始了，等 job5 执行完成后，主线程退出
     *
     * @throws Exception e
     */
    public static void combinationByThenCombineOrThenAcceptBothOrRunAfterBoth() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        // cf 和 cf2 的异步任务都执行完成后，会将其执行结果作为方法入参传递给 cf3，且有返回值
        CompletableFuture<Double> cf3 = cf.thenCombine(cf2, (a, b) -> {
            System.out.println(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            System.out.println("job3 param a->" + a + ",b->" + b);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return a + b;
        });

        // cf 和 cf2 的异步任务都执行完成后，会将其执行结果作为方法入参传递给 cf3，无返回值
        CompletableFuture cf4 = cf.thenAcceptBoth(cf2, (a, b) -> {
            System.out.println(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            System.out.println("job4 param a->" + a + ",b->" + b);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });

        // cf4 和 cf3 都执行完成后，执行 cf5，无入参，无返回值
        CompletableFuture cf5 = cf4.runAfterBoth(cf3, () -> {
            System.out.println(Thread.currentThread() + " start job5,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("cf5 do something");
            System.out.println(Thread.currentThread() + " exit job5,time->" + System.currentTimeMillis());
        });

        System.out.println("main thread start cf.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("cf run result->" + cf.get());
        System.out.println("main thread start cf5.get(),time->" + System.currentTimeMillis());
        System.out.println("cf5 run result->" + cf5.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * 这三个方法都是将两个 CompletableFuture 组合起来，只要其中一个执行完了就会执行某个任务，
     * 其区别在于 applyToEither 会将已经执行完成的任务的执行结果作为方法入参，并有返回值；
     * acceptEither 同样将已经执行完成的任务的执行结果作为方法入参，但是没有返回值；
     * runAfterEither 没有方法入参，也没有返回值。
     * 注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     * 运行解析如下：
     * job1 和 job2 同时开始运行，job2 先执行完成，然后 job4 开始执行，
     * 理论上 job3 和 job4 应该同时开始运行，但是此时只有 job4 开始执行了，job3 是等待 job1 执行完成后才开始执行，job4 先于 job3 执行完成，
     * 然后 job5 开始执行，等 job5 执行完成后，主线程退出
     *
     * @throws Exception e
     */
    public static void combinationByApplyToEitherOrAcceptEitherOrRunAfterEither() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        // cf 和 cf2 的异步任务都执行完成后，会将其执行结果作为方法入参传递给 cf3，且有返回值
        CompletableFuture<Double> cf3 = cf.applyToEither(cf2, (result) -> {
            System.out.println(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            System.out.println("job3 param result->" + result);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return result;
        });

        // cf 和 cf2 的异步任务都执行完成后，会将其执行结果作为方法入参传递给 cf3,无返回值
        CompletableFuture cf4 = cf.acceptEither(cf2, (result) -> {
            System.out.println(Thread.currentThread() + " start job4,time->" + System.currentTimeMillis());
            System.out.println("job4 param result->" + result);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job4,time->" + System.currentTimeMillis());
        });

        // cf4 和 cf3 都执行完成后，执行 cf5，无入参，无返回值
        CompletableFuture cf5 = cf4.runAfterEither(cf3, () -> {
            System.out.println(Thread.currentThread() + " start job5,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("cf5 do something");
            System.out.println(Thread.currentThread() + " exit job5,time->" + System.currentTimeMillis());
        });

        System.out.println("main thread start cf.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("cf run result->" + cf.get());
        System.out.println("main thread start cf5.get(),time->" + System.currentTimeMillis());
        System.out.println("cf5 run result->" + cf5.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * thenCompose 方法会在某个任务执行完成后，将该任务的执行结果作为方法入参然后执行指定的方法，
     * 该方法会返回一个新的 CompletableFuture 实例，
     * 如果该 CompletableFuture 实例的 result 不为 null，则返回一个基于该 result 的新的 CompletableFuture 实例；
     * 如果该 CompletableFuture 实例为 null，则然后执行这个新任务
     * 运行结果解析：
     * job1 执行完成后 job2 开始执行，等 job2 执行完成后会把 job3 返回，然后执行 job3，等 job3 执行完成后，主线程退出。
     *
     * @throws Exception e
     */
    public static void combinationByThenCompose() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<String> cf2 = cf.thenCompose((param) -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
                return "job3 test";
            });
        });
        System.out.println("main thread start cf.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("cf run result->" + cf.get());
        System.out.println("main thread start cf2.get(),time->" + System.currentTimeMillis());
        System.out.println("cf2 run result->" + cf2.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }

    /**
     * allOf 返回的 CompletableFuture 是多个任务都执行完成后才会执行，只有有一个任务执行异常，则返回的 CompletableFuture 执行 get 方法时会抛出异常，如果都是正常执行，则 get 返回 null。
     * anyOf 返回的 CompletableFuture 是多个任务只要其中一个执行完成就会执行，其 get 返回的是已经执行完成的任务的执行结果，如果该任务执行异常，则抛出异常
     *
     * @throws Exception e
     */
    public static void combinationByAllOfOrAnyOf() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job1,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job1,time->" + System.currentTimeMillis());
            return 1.2;
        });
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job2,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit job2,time->" + System.currentTimeMillis());
            return 3.2;
        });
        CompletableFuture<Double> cf3 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start job3,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
            }
            // throw new RuntimeException("test");
            System.out.println(Thread.currentThread() + " exit job3,time->" + System.currentTimeMillis());
            return 2.2;
        });
        // allOf 等待所有任务执行完成才执行 cf4，如果有一个任务异常终止，则 cf4.get 时会抛出异常，都是正常执行，cf4.get 返回 null
        // anyOf 是只有一个任务执行完成，无论是正常执行或者执行异常，都会执行 cf4，cf4.get 的结果就是已执行完成的任务的执行结果
        CompletableFuture cf4 = CompletableFuture.allOf(cf, cf2, cf3).whenComplete((a, b) -> {
            if (b != null) {
                System.out.println("error stack trace->");
                b.printStackTrace();
            } else {
                System.out.println("run succ,result->" + a);
            }
        });

        System.out.println("main thread start cf4.get(),time->" + System.currentTimeMillis());
        // 等待子任务执行完成
        System.out.println("cf4 run result->" + cf4.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());
    }
}

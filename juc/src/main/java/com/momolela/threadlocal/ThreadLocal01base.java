package com.momolela.threadlocal;

public class ThreadLocal01base {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 输出如下：
     * A : Thread_A
     * after remove : null
     * B : Thread_B
     * after remove : null
     *
     * 从这个示例中我们可以看到，两个线程分别获取了自己线程存放的变量，他们之间变量的获取并不会错乱
     */
    public static void main(String[] args) {
        new Thread(() -> {
            ThreadLocal01base.threadLocal.set("Thread_A");
            print("A");
            System.out.println("after remove : " + threadLocal.get());
        }, "A").start();

        new Thread(() -> {
            ThreadLocal01base.threadLocal.set("Thread_B");
            print("B");
            System.out.println("after remove : " + threadLocal.get());
        }, "B").start();
    }

    private static void print(String str) {
        System.out.println(str + " : " + threadLocal.get());
        threadLocal.remove();
    }
}

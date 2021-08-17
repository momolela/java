package com.momolela.jdk8.lambda;


/**
 * lambda 表达式创建线程
 *
 * 我们以往都是通过创建 Thread 对象，然后通过匿名内部类重写 run() 方法，一提到匿名内部类我们就应该想到可以使用 lambda 表达式来简化线程的创建过程。
 */
public class Lambda04UseCase3 {
    public static void main(String[] args) {


        // 这里面并不需要显式地把它转成一个 Runnable 参数，因为 Java 能根据上下文自动推断出来：
        // 一个 Thread 的构造函数接受一个 Runnable 参数，而传入的 lambda 表达式正好符合其 run() 函数，所以 Java 编译器推断它为 Runnable。
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(2 + ":" + i);
            }
        });
        t.start();
    }
}

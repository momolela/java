package com.momolela.thread;

import java.util.ArrayList;

/**
 * 当多个线程要同时处理公共资源 ticket，可能出现并发安全问题
 * 解决办法：对多条操作共享数据的语句，只能让一个线程都执行完，才能让其他线程执行；java 提供了同步代码块
 * 对象如同锁，持有锁的线程可以在同步代码块中执行，没有持有锁的线程，即使有 cpu 的执行权，也不能在同步代码块中执行
 * 使用同步代码块的前提：1、必须有两个或者两个以上的线程；2、必须是多个线程使用同一个锁
 * 好处：解决了多线程中的安全问题
 * 弊端：多个线程都需要判断，消耗资源
 */
class Thread04ConcurrentProblemDemo implements Runnable {
    private int ticket = 100;
    Object object = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(1000); // 当这里线程睡1s的时候，多个线程都在这里即将要同时处理公共资源ticket，可能出现并发安全问题
                        // TimeUnit.SECONDS.sleep(1); // JUC 里面的线程等待，易读
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " sale " + ticket--);
                } else {
                    break;
                }
            }

        }
    }
}

class Room {
    int count = 0;

    // 静态函数上加上 synchronized 相当于锁住的是类对象
    // public synchronized static void test() {
    //     System.out.println("test");
    // }
    // public static void test() {
    //     synchronized (Room.class) {
    //         System.out.println("test");
    //     }
    // }

    // 相当于这样写，也是锁的 this 对象
    // public synchronized void increment() {
    //     count++;
    // }
    public void increment() {
        synchronized (this) {
            count++; // 成员变量，被共享了，且有被修改操作，所以要考虑线程安全问题，局部变量一般不会出现线程安全问题
        }
    }

    public void decrement() {
        synchronized (this) {
            count--; // 成员变量，被共享了，且有被修改操作，所以要考虑线程安全问题，局部变量一般不会出现线程安全问题
        }
    }

    public int getCount() {
        synchronized (this) {
            return count;
        }
    }
}

class ThreadSafe {
    public void method1(int loopNumber) {
        // 这里作为局部变量，调用 method2，method3 也是线程安全的
        // 如果 method2，method3 用 public 修饰，也是线程安全的，因为外面进来的 list 也不是 method1 中的局部变量，要线程安全问题，必须是共享资源
        // 如果被 public 修饰了，说明可以被子类重写了，如 ThreadSafeSubClass 中的 method3，method1 中调用method3 导致 list 又被共享了，这样就会导致线程安全问题
        // 所以修饰符是有效可以控制线程安全的，还有 final，也是开闭原则的闭
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    // private void method2(ArrayList<String> list) {
    //     list.add("0");
    // }
    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    // private void method3(ArrayList<String> list) {
    //     list.remove(0);
    // }
    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadSafe {
    @Override
    public void method3(ArrayList<String> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}

public class Thread04ConcurrentProblem {
    public static void main(String[] args) throws InterruptedException {
        // synchronized 简单使用
        // synchronizedUse();

        // synchronized 面向对象优化
        // synchronizedOptimize();

        // 局部变量一般情况下是线程安全的，但局部变量引用的对象则未必，如果该对象逃离方法的作用范围，则要考虑线程安全问题
        // 偶尔会抛出数组越界异常
        localVariableProblem();
    }

    private static void synchronizedUse() {
        Thread04ConcurrentProblemDemo thread04ConcurrentProblemDemo = new Thread04ConcurrentProblemDemo(); // 相同的资源
        // 相同资源分4个线程运行
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
        new Thread(thread04ConcurrentProblemDemo).start();
    }

    private static void synchronizedOptimize() throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(room.getCount()); // 0
    }

    private static void localVariableProblem() {
        ThreadSafeSubClass threadSafeSubClass = new ThreadSafeSubClass();

        new Thread(() -> {
            threadSafeSubClass.method1(200);
        }, "t1").start();

        new Thread(() -> {
            threadSafeSubClass.method1(200);
        }, "t2").start();
    }
}

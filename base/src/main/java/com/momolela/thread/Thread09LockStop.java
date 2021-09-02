package com.momolela.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程里面是个循环，通过控制 flag 来控制停止线程
 */
class Thread09LockStopDemo implements Runnable {

    private boolean flag = true;

    @Override
    public synchronized void run() {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + " run");

            // 第一种退出循环的方式
            // if (Thread.currentThread().isInterrupted()) {
            //     System.out.println(Thread.currentThread().getName() + " 线程运行出现了异常，肯定执行了interrupt()");
            //     this.changeFlag();
            // }

            // 第二种退出循环的方式
            try {
                wait(); // 当线程处于冻结状态，就不会再执行 while(flag)，就读不到标记，就结束不了，所以外部简单的调用 changeFlag() 可能无效，要使用 interrupt()
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 线程运行出现了异常，肯定执行了interrupt()");
                this.changeFlag();
            }

        }
    }

    void changeFlag() {
        flag = false;
    }
}

public class Thread09LockStop {
    public static void main(String[] args) {
        // interrupt() 可以打断 wait、sleep
        // interruptThread();

        // interrupt() 还可以打断 LockSupport.park();
        interruptPark();
    }

    public static void interruptThread() {
        Thread09LockStopDemo thread09LockStopDemo1 = new Thread09LockStopDemo();
        Thread09LockStopDemo thread09LockStopDemo2 = new Thread09LockStopDemo();
        Thread t1 = new Thread(thread09LockStopDemo1, "thread1");
        Thread t2 = new Thread(thread09LockStopDemo2, "thread2");
        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // thread09LockStopDemo.changeFlag();
        t1.interrupt(); // 线程通过wait()，join()，sleep()都会进入冻结状态，interrupt()可以是线程复活，但是会出现InterruptedException异常
        t2.interrupt(); // 线程通过wait()，join()，sleep()都会进入冻结状态，interrupt()可以是线程复活，但是会出现InterruptedException异常
    }

    public static void interruptPark() {
        Thread myThread = new Thread(() -> {
            System.out.println("park...");
            LockSupport.park(); // 这里执行后会阻塞
            System.out.println("unPark");
            System.out.println("打断状态为：" + Thread.currentThread().isInterrupted());
            // 这里再调用 park() 是不起作用的，因为上面已经执行过了 LockSupport.park();
            LockSupport.park();
            System.out.println("unPark");
        }, "myThread");

        myThread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.interrupt();
    }
}

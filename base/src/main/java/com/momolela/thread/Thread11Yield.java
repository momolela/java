package com.momolela.thread;

class Thread11YieldDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().toString() + " run " + i); // Thread.currentThread().toString() 打印的是[线程名，线程优先级，线程所属组]
            Thread.yield(); // 当前线程的临时降级，优先执行其他线程，这里调用这个方法可以使得线程均匀交互的执行
        }
    }
}

/**
 * Thread.currentThread().toString() 打印的是[线程名，线程优先级，线程所属组]
 * 线程优先级：可以通过setPriority()设置，1：Thread.MIN_PRIORITY，5：Thread.NORM_PRIORITY，10：Thread.MAX_PRIORITY
 * 线程所属组：一般谁开启的线程，线程就属于哪个组，也可以通过new ThreadGroup()创建一个线程组，然后把线程设置进去
 */
public class Thread11Yield {
    public static void main(String[] args) {
        Thread11YieldDemo thread11YieldDemo = new Thread11YieldDemo();
        Thread t1 = new Thread(thread11YieldDemo);
        t1.setPriority(Thread.MAX_PRIORITY);
        Thread t2 = new Thread(thread11YieldDemo);
        t1.start();
        t2.start();
    }
}


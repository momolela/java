package com.momolela.thread;

/**
 * 实现 Runnable 接口
 * 重写 run 方法
 * new 接口子对象实例，其实就是确定 run
 * new Thread，将接口子对象实例放入到 Thread
 * 执行 Thread 的 start
 *
 * 相对于继承 Thread 的方式，实现 Runnable 接口定义的对象，可以作为相同的资源对象传给 new Thread()
 */
class Thread03ImplRunnableDemo implements Runnable {
    private int ticket = 100;

    @Override
    public void run() { // 不能向外抛出异常，因为接口 Runnable 类中 run 没有抛出异常
        while (true) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " sale " + ticket--);
            } else {
                break;
            }
        }
    }
}

public class Thread03ImplRunnable {
    public static void main(String[] args) {
        Thread03ImplRunnableDemo thread03ImplRunnableDemo = new Thread03ImplRunnableDemo(); // 相同的资源
        // 相同资源分4个线程运行
        new Thread(thread03ImplRunnableDemo).start();
        new Thread(thread03ImplRunnableDemo).start();
        new Thread(thread03ImplRunnableDemo).start();
        new Thread(thread03ImplRunnableDemo).start();
    }
}

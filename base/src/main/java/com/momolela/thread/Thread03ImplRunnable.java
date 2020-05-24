package com.momolela.thread;

/**
 * 实现Runnable接口
 * 实现run方法
 * new接口子对象实例，其实就是确定run
 * new Thread，将接口子对象实例放入到Thread
 * 执行Thread的start
 *
 * 相对于继承Thread的方式，实现Runnable接口定义的对象，可以作为相同的资源对象传给new Thread()
 */
class Thread03ImplRunnableDemo implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            if (ticket > 0)
                System.out.println(Thread.currentThread().getName() + " sale " + ticket--);
            else
                break;
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

package com.momolela.thread;

/**
 * 实现Runnable接口
 * 实现run方法
 * new接口子对象实例，其实就是确定run
 * new Thread，将接口子对象实例放入到Thread
 * 执行Thread的start
 */
class Thread03ImplRunnableDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("thread run " + i);
        }
    }
}

public class Thread03ImplRunnable {
    public static void main(String[] args) {
        Thread03ImplRunnableDemo thread03ImplRunnableDemo = new Thread03ImplRunnableDemo();
        new Thread(thread03ImplRunnableDemo).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main run " + i);
        }
    }
}

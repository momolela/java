package com.momolela.thread;

/**
 * 继承Thread
 * 重写run方法
 * new一个线程对象
 * 调用线程对象的start方法
 */
class Thread01ExtendsThreadDemo extends Thread {
    @Override
    public void run() { // 不能向外抛出异常，因为继承的 Thread 类中 run 没有抛出异常
        // 真正要执行的代码
        for (int i = 0; i < 1000; i++) {
            System.out.println("thread run " + i);
        }
    }
}

public class Thread01ExtendsThread {
    public static void main(String[] args) {
        Thread01ExtendsThreadDemo thread01ExtendsThreadDemo = new Thread01ExtendsThreadDemo();
        // thread01ExtendsThreadDemo.run(); // 不开启线程，只执行run方法
        thread01ExtendsThreadDemo.start(); // 开启线程，并且执行run方法

        for (int i = 0; i < 1000; i++) {
            System.out.println("main run " + i);
        }
    }
}

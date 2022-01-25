package com.momolela.thread;

class Thread02ExtendsThreadDemo extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + " run " + i); // 通过Thread.currentThread()获取当前的线程对象  和  this一样
        }
    }
}

public class Thread02ThreadObject {
    public static void main(String[] args) {
        Thread02ExtendsThreadDemo thread02ExtendsThreadDemo1 = new Thread02ExtendsThreadDemo();
        thread02ExtendsThreadDemo1.setName("Thread one ==="); // 设置线程名
        thread02ExtendsThreadDemo1.start(); // 开启线程1，并且执行run方法
        Thread02ExtendsThreadDemo thread02ExtendsThreadDemo2 = new Thread02ExtendsThreadDemo();
        thread02ExtendsThreadDemo2.setName("Thread two ==="); // 设置线程名
        thread02ExtendsThreadDemo2.start(); // 开启线程2，并且执行run方法
    }
}

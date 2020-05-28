package com.momolela.thread;

/**
 * 使用线程更简便的方式
 */
public class Thread12Other {

    public static void main(String[] args) {
        new Thread() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + " run " + i);
                }
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + " run " + i);
                }
            }
        }).start();
    }
}

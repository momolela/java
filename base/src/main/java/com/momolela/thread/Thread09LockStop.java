package com.momolela.thread;

/**
 * 线程里面是个循环，通过控制flag来控制停止线程
 */
class Thread09LockStopDemo implements Runnable {

    private boolean flag = true;

    @Override
    public synchronized void run() {
        while (flag) {
            try {
                wait(); // 当线程处于冻结状态，就不会再执行while(flag)，就读不到标记，就结束不了
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 线程运行出现了异常，肯定执行了interrupt()");
                this.changeFlag();
            }
            System.out.println(Thread.currentThread().getName() + " run");
        }
    }

    void changeFlag() {
        flag = false;
    }
}

public class Thread09LockStop {
    public static void main(String[] args) {
        Thread09LockStopDemo thread09LockStopDemo = new Thread09LockStopDemo();
        Thread t1 = new Thread(thread09LockStopDemo);
        Thread t2 = new Thread(thread09LockStopDemo);
        t1.start();
        t2.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        thread09LockStopDemo.changeFlag();
        t1.interrupt(); // 线程通过wait()，join()，sleep()都会进入冻结状态，interrupt()可以是线程复活，但是会出现InterruptedException异常
        t2.interrupt(); // 线程通过wait()，join()，sleep()都会进入冻结状态，interrupt()可以是线程复活，但是会出现InterruptedException异常

        System.out.println("over");
    }
}

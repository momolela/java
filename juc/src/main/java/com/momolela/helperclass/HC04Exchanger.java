package com.momolela.helperclass;

import java.util.concurrent.Exchanger;

/**
 * 交换器
 * ThreadA 线程到达栅栏后，会首先观察有没其它线程已经到达栅栏，如果没有就会等待，如果已经有其它线程（ThreadB）已经到达了，就会以成对的方式交换各自携带的信息
 * 因此 Exchanger 非常适合用于两个线程之间的数据交换。
 *
 * 打印结果：
 * 线程：ThreadA 交换前拥有的数据是：sunzj
 * 线程：ThreadB 交换前拥有的数据是：hufy
 * 线程：ThreadB 交换后拥有的数据是：sunzj
 * 线程：ThreadA 交换后拥有的数据是：hufy
 */
public class HC04Exchanger {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new ChangeData(exchanger, "sunzj"), "ThreadA").start();
        new Thread(new ChangeData(exchanger, "hufy"), "ThreadB").start();
    }

    static class ChangeData implements Runnable {
        private final Exchanger exchanger;
        private String data;

        ChangeData(Exchanger exchanger, String data) {
            this.exchanger = exchanger;
            this.data = data;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程：" + threadName + " 交换前拥有的数据是：" + this.data);
            try {
                this.data = (String) this.exchanger.exchange(this.data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + threadName + " 交换后拥有的数据是：" + this.data);
        }
    }
}

package com.momolela.thread;

/**
 * 共同的资源
 */
class Resource {
    private String name;
    private int count = 0;
    private boolean flag = false;

    public synchronized void set(String name) {
//        if (flag) {
        while (flag) { // 为了解决多生产者多消费者造成的问题，改成循环判读，每次唤醒的线程都要去判断一次
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name + "-" + this.count++;
        System.out.println(Thread.currentThread().getName() + " ==生产者== " + this.name);
        this.flag = true;
//        this.notify();
        this.notifyAll(); // 为了避免死锁，要唤醒所有的线程
    }

    public synchronized void out() {
//        if (!this.flag) {
        while (!this.flag) { // 为了解决多生产者多消费者造成的问题，改成循环判读，每次唤醒的线程都要去判断一次
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " ==消费者== " + this.name);
        this.flag = false;
//        this.notify();
        this.notifyAll(); // 为了避免死锁，要唤醒所有的线程
    }
}

/**
 * 生产者线程
 */
class Producer implements Runnable {
    Resource res;

    Producer(Resource res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            res.set("+商品+");
        }
    }
}

/**
 * 消费者线程
 */
class Consumer implements Runnable {
    Resource res;

    Consumer(Resource res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            res.out();
        }
    }
}


/**
 * synchronized 做线程通信的最终方案
 *
 * synchronized (lock) {
 *     while (条件不成立) { // 解决虚假唤醒的最终方案
 *         lock.wait();
 *     }
 *     // 干活
 * }
 * // 另外一个线程
 * synchronized (lock) {
 *     lock.notifyAll(); // 解决虚假唤醒的问题
 * }
 */
public class Thread07ProducerConsumer {

    public static void main(String[] args) {
        Resource resource = new Resource();
        // 一个生产者一个消费者的时候，都是正常的
        new Thread(new Producer(resource)).start();
        new Thread(new Consumer(resource)).start();
        // 再加一个生产者和一个消费者，即两个生产者两个消费者的情况，会出现两次生产，一次消费或者一次生产两次消费的问题。
        new Thread(new Producer(resource)).start();
        new Thread(new Consumer(resource)).start();
    }

}

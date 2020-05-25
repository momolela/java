package com.momolela.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共同的资源
 */
class ResourceLock {
    private String name;
    private int count = 0;
    private boolean flag = false;

    private ReentrantLock lock = new ReentrantLock(); // jdk5之后用于替换synchronized的同步函数同步代码块的锁
    private Condition condition_producer = lock.newCondition(); // 锁的对象，生产者
    private Condition condition_consumer = lock.newCondition(); // 锁的对象，消费者

    public void set(String name) {
        lock.lock(); // 加锁
        try {
            while (flag) { // 为了解决多生产者多消费者造成的问题，改成循环判读，每次唤醒的线程都要去判断一次
                try {
                    condition_producer.await(); // 生产者线程await()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.name = name + "-" + this.count++;
            System.out.println(Thread.currentThread().getName() + " ==生产者== " + this.name);
            this.flag = true;
            condition_consumer.signal(); // 为了避免死锁，要唤醒所有的消费者线程
        } finally {
            lock.unlock(); // 一定要释放锁
        }
    }

    public void out() {
        lock.lock();
        try {
            while (!this.flag) { // 为了解决多生产者多消费者造成的问题，改成循环判读，每次唤醒的线程都要去判断一次
                try {
                    condition_consumer.await(); // 消费者线程await()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " ==消费者== " + this.name);
            this.flag = false;
            condition_producer.signal(); // 为了避免死锁，要唤醒所有的生产者线程
        } finally {
            lock.unlock(); // 释放锁
        }
    }
}

/**
 * 生产者线程
 */
class ProducerLock implements Runnable {
    ResourceLock res;

    ProducerLock(ResourceLock res) {
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
class ConsumerLock implements Runnable {
    ResourceLock res;

    ConsumerLock(ResourceLock res) {
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
 * Object中的wait，notify，notifyAll方法，都交给了Condition对象，Condition对象通过lock获得。
 * Condition可以有多个，相对于synchronized，需要一个锁就要加一个synchronized，会形成锁嵌套，造成死锁。
 */
public class Thread08Lock {

    public static void main(String[] args) {
        ResourceLock resourceLock = new ResourceLock();
        // 一个生产者一个消费者的时候，都是正常的
        new Thread(new ProducerLock(resourceLock)).start();
        new Thread(new ConsumerLock(resourceLock)).start();
        // 再加一个生产者和一个消费者，即两个生产者两个消费者的情况，会出现两次生产，一次消费或者一次生产两次消费的问题。
        new Thread(new ProducerLock(resourceLock)).start();
        new Thread(new ConsumerLock(resourceLock)).start();
    }

}

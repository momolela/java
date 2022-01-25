package com.momolela.synchronizedoptimize;

import org.openjdk.jol.info.ClassLayout;

/**
 * 轻量锁
 * 如果一个对象虽然有多线程访问，但是访问是错开的，比如一个线程在上午访问，一个线程在下午访问（也就是没有竞争）。就可以用轻量锁去做。
 * 轻量锁的实现也是基于 synchronized 的，不用单独的语法去实现
 * |----------------------------------------------------|--------------------|
 * |                  Mark word (32 bits)               |       State        |
 * |----------------------------------------------------|--------------------|
 * | hashcode: 25          | age:4 | biased_lock:0 | 01 |       Normal       |
 * |----------------------------------------------------|--------------------|
 * | thread: 23 | epoch: 2 | age:4 | biased_lock:1 | 01 |       Biased       | 偏向锁 101
 * |----------------------------------------------------|--------------------|
 * |        ptr_to_lock_record : 30                | 00 | LightWeight Locked | 轻量锁 000
 * |----------------------------------------------------|--------------------|
 * |        ptr_to_heavyweight_monitor : 30        | 10 | Heavyweight Locked | 重量锁 010
 * |----------------------------------------------------|--------------------|
 * |                                               | 11 |   Marked for GC    |
 * |----------------------------------------------------|--------------------|
 */
public class LightWeightLock {
    public static void main(String[] args) {
        Object obj = new Object();
        LightWeightLock lightWeightLock = new LightWeightLock();
        lightWeightLock.method1(obj);
    }

    public void method1(Object obj) {
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(ClassLayout.parseInstance(obj).toPrintable()); // 000 说明是轻量锁
                System.out.println("t1 线程执行完了 ==========");
                method2(obj);
            }
        }, "t1").start();
    }

    public void method2(Object obj) {
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(ClassLayout.parseInstance(obj).toPrintable()); // 000 说明是轻量锁
                System.out.println("t2 线程执行完了 ==========");
            }
        }, "t2").start();
    }
}

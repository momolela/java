package com.momolela.concurrentcollection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CC01CopyOnWriteArrayList {

    public static void main(String[] args) {
        // CMEInArrayList();
        // useVector();
        // useCollectionSynchronizeList();
        useCopyOnWriteArrayList();
    }

    public static void CMEInArrayList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                // 由于 fail-fast 机制的存在，抛出了 ConcurrentModificationException 修改异常的错误(modCount 是 ArrayList 源码中的一个变量，用来表示修改的次数，因为 ArrayList 不是为并发情况而设计的集合类）
                // ConcurrentModificationException 中文意思就是并发修改异常，存在于并发使用 Iterator 时出现的时候
                // 这个涉及到 fast-fail 机制（快速失败），可以提前预料遍历失败情况，防止数组越界异常
                // list -> toString() -> next -> checkForComodification() -> throw new ConcurrentModificationException();
                System.out.println(list);
            }, "CME" + i).start();
        }
    }

    public static void useVector() {
        List<String> vector = new Vector<>();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                vector.add(UUID.randomUUID().toString().substring(0, 5));
                // 线程安全版的 ArrayList，内部给方法都加了 synchronized，所以不会抛出 ConcurrentModificationException 异常
                // 但是性能不佳
                System.out.println(vector);
            }, "vector" + i).start();
        }
    }

    public static void useCollectionSynchronizeList() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, "csl" + i).start();
        }
    }

    public static void useCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, "csl" + i).start();
        }
    }
}

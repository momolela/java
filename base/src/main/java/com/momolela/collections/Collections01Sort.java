package com.momolela.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Collections 的 sort 方法主要是用来给 List 去做排序用的，List 本身是可重复，但是内容没有顺序可言的
 * Set 集合有 TreeSet 去实现排序，所以不用 Collections 的 sort 方法了
 */
public class Collections01Sort {
    public static void main(String[] args) {
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("hrg");
        arrayList1.add("asdf");
        arrayList1.add("kk");
        arrayList1.add("s");
        System.out.println("通过存入对象本身具备的比较性去排序，排序前是：" + arrayList1);
        /**
         * public static <T extends Comparable<? super T>> void sort(List<T> list) {
         *     list.sort(null);
         * }
         * 上面是 sort 方法的具体定义，在方法上定义了一个泛型 <T extends Comparable<? super T>>
         * <T extends Comparable<? super T>> 的解释是：T extends Comparable 限定说明 T 必须是 Comparable 或者它的子类，也就是 T 要实现 Comparable 接口，也就是 T 本身要具备比较性
         * Comparable<? super T> 的解释是：Comparable 接口接收的必须是 T 或 T 的子类
         * 这里 T 是 String 类型，String 实现了 Comparable 接口
         */
        Collections.sort(arrayList1);
        System.out.println("通过存入对象本身具备的比较性去排序，排序后是：" + arrayList1);


        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("hrg");
        arrayList2.add("asdf");
        arrayList2.add("kk");
        arrayList2.add("s");
        System.out.println("通过比较器根据长度去排序，排序前是：" + arrayList2);
        /**
         * public static <T> void sort(List<T> list, Comparator<? super T> c) {
         *     list.sort(c);
         * }
         * 上面是 sort 方法的具体定义，在方法上定义了一个泛型 <T>，因为是通过比较器去实现排序了，所以不用加限定了
         */
        Collections.sort(arrayList2, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return new Integer(o1.length()).compareTo(new Integer(o2.length()));
            }
        });
        System.out.println("通过比较器根据长度去排序，排序后是：" + arrayList2);
    }
}

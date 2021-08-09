package com.momolela.generic;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class Generic02InComparator {
    public static void main(String[] args) {

        TreeSet<String> treeSet = new TreeSet<String>(new LenComparator()); // 创建 TreeSet 的时候传入比较器
        treeSet.add("ght");
        treeSet.add("ddfd");
        treeSet.add("cgdfd");
        treeSet.add("gsa");
        treeSet.add("gfgds");

        Iterator<String> it = treeSet.iterator();
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
        }
    }
}

/**
 * 比较器类，里面就用了泛型 <String>，
 * 所以在实现 compare 方法时，就需要指定参数为 String 类型
 * 接口其实就是 Comparator<T>，在定义接口的时候，全程用的泛型 T
 */
class LenComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int num = new Integer(o1.length()).compareTo(new Integer(o2.length()));
        if (num == 0) {
            return o1.compareTo(o2);
        } else {
            return num;
        }
    }
}

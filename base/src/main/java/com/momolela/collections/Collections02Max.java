package com.momolela.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Collections02Max {
    public static void main(String[] args) {
        ArrayList<String> maxList1 = new ArrayList<String>();
        maxList1.add("hrg");
        maxList1.add("asdf");
        maxList1.add("kk");
        maxList1.add("s");
        /**
         * public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
         *     ...
         * }
         * 上面是 max 方法的具体定义，在方法上定义了一个泛型 <T extends Object & Comparable<? super T>>
         * <T extends Object & Comparable<? super T>> 的解释是：T 必须是 Object 的子类，必须是 Comparable 的子类，也就是说取最大值，也要让对象本身具备可比性
         */
        String max1 = Collections.max(maxList1);
        System.out.println(maxList1 + " 的最大值（按照ascii）是：" + max1);

        ArrayList<String> maxList2 = new ArrayList<String>();
        maxList2.add("hrg");
        maxList2.add("asdf");
        maxList2.add("kk");
        maxList2.add("s");
        /**
         * public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
         *     ...
         * }
         * 上面是 max 方法的具体定义，在方法上定义了一个泛型 <T>，因为是通过比较器去实现取最大值了，所以不用加限定了
         */
        String max2 = Collections.max(maxList2, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return new Integer(o1.length()).compareTo(new Integer(o2.length()));
            }
        });
        System.out.println(maxList2 + " 的最大值（按照自己定义的比较器，比较长度）是：" + max2);
    }
}

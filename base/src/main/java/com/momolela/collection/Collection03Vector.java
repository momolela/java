package com.momolela.collection;

import java.util.Enumeration;
import java.util.Vector;

public class Collection03Vector {
    public static void main(String[] args) {

        /**
         * 枚举是Vector特有的迭代取出方式；
         * 枚举和迭代器很像；
         * 因为枚举的名称太长被迭代器取代；
         * 底层数组，线程同步，效率慢，不用，
         * 默认10，超过10时100%延长，
         * 用枚举去迭代
         */
        Vector v = new Vector();
        v.add("01");
        v.add("02");
        v.add("03");
        v.add("04");
        Enumeration en = v.elements();
        while (en.hasMoreElements()) {
            System.out.println(en.nextElement());
        }
    }
}

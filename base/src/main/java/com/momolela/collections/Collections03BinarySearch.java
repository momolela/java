package com.momolela.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Collections03BinarySearch {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("hrg");
        arrayList.add("asdf");
        arrayList.add("kk");
        arrayList.add("s");

        String findStr = "aaaa";
        /**
         * 查找对象在 List 中的位置索引、
         * 如果存在就直接返回 index
         * 如果不存在，就返回 -（插入点位置）-1，如果 findStr 是 aaa，返回值是 -1
         */
        int index = Collections.binarySearch(arrayList, findStr);
        System.out.println(findStr + " 的位置在 " + arrayList + " 里是：" + index);

        int halfSearchIndex = halfSearch(arrayList, findStr);
        System.out.println("通过方法 halfSearch 查得 " + findStr + " 的位置在 " + arrayList + " 里是：" + halfSearchIndex);

        int halfSearch2Index = halfSearch2(arrayList, findStr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int num = new Integer(o1.length()).compareTo(new Integer(o2.length()));
                if (num == 0) {
                    return o1.compareTo(o2);
                } else {
                    return num;
                }
            }
        });
        System.out.println("通过方法 halfSearch2 查得 " + findStr + " 的位置在 " + arrayList + " 里是：" + halfSearch2Index);
    }

    /**
     * 自定义的一个折半查找法的方法去模拟 binarySearch
     * 里面 s.compareTo(content) 利用了 String 本身的比较性去比较查找
     *
     * @param list
     * @param content
     * @return
     */
    public static int halfSearch(List<String> list, String content) {
        int min, mid, max;
        min = 0;
        max = list.size() - 1;
        while (min <= max) {
            mid = (min + max) >> 1; // 相当于 /2
            String s = list.get(mid);
            int num = s.compareTo(content);
            if (num > 0) {
                max = mid - 1;
            } else if (num < 0) {
                min = mid + 1;
            } else {
                return mid;
            }
        }
        return -min - 1;
    }

    /**
     * 自定义的一个折半查找法的方法去模拟 binarySearch
     * 里面 comp.compare(s, content) 是用了一个传入的比较器去比较查找
     *
     * @param list
     * @param content
     * @param comp
     * @return
     */
    public static int halfSearch2(List<String> list, String content, Comparator<String> comp) {
        int min, mid, max;
        min = 0;
        max = list.size() - 1;
        while (min <= max) {
            mid = (min + max) >> 1; // 相当于 /2
            String s = list.get(mid);
            int num = comp.compare(s, content);
            if (num > 0) {
                max = mid - 1;
            } else if (num < 0) {
                min = mid + 1;
            } else {
                return mid;
            }
        }
        return -min - 1;
    }
}

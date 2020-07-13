package com.momolela.generic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 数组里的带泛型的集合
 */
public class Generic08GenericInArray {
    public static void main(String[] args) {
        ArrayList<String>[] arrayLists = new ArrayList[5]; // 定义一个数组，里面存储 ArrayList，ArrayList 指定只能存 String

        ArrayList<String> strings0 = new ArrayList<String>();
        strings0.add("haha");
        arrayLists[0] = strings0;

        ArrayList<String> strings1 = new ArrayList<String>();
        strings1.add("hehe");
        arrayLists[1] = strings1;

        ArrayList<String> strings2 = new ArrayList<String>();
        strings2.add("keke");
        arrayLists[2] = strings2;

        ArrayList<String> strings3 = new ArrayList<String>();
        strings3.add("xixix");
        arrayLists[3] = strings3;

        ArrayList<String> strings4 = new ArrayList<String>();
        strings4.add("heihei");
        arrayLists[4] = strings4;

        System.out.println(Arrays.toString(arrayLists));
    }
}

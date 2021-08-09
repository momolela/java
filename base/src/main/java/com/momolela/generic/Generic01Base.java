package com.momolela.generic;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 泛型概述
 * jdk1.5之后出现的新的特性，用于解决安全问题，是一个安全机制；
 *
 *
 * 泛型由来
 * 往 ArrayList 里面存储各种对象，但是用的时候，要对具体对象的类型先进行判读，然后才能操作；
 * 如果能像数组一样规定集合的存储对象类型，就 ok 了；
 * 泛型来了；
 *
 *
 * 泛型的好处
 * 将运行期的问题 ClassCastException，转移到了编译期，方便开发人员解决问题，运行更安全；
 * 避免了强制转换的麻烦；
 */
public class Generic01Base {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("haha");
        arrayList.add("hehe");
        arrayList.add("xixi");
        arrayList.add("keke");

        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String obj = it.next();
            System.out.println(obj);
        }
    }
}

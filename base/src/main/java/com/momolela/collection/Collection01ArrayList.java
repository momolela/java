package com.momolela.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Collection01ArrayList {

    public static void main(String[] args) {
        /**
         * ArrayList 可以添加不同的对象
         * 底层是数组，查询快，增删慢
         * 线程不同步，相对于 Vector ，效率高
         * 默认10的长度，超过10会new新的数组15%延长，然后copy，相对于Vector的new 100% 的新数组，性能好
         */
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add(new ArrayListPerson("sunzj", 25));

        /**
         * 通过 iterator 迭代器遍历list的元素
         * iterator() 方法，是将arrayList的引用给了it，仅仅是引用，所以可以在遍历的时候进行 remove()，不会出现 ConcurrentModificationException
         */
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            System.out.println(next);
            if (next.equals("1")) {
                it.remove(); // Iterator接口中提供了remove()方法，没有add()
            }
        }

        /**
         * listIterator 和 iterator 一样可以用来遍历list的元素
         * listIterator 是list特有的迭代器，提供了 add 方法 和 remove
         */
        ListIterator lit = arrayList.listIterator();
        while (lit.hasNext()) {
            if (lit.next().equals("2")) {
                lit.add("3");
            }
        }

        System.out.println("arrayList final is: " + arrayList);
    }
}


class ArrayListPerson {

    private String name;
    private Integer age;

    public ArrayListPerson() {
    }

    public ArrayListPerson(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

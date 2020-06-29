package com.momolela.collection;

import java.util.HashSet;
import java.util.Iterator;

public class Collection04HashSet {

    public static void main(String[] args) {

        /**
         * 无序，不可重复
         * 只能用迭代器取
         * add()返回boolean
         * 底层用的hash表，
         * 为了保证不可重复性，先通过hashcode判断是否为同一对象，如果hashcode一致，再通过equals()判断，所以为了保证存入Hashset的对象的唯一性，一般会复写hashCode()和equals()方法
         */
        HashSet set = new HashSet();
        set.add("sunzj");
        set.add("haha");
        set.add("sunzj");
        set.add("hufy");
        set.add("abc");
        System.out.println(set);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }


        /**
         * HashSet里面放对象的时候，如果不重写hashCode()和equals()方法，就没办法判断是否为重复对象，或者判断方式不是自己想要的
         */
        HashSet personSet = new HashSet();
        personSet.add(new HashSetPerson("sunzj", 25));
        personSet.add(new HashSetPerson("hufy", 24));
        personSet.add(new HashSetPerson("sunzj", 25));
        personSet.add(new HashSetPerson("abc", 0));
        Iterator iterator = personSet.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof HashSetPerson) {
                System.out.println(((HashSetPerson) next).getName() + "..." + ((HashSetPerson) next).getAge());
            }
        }

    }
}

class HashSetPerson {

    private String name;
    private Integer age;

    public HashSetPerson() {
    }

    public HashSetPerson(String name, Integer age) {
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

    @Override
    public int hashCode() {
        return 0; // 如果返回的都是相同，则需要继续去比较equals方法的
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("equals...开始比较对象是否一致..." + obj);
        if (obj instanceof HashSetPerson) {
            HashSetPerson hashSetPerson = (HashSetPerson) obj;
            if (hashSetPerson.getAge() == this.getAge() && hashSetPerson.getName().equals(this.getName())) {
                return true;
            }
            return false;
        }
        return false;
    }

}

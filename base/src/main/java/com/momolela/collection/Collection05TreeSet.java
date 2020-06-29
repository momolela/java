package com.momolela.collection;

import java.util.Comparator;
import java.util.TreeSet;

public class Collection05TreeSet {
    public static void main(String[] args) {

        /**
         * 可以对set中的元素进行排序，默认按ASCII顺序排序
         * 不可重复
         */
        TreeSet sortTs = new TreeSet();
        sortTs.add("dfe");
        sortTs.add("dfe");
        sortTs.add("cae");
        sortTs.add("jtryadd");
        sortTs.add("dafew");
        sortTs.add("qdfdf");
        System.out.println("TreeSet 本身有默认排序：" + sortTs);

        /**
         * 会报出异常 com.momolela.collection.TreeSetPerson cannot be cast to java.lang.Comparable
         * 插入的对象需要实现Comparable接口，实现CompareTo的方法
         * TreeSet一定要排序；但是如果存的自定义对象不能排序会报异常，需要自定义对象实现Comparable接口，实现compareTo方法，让对象可排序，然后才能存入TreeSet （原理就是让元素自身具备比较性）
         * 底层二叉树，小的往左放，大的往右放，从小到大排序
         */
        TreeSet objTs = new TreeSet();
        objTs.add(new TreeSetPerson("sunzj", 25));
        objTs.add(new TreeSetPerson("hufy", 24));
        objTs.add(new TreeSetPerson("hufy", 24));
        System.out.println("对象本身具备比较功能：" + objTs);

        /**
         * 如果元素本身没有比较性，就是没有实现Comparable接口，没有实现compareTo方法，或者元素对象的比较性不是需要的，那么就需要让TreeSet集合自身具备比较性（new TreeSet的时候，传入一个比较器）。
         * 当比较器和元素本身具备比较性的时候，以比较器为准
         */
        TreeSet compareTorTs = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (!(o1 instanceof TreeSetPerson) || !(o2 instanceof TreeSetPerson)) {
                    throw new RuntimeException("不是TreeSetPerson类型...");
                }
                TreeSetPerson tsp1 = (TreeSetPerson) o1;
                TreeSetPerson tsp2 = (TreeSetPerson) o2;
                return tsp1.getAge().compareTo(tsp2.getAge());
            }
        });
        compareTorTs.add(new TreeSetPerson("sunzj", 25));
        compareTorTs.add(new TreeSetPerson("hufy", 23));
        compareTorTs.add(new TreeSetPerson("hufy", 24));
        System.out.println("当元素不具备比较性，可以通过比较器实现：" + compareTorTs);

        /**
         * 下面的语句执行会报异常，类型转换异常
         * 因为在 add() 里面，会固定TreeSet的 E 类型，后续如果加的类型是其他类型会报错
         */
        TreeSet treeSet = new TreeSet();
        treeSet.add("1");
        treeSet.add(false);
    }
}

/**
 * 现在去定义一个对象
 * 1、复写 hashCode() 和 equals() 来确定存入集合的对象唯一性
 * 2、实现 Comparable 接口，让对象具备默认的比较性，既可以存到 hashSet 也可以存到 TreeSet
 * 3、还可以复写 toString() 方法，改变默认的输出
 */
class TreeSetPerson implements Comparable {

    private String name;
    private Integer age;

    public TreeSetPerson() {
    }

    public TreeSetPerson(String name, Integer age) {
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

    /**
     * 复写这个方法用来存进TreeSet的时候进行排序
     * -1 小于
     * 0 对象相同，不能存进TreeSet
     * 1 大于
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof TreeSetPerson)) {
            throw new RuntimeException("不是TreeSetPerson类型...");
        }
        TreeSetPerson tsp = (TreeSetPerson) o;
        int num = tsp.getAge().compareTo(this.age);
        if (num == 0) {
            return tsp.getName().compareTo(this.name);
        } else {
            return num;
        }
    }
}

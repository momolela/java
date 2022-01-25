package com.momolela.collection;

import java.util.LinkedList;

public class Collection02LinkedList {

    public static void main(String[] args) {

        /**
         * 底层链表，增删快，查询慢
         * 可以添加不同对象的元素
         * 有序可以重复
         */
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add("1");
        linkedList.add("1");
        linkedList.add(true);
        linkedList.add(new LinkedListPerson("hufy", 24));

        /**
         * contains 方法会触发 LinkedListPerson 的equals方法，会挨个和 linkedList 中的元素去比较
         */
        System.out.println(linkedList.contains(new LinkedListPerson("hufy", 24)));

        /**
         * addFirst 加在最前面
         * addLast 加在最后面
         * getFirst 获取第一个，没有会抛出异常
         * pollFirst 获取第一个，没有会返回null
         * removeFirst 会先返回第一个元素，然后删除
         */
        linkedList.addFirst("0");
        linkedList.addLast(999);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.pollFirst());
        System.out.println(linkedList.removeFirst());
    }

}


class LinkedListPerson {

    private String name;
    private Integer age;

    public LinkedListPerson() {
    }

    public LinkedListPerson(String name, Integer age) {
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
    public boolean equals(Object obj) {
        System.out.println("开始比较对象是否一致..." + obj);
        if (obj instanceof LinkedListPerson) {
            LinkedListPerson linkedListPerson = (LinkedListPerson) obj;
            if (linkedListPerson.getAge() == this.getAge() && linkedListPerson.getName().equals(this.getName())) {
                return true;
            }
            return false;
        }
        return false;
    }
}
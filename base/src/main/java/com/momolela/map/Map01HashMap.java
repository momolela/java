package com.momolela.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 现在定义一个对象
 * 1 复写了 hashCode() 和 equals() 方法，就是为了当该对象存入hash中时，能够通过自己实现的方法，去确定是否已经存在相同的对象
 * 2 实现了 Comparable<Student> 接口，就是为了存入底层为二叉树的集合中，能够通过对象本身的排序序去实现排序
 */
class HashMapStudent implements Comparable<HashMapStudent> {
    private String name;
    private Integer age;

    HashMapStudent(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.age * 34;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HashMapStudent) {
            if (this.name.equals(((HashMapStudent) obj).getName()) && this.age == ((HashMapStudent) obj).getAge()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(HashMapStudent o) {
        int num = this.name.compareTo(o.name);
        if (num == 0) {
            return this.age.compareTo(o.age);
        } else {
            return num;
        }
    }
}

/**
 * HashMap<K, V>
 */
public class Map01HashMap {
    public static void main(String[] args) {
        HashMap<HashMapStudent, String> hashMap = new HashMap<HashMapStudent, String>();
        hashMap.put(new HashMapStudent("sunzj", 25), "江西");
        hashMap.put(new HashMapStudent("sunzj", 25), "浙江");
        hashMap.put(new HashMapStudent("hufy", 24), "江西");
        hashMap.put(new HashMapStudent("liuh", 26), "北京");

        // 第一种遍历方式，这种遍历效率较低，拿到 key 之后，要通过 key 去 hashmap 的数据结构中找数据
        Set<HashMapStudent> students = hashMap.keySet();
        Iterator<HashMapStudent> it1 = students.iterator();
        while (it1.hasNext()) {
            HashMapStudent next1 = it1.next();
            System.out.println(next1.getName() + "是" + next1.getAge() + "岁");
        }

        // 第二种遍历方式，这种遍历效率更高
        Set<Map.Entry<HashMapStudent, String>> entrySet = hashMap.entrySet();
        Iterator<Map.Entry<HashMapStudent, String>> it2 = entrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry<HashMapStudent, String> next2 = it2.next();
            HashMapStudent studentKey = next2.getKey();
            String addrValue = next2.getValue();
            System.out.println(studentKey + "===" + addrValue);
        }

    }
}

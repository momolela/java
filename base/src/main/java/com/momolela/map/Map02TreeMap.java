package com.momolela.map;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

class TreeMapStudent implements Comparable<TreeMapStudent> {
    private String name;
    private Integer age;

    TreeMapStudent(String name, Integer age) {
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
    public int compareTo(TreeMapStudent o) {
        int num = this.name.compareTo(o.name);
        if (num == 0) {
            return this.age.compareTo(o.age);
        } else {
            return num;
        }
    }
}

public class Map02TreeMap {
    public static void main(String[] args) {
        TreeMap<TreeMapStudent, String> treeMap = new TreeMap<TreeMapStudent, String>(new Comparator<TreeMapStudent>() {
            @Override
            public int compare(TreeMapStudent o1, TreeMapStudent o2) {
                int num = o1.getName().compareTo(o2.getName());
                if (num == 0) {
                    return o1.getAge().compareTo(o2.getAge()); // 如果姓名和年龄都相等的时候，return 0，则说明TreeMap中已经存在，会覆盖
                } else {
                    return num;
                }
            }
        });
        treeMap.put(new TreeMapStudent("sunzj", 25), "江西");
        treeMap.put(new TreeMapStudent("sunzj", 25), "浙江");
        treeMap.put(new TreeMapStudent("hufy", 24), "江西");
        treeMap.put(new TreeMapStudent("liuh", 26), "北京");

        Set<TreeMapStudent> students = treeMap.keySet();
        Iterator<TreeMapStudent> it1 = students.iterator();
        while (it1.hasNext()) {
            TreeMapStudent next1 = it1.next();
            System.out.println(next1.getName() + "是" + next1.getAge() + "岁");
        }
    }
}

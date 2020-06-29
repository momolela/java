package com.momolela.generic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "person:" + name;
    }
}

class Student extends Person {
    Student(String name) {
        super(name);
    }
}

class Worker extends Person {
    Worker(String name) {
        super(name);
    }
}

public class Generic06Constraint {

    public static void main(String[] args) {
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("abc1");
        arrayList1.add("abc2");
        arrayList1.add("abc3");
        printList1(arrayList1);

        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(1);
        arrayList2.add(2);
        arrayList2.add(3);
        printList2(arrayList2);

        ArrayList<Student> arrayList3 = new ArrayList<Student>();
        arrayList3.add(new Student("sabc---1"));
        arrayList3.add(new Student("sabc---2"));
        arrayList3.add(new Student("sabc---3"));
        // ArrayList<Student> al = new ArrayList<Person>(); 和 ArrayList<Person> al = new ArrayList<Student>();这两种情况是不允许的，左右两边必须一致；

        printList3(arrayList3); // 打印里限定了上限是 Person

        printList4(arrayList3); // 打印里限定了下限是 Student

        /**
         * 比较器可以接收父类，然后里面既可以比较 Student ，也可以比较 Worker
         *
         * 因为 TreeSet 的构造函数，传入的比较器的泛型限定是 <? super E>
         * public TreeSet(Comparator<? super E> comparator) {
         *         this(new TreeMap<>(comparator));
         *     }
         */
        TreeSet<Student> treeSet1 = new TreeSet<Student>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        treeSet1.add(new Student("student---1"));
        treeSet1.add(new Student("student---2"));
        treeSet1.add(new Student("student---3"));
        Iterator<Student> it1 = treeSet1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next().getName());
        }

        /**
         * 比较器可以接收父类，然后里面既可以比较 Student ，也可以比较 Worker
         *
         * 因为 TreeSet 的构造函数，传入的比较器的泛型限定是 <? super E>
         * public TreeSet(Comparator<? super E> comparator) {
         *         this(new TreeMap<>(comparator));
         *     }
         */
        TreeSet<Worker> treeSet2 = new TreeSet<Worker>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        treeSet2.add(new Worker("worker---1"));
        treeSet2.add(new Worker("worker---2"));
        treeSet2.add(new Worker("worker---3"));
        Iterator<Worker> it2 = treeSet2.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next().getName());
        }
    }

    /**
     * 在方法上定义泛型 T ，能处理一个固定类型
     *
     * @param arrayList
     * @param <T>
     */
    private static <T> void printList1(ArrayList<T> arrayList) {
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            T next = it.next();
            System.out.println(next);
        }
    }

    /**
     * 用 ? 通配符接收，能处理任意类型
     *
     * @param arrayList
     */
    private static void printList2(ArrayList<?> arrayList) {
        Iterator<?> it = arrayList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /**
     * 这是上限，上面限定死了
     * 就只能打印 Person 和 Person 的所有子类
     *
     * @param arrayList
     */
    private static void printList3(ArrayList<? extends Person> arrayList) {
        Iterator<? extends Person> it = arrayList.iterator();
        while (it.hasNext()) {
            Person next = it.next();
            System.out.println(next.getName());
        }
    }

    /**
     * 这是下限，下面限定死了
     * 就只能打印 Student 和 Student 的所有父类
     *
     * @param arrayList
     */
    private static void printList4(ArrayList<? super Student> arrayList) {
        Iterator<? super Student> it = arrayList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

}

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

class Baby extends Student {
    Baby(String name) {
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
        // printList1 方法入参定义为 ArrayList<String> arrayList
        // 在方法上定义泛型 T ，能处理一个固定类型
        // T 是类型形参
        printList1(arrayList1);
        // printList1(arrayList2); // 排除定义先后的错，这里还会报泛型的错，因为他处理不了 Integer 的类型

        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(1);
        arrayList2.add(2);
        arrayList2.add(3);
        // printList2 方法入参定义为 ArrayList<?> arrayList
        // 用 ? 通配符接收，能处理任意类型，T 是固定一个类型之后不能变了，但是 ? 可以对应任意类型。
        // ? 是类型实参数
        printList2(arrayList2);
        printList2(arrayList1); // 这里也可以正常打印

        ArrayList<Person> arrayList3 = new ArrayList<Person>();
        arrayList3.add(new Person("pabc---1"));

        ArrayList<Student> arrayList4 = new ArrayList<Student>();
        arrayList4.add(new Student("sabc---1"));
        // ArrayList<Student> al = new ArrayList<Person>(); 是不允许的，左右两边必须一致；
        // ArrayList<Person> al = new ArrayList<Student>(); 是不允许的，左右两边必须一致；

        ArrayList<Baby> arrayList5 = new ArrayList<Baby>();
        arrayList3.add(new Baby("babc---1"));

        // printList3(arrayList3); // 会报错，因为打印里限定了上限是 Student，只能是 Student 和它的子类类
        printList3(arrayList4); // 打印里限定了上限是 Student
        printList3(arrayList5); // 不会报错

        printList4(arrayList3); // 不会报错
        printList4(arrayList4); // 打印里限定了下限是 Student
        // printList4(arrayList5); // 会报错，因为打印里限定了下限是 Student，只能是 Student 和它的父类

        /**
         * 比较器可以接收父类 Person，然后里面既可以比较 Student ，也可以比较 Worker
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
        //treeSet1.add(new Student("student---1"));
        //treeSet1.add(new Student("student---2"));
        //treeSet1.add(new Student("student---3"));
        //Iterator<Student> it1 = treeSet1.iterator();
        //while (it1.hasNext()) {
        //    System.out.println(it1.next().getName());
        //}

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
     * T 只能用在定义上，在方法上定义泛型 T ，能处理一个固定类型
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
     * ? 只能用在定义上，用 ? 通配符接收，能处理任意类型，T 是固定一个类型之后不能变了，但是 ? 可以对应任意类型
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
     * <? extends Person> 只能用在定义上
     * 这是上限，上面限定死了
     * 就只能打印 Person 和 Person 的所有子类
     *
     * @param arrayList
     */
    private static void printList3(ArrayList<? extends Student> arrayList) {
        // 这里不能添加，下面的会报错，因为 ? 可以是 Student 或者 Baby，没有共同父类，所以不能添加具体的类型
        // arrayList.add(new Person("a"));
        // arrayList.add(new Student("a"));
        // arrayList.add(new Baby("a"));
        Iterator<? extends Student> it = arrayList.iterator();
        while (it.hasNext()) {
            Student next = it.next();
            System.out.println(next.getName());
        }
    }

    /**
     * <? super Student> 只能用在定义上
     * 这是下限，下面限定死了
     * 就只能打印 Student 和 Student 的所有父类
     *
     * @param arrayList
     */
    private static void printList4(ArrayList<? super Student> arrayList) {
        // 如下面，可以添加 Student 和 Baby 类型，因为 ? 可以是 Student 或者 Person，一定有个共同父类 Student，所以可以添加 Student 或者 Student 的子类
        // arrayList.add(new Person("a")); // 会报错
        // arrayList.add(new Student("a")); // 不会报错
        // arrayList.add(new Baby("a")); // 不会报错
        Iterator<? super Student> it = arrayList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    /**
     * 泛型的限定除了使用 ? extends 和 ? super ，还可以使用 T extends 和 T super
     */
    private static <T extends Person> void printList5(ArrayList<T> arrayList) {
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            Person next = it.next();
            System.out.println(next.getName());
        }
    }

}

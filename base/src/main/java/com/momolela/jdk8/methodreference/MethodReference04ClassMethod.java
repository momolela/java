package com.momolela.jdk8.methodreference;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface TestInterface {
    // 注意：入参比 Student 类的 setNameAndAge 方法多1个 Student 对象，除第一个外其它入参类型一致
    void set(Student student, String name, int age);
}

class Student {
    private String name;
    private int age;

    void setNameAndAge(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Student is " + this.name + " age is " + this.age);
    }
}


/**
 * 类的任意对象的实例方法引用
 */
public class MethodReference04ClassMethod {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList(new String[]{"sunzj", "hufy", "liuhuan"});

        // 排序并且遍历打印
        stringList.stream().sorted((s1, s2) -> s1.compareToIgnoreCase(s2)).forEach((s) -> System.out.println(s));


        // 排序并且遍历打印
        // 下面采用方法引用可以简化 Lambda 表达式，要遵循：参数数量和类型要与接口中定义的一致，返回值类型要与接口中定义的一致
        // String::compareToIgnoreCase，类的任意对象的实例方法引用
        // System.out::println，特定对象的实例方法引用
        stringList.stream().sorted(String::compareToIgnoreCase).forEach(System.out::println);


        // 仔细分析
        // sorted 接收的函数式接口 Comparator<? super T> comparator，方法 int compare(T o1, T o2)；
        // String 的 compareToIgnoreCase 方法 compareToIgnoreCase 只有一个参数；
        // public int compareToIgnoreCase(String str) {
        //     return CASE_INSENSITIVE_ORDER.compare(this, str);
        // }
        // 懵了？？？看下面的示例
        // 所以类的任意对象的实例方法引用的特性为：
        // 1、方法引用的通用特性：方法引用所使用方法的入参和返回值与 lambda 表达式实现的函数式接口的入参和返回值一致；
        // 2、lambda 表达式的第一个入参为实例方法的调用者，后面的入参与实例方法的入参一致；

        //TestInterface testInterface = (student, name, score) -> student.setNameAndAge(name, score);
        TestInterface testInterface = Student::setNameAndAge;
        testInterface.set(new Student(), "sunzj", 26);
    }
}

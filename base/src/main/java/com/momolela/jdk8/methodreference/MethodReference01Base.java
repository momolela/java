package com.momolela.jdk8.methodreference;

import java.util.Arrays;
import java.util.List;

/**
 * lambda表达式可用方法引用代替的场景可以简要概括为：
 *
 * lambda表达式的主体仅包含一个表达式，且该表达式仅调用了一个已经存在的方法。
 * 方法引用的通用特性：方法引用所使用方法的入参和返回值与 lambda 表达式实现的函数式接口的入参和返回值一致。
 */
public class MethodReference01Base {

    public static void main(String[] args) {
        List<String> stringList = Arrays.asList(new String[]{"sunzj", "hufy", "liuhuan"});

        // 排序并且遍历打印
        stringList.stream().sorted((s1, s2) -> s1.compareToIgnoreCase(s2)).forEach((s) -> System.out.println(s));

        // 排序并且遍历打印
        // 下面采用方法引用可以简化 Lambda 表达式，要遵循：参数数量和类型要与接口中定义的一致，返回值类型要与接口中定义的一致
        // String::compareToIgnoreCase，类的任意对象的实例方法引用
        // System.out::println，特定对象的实例方法引用
        stringList.stream().sorted(String::compareToIgnoreCase).forEach(System.out::println);
    }

}

package com.momolela.jdk8.methodreference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 构造器引用
 *
 * 构造器引用适用于 lambda 表达式主体中仅仅调用了某个类的构造函数返回实例的场景
 */
public class MethodReference03Constructor {
    public static void main(String[] args) {
        Supplier<List<String>> supplier = () -> new ArrayList<String>();

        Supplier<List<String>> supplier1 = ArrayList::new;
    }
}

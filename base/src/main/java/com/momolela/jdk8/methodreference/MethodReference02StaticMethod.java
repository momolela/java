package com.momolela.jdk8.methodreference;

import java.util.Arrays;

/**
 * 静态方法引用
 *
 * 静态方法引用适用于 lambda 表达式主体中仅仅调用了某个类的静态方法的场景
 */
public class MethodReference02StaticMethod {

    public static void main(String[] args) {
        // Lambda 表达式使用
        Arrays.asList("sunzj", "hufy", "liuhuan").stream().forEach(s -> MethodReference02StaticMethod.print(s));

        // 静态方法引用使用
        Arrays.asList("sunzj", "hufy", "liuhuan").stream().forEach(MethodReference02StaticMethod::print);
    }

    public static void print(String s) {
        System.out.println(s);
    }
}

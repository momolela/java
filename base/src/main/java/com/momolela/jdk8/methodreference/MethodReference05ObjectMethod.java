package com.momolela.jdk8.methodreference;

import java.util.Arrays;

/**
 * 对象实例方法引用
 *
 * 特定对象的实例方法引用适用于 lambda 表达式的主体中仅仅调用了某个对象的某个实例方法的场景。
 */
public class MethodReference05ObjectMethod {
    public static void main(String[] args) {
        MethodReference05ObjectMethod methodReference05ObjectMethod = new MethodReference05ObjectMethod();

        // Lambda 表达式使用
        Arrays.asList("sunzj", "hufy", "liuhuan").stream().forEach(s -> methodReference05ObjectMethod.println(s));

        // 对象实例方法引用使用
        Arrays.asList("sunzj", "hufy", "liuhuan").stream().forEach(methodReference05ObjectMethod::println);
    }

    public void println(String s) {
        System.out.println(s);
    }
}

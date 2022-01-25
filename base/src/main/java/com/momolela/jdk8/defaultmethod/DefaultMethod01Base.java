package com.momolela.jdk8.defaultmethod;

interface TestInterface1 {
    default void test() {
        System.out.println("TestInterface1");
        testThen();
    }

    // java8 支持接口中定义静态方法（需要提供默认实现），写法上将默认方法的 default 关键字换成 static 关键字即可。
    static void testThen() {
        System.out.println("TestInterface1 testThen");
    }

    // // java9 才支持接口中定义私有方法和私有静态方法
    // private void testAfter() {
    //
    // }
    // private static void testAfter() {
    //
    // }
}

interface TestInterface2 {
    default void test() {
        System.out.println("TestInterface2");
        testThen();
    }

    // java8 支持接口中定义静态方法（需要提供默认实现），写法上将默认方法的 default 关键字换成 static 关键字即可。
    static void testThen() {
        System.out.println("TestInterface2 testThen");
    }

    // // java9 才支持接口中定义私有方法和私有静态方法
    // private void testAfter() {
    //
    // }
    // private static void testAfter() {
    //
    // }
}


/**
 * 简介
 * 默认方法是指 接口 的默认方法，它是 java8 的新特性之一。顾名思义，默认方法就是接口提供一个默认实现，且不强制实现类去覆写的方法。默认方法用 default 关键字来修饰。
 *
 *
 * 为什么要提出默认方法
 * 首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，
 * 目前的 java 8 之前的集合框架没有 foreach 方法，通常能想到的解决办法是在 JDK 里给相关的接口添加新的方法及实现。
 * 然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。
 * 所以引进的默认方法。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。
 */
public class DefaultMethod01Base implements TestInterface1, TestInterface2 {


    /**
     * 当一个类实现多个接口时，若多个接口中存在相同默认方法（方法名、参数、返回值相同），此时实现类必须要覆写默认方法。
     */
    @Override
    public void test() {
        // 采用super关键字来调用指定接口的默认方法，调用 TestInterface1 接口的默认 test() 方法
        TestInterface1.super.test();
    }

    // 实现类自己实现方法逻辑 或者 创建自己的默认方法
    //default void test () {
    //    TestInterface1.super.test();
    //}


    public static void main(String[] args) {
        DefaultMethod01Base defaultMethod01Base = new DefaultMethod01Base();
        defaultMethod01Base.test();
    }

}

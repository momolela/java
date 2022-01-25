package com.momolela.test;


/**
 * java9 接口中支持
 * 抽象方法
 * 默认方法
 * 静态方法
 * 私有方法
 * 私有静态方法
 */
interface interfaceTest1 {
    void test();

    default void testDefault() {
        System.out.println("testDefault in interfaceTest1");
        testPrivate();
        testStatic();
        testPrivateStatic();
    }

    static void testStatic() {
        System.out.println("testStatic in interfaceTest1");
    }

    private void testPrivate() {
        System.out.println("testPrivate in interfaceTest1");
    }

    private static void testPrivateStatic() {
        System.out.println("testPrivateStatic in interfaceTest1");
    }
}

/**
 * java9 接口中支持
 * 抽象方法
 * 默认方法
 * 静态方法
 * 私有方法
 * 私有静态方法
 */
interface interfaceTest2 {
    void test();

    default void testDefault() {
        System.out.println("testDefault in interfaceTest2");
        testPrivate();
        testStatic();
        testPrivateStatic();
    }

    static void testStatic() {
        System.out.println("testStatic in interfaceTest2");
    }

    private void testPrivate() {
        System.out.println("testPrivate in interfaceTest2");
    }

    private static void testPrivateStatic() {
        System.out.println("testPrivateStatic in interfaceTest2");
    }
}


public class DefaultMethodTest implements interfaceTest1, interfaceTest2 {

    @Override
    public void test() {
        System.out.println("实现接口的抽象方法");
    }

    @Override
    public void testDefault() {
        interfaceTest1.super.testDefault();
    }

    public static void main(String[] args) {
        DefaultMethodTest defaultMethod01Base = new DefaultMethodTest();
        defaultMethod01Base.testDefault();
    }
}

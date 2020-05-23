package com.momolela.exception;

class Exception01BaseDemo {
    int div(int a, int b) {
        return a / b;
    }
}

/**
 * 程序在运行时出现不正常的情况。
 * 问题也是现实生活中一个具体的事物，也可以通过java类的形式进行描述，并封装成对象。
 * 异常其实就是java对不正常情况进行描述后的对象体现。
 *
 * Throwable 都具有共性内容，如不正常情况的信息，引发原因
 *  |----- Error 严重问题，一般不编写针对性的代码对其进行处理
 *  |----- Exception 非严重问题，可以使用针对性的代码对其进行处理
 */
public class Exception01Base {

    public static void main(String[] args) {
        Exception01BaseDemo exception01BaseDemo = new Exception01BaseDemo();
        int div = exception01BaseDemo.div(4, 0); // 会抛出异常：Exception in thread "main" java.lang.ArithmeticException: / by zero
        System.out.println("结果是：" + div);
        System.out.println("over");
    }

}

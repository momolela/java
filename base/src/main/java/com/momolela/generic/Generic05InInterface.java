package com.momolela.generic;

interface demo<T> {
    void show(T t);
}

/**
 * 实现接口的时候，知道要传入的类型可以直接指定
 */
class demo1Impl implements demo<String> {
    @Override
    public void show(String s) {
        System.out.println("interface show:" + s);
    }
}

/**
 * 实现接口的时候，也不知道要传入什么具体的类型，可以再定义泛型类
 *
 * @param <T>
 */
class demo2Impl<T> implements demo<T> {
    @Override
    public void show(T t) {
        System.out.println("interface show:" + t);
    }
}

public class Generic05InInterface {
    public static void main(String[] args) {
        demo1Impl demo1 = new demo1Impl();
        demo1.show("123");

        demo2Impl<Integer> demo2 = new demo2Impl<Integer>();
        demo2.show(new Integer("456"));
    }
}

package com.momolela.jdk8.optional;

import java.util.Optional;

public class Optional01Base {

    public static void main(String[] args) {
        Optional01Base optional01Base = new Optional01Base();
        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);

        System.out.println(optional01Base.sum(a, b));
    }


    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        // Optional.isPresent - 判断值是否存在
        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        // Optional.orElse - 如果值存在，返回它，否则返回默认值 other
        Integer value1 = a.orElse(new Integer(0));
        //Integer value1 = a.orElseGet(this::getDefaultValue); // 如果存在该值，返回值， 否则触发 other，并返回 other 调用的结果。

        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();

        return value1 + value2;
    }

    public Integer getDefaultValue() {
        return 0;
    }

}

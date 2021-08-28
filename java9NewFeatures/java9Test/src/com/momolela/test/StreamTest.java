package com.momolela.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream 增强
 */
public class StreamTest {
    public static void main(String[] args) {
        new StreamTest().test1();
        new StreamTest().test2();
        new StreamTest().test3();
        new StreamTest().test4();
    }

    /**
     * 1、takeWhile，循环获取符合条件的数据，当遇到不符合的值，就停止
     */
    public void test1() {
        List<Integer> integers = Arrays.asList(12, 32, 42, 21, 45, 36, 28);
        integers.stream().takeWhile((x) -> x < 40).forEach(System.out::println); // 12, 32
        System.out.println("========== 1");
    }

    /**
     * 2、dropWhile，和 takeWhile 相反，返回剩余的数据
     */
    public void test2() {
        List<Integer> integers = Arrays.asList(12, 32, 42, 21, 45, 36, 28);
        integers.stream().dropWhile((x) -> x < 40).forEach(System.out::println); // 42, 21, 45, 36, 28
        System.out.println("========== 2");
    }

    /**
     * 3、ofNullable
     */
    public void test3() {
        // Stream.of(null); // 这个是会报错的，因为 Java8 Stream 不允许存放单个 null
        Stream<Object> stream = Stream.ofNullable(null);
        System.out.println(stream.count());
        System.out.println("========== 3");
    }

    /**
     * 4、iterate 优化，第二个参数是结束标识
     */
    public void test4() {
        // new ArrayList<>().stream();
        // Stream.of();
        // Arrays.stream();
        // Stream.iterate();
        // Optional.ofNullable(null).stream();
        Stream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println); // java8
        System.out.println();
        Stream.iterate(0, x -> x < 10, x -> x + 1).forEach(System.out::println); // java9
        System.out.println("========== 4");
    }
}

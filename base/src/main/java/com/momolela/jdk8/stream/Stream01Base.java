package com.momolela.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream01Base {

    public static void main(String[] args) {

        // filter
        // 过滤出数字
        List<String> strList1 = Arrays.asList("5", "Sunzj", "2", "Hufy", "0");
        List<String> result1 = strList1.stream().filter((s) -> s.toUpperCase().equals(s.toLowerCase())).collect(Collectors.toList());
        System.out.println(result1);
        System.out.println("==========");

        // map
        // 将每个元素替换成新的元素，用于改变流中元素本身类型
        List<String> strList2 = Arrays.asList("5", "Sunzj", "2", "Hufy", "0");
        List<String> result2 = strList2.stream().map((s) -> {
            if (s.toUpperCase().equals(s.toLowerCase())) {
                return "love";
            } else {
                return s;
            }
        }).collect(Collectors.toList());
        System.out.println(result2);
        System.out.println("==========");

        // peek
        // 一般用于不想改变流中元素本身的类型或者只想元素的内部状态时,一般用于 debugger 的时候，如下 peek 用于打印过滤后的结果
        // peek，接收的是 Consumer，没有返回值，不会重新写入到 stream 中
        // map，接收的是 Function，有返回值，会生成新的值重新写入到 stream 中
        // 如下 .peek(s -> s.toUpperCase()) 不会起作用，因为不会返回新的值，所以不会改变 Stream
        // 但是 .peek(obj -> obj.setName("sunzj")) obj 的 name 属性是会变化的
        Stream<String> stream3 = Stream.of("5", "Sunzj", "2", "Hufy", "0");
        stream3.filter((s) -> !s.toUpperCase().equals(s.toLowerCase())).peek(System.out::println).peek(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println("==========");

        // foreach
        // 迭代流中的每个数据
        Stream<String> stream4 = Stream.of("5", "Sunzj", "2", "Hufy", "0");
        stream4.forEach((s) -> System.out.print(s + " "));
        System.out.println();
        System.out.println("==========");

        // limit
        // 用于获取指定数量的流
        // 随机打印10条数据
        Random random5 = new Random();
        random5.ints().limit(10).forEach(System.out::println);
        System.out.println("==========");

        // sorted
        // 用于排序
        // 随机打印10条数据，然后排序
        Random random6 = new Random();
        random6.ints().limit(10).sorted().forEach(System.out::println);
        System.out.println("==========");
    }

}

package com.momolela.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Optional 增加 Stream
 */
public class OptionalTest {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("AA");
        stringList.add("BB");
        stringList.add("CC");

        Optional<List<String>> optionalList = Optional.ofNullable(stringList);
        System.out.println(optionalList.stream().flatMap(x -> x.stream()).count());
    }

}

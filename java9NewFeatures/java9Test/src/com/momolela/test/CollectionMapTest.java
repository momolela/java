package com.momolela.test;

import java.util.*;

/**
 * java9 之后更方便创建只读集合
 */
public class CollectionMapTest {

    public static void main(String[] args) {
        CollectionMapTest collectionMapTest = new CollectionMapTest();
        collectionMapTest.testInJava8();
    }

    /**
     * Java8 中通过 Collections.unmodifiableList 将集合变成只读集合
     */
    public void testInJava8() {
        List<String> stringList = new ArrayList<>();
        stringList.add("GG");
        stringList.add("DD");
        stringList.add("JJ");
        stringList.add("MM");
        List<String> unmodifiableList = Collections.unmodifiableList(stringList);
        // unmodifiableList.add("BB"); // 这里会报错，因为上面已经通过 Collections.unmodifiableList(stringList) 转变为了不可修改的集合

        // List
        List<Integer> integers = Collections.unmodifiableList(Arrays.asList(1, 2, 3)); // 之后 integers 不可修改

        // Set
        Set<Integer> integerSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1, 2, 3))); // 之后 integerSet 不可修改

        // Map
        Map<Object, Object> objectMap = Collections.unmodifiableMap(new HashMap<>()); // 之后 objectMap 不可修改
    }

    /**
     * Java9 中通过 .of() 将集合变成只读集合
     */
    public void testInJava9() {
        // List
        List<Integer> integers = List.of(1, 2, 3); // 之后 integers 不可修改

        // Set
        Set<Integer> integerSet = Set.of(1, 2, 3); // 之后 integerSet 不可修改

        // Map
        Map<String, Integer> integerMap = Map.of("sunzj", 26, "hufy", 25); // 之后 integerMap 不可修改
        Map<String, Integer> integerMap1 = Map.ofEntries(Map.entry("sunzj", 26), Map.entry("hufy", 25)); // 之后 integerMap1 不可修改
    }

}

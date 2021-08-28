package com.momolela.test;

import java.util.HashSet;
import java.util.Set;

/**
 * 钻石操作符 <> 可以和匿名内部类同时存在
 */
public class DiamondOperateTest {

    public static void main(String[] args) {
        new DiamondOperateTest().diamondOperate();
    }

    public void diamondOperate() {

        // <> 就是钻石操作符
        // 在 java9 之前，匿名内部类和 <> 钻石操作符不能共用
        // Set<String> set = new HashSet<>(); 在 java9 之前只能这样写
        Set<String> set = new HashSet<>() {}; // 相当于创建了一个继承于 HashSet 的匿名子类的对象

        set.add("GG");
        set.add("DD");
        set.add("JJ");
        set.add("MM");

        for (String s : set) {
            System.out.println(s);
        }
    }
}

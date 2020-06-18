package com.momolela.string;

public class String01Base {
    public static void main(String[] args) {
        String s1 = new String();
        String s2 = "";
        System.out.println(s1 == s2); // false，因为是两个对象
        System.out.println(s1.equals(s2)); // true，equals()复写过Object的方法，是用来比较字符串的内容

        // haha和xixi在内存中作为两个对象本身没变，只是变量a的地址变了
        String a = "haha";
        a = "xixi";

        String b1 = new String("abc"); // 两个对象，new的一个，"abc"一个
        String b2 = "abc"; // "abc" 一个对象
        System.out.println(b1 == b2); // false，对象不同
        System.out.println(b1.equals(b2)); // true，内容相同
    }
}

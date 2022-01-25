package com.momolela.string;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class String01Base {
    public static void main(String[] args) {
        String s1 = new String();
        String s2 = "";
        System.out.println(s1 == s2); // false，因为是两个对象
        System.out.println(s1.equals(s2)); // true，String 的 equals() 复写过 Object 的方法，是用来比较字符串的内容


        String b1 = new String("abc"); // 两个对象，new String("abc") 的一个，"abc"一个
        String b2 = "abc"; // "abc" 一个对象
        System.out.println(b1 == b2); // false，对象不同
        System.out.println(b1.equals(b2)); // true，内容相同


        // haha 和 xixi 在内存中作为两个对象本身没变，只是变量 smile 的地址变了
        String smile = "haha";
        smile = "xixi";


        Map<String, Object> test = new HashMap<>();
        String testStr = (String) test.get("test");
//        System.out.println(testStr.getClass()); // 这里会报空指针异常，因为 testStr 还是为 null
        System.out.println(testStr);  // null
        System.out.println(StringUtils.isEmpty(testStr)); // true


        String x = "abc" + "def";
        String y = "abcdef";
        System.out.println(x == y); // true，第一行在编译的时候 abc 和 def 合在一起了，可以用 javap 命令查看，所以 x == y
        String a = "aaa";
        String b = "bbb";
        String c = a + b; // 这里通过 javap 命令可以得知，用了 new StringBuilder 去装载 a 和 b，是新对象
        String d = "aaabbb";
        System.out.println(c == d); // false，c 是 StringBuilder 的新对象


        String e = "eee";
        String f = "fff";
        // 当 intern() 方法被调用，
        // 如果字符串常量池里面没有，先往常量池里面放入字符串，然后返回引用；
        // 如果字符串常量池里面有一个字符串和当前调用方法字符串 equals 相等，那么直接返回字符串常量池中的字符串引用；
        String g = (e + f).intern();
        String h = "eeefff";
        System.out.println(g == h); // true


        System.out.println((char)44); // ,

    }
}

package com.momolela.string;

/**
 * 字符串缓冲区，
 * 长度可变化，
 * 可以操作多种数据类型，
 * 可以用toString()变成字符串，
 * 线程同步
 */
public class String03StringBuffer {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        // 存储
        System.out.println(sb.append("1").append(2).append("hehe").toString());
        System.out.println(sb.insert(0, "haha").toString());
        // 删除
        System.out.println(sb.delete(0, 1).toString());
        System.out.println(sb.deleteCharAt(0).toString());
        // 获取
        System.out.println(sb.indexOf("he"));
        System.out.println(sb.lastIndexOf("ha"));
        System.out.println(sb.length());
        System.out.println(sb.substring(0, 1));
        System.out.println(sb.charAt(1));
        // 修改
        System.out.println(sb.replace(0, 1, "hehe"));
        // 反转
        System.out.println(sb.reverse());
        // 将缓冲区的部分字符串存储到指定数组中
        char[] dst = new char[]{'a', 'b', 'c'};
        sb.getChars(0, 1, dst, 0);
    }
}

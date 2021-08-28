package com.momolela.test;


/**
 * java9 之前， String，StringBuffer，StringBuilder 底层就是 char[]
 * java9 开始， String，StringBuffer，StringBuilder 底层就是 byte[](配合 encoding flag)
 * 因为不管是中文还是拉丁文，都是两个 byte，浪费了，所以改成了 byte[]
 */
public class StringTest {

    public static void main(String[] args) {
        String s = "sunzj";
    }
}

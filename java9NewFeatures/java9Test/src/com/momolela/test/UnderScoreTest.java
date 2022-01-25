package com.momolela.test;

/**
 * java9 开始 _ 不能作为变量名，单独使用了
 */
public class UnderScoreTest {

    /**
     * 在 java9 之前，_ 可以作为变量名
     */
    // public void testInJava7Or8 (){
    //     String _ = "hello";
    //     System.out.println(_);
    // }

    /**
     * java9 开始，_ 计划被作为关键字，所以不能再被作为变量名
     * 就像 lambda 的 ->
     */
    // public void testInJava9 (){
    //     String _ = "hello";
    //     System.out.println(_);
    // }
}

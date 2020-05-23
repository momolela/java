package com.momolela.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

class ExceptionDealDemo {
    int div(int a, int b) throws Exception{
        return a / b;
    }
}

/**
 * 异常处理的方式
 * 1、try catch
 * 2、声明抛出
 */
public class ExceptionDeal {
    public static void main(String[] args) throws Exception {
        ExceptionDealDemo exceptionDealDemo = new ExceptionDealDemo();
//        try {
            int div = exceptionDealDemo.div(4, 0); // 会抛出异常：Exception in thread "main" java.lang.ArithmeticException: / by zero
            System.out.println("结果是：" + div);
//        } catch (Exception e) {
//
//            // 异常信息
//            System.out.println(e.getMessage());
//
//            // 异常名称，异常信息
//            System.out.println(e.toString());
//
//            // 异常堆栈信息；内部使用的是System.err；输出流是有缓冲区的，所以对于什么时候具体输出也是随机的。占用太多内存。
//            e.printStackTrace();
//
//            // 利用io解析异常堆栈信息
//            StringWriter stringWriter = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(stringWriter);
//            e.printStackTrace(printWriter);
//            System.out.println(stringWriter.toString());
//
//        }
        System.out.println("over");
    }
}

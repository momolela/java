package com.momolela.io.stream;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 字节打印流 PrintStream
 * 构造函数可以接收的参数类型
 * 1 File 对象：File
 * 2 字符串路径：String
 * 3 字节输出流：OutputStream
 *
 *
 * 字符打印流 PrintWriter
 * 构造函数可以接收的参数类型
 * 1 File 对象：File
 * 2 字符串路径：String
 * 3 字节输出流：OutputStream
 * 4 字符输出流：Writer
 */
public class IO14PrintWriterPrintStream {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // PrintWriter printWriter = new PrintWriter(new FileWriter("test.txt"), true); // 可以写到文件中，但是要加上 autoFlush ，第一个参数要用 FileWriter
        PrintWriter printWriter = new PrintWriter(System.out, true);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            if ("over".equals(line)) {
                break;
            }
            printWriter.println(line); // printWriter 可以使用 println 代替 write 方法，实现直接换行
            // printWriter.flush(); // printWriter 可以使用 autoFlush 参数
        }

        printWriter.close();
        bufferedReader.close();
    }
}

package com.momolela.test;

import java.io.*;

/**
 * try-with-resources 的优化
 */
public class TryWithResourcesTest {

    /**
     * 在 java7 中要自己手动在 finally 中关闭相应的资源
     */
    public void testInJava7() {
        InputStreamReader reader = null;
        OutputStreamWriter writer = null;
        try {
            reader = new InputStreamReader(System.in);
            reader.read();
            writer = new OutputStreamWriter(new FileOutputStream("cs.txt"));
            writer.write("hello");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * java8 开始有了 try-with-resources 的新特性
     * try-with-resources 的新特性，可以不用自己手动关闭资源
     * 只需要在 try () 中定义资源即可
     */
    public void testInJava8() {
        try (InputStreamReader reader = new InputStreamReader(System.in); OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("cs.txt"))) {
            reader.read();
            writer.write("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * java9 对 try-with-resources 特性进行了优化
     * 资源可以定义在 try () 外部，只要资源对象常量在 try () 中即可
     * 也可以做到自动关闭资源
     */
    public void testInJava9() throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(System.in);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("cs.txt"));
        try (reader; writer) {
            reader.read();
            writer.write("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

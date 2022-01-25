package com.momolela.io.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * ByteArrayInputStream 用于操作字节数组的流对象
 * 用流的思想去操作数组
 * 用于：读取一个文件到内存中，内存其实就是 ArrayStream
 */
public class IO21ByteArrayStream {
    public static void main(String[] args) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("abcdefg".getBytes()); // 创建 ByteArrayInputStream 对象的时候需要明确源，而且源必须是 byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 创建 ByteArrayOutputStream 对象的时候，不需要明确目的，因为内部封装了可变长度的字节数组，字节数组就是目的

        int num = 0;
        while ((num = byteArrayInputStream.read()) != -1) {
            byteArrayOutputStream.write(num);
        }

        System.out.println(byteArrayOutputStream.size());
        System.out.println(byteArrayOutputStream.toString());

        // 不需要 close() ，因为这两个流都是操作字节数组，没有使用系统资源，所以不用 close()
    }
}

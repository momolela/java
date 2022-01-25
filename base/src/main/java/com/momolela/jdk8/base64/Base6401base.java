package com.momolela.jdk8.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base6401base {
    public static void main(String[] args) {
        // 编码 jdk1.7
        String encodeStr1 = new BASE64Encoder().encode("hello i am sunzj".getBytes(StandardCharsets.UTF_8));
        System.out.println("jdk1.7 BASE64 编码后的结果是：" + encodeStr1);
        // 解码 jdk1.7
        byte[] decode1 = new byte[0];
        try {
            decode1 = new BASE64Decoder().decodeBuffer(encodeStr1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String originStr1 = new String(decode1, StandardCharsets.UTF_8);
        System.out.println("jdk1.7 BASE64 解码后的结果是：" + originStr1);


        // 编码 jdk1.8
        Base64.Encoder encoder2 = Base64.getEncoder();
        String encodeStr2 = encoder2.encodeToString("hello i am sunzj".getBytes(StandardCharsets.UTF_8));
        System.out.println("jdk1.8 BASE64 编码后的结果是：" + encodeStr2);
        // 解码 jdk1.8
        Base64.Decoder decoder2 = Base64.getDecoder();
        byte[] decode2 = decoder2.decode(encodeStr2);
        String originStr2 = new String(decode2, StandardCharsets.UTF_8);
        System.out.println("jdk1.8 BASE64 解码后的结果是：" + originStr2);
    }
}

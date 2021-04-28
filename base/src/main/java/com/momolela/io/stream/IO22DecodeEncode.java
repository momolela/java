package com.momolela.io.stream;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 编码：字符串变成字节数组 String --> byte[]    str.getBytes(charsetName);
 * 解码：字节数组变成字符串 byte[] --> String    new String(byte[], charsetName)
 */
public class IO22DecodeEncode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("我本机的文件编码是：" + System.getProperty("file.encoding"));

        String s = "你好";

        // 编码 utf-8
        byte[] bytes1 = s.getBytes("utf-8"); // 我的电脑默认是 utf-8 ，测试自己的编码，看下面结果输出是否一致
        System.out.println(Arrays.toString(bytes1));
        // 解码 utf-8
        String s1 = new String(bytes1, "utf-8");
        System.out.println("s1 = " + s1);

        // 编码 ISO8859-1
        byte[] bytes2 = s.getBytes("ISO8859-1");
        System.out.println(Arrays.toString(bytes2));
        // 解码 ISO8859-1
        String s2 = new String(bytes2, "ISO8859-1");
        System.out.println("s2 = " + s2); // 编码用了 ISO8859-1 就完了，因为 ISO8859-1 压根就不支持中文，解码就根本解不出来了

        // 编码 utf-8
        byte[] bytes3 = s.getBytes("utf-8");
        System.out.println(Arrays.toString(bytes3));
        // 解码 ISO8859-1
        String s3 = new String(bytes3, "ISO8859-1");
        System.out.println("s3 = " + s3); // 编码用 utf-8 但解码用了 ISO8859-1 还是有救的，只需要再用 ISO8859-1 编码，然后再用 utf-8 去解码就ok了
        bytes3 = s3.getBytes("ISO8859-1");
        s3 = new String(bytes3, "utf-8");
        System.out.println("s3 = " + s3);

        /**
         * 开发中的常见的问题：本机发送请求是 gbk，但是发到服务器，tomcat 是 ISO8859-1，会导致乱码
         * 解决方式：
         * 1、get，把乱码用 ISO8859-1 编一次，然后再用 gbk 解一次
         * 2、post，后台用 setCharacterEncoding 去设置成 gbk
         * 3、服务器码表改成 gbk，但是有问题，gbk 不像 ISO8859-1 一样广泛，其他 编码来了照样乱码
         */

        // 编码 gbk
        byte[] bytes4 = s.getBytes("gbk");
        System.out.println(Arrays.toString(bytes4));
        // 解码 utf-8
        String s4 = new String(bytes4, "utf-8");
        System.out.println("s4 = " + s4); // 编码用 gbk 但解码用了 utf-8；然后下面用 utf-8 去编码，再用 gbk 去解码，还是不行的；gbk 和 utf-8 都支持中文，编码解码的过程会串；反过来是 ok 的
        bytes4 = s4.getBytes("utf-8");
        s4 = new String(bytes4, "gbk");
        System.out.println("s4 = " + s4);

        /**
         * 记事本联通问题
         * 1、用 gbk 或者 gb2312 存 “联通” 两个字
         * 2、保存关闭
         * 3、重新打开，发现乱码了
         *
         * 因为 “联通” 两个字，转成二进制之后，他的编码标识头被解析成了 utf-8，所以他3个字节3个字节解码文件，就乱码了，其实他是用 gbk 或者 gb312 存的
         */

        String xin = "♥";
        byte[] bytes5 = xin.getBytes("gbk");
        String s5 = new String(bytes5, "gbk");
        System.out.println("s5 = " + s5);


    }

}

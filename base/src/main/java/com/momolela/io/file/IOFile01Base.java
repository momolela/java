package com.momolela.io.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class IOFile01Base {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 1
        File f1 = new File("a.txt");
        System.out.println("f1" + f1);
        // 2
        File f2 = new File("e:\\ioTest", "a.txt");
        System.out.println("f2" + f2);
        // 3
        File dir = new File("e:\\ioTest");
        File f3 = new File(dir, "b.txt");
        System.out.println("f3" + f3);
        // 4
        File f4 = new File("e:" + File.separator + "ioTest" + File.separator + "c.txt");
        System.out.println("f4" + f4);

        // 当前类文件的 path
        System.out.println(new File(IOFile01Base.class.getResource("/").getPath()));
        // web 项目的 base path
        System.out.println(URLDecoder.decode(new File(IOFile01Base.class.getResource("/").getPath()).getParentFile().getParent(), "UTF-8")); // 生产环境 classes 退两层 -> classes -> WEB-INF
    }
}

package com.momolela.io.file;

import java.io.File;

public class IOFile01Base {
    public static void main(String[] args) {
        // 1
        File f1 = new File("a.txt");
        // 2
        File f2 = new File("e:\\ioTest", "a.txt");
        // 3
        File dir = new File("e:\\ioTest");
        File f3 = new File(dir, "b.txt");
        // 4
        File f4 = new File("e:" + File.separator + "ioTest" + File.separator + "c.txt");

        System.out.println("f1" + f1);
        System.out.println("f2" + f2);
        System.out.println("f3" + f3);
        System.out.println("f4" + f4);
    }
}

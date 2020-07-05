package com.momolela.io.stream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO03FileCopy {
    public static void main(String[] args) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader("test.txt");
            fw = new FileWriter("test-copy.txt");
            char[] buf = new char[1024];
            int num = 0;
            while ((num = fr.read(buf)) != -1) {
                fw.write(buf);
            }
            System.out.println("文件复制成功");
        } catch (IOException e) {
            try {
                throw new Exception("文件复制失败");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

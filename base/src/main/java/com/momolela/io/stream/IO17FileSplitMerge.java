package com.momolela.io.stream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class IO17FileSplitMerge {

    public static void main(String[] args) throws IOException {
//        pngSplit();
//        pngMerge();

//        createBigFile();
//        textSplit();
//        textMerge();
    }

    /**
     * 创建大文件
     *
     * @throws IOException
     */
    private static void createBigFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("test-big.txt"));
        for (int i = 0; i < 100000; i++) {
            String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1";
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 文本按照大小分割，10M 一个
     *
     * @throws IOException
     */
    private static void textSplit() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test-big.txt");
        byte[] buffer = new byte[1024 * 1024]; // 1M
        int num = 0, part = 1, size = 10; // 就是 10 个 1M，按照 10M 分割
        while (true) {
            FileOutputStream fileOutputStream = new FileOutputStream("test-big-part-" + (part++) + ".txt");
            for (int i = 0; i < size; i++) {
                if ((num = fileInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, num);
                } else {
                    return; // 退出所有循环
                }
            }
        }
    }

    /**
     * 文本合并
     *
     * @throws IOException
     */
    private static void textMerge() throws IOException {
        // 组装 SequenceInputStream 流
        ArrayList<FileInputStream> fisList = new ArrayList<FileInputStream>();
        for (int i = 0; i < 10; i++) {
            fisList.add(new FileInputStream("test-big-part-" + (i + 1) + ".txt"));
        }
        Iterator<FileInputStream> it = fisList.iterator();
        Enumeration<FileInputStream> enumeration = new Enumeration<FileInputStream>() {
            @Override
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            @Override
            public FileInputStream nextElement() {
                return it.next();
            }
        };
        SequenceInputStream sequenceInputStream = new SequenceInputStream(enumeration);
        // 文件合并，写文件
        FileOutputStream fileOutputStream = new FileOutputStream("test-big-merge.txt");
        int num = 0;
        byte[] buff = new byte[1024 * 1024];
        while ((num = sequenceInputStream.read(buff)) != -1) {
            fileOutputStream.write(buff, 0, num);
        }
        fileOutputStream.close();
        sequenceInputStream.close();
    }

    /**
     * 图片合并
     *
     * @throws IOException
     */
    private static void pngMerge() throws IOException {
        // 用 ArrayList 构造
        ArrayList<FileInputStream> fisList = new ArrayList<FileInputStream>();
        fisList.add(new FileInputStream("1.part"));
        fisList.add(new FileInputStream("2.part"));
        fisList.add(new FileInputStream("3.part"));
        Iterator<FileInputStream> iterator = fisList.iterator();
        Enumeration<FileInputStream> enumeration = new Enumeration<FileInputStream>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public FileInputStream nextElement() {
                return iterator.next();
            }
        };
        SequenceInputStream sequenceInputStream = new SequenceInputStream(enumeration);

/*
        // 用 Vector 构造
        Vector<FileInputStream> fileInputStreams = new Vector<FileInputStream>();
        fileInputStreams.add(new FileInputStream("1.part"));
        fileInputStreams.add(new FileInputStream("2.part"));
        fileInputStreams.add(new FileInputStream("3.part"));
        Enumeration<FileInputStream> elements = fileInputStreams.elements();
        SequenceInputStream sequenceInputStream = new SequenceInputStream(elements);
*/

        FileOutputStream fileOutputStream = new FileOutputStream("test-merge.png");
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = sequenceInputStream.read(buf)) != -1) {
            fileOutputStream.write(buf, 0, num);
        }
        fileOutputStream.close();
        sequenceInputStream.close();
    }

    /**
     * 图片分割
     *
     * @throws IOException
     */
    private static void pngSplit() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.png");
        byte[] buff = new byte[1024 * 1024];
        int num = 0, count = 1;
        while ((num = fileInputStream.read(buff)) != -1) {
            FileOutputStream fileOutputStream = new FileOutputStream(count + ".part");
            fileOutputStream.write(buff, 0, num);
            count++;
            fileOutputStream.close();
        }
        fileInputStream.close();
    }

}

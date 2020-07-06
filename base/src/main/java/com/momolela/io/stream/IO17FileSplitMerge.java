package com.momolela.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class IO17FileSplitMerge {

    public static void main(String[] args) throws IOException {
        split();
        merge();
    }

    private static void merge() throws IOException {
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

    private static void split() throws IOException {
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

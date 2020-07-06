package com.momolela.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 流的合并，多个源对应于一个目的的情况
 */
public class IO15SequenceInputStream {

    public static void main(String[] args) throws IOException {
        Vector<FileInputStream> fileInputStreams = new Vector<FileInputStream>();
        fileInputStreams.add(new FileInputStream("test.txt"));
        fileInputStreams.add(new FileInputStream("test-copy.txt"));
        fileInputStreams.add(new FileInputStream("test-system-out.txt"));

        Enumeration<FileInputStream> elements = fileInputStreams.elements();

        SequenceInputStream sequenceInputStream = new SequenceInputStream(elements);

        FileOutputStream fileOutputStream = new FileOutputStream("test-merge.txt");

        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = sequenceInputStream.read(buf)) != -1) {
            fileOutputStream.write(buf, 0, num);
        }

        fileOutputStream.close();
        sequenceInputStream.close();
    }
}

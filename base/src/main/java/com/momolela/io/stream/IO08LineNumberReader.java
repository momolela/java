package com.momolela.io.stream;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class IO08LineNumberReader {

    public static void main(String[] args) throws IOException {
        // LineNumberReader 也是一个装饰类
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader("test.txt"));
        // 重新设置开始的行号
        lineNumberReader.setLineNumber(100);
        String line = null;
        while ((line = lineNumberReader.readLine()) != null) {
            System.out.println(lineNumberReader.getLineNumber() + ": " + line);
        }
        lineNumberReader.close();
    }

}

package com.momolela.io.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO05BufferedReader {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("test.txt");
        // 缓冲区创建的时候，一定要有流，因为缓冲区就是对流的包装，提高效率
        BufferedReader bufferedReader = new BufferedReader(fr);
        // bufferedReader.readLine() 是一行一行的去读，当返回 null 的时候 说明文件读完了，其实 readLine() 是边读边存，读完一行整体返回
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line); // 因为一行一行读，并不会读取换行符，所以用 System.out.println(); 换行打印
        }
        // 关闭缓冲区资源，也关闭了流资源
        bufferedReader.close();
    }
}

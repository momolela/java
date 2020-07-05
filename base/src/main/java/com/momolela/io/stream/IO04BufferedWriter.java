package com.momolela.io.stream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IO04BufferedWriter {

    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("test.txt");

        // 缓冲区创建的时候，一定要有流，因为缓冲区就是对流的包装，提高效率
        BufferedWriter bufferedWriter = new BufferedWriter(fw);

        for (int i = 0; i < 10; i++) {
            // 写入的缓冲区
            bufferedWriter.write("sunzj" + i);
            // 缓冲区提供的换行方法，windows 和 linux 兼容
            bufferedWriter.newLine();
            // 将缓冲区的内容刷入到磁盘
            bufferedWriter.flush();
        }

        // 关闭缓冲区资源即可，也直接关闭了流资源
        bufferedWriter.close();
    }

}

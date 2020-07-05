package com.momolela.io.stream;

import java.io.*;

public class IO06FileCopyByBuf {
    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("test.txt"));
            bw = new BufferedWriter(new FileWriter("test-copy.txt"));

            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine(); // 要手动加上换行
                bw.flush();
            }
        } catch (IOException e) {
            throw new Exception("文件复制失败");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new Exception("关闭写入流缓冲区失败");
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new Exception("关闭读出流缓冲区失败");
            }
        }
    }
}

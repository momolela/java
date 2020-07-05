package com.momolela.io.stream;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class IO07CustomBufferedReader extends Reader {
    private Reader r;

    /**
     * 装饰类通常会通过自己的构造函数接收被装饰的类对象
     * 并基于被装饰对象的功能（read），提供更强大的功能（readLine）
     *
     * @param r
     */
    public IO07CustomBufferedReader(Reader r) {
        this.r = r;
    }

    public String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while ((num = this.r.read()) != -1) {
            if ((char) num == '\r') {
                continue;
            } else if ((char) num == '\n') {
                return sb.toString();
            } else {
                sb.append((char) num);
            }
        }
        if (sb.length() != 0) {
            return sb.toString();
        }
        return null;
    }

    /**
     * 抽象方法必须要实现
     *
     * @param cbuf
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return r.read(cbuf, off, len);
    }

    /**
     * 抽象方法必须要实现
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.r.close();
    }

    /**
     * 自定义的 IO07CustomBufferedReader
     * 对已有的 FileReader 对象进行了功能的增强：FileReader 的 read 只能一个一个字符读，现在利用 read 实现 readLine 做到一行一行读
     * 基于已有的功能，提供加强的功能，这就是装饰者模式，自定义的 IO07CustomBufferedReader 类就是装饰类
     */
    public static void main(String[] args) throws IOException {
        IO07CustomBufferedReader customBufferedReader = new IO07CustomBufferedReader(new FileReader("test.txt"));
        String line = null;
        while ((line = customBufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        customBufferedReader.close();
    }

    /**
     * 继承到装饰的演变
     *
     * Buffered 是缓冲区功能，MyReader 的子类可能都拥有缓冲区功能，继承的实现就是再创建具有缓冲区功能的子类
     * MyReader
     *     |---- MyTextReader
     *               |---- MyBufferedTextReader
     *     |---- MyMediaReader
     *               |---- MyBufferedMediaReader
     *     |---- MyDataReader
     *               |---- MyBufferedDataReader
     *
     *
     * 由上通过继承加子类的方式，给子类增加缓冲区功能，导致继承体系同于臃肿，所以用一个公共的装饰类，去给所有的子类加缓冲区功能
     * MyReader
     *     |---- MyTextReader
     *     |---- MyMediaReader
     *     |---- MyDataReader
     *     |---- MyBufferedReader 公共的装饰类
     * 再结合多态
     * class MyBufferedReader extends MyReader{
     *     private MyReader r;
     *     public MyBufferedReader (MyReader r) {
     *          // 传入了 r ，后续添加更强大功能的方法可以利用 r 已经有的方法
     *     }
     * }
     *
     * 总结：继承结构变成了组合结构，降低了类与类的关系
     */
}

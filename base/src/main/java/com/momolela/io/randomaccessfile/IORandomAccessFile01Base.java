package com.momolela.io.randomaccessfile;

import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * RandomAccessFile 不属于 IO 体系，但是在 IO 包中成员，因为他直接就包含读和写的功能，内部就是用字节流实现，因为只有流才能操作数据
 * 内部封装了一个数组，通过指针操作数组元素，可以通过 .getFilePointer() 方法可以获取指针位置，也可以通过 .seek() 改变指针位置
 * 通过他的构造函数，得知：这个类只能操作文件，而且 mode 可以有 r rw ... 等模式
 * r 只读模式，不能创建文件，会读文件，文件不存在会抛出异常
 * rw 读写模式，要操作的文件不存在会创建，存在不会覆盖
 * 该类可用于多线程读取文件，下载文件，想起迅雷
 */
public class IORandomAccessFile01Base {

    public static void main(String[] args) throws IOException {
        writeFile1();
        readFile1();

        writeFile2();
        readFile2();
    }

    public static void writeFile1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test-random.txt", "rw");
        randomAccessFile.write("张三".getBytes()); // 默认以 utf-8 写入
//        randomAccessFile.write(258); // 这个写法会丢失精度，因为 write 只会写 8 个字节，258 是 1 00000010，前面的 1 会丢失
        randomAccessFile.writeInt(258); // 这个写法不会丢失精度
        randomAccessFile.close();
    }

    public static void readFile1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test-random.txt", "r");
        byte[] buff = new byte[6]; // utf-8 的编码，一个中文 3 个字节
        randomAccessFile.read(buff);
        String name = new String(buff);
//        String name = new String(randomAccessFile.readLine().getBytes("ISO-8859-1"), "utf-8"); // readLine() 需要转码，readLine() 本身是用 ISO-8859-1 解码的，所以要先用 .getBytes("ISO-8859-1") 编回去，然后再用 utf-8 解码
        System.out.println("name === " + name);

        int age = randomAccessFile.readInt();
        System.out.println("age === " + age);

        randomAccessFile.close();
    }

    public static void writeFile2() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test-random.txt", "rw");
        randomAccessFile.write("张三".getBytes()); // 默认以 utf-8 写入，一个汉字 3 个字节
        randomAccessFile.write(97); // 默认以 utf-8 写入，总共一个字节
        randomAccessFile.write("李四".getBytes()); // 默认以 utf-8 写入，一个汉字 3 个字节
        randomAccessFile.write(98); // 默认以 utf-8 写入，总共一个字节
        randomAccessFile.close();
    }

    public static void readFile2() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test-random.txt", "r");
        randomAccessFile.seek(7); // 掠过 张三97 一共 7 个字节
        byte[] buff = new byte[6];
        randomAccessFile.read(buff);
        String name = new String(buff);
        System.out.println("name === " + name);
        randomAccessFile.close();
    }

}

package com.momolela.io.stream;

import java.io.*;

public class IO20DataStream {
    public static void main(String[] args) throws IOException {
//        writeData();
//        readData();

//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("gbk.txt"), "gbk");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("utf.txt"), "utf-8");
        outputStreamWriter.write("你好"); // 这里用 utf-8 的编码写入，但是下面用 readUTF 去读是读不了的
        outputStreamWriter.close();

        writeUTFData();
        readUTFData();
    }

    public static void writeUTFData() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("test-data-utf.txt"));
        dataOutputStream.writeUTF("你好a"); // 用 writeUTF 这个写，只能用 readUTF 读
        dataOutputStream.close();
    }

    public static void readUTFData() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("test-data-utf.txt"));
        String s = dataInputStream.readUTF(); // 用 writeUTF 写，只能用这个 readUTF 读
        System.out.println(s);
        dataInputStream.close();
    }

    public static void writeData() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("test-data.txt"));
        dataOutputStream.writeInt(258);
        dataOutputStream.writeBoolean(true);
        dataOutputStream.writeDouble(9984.255);
        dataOutputStream.close();
    }

    public static void readData() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("test-data.txt"));
        int num = dataInputStream.readInt();
        boolean bo = dataInputStream.readBoolean();
        double dou = dataInputStream.readDouble();
        System.out.println(num);
        System.out.println(bo);
        System.out.println(dou);
    }
}

package com.momolela.io.stream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IO16FileBase64Transform {

    /**
     * 将文件转成 base64 字符串，大文件慎重
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将 base64 字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将 base64 字符保存文本文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 文本分割
     * @throws IOException
     */
    private static void textSplit() throws IOException {
        File file = new File("D:\\fileBase64.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024 * 1024]; // 1M
        int num = 0, part = 1, size = 10; // 就是 10 个 1M，按照 10M 分割
        while (true) {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\file-base64-part-" + (part++) + ".txt"));
            for (int i = 0; i < size; i++) {
                if ((num = fileInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, num);
                } else {
                    return; // 退出所有循环
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            String base64Code = encodeBase64File("/Users/sunzj/vscode/hip/branches/dev_4.0.01/hip-drs-web/src/com/bsoft/drsadmin/front/assets/iconfont/iconfont.ttf"); // 将文件转成 base64 码
            //System.out.println(base64Code);
            //decoderBase64File(base64Code, "D:\\hai-cnode-3.2.war");
            toFile(base64Code, "/Users/sunzj/vscode/hip/branches/dev_4.0.01/hip-drs-web/src/com/bsoft/drsadmin/front/assets/iconfont/iconfontttf.txt"); // 将 base64 码写入到文件
            //textSplit(); // 切割大文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
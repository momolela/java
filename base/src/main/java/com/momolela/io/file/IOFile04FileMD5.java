package com.momolela.io.file;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * MD5 的全称是 Message-Digest Algorithm 5，它一种被广泛使用的密码散列函数，可以产生出一个 128 位（16字节）的散列值（hash value），用于确保信息传输完整一致。
 *
 * MD5 值等同于文件的 ID，它的值是唯一的。
 * 如果文件已被修改（例如嵌入式病毒，特洛伊木马等），其 MD5 值将发生变化。
 * 因此，一些常规下载 URL 提供文件，并且通常提供 MD5 值。
 * 如果用户在下载后发现他们的 MD5 值与网站公告不一致，可能是文件被修改过或者下载出错。
 */
public class IOFile04FileMD5 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("file.txt");
        String md5Hex = DigestUtils.md5Hex(fileInputStream);
        System.out.println(md5Hex);
    }
}

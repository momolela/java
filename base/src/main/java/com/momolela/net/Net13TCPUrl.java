package com.momolela.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net13TCPUrl {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.jianshu.com/p/8b4d209355d7?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation");
        System.out.println("url.getProtocol()：" + url.getProtocol());
        System.out.println("url.getHost()：" + url.getHost());
        System.out.println("url.getPath()：" + url.getPath());
        System.out.println("url.getFile()：" + url.getFile());
        System.out.println("url.getQuery()：" + url.getQuery());
        System.out.println("url.getPort()：" + url.getPort());
        System.out.println("url.getDefaultPort()：" + url.getDefaultPort());

//        InputStream inputStream = url.openStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = null;
//        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//        }

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置连接超时时间
        httpURLConnection.setConnectTimeout(2000);
        // 设置超时读取时间
        httpURLConnection.setReadTimeout(2000);
        // 获取实体类型
        System.out.println("Content-Type === " + httpURLConnection.getContentType());
        // 获取长度
        System.out.println("Content-Length === " + httpURLConnection.getContentLength());
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }

    }
}

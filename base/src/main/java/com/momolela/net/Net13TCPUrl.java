package com.momolela.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Net13TCPUrl {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://127.0.0.1:8080/myweb/index.html?name=haha&age=24");
        System.out.println("url.getProtocol()：" + url.getProtocol());
        System.out.println("url.getHost()：" + url.getHost());
        System.out.println("url.getPath()：" + url.getPath());
        System.out.println("url.getFile()：" + url.getFile());
        System.out.println("url.getQuery()：" + url.getQuery());
        System.out.println("url.getPort()：" + url.getPort());
        System.out.println("url.getDefaultPort()：" + url.getDefaultPort());


        InputStream inputStream = url.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

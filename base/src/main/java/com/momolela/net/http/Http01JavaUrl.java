package com.momolela.net.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http01JavaUrl {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(2000);
        connection.setRequestMethod("GET"); // 查询
//        connection.setRequestMethod("HEAD"); // 查询，但是不会返回内容主体，主要是测试服务器性能
//        connection.setRequestMethod("POST"); // 新增，修改
//        connection.setRequestMethod("PUT"); // 全部更新，只针对已经存在的资源
//        connection.setRequestMethod("DELETE"); // 删除
//        connection.setRequestMethod("OPTIONS"); // 请求返回服务器该请求支持的请求方法，一般用于跨域的嗅探，在返回的请求头中会有 "access-control-allow-methods","access-control-allow-origin","access-control-allow-credentials" 等;
//        connection.setRequestMethod("TRACE"); // 请求服务器回显其收到的请求信息，该方法主要用于HTTP请求的测试或诊断
//        connection.setRequestMethod("CONNECT"); // http1.1协议预留的请求方法，这里设置会报错，不支持
//        connection.setRequestMethod("PATCH"); // 部分更新，资源不存在会创建，这里设置会报错，不支持
        System.out.println("Content-Type：" + connection.getContentType());
        System.out.println("Content-Length：" + connection.getContentLength());
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}

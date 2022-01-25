package com.momolela.net.http.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpProxy04SetRequestProxy {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();

        // 设置请求代理，解决通过一个客户端频繁访问，然后客户端被封禁的问题；可以去 66ip 的网站找测试用的免费 ip 和 port
        String ip = "192.109.165.114";
        int port = 80;
        HttpHost httpHost = new HttpHost(ip, port);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).build();

        String url = "https://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (HttpStatus.SC_OK == statusCode) {
                System.out.println("通过请求代理访问成功");
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                System.out.println(s);
            } else {
                System.out.println("通过请求代理访问失败，原因是：" + response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

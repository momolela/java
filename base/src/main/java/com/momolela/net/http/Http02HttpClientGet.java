package com.momolela.net.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 使用 httpclient 发送 get 请求
 * 1、无参请求
 * 2、加请求头
 * 3、有参数的时候，要使用 UrlEncode 进行编码
 */
public class Http02HttpClientGet {
    public static void main(String[] args) throws Exception {
        // 创建 CloseableHttpClient 对象
        CloseableHttpClient client = HttpClients.createDefault();

        // url中加入请求参数的时候，对于一些特殊字符处理，需要使用 UrlEncode，浏览器访问会自动编码处理，httpclient如果不手动处理会报错
        String wd = "szj+hfy 123|456";
        String wdEncode = URLEncoder.encode(wd, Charset.defaultCharset().name());

        // get 请求
        String url = "https://www.baidu.com/s?wd=" + wdEncode;
        HttpGet httpGet = new HttpGet(url);

        // 设置请求头
        // 设置 User-Agent，说明是真人请求，解决 httpclient 请求被认为非真人请求而拒绝的问题
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        // 设置 Referer，防盗链
        httpGet.setHeader("Referer", "https://www.baidu.com/");

        // 请求和响应
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                System.out.println("响应成功");

                // 获取响应头
                for (Header header : response.getAllHeaders()) {
                    System.out.println("响应头：" + header.getName() + " 响应值：" + header.getValue());
                }

                HttpEntity entity = response.getEntity();
//                System.out.println(entity.getContentType()); // 这是获取 Content-Type 的快捷方式
                String s = EntityUtils.toString(entity, Charset.defaultCharset());
                System.out.println(s);
                EntityUtils.consume(entity);
            } else {
                System.out.println("响应失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.momolela.net.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;

public class Http03GetImageFromWeb {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://himg.bdimg.com/sys/portrait/item/1cf4e5ad99e88287e5b086a837.jpg";
//        url = URLEncoder.encode(url, Charset.defaultCharset().name());
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        CloseableHttpResponse response = null;

        try {
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (HttpStatus.SC_OK == statusCode) {
                System.out.println("图片下载成功");
                HttpEntity entity = response.getEntity();
                String contentTypeValue = entity.getContentType().getValue();
                String suffix = ".jpg";
                if (contentTypeValue.contains("jpg") || contentTypeValue.contains("jpeg")) {
                    suffix = ".jpg";
                } else if (contentTypeValue.contains("bmp") || contentTypeValue.contains("bitmap")) {
                    suffix = ".bmp";
                } else if (contentTypeValue.contains("png")) {
                    suffix = ".png";
                } else if (contentTypeValue.contains("gif")) {
                    suffix = ".gif";
                }
                // 将图片下载到本地
                byte[] bytes = EntityUtils.toByteArray(entity);
                FileOutputStream fileOutputStream = new FileOutputStream("E:\\idea\\java\\szj" + suffix);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } else {
                System.out.println("图片下载失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }
}

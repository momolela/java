package com.momolela.net.http.proxy;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 发送 json 参数
 */
public class HttpProxy07SendJsonParam {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();

        String url = "https://www.baidu.com";
        HttpPost httpPost = new HttpPost(url);

        // 发送 json 类型的 post 请求，Content-Type 默认是：application/json
        httpPost.setHeader("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "szj");
        jsonObject.put("age", "25");
        StringEntity jsonEntity = new StringEntity(jsonObject.toString(), Charset.defaultCharset().name());
        jsonEntity.setContentEncoding(Charset.defaultCharset().name());
        httpPost.setEntity(jsonEntity);

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                System.out.println("请求成功");
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                System.out.println(s);
            } else {
                System.out.println("请求失败，原因是：" + response.getStatusLine().getReasonPhrase());
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

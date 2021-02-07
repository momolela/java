package com.momolela.net.http.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送简单的表单参数
 */
public class HttpProxy06SendFormParam {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();

        String url = "http://10.0.38.178:9526/momolela/printName/"; // 这里必须要带上最后的 /，不然 post 请求失败
        HttpPost httpPost = new HttpPost(url);

        // 发送表单类型的 post 请求，Content-Type 默认是：application/x-www-form-urlencoded
        List<NameValuePair> nvpList = new ArrayList<>();
        nvpList.add(new BasicNameValuePair("name", "szj"));
        nvpList.add(new BasicNameValuePair("age", "25"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvpList, Charset.defaultCharset());
        httpPost.setEntity(formEntity);

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
                System.out.println("请求失败，status 是: " + statusCode + "原因是: " + response.getStatusLine().getReasonPhrase());
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

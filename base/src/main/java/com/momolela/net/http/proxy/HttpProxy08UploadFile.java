package com.momolela.net.http.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 文件表单上传和普通字段的传递
 */
public class HttpProxy08UploadFile {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();

        String url = "https://www.baidu.com";
        HttpPost httpPost = new HttpPost(url);

        StringBody nameStringBody = new StringBody("孙肇将", ContentType.create("text/plain", Charset.defaultCharset()));
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.defaultCharset());
        builder.setContentType(ContentType.create("multipart/form-data", Charset.defaultCharset()));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity entity = builder
//                .addTextBody("name", "孙肇将") // text 为中文时，直接用 addTextBody 服务器端接收会是乱码，要使用下面的方式
                .addPart("name", nameStringBody)
                .addPart("fileName", new FileBody(new File("E:\\idea\\java\\test.txt"))) // 可以通过 addPart 指定要上传的文件
                .addBinaryBody("fileName", new File("E:\\idea\\java\\user.txt")) // 可以通过 addBinaryBody 指定要上传的文件
                .addTextBody("age", "25") // text 为非中文时，可以使用 addTextBody
                .build();

        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                System.out.println("请求成功");
                HttpEntity responseEntity = response.getEntity();
                String s = EntityUtils.toString(responseEntity);
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

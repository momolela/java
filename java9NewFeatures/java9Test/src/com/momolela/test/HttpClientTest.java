package com.momolela.test;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

/**
 * java9 直接集成了 HttpClient，不用引用 apache 的 jar 包
 * 且支持 webSocket ， http 2.0
 */
public class HttpClientTest {
    public static void main(String[] args){
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).GET().build();

            HttpResponse<String> response = null;
            response = httpClient.send(request, HttpResponse.BodyHandler.asString());
            System.out.println(response.statusCode());
            System.out.println(response.version().name());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.momolela.net.http.proxy;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpProxy10HttpClientUtil {

    private static final HttpClientBuilder HTTP_CLIENT_BUILDER = HttpClients.custom();

    static {
        /**
         * 绕过不安全的 https 请求的证书验证
         */
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", trustHttpsCertificates())
                .build();

        /**
         * common-httpclient 3.x 系列，连接池要使用如下配置
         * this.httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
         * this.httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(1000);
         * ...
         * 下面是使用 httpclient 4.5.x 系列去创建连接池，修改连接池配置
         */
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(50); // 连接池最大 50 个连接
        // 路由：ip + port 指定一个唯一路由
        cm.setDefaultMaxPerRoute(50); // 每个路由默认有多少连接
//        System.out.println("cm.getMaxTotal(): " + cm.getMaxTotal()); // 连接池的最大连接数
//        System.out.println("cm.getDefaultMaxPerRoute(): " + cm.getDefaultMaxPerRoute()); // 每个路由的最大连接数
//        PoolStats totalStats = cm.getTotalStats();
//        System.out.println("totalStats.getMax(): " + totalStats.getMax()); // 连接池的最大连接数
//        System.out.println("totalStats.getLeased(): " + totalStats.getLeased()); // 连接池里有多少连接是被占用的
//        System.out.println("totalStats.getAvailable(): " + totalStats.getAvailable()); // 连接池里有多少连接是可用的
        HTTP_CLIENT_BUILDER.setConnectionManager(cm);

        /**
         * 设置请求默认设置
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(3000)
                .setConnectionRequestTimeout(5000)  // 连接池里获取连接的超时时间
                .build();
        HTTP_CLIENT_BUILDER.setDefaultRequestConfig(requestConfig);

        /**
         * 设置默认的一些 header
         */
        List<Header> defaultHeaders = new ArrayList<>();
        BasicHeader userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        defaultHeaders.add(userAgentHeader);
        HTTP_CLIENT_BUILDER.setDefaultHeaders(defaultHeaders);
    }

    /**
     * 发送 get 请求
     *
     * @param url     请求地址，参数需要先经过 UrlEncode 处理
     * @param headers 请求头
     * @param params  额外的参数
     * @return
     */
    public static Map<String, Object> doGet(String url, Map<String, String> headers, Map<String, String> params, String inputEncoding, String outputEncoding) throws Exception {
        if (StringUtils.isEmpty(inputEncoding)) {
            inputEncoding = "UTF-8";
        }
        if (StringUtils.isEmpty(outputEncoding)) {
            outputEncoding = "UTF-8";
        }
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HTTP_CLIENT_BUILDER.build();
        // 参数包装，参数经过 URIBuilder，会自动拼接到 url 上，而且会进行 URLEncoder.encode() 处理
        if (params != null) {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
            url = uriBuilder.setCharset(Charset.forName(inputEncoding)).build().toString();
        }
        HttpGet httpGet = new HttpGet(url);
        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            result.put("status", statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                result.put("content", EntityUtils.toString(entity, outputEncoding));
            } else {
                result.put("content", statusLine.getReasonPhrase()); // 返回错误原因
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", 900);
            result.put("content", e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 发送表单类型的 post 请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static Map<String, Object> doPostForm(String url, Map<String, String> headers, Map<String, String> params, String inputEncoding, String outputEncoding) throws Exception {
        if (StringUtils.isEmpty(inputEncoding)) {
            inputEncoding = "UTF-8";
        }
        if (StringUtils.isEmpty(outputEncoding)) {
            outputEncoding = "UTF-8";
        }
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HTTP_CLIENT_BUILDER.build();
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        // 参数包装
        List<NameValuePair> nvpList = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvpList, inputEncoding);
        httpPost.setEntity(formEntity);
        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            result.put("status", statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                result.put("content", EntityUtils.toString(entity, outputEncoding));
            } else {
                result.put("content", statusLine.getReasonPhrase()); // 返回错误原因
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", 900);
            result.put("content", e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 发送 json 或者 String 类型的 post 请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  参数
     * @return
     */
    public static Map<String, Object> doPostString(String url, Map<String, String> headers, Object params, String inputEncoding, String outputEncoding) throws Exception {
        if (StringUtils.isEmpty(inputEncoding)) {
            inputEncoding = "UTF-8";
        }
        if (StringUtils.isEmpty(outputEncoding)) {
            outputEncoding = "UTF-8";
        }
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HTTP_CLIENT_BUILDER.build();
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        // 参数包装
        StringEntity stringEntity = null;
        if (params != null) {
            if (params instanceof Map) {
                JSONObject jsonObject = new JSONObject();
                if (params != null) {
                    for (Map.Entry<String, String> entry : ((Map<String, String>) params).entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                }
                stringEntity = new StringEntity(jsonObject.toString(), inputEncoding);
            } else if (params instanceof String) {
                stringEntity = new StringEntity((String) params, inputEncoding);
            } else {
                throw new Exception("请传递正确的请求参数：String 或者 Map<String, String>");
            }
        }
        stringEntity.setContentEncoding(inputEncoding);
        httpPost.setEntity(stringEntity);
        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            result.put("status", statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                result.put("content", EntityUtils.toString(entity, outputEncoding));
            } else {
                result.put("content", statusLine.getReasonPhrase()); // 返回错误原因
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", 900);
            result.put("content", e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 发送表单类型的 put 请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static Map<String, Object> doPut(String url, Map<String, String> headers, Map<String, String> params, String inputEncoding, String outputEncoding) throws Exception {
        if (StringUtils.isEmpty(inputEncoding)) {
            inputEncoding = "UTF-8";
        }
        if (StringUtils.isEmpty(outputEncoding)) {
            outputEncoding = "UTF-8";
        }
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HTTP_CLIENT_BUILDER.build();
        HttpPut httpPut = new HttpPut(url);
        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        // 参数包装
        List<NameValuePair> nvpList = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvpList, inputEncoding);
        httpPut.setEntity(formEntity);
        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPut);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            result.put("status", statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                result.put("content", EntityUtils.toString(entity, outputEncoding));
            } else {
                result.put("content", statusLine.getReasonPhrase()); // 返回错误原因
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", 900);
            result.put("content", e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 发送 delete 请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static Map<String, Object> doDelete(String url, Map<String, String> headers, Map<String, String> params, String inputEncoding, String outputEncoding) throws Exception {
        if (StringUtils.isEmpty(inputEncoding)) {
            inputEncoding = "UTF-8";
        }
        if (StringUtils.isEmpty(outputEncoding)) {
            outputEncoding = "UTF-8";
        }
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HTTP_CLIENT_BUILDER.build();
        // 参数包装，参数经过 URIBuilder，会自动拼接到 url 上，而且会进行 URLEncoder.encode() 处理
        if (params != null) {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
            url = uriBuilder.setCharset(Charset.forName(inputEncoding)).build().toString();
        }
        HttpDelete httpDelete = new HttpDelete(url);
        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDelete.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpDelete);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            result.put("status", statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                result.put("content", EntityUtils.toString(entity, outputEncoding));
            } else {
                result.put("content", statusLine.getReasonPhrase()); // 返回错误原因
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", 900);
            result.put("content", e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * 自定义 ConnectionSocketFactory，绕过 https 的安全认证
     *
     * @return
     * @throws Exception
     */
    private static ConnectionSocketFactory trustHttpsCertificates() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                // 判断是否信任
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = sslContextBuilder.build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"},
                    null,
                    NoopHostnameVerifier.INSTANCE);
            return sslConnectionSocketFactory;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("构造安全连接工厂失败");
        }
    }
}

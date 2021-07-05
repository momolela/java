package com.momolela.net.http.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 请求不安全的 https 地址时，程序不能正常返回，会报错，处理方式有两种
 * 1、给 httpclient 添加证书的密钥配置去请求
 * 2、让 httpclient 绕过 https 的安全认证
 */
public class HttpProxy09RequestHttps {
    public static void main(String[] args) throws Exception {
        // 要自定义 HttpClient，因为要指定自定义 ConnectionSocketFactory，去绕过 https 的安全认证
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", trustHttpsCertificates())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager);
        CloseableHttpClient client = httpClientBuilder.build();

//        String url = "https://localhost:8090/test/haha"; // 正常情况下，去访问这个地址，因为 https 是不安全的，会报错
        String url = "https://test.51trust.com/gateway/oauth/authorize/?clientId=2017070411003376&responseType=code";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                System.out.println("请求成功");
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                System.out.println(s);
            } else {
                System.out.println("请求失败，原因是：" + response.getStatusLine().getReasonPhrase());
            }
        } catch (IOException e) {
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

    /**
     * 自定义 ConnectionSocketFactory，绕过 https 的安全认证
     *
     * @return
     * @throws Exception
     */
    private static ConnectionSocketFactory trustHttpsCertificates() throws Exception {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
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
    }
}

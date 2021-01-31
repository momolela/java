package com.momolela.net.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Http99InvokeDemo {

    public static void main(String[] args) {
//        testHaiHttp();
        testHISHttp();
    }

    static class runThreadHttp implements Callable {

        @Override
        public Long call() {
//            try {
//                DefaultHttpClient httpclient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://10.8.2.178:8081/bs-whis/BSXml_webCloudClinic/getMedicalRecordInformation"); // url 请求地址
//                StringEntity stringEntity = new StringEntity("<BSXml><MsgHeader><Organization>1</Organization><Sender>GOL</Sender><ServiceType>service</ServiceType><MsgType>ODS_02100005</MsgType><MsgVersion>3.3</MsgVersion></MsgHeader><MsgBody><VisitOrganization>1</VisitOrganization><VisitId>71</VisitId></MsgBody></BSXml>", "UTF-8");
//                httpPost.setEntity(stringEntity);
//                httpPost.setHeader("Content-Type", "application/xml");
//
//                long s = System.currentTimeMillis();
//                HttpResponse response = httpclient.execute(httpPost);
//                long e = System.currentTimeMillis();
//                long cost = e - s;
//                System.out.println(cost);
//
//                HttpEntity entity = response.getEntity();
//                try {
//                    if (entity != null) {
//                        String str = EntityUtils.toString(entity, "UTF-8");
//                        System.out.println(str);
//                        return cost;
//                    }
//                } finally {
//                    if (entity != null) {
//                        entity.getContent().close();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;


            CloseableHttpResponse response = null;
            CloseableHttpClient client = null;
            try {
                HttpPost put = new HttpPost("http://10.8.2.178:8081/bs-whis/BSXml_webCloudClinic/getMedicalRecordInformation");
                StringEntity entity = new StringEntity("<BSXml><MsgHeader><Organization>1</Organization><Sender>GOL</Sender><ServiceType>service</ServiceType><MsgType>ODS_02100005</MsgType><MsgVersion>3.3</MsgVersion></MsgHeader><MsgBody><VisitOrganization>1</VisitOrganization><VisitId>71</VisitId></MsgBody></BSXml>", "UTF-8");
                put.setEntity(entity);
                put.addHeader("Content-Type", "application/xml");
                client = HttpClientBuilder.create().build();

                long s = System.currentTimeMillis();
                response = client.execute(put);
                long e = System.currentTimeMillis();
                long cost = e - s;
                System.out.println(cost);

                HttpEntity responseEntity = response.getEntity();
                System.out.println(EntityUtils.toString(responseEntity, "UTF-8"));
                return cost;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;

        }
    }

    public static void testHISHttp() {
        int total = 1; // 并发次数
        long totalCost = 0; // 总共耗时
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Future future = threadPool.submit(new runThreadHttp());
            futureList.add(future);
        }
        for (Future f : futureList) {
            try {
                long cost = (long) f.get();
                totalCost = totalCost + cost;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        System.out.println("avg：" + (totalCost / total));
    }

    public static void testHaiHttp() {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://10.8.2.161:9526/hai/HttpEntry"); // url 请求地址
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("service", "updateTriageKnowledgeBase")); // service 流程名称（必填）
            nvps.add(new BasicNameValuePair("urid", "")); //urid 用户名
            nvps.add(new BasicNameValuePair("pwd", "")); //pwd 密码
            nvps.add(new BasicNameValuePair("parameter", "POST /10.0.38.85:8399 HTTP/1.1\r\nContent-Type:application/as-xml\r\nContent-Length:766\r\nConnection: Close\r\n\r\n<BSXml><MsgHeader><Organization>2</Organization><Sender>HESP</Sender><ServiceType>service</ServiceType><MsgType>ODS_1001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>3300</seq><method>updateTriageKnowledgeBase</method><params><hospitalCode>cy01</hospitalCode><hospitalName>a</hospitalName><mainComplaintId>1010102</mainComplaintId><mainComplaintName>a</mainComplaintName><mainComplaintCode>null</mainComplaintCode><mainComplaintParentCode>null</mainComplaintParentCode><mainComplaintPinyinCode>ZDHXJP1</mainComplaintPinyinCode><triageLevel>null</triageLevel><treeLevel>null</treeLevel><departmentId>null</departmentId><cancelSign>null</cancelSign><diseaseType>4</diseaseType><operationType>update</operationType></params></as></MsgBody></BSXml>")); //parameter 字符串格式
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            try {
                if (entity != null) {
                    String str = EntityUtils.toString(entity, "UTF-8");
                    System.out.println(str);
                }
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

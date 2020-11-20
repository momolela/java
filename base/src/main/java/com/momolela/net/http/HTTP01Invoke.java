package com.momolela.net.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class HTTP01Invoke {

    public static void main(String[] args) {
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

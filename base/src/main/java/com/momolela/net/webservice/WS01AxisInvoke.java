package com.momolela.net.webservice;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.soap.SOAPConstants;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WS01AxisInvoke {

    public static void main(String[] args) {
        int total = 10;
        long totalCost = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(200);
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Future future = threadPool.submit(new runThread());
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


    static class runThread implements Callable {

        @Override
        public Long call() {
            String soapAction = "http://ws.access.hai/";
            // 你的webservice地址
            String endpoint = "http://192.168.0.186:9528/hai/WebServiceEntry?wsdl";
            Service service = new Service();
            try {
                Call call = (Call) service.createCall();
                call.setTimeout(new Integer(80000));
                call.setTargetEndpointAddress(new URL(endpoint));
                // 你需要远程调用的方法
                call.setOperationName(new QName(soapAction, "invoke"));

                // 针对 soap1.2 的调用，http 的头部信息：content-type 的值是：application/soap+xml；不同于 soap1.1 的 text/xml
                call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);

                // 方法参数，如果没有参数请无视
                // call.addParameter(new QName(soapaction,"ODS_updatePrescriptionState"), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,""), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,""), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,"<BSXml><MsgHeader><Organization>1</Organization><ServiceType>service</ServiceType><Sender>PTS</Sender><MsgType>ODS_03040009</MsgType><MsgVersion></MsgVersion></MsgHeader><MsgBody><PrescriptionLists><PrescriptionList><VisitOrganization>1</VisitOrganization><HosArea>0</HosArea><PatientType>01</PatientType><RecipeNumber>2086</RecipeNumber><AuditStatus>0</AuditStatus><AuditOpinion>null</AuditOpinion><SupplementType>0</SupplementType><AuditDoctorId/><AuditDoctor/></PrescriptionList></PrescriptionLists></MsgBody></BSXml>"), XMLType.XSD_STRING, ParameterMode.IN);

                // 设置返回类型，对方接口返回的json，我就用string接收了,自定义类型另贴一个代码
                // call.setReturnType(XMLType.XSD_STRING);

                // 调用方法并传递参数，没有参数的话： call.invoke(new Object[] {null});
                call.setEncodingStyle("UTF-8");
                call.addParameter("service", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("urid", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("pwd", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("parameter", Constants.XSD_STRING, String.class, ParameterMode.IN);

                long s = System.currentTimeMillis();
                String result = (String) (call.invoke(new Object[]{"EMRDocumentRetrieve", "", "", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<BSXml>\n" +
                        "  <MsgHeader>\n" +
                        "    <Sender>HIS</Sender>\n" +
                        "    <MsgType>CDR_0103</MsgType>\n" +
                        "    <MsgVersion>3.0</MsgVersion>\n" +
                        "  </MsgHeader>\n" +
                        "  <Document>\n" +
                        "    <DocId>ee05c3e0f87c4db3b3f39c08e9181c46</DocId>\n" +
                        "    <DocCode>PatientBasicInfo</DocCode>\n" +
                        "    <DocFormat>02</DocFormat>\n" +
                        "  </Document>\n" +
                        "</BSXml>\n"}));
                long e = System.currentTimeMillis();
                System.out.println(result);
                long cost = e - s;
                System.out.println(cost);
                return cost;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

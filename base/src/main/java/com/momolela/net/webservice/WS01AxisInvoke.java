package com.momolela.net.webservice;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

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
        int total = 1;
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
            String endpoint = "http://10.8.2.73:9528/hai/WebServiceEntry?wsdl";
            Service service = new Service();
            try {
                Call call = (Call) service.createCall();
                call.setTimeout(new Integer(80000));
                call.setTargetEndpointAddress(new URL(endpoint));
                // 你需要远程调用的方法
                call.setOperationName(new QName(soapAction, "invoke"));

                // 方法参数，如果没有参数请无视
                // call.addParameter(new QName(soapaction,"ODS_updatePrescriptionState"), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,""), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,""), XMLType.XSD_STRING, ParameterMode.IN);
                // call.addParameter(new QName(soapaction,"<BSXml><MsgHeader><Organization>1</Organization><ServiceType>service</ServiceType><Sender>PTS</Sender><MsgType>ODS_03040009</MsgType><MsgVersion></MsgVersion></MsgHeader><MsgBody><PrescriptionLists><PrescriptionList><VisitOrganization>1</VisitOrganization><HosArea>0</HosArea><PatientType>01</PatientType><RecipeNumber>2086</RecipeNumber><AuditStatus>0</AuditStatus><AuditOpinion>null</AuditOpinion><SupplementType>0</SupplementType><AuditDoctorId/><AuditDoctor/></PrescriptionList></PrescriptionLists></MsgBody></BSXml>"), XMLType.XSD_STRING, ParameterMode.IN);

                // 设置返回类型，对方接口返回的json，我就用string接收了,自定义类型另贴一个代码
                // call.setReturnType(XMLType.XSD_STRING);

                // 调用方法并传递参数，没有参数的话： call.invoke(new Object[] { null});
                call.setEncodingStyle("UTF-8");
                call.addParameter("service", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("urid", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("pwd", Constants.XSD_STRING, String.class, ParameterMode.IN);
                call.addParameter("parameter", Constants.XSD_STRING, String.class, ParameterMode.IN);

                long s = System.currentTimeMillis();
                String result = (String) (call.invoke(new Object[]{"phportNotifyHospitalPatients", "", "", "POST /10.8.2.56:60023 HTTP/1.1\n" +
                        "Content-Type:application/as-xml;\n" +
                        "Content-Length:1249\n" +
                        "Connection: Close\n" +
                        "\n" +
                        "<BSXml>  <MsgHeader>    <Organization>46640405-8</Organization>    <Sender>HIS</Sender>    <ServiceType>service</ServiceType>    <MsgType>ODS_03120005</MsgType>    <MsgVersion>3.3</MsgVersion>  </MsgHeader><MsgBody><as><method>phportNotifyHospitalPatients</method><seq>1</seq><params><hospitalCode>46640405-8</hospitalCode><hospitalName>创业智慧医院</hospitalName><patient><action>update</action><inpatientBrid>2659</inpatientBrid><triageNum></triageNum><triageTime>1900-01-01 00:00:00</triageTime><channelType>cs</channelType><triageLevel>1</triageLevel><triageArea>1</triageArea><inpatientNumber>1908000865</inpatientNumber ><outpatientNumber></outpatientNumber><arriveHospitalDate>2020-11-06 15:51:30</arriveHospitalDate><wristbandNum></wristbandNum><wristbandID></wristbandID><patientComplaint></patientComplaint><name>小金刚</name><age>9</age><sex>男</sex><borndate>2020-05-14 12:05:00</borndate><contactPhone></contactPhone><contactName>小金刚</contactName><identityType>身份证</identityType><identityNumber></identityNumber><bloodOxygen></bloodOxygen><pulse></pulse><bodyTemperature></bodyTemperature><breathe></breathe><bloodPressure></bloodPressure><alertness></alertness></patient><tid></tid><pid>1572</pid></params></as></MsgBody></BSXml>"}));
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

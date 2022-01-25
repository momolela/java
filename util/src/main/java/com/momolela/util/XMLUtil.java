package com.momolela.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

public class XMLUtil {
    public static void main(String[] args) throws DocumentException, IOException {
        testEscapeXml1();
        testEscapeXml("<div><'\"&</div>");
    }

    public static void testEscapeXml(String xml) throws DocumentException {
        String xmlNew = StringEscapeUtils.escapeXml(xml);
        System.out.println(xmlNew);

        String unescapeXml = StringEscapeUtils.unescapeXml(xmlNew);
        System.out.println(unescapeXml);

        DocumentHelper.parseText(unescapeXml);
    }

    public static void testEscapeXml1() throws IOException, DocumentException {
        String haha = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><validateInvoiceResponse soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><validateInvoiceReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">{&quot;returncode&quot;:&quot;10006&quot;,&quot;returnmsg&quot;:&quot;&#x672A;&#x83B7;&#x53D6;&#x5230;&#x4EA4;&#x6613;&#x673A;&#x6784;&#x4FE1;&#x606F;&quot;,&quot;fpdm&quot;:&quot;null&quot;,&quot;fphm&quot;:&quot;null&quot;,&quot;kprq&quot;:&quot;null&quot;}\n" +
                "</validateInvoiceReturn></validateInvoiceResponse></soapenv:Body></soapenv:Envelope>";

        System.out.println(haha);

        Document document = DocumentHelper.parseText(haha);

        OutputFormat format = OutputFormat.createPrettyPrint();
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        xw.setEscapeText(false);
        xw.write(document);
        xw.flush();

        String finalRetXml = sw.toString();
        System.out.println("最终返回报文:\n" + finalRetXml);
    }
}

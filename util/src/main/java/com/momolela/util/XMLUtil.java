package com.momolela.util;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class XMLUtil {
    public static void main(String[] args) throws DocumentException, IOException {
        // // 创建 xml
        // Document document = DocumentHelper.createDocument();
        // Element school = document.addElement("school");
        // Element student = school.addElement("student");
        // student.addAttribute("id", "1");
        // Element name = student.addElement("name");
        // // 特殊字符 <
        // name.setText("张三<");
        // System.out.println(documentToString(document, true));

        // String xmlNew = StringEscapeUtils.escapeXml("<div><'\"&</div>");
        // System.out.println(xmlNew);
        // String unescapeXml = StringEscapeUtils.unescapeXml(xmlNew);
        // System.out.println(unescapeXml);
        // // <div><'"&</div> 进行 parseText 会报错
        // DocumentHelper.parseText(unescapeXml);

        // String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        //         "<ClinicalDocument xmlns=\"urn:hl7-org:v3\"\n" +
        //         "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:hl7-org:v3 ../sdschemas/SDA.xsd\">\n" +
        //         "  <!--地域代码-->\n" +
        //         "  <realmCode code=\"CN\"/>\n" +
        //         "</ClinicalDocument>";
        // Document document = DocumentHelper.parseText(xml);
        // // 定义命名空间
        // HashMap<String, Object> nsMap = new HashMap<>();
        // nsMap.put("urn", "urn:hl7-org:v3");
        // XPath xPath = document.createXPath("//urn:ClinicalDocument//urn:realmCode");
        // xPath.setNamespaceURIs(nsMap);
        // Element element = (Element) xPath.selectSingleNode(document);
        // System.out.println(element.attributeValue("code"));
    }


    /**
     * document 通过格式化器转字符串
     *
     * @param document document
     * @param escape   escape
     * @return string
     */
    public static String documentToString(Document document, boolean escape) {
        try {
            // xml 格式化器
            OutputFormat format = OutputFormat.createPrettyPrint();
            StringWriter sw = new StringWriter();
            XMLWriter xw = new XMLWriter(sw, format);
            // 配置是否转义
            xw.setEscapeText(escape);
            xw.write(document);
            xw.flush();
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

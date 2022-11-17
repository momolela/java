package com.momolela.webservice.publish;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.FileReader;
import java.io.IOException;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING) // 定义 soap1.2 的协议方式
public class HelloServiceImpl implements HelloService {

    @Override
    @WebMethod
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "hello " + name;
    }

    @Override
    @WebMethod
    public String helloBigData() throws Exception {
        FileReader reader = new FileReader("bigdata.txt");
        StringBuffer sb = new StringBuffer();
        int num = 0;
        while ((num = reader.read()) != -1) {
            sb.append((char) num);
        }
        reader.close();
        String str = sb.toString();

        // String returnStr = gzipCompress(str);
        // System.out.println(returnStr);
        // System.out.println("==========================================");
        // System.out.println(gzipDeCompress(returnStr));
        //
        //
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         try {
        //             MessageFactory messageFactory = MessageFactory.newInstance();
        //             MimeHeaders mime = new MimeHeaders();
        //             SOAPMessage message = messageFactory.createMessage(mime, new ByteArrayInputStream(str.getBytes("UTF-8")));
        //             SOAPBody soapBody = message.getSOAPBody();
        //             Node fNode = getFirstNoTextChild(soapBody);  //(Node) (soapBody.getFirstChild());
        //             if (fNode == null)
        //                 System.out.println("");
        //             if ("FAULT".equals(fNode.getLocalName().toUpperCase())) {// 异常情况
        //                 throw new SOAPException(fNode.getTextContent());
        //             } else {
        //                 Node resultNode = getFirstNoTextChild(fNode);//(Node) (resultNodeFist.getFirstChild());
        //                 Object reObj;
        //                 if (resultNode == null)
        //                     reObj = "";
        //                 else {
        //                     reObj = getNodeValue(resultNode);
        //                 }
        //                 System.out.println(reObj);
        //             }
        //             System.out.println("soap 返回值压缩后解压缩成功...");
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }).start();

        return str;
    }

    /**
     * gzip 压缩
     *
     * @param str
     * @return
     * @throws IOException
     */
    // public String gzipCompress(String str) throws IOException {
    //     ByteArrayOutputStream out = new ByteArrayOutputStream();
    //     GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
    //     gzipOutputStream.write(str.getBytes());
    //     gzipOutputStream.close();
    //     String returnStr = new BASE64Encoder().encode(out.toByteArray());
    //     return returnStr;
    // }

    /**
     * gzip 解压缩
     */
    // public String gzipDeCompress(String str) {
    //     ByteArrayOutputStream out = new ByteArrayOutputStream();
    //     ByteArrayInputStream in = null;
    //     GZIPInputStream ginzip = null;
    //     byte[] compressed = null;
    //     String decompressed = null;
    //     try {
    //         compressed = new sun.misc.BASE64Decoder().decodeBuffer(str);
    //         in = new ByteArrayInputStream(compressed);
    //         ginzip = new GZIPInputStream(in);
    //         byte[] buffer = new byte[1024];
    //         int offset = -1;
    //         while ((offset = ginzip.read(buffer)) != -1) {
    //             out.write(buffer, 0, offset);
    //         }
    //         decompressed = out.toString();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (ginzip != null) {
    //             try {
    //                 ginzip.close();
    //             } catch (IOException e) {
    //             }
    //         }
    //         if (in != null) {
    //             try {
    //                 in.close();
    //             } catch (IOException e) {
    //             }
    //         }
    //         if (out != null) {
    //             try {
    //                 out.close();
    //             } catch (IOException e) {
    //             }
    //         }
    //     }
    //     return decompressed;
    // }

    // private Node getFirstNoTextChild(Node node) {
    //     NodeList nodeList = node.getChildNodes();
    //     for (int i = 0; i < nodeList.getLength(); i++) {
    //         Node element = nodeList.item(i);
    //         if (element.getNodeType() != 3)
    //             return element;
    //     }
    //     return null;
    // }

    // private Object getNodeValue(Node node) {
    //     NodeList nodeList = node.getChildNodes();
    //     for (int i = 0; i < nodeList.getLength(); i++) {
    //         Node element = nodeList.item(i);
    //         if (element.getNodeType() == 1)
    //             return nodeToString(node);
    //     }
    //     return node.getTextContent();
    // }

    // private String nodeToString(Node node) {
    //     Transformer transformer = null;
    //     String result = null;
    //     if (node == null) {
    //         throw new IllegalArgumentException();
    //     }
    //     try {
    //         transformer = TransformerFactory.newInstance().newTransformer();
    //     } catch (Exception e) {
    //         throw new RuntimeException(e.getMessage());
    //     }
    //     if (transformer != null) {
    //         try {
    //             StringWriter sw = new StringWriter();
    //
    //             transformer.transform(new DOMSource(node), new StreamResult(sw));
    //             String xml = sw.toString();
    //             xml = xml.substring(38);
    //             return xml;
    //         } catch (TransformerException te) {
    //             throw new RuntimeException(te.getMessage());
    //         }
    //     }
    //     return result;
    // }
}

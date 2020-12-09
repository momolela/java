package com.momolela.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Net06TCPServer {
    public static void main(String[] args) throws IOException {
        TCPSocketRead();
    }

    private static void TCPSocketRead() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(10001, 3); // 指定只能建立3个连接，backlog
        ServerSocket serverSocket = new ServerSocket(10001);
        while (true) {
            Socket socket = serverSocket.accept();// 阻塞，没有数据就等，如果有数据，会使用对应的客户端对象，利用该对象的读取流读取数据
            InputStream inputStream = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = inputStream.read(buff); // 读取来自客户端的数据
            System.out.println("来自客户端的数据 【" + new String(buff, 0, len, "utf-8") + "】");
            OutputStream outputStream = socket.getOutputStream(); // 也会使用对应的客户端对象，利用该对象的写入流写数据
            String message = "HTTP/1.1 200 OK\n" +
            "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>HTTP/1.1 200 OK\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/as-xml\n" +
                    "Content-Length: 1241\n" +
                    "\n" +
                    "<BSXml>\n" +
                    "<MsgHeader>\n" +
                    "<Organization></Organization>\n" +
                    "<Sender>HESP</Sender>\n" +
                    "<ServiceType>service</ServiceType>\n" +
                    "<MsgType></MsgType>\n" +
                    "<MsgVersion>3.3</MsgVersion>\n" +
                    "</MsgHeader>\n" +
                    "<MsgBody>\n" +
                    "<Status>true</Status>\n" +
                    "<Code>0</Code>\n" +
                    "<Detail>Success</Detail>\n" +
                    "<Data>\n" +
                    "<as>\n" +
                    "<seq>4889</seq>\n" +
                    "<method>phportGetWristbandsResponse</method>\n" +
                    "<params>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0001</wristbandNum>\n" +
                    "<wristbandID>41</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>0101</wristbandNum>\n" +
                    "<wristbandID>42</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<wristband>\n" +
                    "<hospitalCode>cy01</hospitalCode>\n" +
                    "<hospitalName>创业测试医院</hospitalName>\n" +
                    "<wristbandNum>2222</wristbandNum>\n" +
                    "<wristbandID>43</wristbandID>\n" +
                    "<state>normal</state>\n" +
                    "<del>0</del>\n" +
                    "<deposit>hospital</deposit>\n" +
                    "<lowElectricity>0</lowElectricity>\n" +
                    "</wristband>\n" +
                    "<responseCode>0</responseCode>\n" +
                    "<responseDesc>Success</responseDesc>\n" +
                    "</params>\n" +
                    "</as>\n" +
                    "</Data>\n" +
                    "</MsgBody>\n" +
                    "</BSXml>";
            outputStream.write(message.getBytes()); // 向客户端返回数据
            socket.close(); // 关闭 socket 连接
//        serverSocket.close(); // 关闭服务端
        }
    }
}

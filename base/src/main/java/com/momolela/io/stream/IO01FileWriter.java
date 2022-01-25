package com.momolela.io.stream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IO01FileWriter {

    public static void main(String[] args) {
        FileWriter writer = null;
        try {
            // 创建一个 FileWriter 对象，该对象被创建的时候就需要明确操作的文件
            // 文件不存在会自动创建
            // 文件存在会创建并且覆盖，第二个参数 append ，如果为 true ，就是续写文件，文件不会删除
            // writer = new FileWriter("test.txt", true);
            writer = new FileWriter("D:" + File.separator + "test.txt"); // 会抛出异常

            // 调用 writer 方法，将字符写入到流中
            writer.write("POST /10.0.38.85:8399 HTTP/1.1\r\nContent-Type:application/as-xml\r\nContent-Length:766\r\nConnection: Close\r\n\r\n<BSXml><MsgHeader><Organization>2</Organization><Sender>HESP</Sender><ServiceType>service</ServiceType><MsgType>ODS_1001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>3300</seq><method>updateTriageKnowledgeBase</method><params><hospitalCode>cy01</hospitalCode><hospitalName>a</hospitalName><mainComplaintId>1010102</mainComplaintId><mainComplaintName>a</mainComplaintName><mainComplaintCode>null</mainComplaintCode><mainComplaintParentCode>null</mainComplaintParentCode><mainComplaintPinyinCode>ZDHXJP1</mainComplaintPinyinCode><triageLevel>null</triageLevel><treeLevel>null</treeLevel><departmentId>null</departmentId><cancelSign>null</cancelSign><diseaseType>4</diseaseType><operationType>update</operationType></params></as></MsgBody></BSXml>"); // linux 中是 \n ，windows 中是 \r\n

            // 将流中的字符刷入到文件中
            // 刷完之后，还能继续写入，继续刷入
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // 关闭流资源
                // 而且在关闭流资源之前，会再一次将流中的字符刷入到文件中，确保流中没有字符
                // 关闭流资源后不能再写入
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

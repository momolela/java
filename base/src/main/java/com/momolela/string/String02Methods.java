package com.momolela.string;

import java.io.UnsupportedEncodingException;

public class String02Methods {
    public static void main(String[] args) {

        String str = "abbacsunzj";

        // 获取
        System.out.println(str.length()); // 10 获取字符串的长度
        System.out.println(str.charAt(5)); // s 获取指定位置的char
        System.out.println(str.indexOf("sunzj")); // 5
        System.out.println(str.lastIndexOf("sunzj")); // 5
        System.out.println(str.substring(5, 10)); // sunzj 包含头不包含尾


        // 判断
        System.out.println(str.startsWith("sunzj")); // false
        System.out.println(str.endsWith("sunzj")); // true
        System.out.println(str.isEmpty()); // false
        System.out.println(str.contains("sunzj")); // true
        System.out.println(str.equals("sunzj")); // false
        System.out.println(str.equalsIgnoreCase("abbacSunZj")); // true


        // 转换
        char[] arr = {'a', 'b', 'c', 'd', 'e', 'f'};
        System.out.println(new String(arr)); // abcdef 字符数组转字符串
        System.out.println(str.copyValueOf(arr)); // abcdef 字符数组转字符串
        System.out.println(str.valueOf(arr)); // abcdef 字符数组转字符串,valueOf()可以将基本数据类型转字符串
        System.out.println(str.toCharArray()); // abbacsunzj 字符串转字符数组
        System.out.println(3 + ""); // 3 转字符串
        try {
            System.out.println(new String(str.getBytes("UTF-8"))); // abbacsunzj 将字节数组转字符串
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // 替换
        String replace = str.replace("sunzj", "hufy");
        System.out.println(replace); // abbachufy


        // 切割
        System.out.println(str.split("b")); // [Ljava.lang.String;@66133adc


        // 转换大小写
        System.out.println(str.toUpperCase()); // ABBACSUNZJ
        System.out.println(str.toLowerCase()); // abbacsunzj


        // 去空格
        System.out.println(str.trim()); // abbacsunzj


        // 比较
        System.out.println(str.compareTo("bbbbbsunzj")); // -1


        // 回车换行替换换行
        String haha = "POST /10.0.38.85:8399 HTTP/1.1\nContent-Type:application/as-xml\nContent-Length:766\nConnection: Close\n\n<BSXml><MsgHeader><Organization>2</Organization><Sender>HESP</Sender><ServiceType>service</ServiceType><MsgType>ODS_1001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>3300</seq><method>updateTriageKnowledgeBase</method><params><hospitalCode>cy01</hospitalCode><hospitalName>a</hospitalName><mainComplaintId>1010102</mainComplaintId><mainComplaintName>a</mainComplaintName><mainComplaintCode>null</mainComplaintCode><mainComplaintParentCode>null</mainComplaintParentCode><mainComplaintPinyinCode>ZDHXJP1</mainComplaintPinyinCode><triageLevel>null</triageLevel><treeLevel>null</treeLevel><departmentId>null</departmentId><cancelSign>null</cancelSign><diseaseType>4</diseaseType><operationType>update</operationType></params></as></MsgBody></BSXml>";
        haha = haha.replaceAll("\\n", "\\r\\n");
        System.out.println(haha); // POST /10.0.38.85:8399 HTTP/1.1rnContent-Type:application/as-xmlrnContent-Length:766rnConnection: Closernrn<BSXml><MsgHeader><Organization>2</Organization><Sender>HESP</Sender><ServiceType>service</ServiceType><MsgType>ODS_1001</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>3300</seq><method>updateTriageKnowledgeBase</method><params><hospitalCode>cy01</hospitalCode><hospitalName>a</hospitalName><mainComplaintId>1010102</mainComplaintId><mainComplaintName>a</mainComplaintName><mainComplaintCode>null</mainComplaintCode><mainComplaintParentCode>null</mainComplaintParentCode><mainComplaintPinyinCode>ZDHXJP1</mainComplaintPinyinCode><triageLevel>null</triageLevel><treeLevel>null</treeLevel><departmentId>null</departmentId><cancelSign>null</cancelSign><diseaseType>4</diseaseType><operationType>update</operationType></params></as></MsgBody></BSXml>


        // 截取 beginIndex（包含在内） endIndex（不包含在内）
        System.out.println("fddfs_sdfsd".substring(0, "fddfs_sdfsd".indexOf("_"))); // fddfs
        System.out.println("fddfs_qwert".substring(3)); // fs_qwert
    }
}

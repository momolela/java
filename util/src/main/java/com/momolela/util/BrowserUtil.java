package com.momolela.util;




import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 浏览器相关工具
 */
public class BrowserUtil {

    public static void main(String[] args) {

    }

    private static final String[] IEBrowserSignals = {"msie", "trident", "edge"};

    /**
     * 判断是否是IE浏览器
     *
     * @param userAgent 浏览器标识
     * @return boolean
     */
    public static boolean isMSBrowser(String userAgent) {
        for (String signal : IEBrowserSignals) {
            if (userAgent.toLowerCase().contains(signal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 针对不同浏览器文件名乱码处理
     *
     * @param fileName  文件名
     * @param userAgent 浏览器标识
     * @throws UnsupportedEncodingException
     */
    public static String formatFileName(String fileName, String userAgent) throws UnsupportedEncodingException {
        String new_filename = URLEncoder.encode(fileName, "UTF8");
        // IE 浏览器，只能采用 URLEncoder 编码
        if (isMSBrowser(userAgent)) {
            return "\"" + new_filename + "\"";
        }
        // Opera 浏览器只能采用 filename*
        else if (userAgent.contains("opera")) {
            return "UTF-8''" + new_filename;
        }
        // Safari 浏览器，只能采用 ISO 编码的中文输出
        else if (userAgent.contains("safari")) {
            return "\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"";
        }
        // Chrome 浏览器，只能采用 MimeUtility 编码或 ISO 编码的中文输出
        else if (userAgent.contains("applewebkit")) {
            new_filename = MimeUtility.encodeText(fileName, "UTF8", "B");
            return "\"" + new_filename + "\"";
        }
        // FireFox 浏览器，可以使用 MimeUtility 或 filename* 或 ISO 编码的中文输出
        else if (userAgent.contains("mozilla")) {
            return "\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"";
        }
        return null;
    }
}

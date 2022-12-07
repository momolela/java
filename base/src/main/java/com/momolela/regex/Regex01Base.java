package com.momolela.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex01Base {
    public static void main(String[] args) {
        replace();
        System.out.println(xX2x_x("wechatApplet"));
    }

    public static void demo1() {
        // 将 ## 替换为 #{}
        String line = "nihao#dfd#ggfgd#fg#gfd";
        Pattern pattern = Pattern.compile("\\#[A-Za-z0-9_]*\\#");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String ori = matcher.group();
            String str = "#{" + ori.substring(1, ori.length() - 1) + "}";
            line = line.replace(ori, str);
        }
        System.out.println(line); // nihao#{dfd}ggfgd#{fg}gfd
    }

    public static void demo2() {
        // 取出 bsoft-service: \r\n 中的内容
        String regex = "bsoft-service: (.*?)\\r\\n";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("nihao#dfbsoft-service: nihao\r\nggfgd#fbsoft-service: [g#gf]\r\nd");
        while (matcher.find()) {
            System.out.println(matcher.group(1)); // nihao    [g#gf]
        }
    }

    public static void find() {
        String regex = "bsoft-service: (.*?)\\r\\n";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("nihao#dfbsoft-service: nihao\r\nggfgd#fbsoft-service: [g#gf]\r\nd");
        System.out.println(matcher.lookingAt()); // false，Matcher.lookingAt()字符串进行匹配,只有匹配到的字符串在最前面才返回 true
        System.out.println(matcher.find()); // true，有点 .next() 的感觉
        System.out.println(matcher.find(37)); // true
        System.out.println(matcher.find(38)); // false
        System.out.println(matcher.matches()); // false，尝试将整个区域与模式匹配，匹配上返回 true
    }

    public static void replace() {
        String regex = "bsoft-service: (.*?)\\r\\n";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("nihao#dfbsoft-service: nihao\r\nggfgd#fbsoft-service: [g#gf]\r\nd");
        // appendReplacement()
        // while (matcher.find()) {
        //     StringBuffer sb = new StringBuffer();
        //     // 将当前匹配子串替换为指定字符串，
        //     // 并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个 StringBuffer 对象里
        //     matcher.appendReplacement(sb, "sunzj");
        //     System.out.println(sb);
        // }

        // appendTail()
        while (matcher.find()) {
            StringBuffer sb = new StringBuffer();
            // 方法则将最后一次匹配工作后剩余的字符串添加到一个 StringBuffer 对象里
            matcher.appendTail(sb);
            System.out.println(sb);
        }
        // replaceAll()
        // replaceFirst()
        // quoteReplacement()
    }

    /**
     * 将驼峰转为下划线
     */
    public static String xX2x_x(String str) {
        Pattern compile = Pattern.compile("[A-Z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将下划线转为驼峰
     */
    public static String x_x2xX(String str) {
        str = str.toLowerCase();
        Pattern compile = Pattern.compile("_[a-z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase().replace("_", ""));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

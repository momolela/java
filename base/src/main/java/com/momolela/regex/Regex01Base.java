package com.momolela.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex01Base {
    public static void main(String[] args) {
        // 将 ## 替换为 #{}
        String line = "nihao#dfd#ggfgd#fg#gfd";
        Pattern pattern = Pattern.compile("\\#[A-Za-z0-9_]*\\#");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String ori = matcher.group();
            String str = "#{" + ori.substring(1, ori.length() - 1) + "}";
            line = line.replace(ori, str);
        }
        System.out.println(line);


        // 取出 bsoft-service: \r\n 中的内容
        String regex = "bsoft-service: (.*?)\\r\\n";
        Pattern pattern1 = Pattern.compile(regex);
        Matcher matcher1 = pattern1.matcher("nihao#dfbsoft-service: nihao\r\nggfgd#fbsoft-service: [g#gf]\r\nd");
        while (matcher1.find()) {
            System.out.println(matcher1.group(1));
        }
    }
}

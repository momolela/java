package com.momolela.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex01Base {
    public static void main(String[] args) {
        String line = "nihao#dfd#ggfgd#fg#gfd";
        Pattern pattern = Pattern.compile("\\#[A-Za-z0-9_]*\\#");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String ori = matcher.group();
            String str = "#{" + ori.substring(1, ori.length() - 1) + "}";
            line = line.replace(ori, str);
        }
        System.out.println(line);
    }
}

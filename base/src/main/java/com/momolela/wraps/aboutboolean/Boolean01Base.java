package com.momolela.wraps.aboutboolean;

public class Boolean01Base {

    public static void main(String[] args) {
        // 字符串转 boolean
        System.out.println(Boolean.parseBoolean("truE"));

        // 获取 System 中的值类型为 boolean 的参数，内部用了 Boolean.parseBoolean 去转
        System.setProperty("custom", "true");
        System.out.println(Boolean.getBoolean("custom"));
    }

}

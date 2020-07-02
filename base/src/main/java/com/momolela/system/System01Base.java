package com.momolela.system;

import java.util.Properties;

public class System01Base {

    public static void main(String[] args) {
        System.out.println("标准输出\n");

        System.setProperty("sunzj", "hufy");

        // 以此来做跨平台处理的操作
        System.getProperty("os.name");

        /**
         * Properties 是 HashTable 的子类，也就是 Map 接口的子类，所以能用 Map 接口公共的方法
         */
        Properties properties = System.getProperties();
        for (Object obj : properties.keySet()) {
            Object value = properties.get(obj);
            System.out.println(obj + " === " + value);
        }

        /**
         * 在 jvm 启动的时候 setProperty，可以用 java -D<key>=<value> 程序字节码
         */
    }

}

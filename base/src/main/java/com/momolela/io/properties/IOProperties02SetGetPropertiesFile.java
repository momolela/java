package com.momolela.io.properties;

import java.io.IOException;

public class IOProperties02SetGetPropertiesFile {
    public static void main(String[] args) throws IOException {
//        Properties prop = new Properties();
//        FileInputStream fis = new FileInputStream("test.properties");
//        prop.load(fis); // 加载配置文件并且存储到 prop 中
//        prop.list(System.out); // 将 prop 中的数据展示到控制台
//
//        prop.setProperty("liuh", "26");
//
//        FileOutputStream fos = new FileOutputStream("test.properties");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        prop.store(fos, "这是修改配置文件的注释 " + dateFormat.format(new Date())); // 将修改的数据重新存入到 test.properties 文件中
//
//        fos.close();
//        fis.close();

        for (int i = 0; i < 1000; i++) {

        long l = 1 + (long) (Math.random() * (2 - 1));

        System.out.println(l);
        }


    }
}

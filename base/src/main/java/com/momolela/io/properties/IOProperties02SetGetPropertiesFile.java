package com.momolela.io.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class IOProperties02SetGetPropertiesFile {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("test.properties");
        prop.load(fis); // 加载配置文件并且存储到 prop 中
        prop.list(System.out); // 将 prop 中的数据展示到控制台

        prop.setProperty("hufy", "25");
        FileOutputStream fos = new FileOutputStream("test.properties");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        prop.store(fos, null); // 将修改的数据重新存入到 test.properties 文件中

        fos.close();
        fis.close();
    }


}

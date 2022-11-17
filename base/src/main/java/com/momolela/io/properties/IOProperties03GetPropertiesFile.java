package com.momolela.io.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IOProperties03GetPropertiesFile {

    private static Properties properties = new Properties();

    static {
        InputStream resourceAsStream = IOProperties03GetPropertiesFile.class.getResourceAsStream("/test1.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
        System.out.println(IOProperties03GetPropertiesFile.properties.get("sunzj"));
    }

}

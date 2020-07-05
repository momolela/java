package com.momolela.io.properties;

import java.util.Properties;
import java.util.Set;

public class IOProperties01SetGet {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("sunzj", "25");
        properties.setProperty("hufy", "24");

        System.out.println("sunzj's age is " + properties.get("sunzj"));

        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            System.out.println(propertyName + "=" + properties.getProperty(propertyName));
        }
    }
}

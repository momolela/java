package com.momolela.util;

import java.util.Properties;

/**
 * 操作系统相关工具
 */
public class OsUtil {

    public enum OSType {
        WINDWODS, LIUNX, UNNOWE, OSX
    }

    public static void main(String[] args) {
        System.out.println(getOsType());
    }

    public static OSType getOsType() {
        Properties props = System.getProperties();
        if (props.getProperty("os.name").toLowerCase().contains("windows")) {
            return OSType.WINDWODS;
        } else if (props.getProperty("os.name").toLowerCase().contains("linux")) {
            return OSType.LIUNX;
        } else if (props.getProperty("os.name").toLowerCase().contains("mac")) {
            return OSType.OSX;
        }
        return OSType.UNNOWE;
    }
}

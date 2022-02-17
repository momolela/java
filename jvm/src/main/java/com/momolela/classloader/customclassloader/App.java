package com.momolela.classloader.customclassloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) {
        DiskClassloader diskClassloader = new DiskClassloader("/Users/sunzj/idea/java/classloader/customclassloader");
        try {
            Class c = diskClassloader.loadClass("com.momolela.classloader.customclassloader.Test");
            if (c != null) {
                System.out.println(c.getName() + ".class 的类加载器是：" + c.getClassLoader());
                Object obj = c.newInstance();
                Method method = c.getDeclaredMethod("sayHello", null);
                method.invoke(obj, null);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

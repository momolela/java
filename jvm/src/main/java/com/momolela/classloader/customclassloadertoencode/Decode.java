package com.momolela.classloader.customclassloadertoencode;

import com.momolela.classloader.customclassloader.DiskClassloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Decode {
    public static void main(String[] args) {
        DeClassLoader deClassLoader = new DeClassLoader("/Users/sunzj/idea/java/classloader/customclassloadertoencode");
        try {
            Class<?> c = deClassLoader.loadClass("com.momolela.classloader.customclassloadertoencode.Test");
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

package com.momolela.classloader.contextclassloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) {
        DiskClassLoader diskLoader1 = new DiskClassLoader("/Users/sunzj/idea/java/classloader/contextclassloader/1");
        Class cls1 = null;
        try {
            cls1 = diskLoader1.loadClass("com.momolela.classloader.contextclassloader.SpeakTest");
            System.out.println(cls1.getClassLoader().toString());
            if (cls1 != null) {
                try {
                    Object obj = cls1.newInstance();
                    // SpeakTest1 speak = (SpeakTest1) obj;
                    // speak.speak();
                    Method method = cls1.getDeclaredMethod("speak", null);
                    method.invoke(obj, null);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DiskClassLoader diskLoader = new DiskClassLoader("/Users/sunzj/idea/java/classloader/contextclassloader/2");
        System.out.println("Thread " + Thread.currentThread().getName() + " classloader: " + Thread.currentThread().getContextClassLoader().toString());
        new Thread(() -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " classloader: " + Thread.currentThread().getContextClassLoader().toString());
            try {
                // Thread.currentThread().setContextClassLoader(diskLoader); // 开启后就不会报错了
                // Class c = diskLoader.loadClass("com.frank.test.SpeakTest");
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                Class c = cl.loadClass("com.momolela.classloader.contextclassloader.SpeakTest");
                // Class c = Class.forName("com.frank.test.SpeakTest");
                System.out.println(c.getClassLoader().toString());
                if (c != null) {
                    try {
                        Object obj = c.newInstance();
                        // SpeakTest1 speak = (SpeakTest1) obj;
                        // speak.speak();
                        Method method = c.getDeclaredMethod("speak", null);
                        method.invoke(obj, null);
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

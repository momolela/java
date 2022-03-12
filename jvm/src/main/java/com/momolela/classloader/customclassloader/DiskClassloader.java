package com.momolela.classloader.customclassloader;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义类加载器
 * 1、继承 ClassLoader
 * 2、重写 findClass
 * 3、在 findClass 中通过 defineClass 生成 class 对象
 */
public class DiskClassloader extends ClassLoader {

    private String classPath;

    public DiskClassloader(String path) {
        this.classPath = path;
    }

    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        if (name == null || "".equals(name)) {
            throw new RuntimeException("请传入正确的类路径");
        }
        String fileName = getFileName(name);
        File file = new File(classPath, fileName);
        try {
            // 读取字节码文件到 byte[]
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            int line = 0;
            while ((line = inputStream.read()) != -1) {
                outputStream.write(line);
            }
            byte[] data = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();
            // 通过调用 defineClass() 方法生成 class 对象
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        String fileName;
        int index = name.lastIndexOf(".");
        if (index == -1) {
            fileName = name + ".class";
        } else {
            fileName = name.substring(index + 1) + ".class";
        }
        return fileName;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                /**
                 * 直接执行findClass()...什么意思呢?
                 * 首先会使用自定义类加载器加载类, 不在向上委托, 直接由自己执行
                 *
                 * jvm 自带的类还是需要由引导类加载器自动加载
                 */
                if (!name.startsWith("com.momolela.classloader.customclassloader")) {
                    c = this.getParent().loadClass(name);
                } else {
                    c = findClass(name); // 直接由自定义类加载器执行加载
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}

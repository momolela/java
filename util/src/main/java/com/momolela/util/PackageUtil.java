package com.momolela.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * java 包下相关工具
 */
public class PackageUtil {
    public static void main(String[] args) {
        Set<Class<?>> classes = getClasses("com.momolela.util");
        System.out.println(classes);
    }

    /**
     * 从包 package 中获取所有的 Class
     *
     * @param packagePath 包名路径
     * @return Set<Class>
     */
    public static Set<Class<?>> getClasses(String packagePath) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 是否递归
        boolean recursion = true;
        // 获取包的名字 并进行替换
        String packageName = packagePath;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合，并进行循环来处理这个目录下的 things
        Enumeration<URL> dirs;
        try {
            // 通过包名获取资源路径
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, true, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        // 获取 jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            // 获取 jar 里的一个实体，可以是目录和一些 jar 包里的其他文件，如 META-INF 等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以 / 开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾，是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去，并且是一个包
                                if ((idx != -1) || recursion) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到 classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有 Class
     *
     * @param packageName 包名路径
     * @param filePath 文件路径
     * @param recursion 是否递归
     * @param classes classes 集合
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String filePath, final boolean recursion, Set<Class<?>> classes) {
        // 获取此包的目录，建立一个File
        File dir = new File(filePath);
        // 如果不存在或者也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在，就获取包下的所有文件包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以 .class 结尾的文件(编译好的java类文件)
        File[] dirFiles = dir.listFiles(file -> (recursion && file.isDirectory()) || (file.getName().endsWith(".class")));
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursion, classes);
            } else {
                // 如果是 java 类文件 去掉后面的 .class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' + className));
                    // 这里用forName有一些不好，会触发 static 方法，没有使用 classLoader 的 load 干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.momolela.classloader.classloaderorder;

public class ClassloaderTest {
    public static void main(String[] args) {
        ClassLoader cl1 = Test.class.getClassLoader();
        // Test.class's Classloader is sun.misc.Launcher$AppClassLoader@18b4aac2
        // 说明类加载器是 AppClassLoader
        System.out.println("Test.class's Classloader is " + cl1.toString());

        // Test.class's Classloader's parent is:sun.misc.Launcher$ExtClassLoader@266474c2
        // 说明 AppClassLoader 父类加载器是 ExtClassLoader
        System.out.println("Test.class's Classloader's parent is:" + cl1.getParent().toString());

        // 会报空指针异常，因为父加载器是 Bootstrap ClassLoader
        // Bootstrap ClassLoader 是 C 写的，java 加载不了，所以报空指针异常
        // System.out.println("Test.class's Classloader's parent's parent is:" + cl1.getParent().getParent().toString());

        ClassLoader cl2 = int.class.getClassLoader();
        // 会报错，cl2.toString() 报空指针，并不是说明没有类加载器
        // 类加载器是 Bootstrap ClassLoader
        // System.out.println("int.class's Classloader is:" + cl2.toString());

        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/resources.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/rt.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/sunrsasign.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jsse.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jce.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/charsets.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jfr.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/classes
        // 输出 Bootstrap ClassLoader 类加载器的加载路径
        System.out.println(System.getProperty("sun.boot.class.path"));

        // /Users/sunzj/Library/Java/Extensions:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext:
        // /Library/Java/Extensions:
        // /Network/Library/Java/Extensions:
        // /System/Library/Java/Extensions:/usr/lib/java
        // 输出 ExtClassLoader 类加载器的加载路径
        System.out.println(System.getProperty("java.ext.dirs"));

        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/charsets.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/dnsns.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/jaccess.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/legacy8ujsse.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/localedata.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/nashorn.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/openjsse.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/sunec.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/ext/zipfs.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jce.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jfr.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/jsse.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/management-agent.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/resources.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/jre/lib/rt.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/lib/dt.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/lib/jconsole.jar:
        // /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/lib/tools.jar:
        // /Users/sunzj/idea/java/jvm/target/classes:
        // /Users/sunzj/maven repository/com/google/code/gson/gson/2.8.2/gson-2.8.2.jar:
        // /Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:
        // /Users/sunzj/Library/Caches/JetBrains/IntelliJIdea2021.2/captureAgent/debugger-agent.jar
        // 输出 AppClassLoader 类加载器的加载路径，其实就是当前 java 工程bin目录等，里面存放的是编译生成的 class 文件，也就是 java.class.path
        System.out.println(System.getProperty("java.class.path"));
    }
}

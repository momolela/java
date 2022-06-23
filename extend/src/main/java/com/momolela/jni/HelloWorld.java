package com.momolela.jni;

public class HelloWorld {

    public native void hello();

    static {
        // 设置查找路径为当前项目路径
        // System.setProperty("java.library.path", ".");
        // 加载动态库的名称
        // System.loadLibrary("hello");

        // 加载动态库的名称
        System.load("/Users/sunzj/idea/java/extend/src/main/java/com/momolela/jni/libhello.jnilib");
    }

    public static void main(String[] args) {
        new HelloWorld().hello();
    }
}

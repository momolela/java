> jni 的示例，用 Java 调用 mac 上 c 语言的功能实现，具体需要以下步骤

### 1. 编写 HelloWorld 的 Java 文件
```java
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

```

### 2. 编译 HelloWorld 生成 class 文件
```shell
javac HelloWorld.java
```

### 3. 生成 com_momolela_jni_HelloWorld.h 头文件标准
```shell
javah -classpath /Users/sunzj/idea/java/extend/src/main/java com.momolela.jni.HelloWorld
```

### 4. 编写 HelloWorldImpl.c 文件
```java
#include "jni.h"
#include "com_momolela_jni_HelloWorld.h"
#include <stdio.h>
JNIEXPORT void JNICALL Java_com_momolela_jni_HelloWorld_hello(JNIEnv *env, jobject obj){
    printf("Hello World!\n");
    return;
}
```

### 5. 为防止报错，做文件拷贝
* /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/include/darwin/jni_md.h 复制到 /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/include

### 6. 通过 HelloWorldImpl.c 文件生成动态链接库文件 libhello.jnilib
```shell
gcc -dynamiclib -I /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/include HelloWorldImpl.c -o libhello.jnilib
```

### 7. 运行 HelloWorld 的 main 方法，会打印 Hello World!
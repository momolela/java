#include "jni.h"
#include "com_momolela_jni_HelloWorld.h"
#include <stdio.h>
JNIEXPORT void JNICALL Java_com_momolela_jni_HelloWorld_hello(JNIEnv *env, jobject obj){
    printf("Hello World!\n");
    return;
}

package com.momolela.thread;

import com.momolela.util.*;

public class Thread16getStack {
    public static void main(String[] args) {
        getName4();
    }

    public static void getName1() {

        // com.momolela.thread.Thread16getStack.getName1(Thread16getStack.java:25)
        // com.momolela.thread.Thread16getStack.getName2(Thread16getStack.java:30)
        // com.momolela.thread.Thread16getStack.getName3(Thread16getStack.java:34)
        // com.momolela.thread.Thread16getStack.getName4(Thread16getStack.java:38)
        // com.momolela.thread.Thread16getStack.main(Thread16getStack.java:7)
        System.out.println(ThreadUtil.getSignedCallHierarchy(Thread16getStack.class));

    }

    public static void getName2() {
        getName1();
    }

    public static void getName3() {
        getName2();
    }

    public static void getName4() {
        getName3();
    }
}

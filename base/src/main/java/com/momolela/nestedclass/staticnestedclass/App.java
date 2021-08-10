package com.momolela.nestedclass.staticnestedclass;

class Outer {
    private static String name = "sunzj";

    public static class Inner { // 静态内部类
        static String aliasName = "jg";

        public void sayName() {
            System.out.println(name);
            // System.out.println(Outer.this.name);
        }
    }
}

public class App {
    public static void main(String[] args) {
        new Outer.Inner().sayName();
        System.out.println(Outer.Inner.aliasName);
    }
}

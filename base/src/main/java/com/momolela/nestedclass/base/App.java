package com.momolela.nestedclass.base;

class Outer {
    private String name = "sunzj";
    public class Inner {
        public void sayName() {
            System.out.println(name);
            System.out.println(Outer.this.name);
        }
    }
}

public class App {
    public static void main(String[] args) {
        Outer.Inner inner = new Outer().new Inner();
        inner.sayName();
    }
}
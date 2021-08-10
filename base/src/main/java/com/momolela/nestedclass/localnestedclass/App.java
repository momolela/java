package com.momolela.nestedclass.localnestedclass;

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

    }
}

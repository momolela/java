package com.momolela.extend;

class Father {
    public String name = "sunzj";

    public String print() {
        return name;
    }

    public void accept(Visitor visitor) {
        visitor.method(this);
    }
}

class Son1 extends Father {
    public String name = "sunzjs1";

    @Override
    public String print() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.method(this);
    }
}

class Son2 extends Father {
    public String name = "sunzjs2";

    @Override
    public String print() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.method(this);
    }
}

class Visitor {
    public void method(Son1 son) {
        System.out.println("This is Son1's method");
    }

    public void method(Son2 son) {
        System.out.println("This is Son2's method");
    }

    public void method(Father father) {
        System.out.println("This is Father's method");
        System.out.println(father.name);
        System.out.println(father.print());
    }
}

public class Extend02invoke {
    public static void main(String[] args) {
        Father father = new Father();

        Father fs1 = new Son1();
        Father fs2 = new Son2();

        Son1 s1 = new Son1();
        Son2 s2 = new Son2();

        Visitor visitor = new Visitor();
        visitor.method(father); // This is Father's method，只会走工具类中 method(Father father) 方法
        visitor.method(fs1); // This is Father's method，只会走工具类中 method(Father father) 方法
        visitor.method(fs2); // This is Father's method，只会走工具类中 method(Father father) 方法

        visitor.method(s1); // This is Son1's method，只会走工具类中 method(Son1 son) 方法
        visitor.method(s2); // This is Son2's method，只会走工具类中 method(Son2 son) 方法

        father.accept(visitor); // This is Father's method，体现了多态
        fs1.accept(visitor); // This is Son1's method，体现了多态
        fs2.accept(visitor); // This is Son2's method，体现了多态
    }
}
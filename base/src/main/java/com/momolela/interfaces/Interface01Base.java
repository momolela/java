package com.momolela.interfaces;

interface InterfaceA {
    public static final String A_STR = "str a"; // 接口中的所有的变量都被public static final 修饰，不加默认加上，那么他就变成了常量了。命名就是XXX_YYY_ZZZ

    // 接口不可以被创建实例化，因为有抽象方法
    // 接口中的所有方法都被public abstract 修饰，不加默认加上，那么接口中所有方法都是抽象方法了
    // 如果一个类实现了该接口，但是没有全部实现里面的方法，那么需要定义这个实现类为抽象类
    public abstract void a();

    // jdk8的接口是可以定义default修饰的方法，他可以不是抽象方法，可以有实现方法体，而且实现该接口的对象可以直接调用该方法
    default void defaultMethod() {
        System.out.println("default method");
    }
}

interface InterfaceB {
    void b();
}

// 接口之间可以实现多继承
interface InterfaceC extends InterfaceA, InterfaceB {
    void c();
}

// 可以多实现
class InterfaceImpl implements InterfaceA, InterfaceB, InterfaceC {

    @Override
    public void a() {
        System.out.println("a");
    }

    @Override
    public void b() {
        System.out.println("b");
    }

    @Override
    public void c() {
        System.out.println("c");
    }
}

public class Interface01Base {

    public static void main(String[] args) {
        InterfaceImpl anInterface = new InterfaceImpl();
        anInterface.a();
        anInterface.b();
        anInterface.c();

        // 可以直接用接口名访问接口中的常量，因为static修饰符
        System.out.println(InterfaceA.A_STR);
    }

}

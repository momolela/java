package com.momolela.singletonpattern.byenum;

/**
 * 首先来分析一下克隆、反射、反序列化对单例模式的破坏。
 * <p>
 * 在其他创建型设计模式的学习中，我们已经了解，除了直接通过 new 和使用工厂来创建对象以外，还可以通过克隆、反射、反序列化等方式来创建对象。
 * <p>
 * 但是用这些方式来创建对象时有可能会导致单例对象的不唯一，如何解决这些问题呢？
 * <p>
 * (1) 为了防止客户端使用克隆方法来创建对象
 * 解决方法一：单例类不能实现 Cloneable 接口，即不能支持 clone() 方法。
 * <p>
 * (2) 由于反射可以获取到类的构造函数，包括私有构造函数，因此反射可以生成新的对象。
 * 解决方法一：采用一些传统的实现方法都不能避免客户端通过反射来创建新对象，此时，我们可以通过枚举单例对象的方式来解决该问题。
 * <p>
 * (3) 在原型模式中，我们可以通过反序列化实现深克隆，反序列化也会生成新的对象。具体来说就是每调用一次 readObject() 方法，都将会返回一个新建的实例对象，这个新建的实例对象不同于类在初始化时创建的实例对象。
 * 解决方法一：是类不能实现 Serializable 接口，即不允许该类支持序列化，这将导致类的应用受限（有时候我们还是需要对一个对象进行持久化处理）；
 * 解决方法二：是本文将要详细介绍的枚举实现。
 *
 * @author sunzj
 */
public class App {
    public static void main(String[] args) {
        SingletonByEnum s1 = SingletonByEnum.INSTANCE;
        SingletonByEnum s2 = SingletonByEnum.INSTANCE;
        System.out.println(s1 == s2);

        // 普通类改造成枚举单例
        Singleton se1 = Singleton.SingletonEnum.SINGLETON.getInstance();
        Singleton se2 = Singleton.SingletonEnum.SINGLETON.getInstance();
        System.out.println(se1 == se2);
    }
}
package com.momolela.singletonpattern.lhnotsynchronized;

/**
 * @author sunzj
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
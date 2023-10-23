package com.momolela.singletonpattern.byenum;

/**
 * @author sunzj
 */
public class Singleton {
    private Singleton() {
    }

    public static enum SingletonEnum {
        /**
         * 唯一实例
         */
        SINGLETON;

        private final Singleton instance;

        SingletonEnum() {
            instance = new Singleton();
        }

        public Singleton getInstance() {
            return instance;
        }
    }
}
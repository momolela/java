package com.momolela.singletonpattern.byenum;

/**
 * @author sunzj
 */

public enum SingletonByEnum {
    /**
     * 唯一实例
     */
    INSTANCE;

    public void businessMethod() {
        System.out.println("我是一个单例！");
    }
}
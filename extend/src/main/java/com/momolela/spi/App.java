package com.momolela.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App {

    /**
     * Java SPI 技术
     * 机制：标准调用方 -----(调用)-----> 标准服务接口 <-----(SPI规范：服务发现、服务加载)----- 标准实现方（实现类A、实现类B）
     *
     * 实现：
     * 1、标准调用方 ---------- 定义一个接口 ICar
     * 2、标准实现方 ---------- 两个实现类 BYDCar、BMWCar
     * 3、标准实现方 ---------- 在 resources/META_INF/services 中添加文件，文件名：接口全类名，文件内容：实现类的全类名
     * 4、标准调用方 ---------- 通过 ServiceLoader 去加载服务，并调用方法
     *
     * @param args args
     */
    public static void main(String[] args) {
        ServiceLoader<ICar> serviceLoader = ServiceLoader.load(ICar.class);
        Iterator<ICar> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            ICar car = iterator.next();
            car.start();
            car.run();
        }
    }
}

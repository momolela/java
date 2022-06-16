package com.momolela.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App {

    /**
     * Java SPI 技术
     * 机制：调用方 -----(调用)-----> 标准服务接口 <-----(SPI规范：服务发现、服务加载)----- 服务提供方（实现类A、实现类B）
     *
     * 实现：
     * 1、定义一个接口 ICar
     * 2、两个实现类 BYDCar、BMWCar
     * 3、在 resources/META_INF/services 中添加文件，文件名：接口全类名，文件内容：实现类的全类名
     * 4、通过 ServiceLoader 去加载服务，并调用方法
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

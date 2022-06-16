package com.momolela.spi.impl;

import com.momolela.spi.ICar;

public class BMWCar implements ICar {
    @Override
    public void start() {
        System.out.println("BMW start");
    }

    @Override
    public void run() {
        System.out.println("BMW run");
    }
}

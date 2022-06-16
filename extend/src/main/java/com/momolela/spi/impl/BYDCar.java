package com.momolela.spi.impl;

import com.momolela.spi.ICar;

public class BYDCar implements ICar {
    @Override
    public void start() {
        System.out.println("BYD start");
    }

    @Override
    public void run() {
        System.out.println("BYD run");
    }
}

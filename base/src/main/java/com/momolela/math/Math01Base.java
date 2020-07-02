package com.momolela.math;

import java.util.Random;

public class Math01Base {

    public static void main(String[] args) {

        Math.ceil(16.27);
        Math.floor(16.27);
        Math.round(16.27);
        Math.pow(2, 3);

        double random = Math.random(); // 返回 1.0> [随机数] >=0.0 伪随机数，通过一个算法得出的随机数，只要通过算法就是伪随机数；
        int v = (int) (random * 10 + 1);
        System.out.println(v);

        Random random1 = new Random();
        int v1 = random1.nextInt(10) + 1;
        System.out.println(v1);

    }

}

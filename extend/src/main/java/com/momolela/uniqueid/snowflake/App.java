package com.momolela.uniqueid.snowflake;

public class App {
    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(0);

        for (int i = 0; i < 16; i++) {
            System.out.println(snowFlake.nextId() + " === " + Integer.toBinaryString((int) snowFlake.nextId()));
        }
    }
}

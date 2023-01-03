package com.momolela.uniqueid.uuid;

import java.util.Random;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }

        // 随机 400 到 500 的整数
        Random random = new Random();
        int s = random.nextInt(500) % (500 - 400 + 1) + 400;
        System.out.println(s);
    }
}

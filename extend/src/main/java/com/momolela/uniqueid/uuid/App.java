package com.momolela.uniqueid.uuid;

import java.util.UUID;

public class App {
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}

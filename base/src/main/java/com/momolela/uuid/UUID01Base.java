package com.momolela.uuid;

import java.util.UUID;

public class UUID01Base {
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(Math.abs(UUID.randomUUID().toString().hashCode()));
        }
    }
}

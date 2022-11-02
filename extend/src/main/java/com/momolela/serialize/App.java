package com.momolela.serialize;

public class App {
    public static void main(String[] args) {
        Serializer serializer = SerializerFactory.getSerializer((byte) 3);

        Test sunzj = new Test("sunzj", 27);
        byte[] bytes = serializer.write(sunzj);
        System.out.println(new String(bytes));
    }
}

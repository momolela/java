package com.momolela.createobject;

import java.io.Serializable;

public class Person implements Cloneable, Serializable {
    String name;
    int age;

    @Override
    public String toString() {
        return this.name + " 是 " + this.age + " 岁。";
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
        this.name = "sunzj";
        this.age = 26;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}

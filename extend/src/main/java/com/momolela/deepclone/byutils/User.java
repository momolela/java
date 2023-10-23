package com.momolela.deepclone.byutils;

import java.io.Serializable;

/**
 * @author sunzj
 */
public class User implements Serializable {
    private String name;
    private int age;
    private House house;

    public User(String name, int age, House house) {
        this.name = name;
        this.age = age;
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
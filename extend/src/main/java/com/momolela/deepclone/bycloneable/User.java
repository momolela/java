package com.momolela.deepclone.bycloneable;

/**
 * @author sunzj
 */
public class User implements Cloneable {

    private String name;

    private int age;

    private House house;

    public User(String name, int age, House house) {
        this.name = name;
        this.age = age;
        this.house = house;
    }

    @Override
    protected User clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        user.setHouse(house.clone());
        return user;
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

    public void setHouse(House house) {
        this.house = house;
    }
}
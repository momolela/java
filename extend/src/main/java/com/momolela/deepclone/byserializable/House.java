package com.momolela.deepclone.byserializable;

import java.io.Serializable;

/**
 * @author sunzj
 */
public class House implements Serializable {
    private String location;
    private double price;

    public House(String location, int price) {
        this.location = location;
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
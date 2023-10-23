package com.momolela.deepclone.bycloneable;

/**
 * @author sunzj
 */
public class House implements Cloneable {

    private String location;

    private double price;

    public House(String location, double price) {
        this.location = location;
        this.price = price;
    }


    @Override
    protected House clone() throws CloneNotSupportedException {
        return (House) super.clone();
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
package com.momolela.generic;

import java.util.ArrayList;

class Fruit {
}

class Apple extends Fruit {
    private String name;

    public Apple(String name) {
        this.name = name;
    }
}

public class Generic07GenericNest<T> {
    private T item;

    public Generic07GenericNest(T t) {
        item = t;
    }

    public Generic07GenericNest() {
        System.out.println("");
    }

    public void set(T t) {
        item = t;
    }

    public T get() {
        return item;
    }


    public static void main(String[] args) {

        Generic07GenericNest<? super Fruit> p = new Generic07GenericNest();
        p.set(new Fruit());
        p.set(new Apple("sss"));
//        Generic07GenericNest<? extends Fruit> p = new Generic07GenericNest();
//        p.set(new Fruit()); //Error
//        p.set(new Apple("sss")); //Error

        ArrayList<? super Fruit> ll = new ArrayList(2);
        ll.add(new Apple("SS"));
//        ArrayList<? extends Fruit> ll = new ArrayList(2);
//        ll.add(new Apple("SS")); // Error
    }
}

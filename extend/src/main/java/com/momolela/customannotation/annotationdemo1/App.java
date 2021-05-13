package com.momolela.customannotation.annotationdemo1;

import java.util.Arrays;

@Person(name = "sunzj", age = 24)
public class App {

    public static void print(Class cls) {
        System.out.println("class name is：" + cls.getName());
        Person person = (Person) cls.getAnnotation(Person.class);
        System.out.println("App 上注解有：" + Arrays.toString(cls.getAnnotations()));
        if (person != null) {
            System.out.println("App 上注解属性有 name：" + person.name() + "，age：" + person.age());
        } else {
            System.out.println("App 上没有 Person 注解");
        }
    }

    public static void main(String[] args) {
        App.print(App.class);
    }

}

package com.momolela.customannotation.annotationdemo1;

import java.util.Arrays;

@Person(age = 24, value = "")
public class App {

    public static void print(Class cls) {
        System.out.println("class name is：" + cls.getName());

        System.out.println("App 上注解有：" + Arrays.toString(cls.getAnnotations()));

        Person person = (Person) cls.getAnnotation(Person.class);
        if (person != null) {
            System.out.println("App 上注解属性有 name：" + person.name() + "，age：" + person.age() + "，value：" + person.value());
        } else {
            System.out.println("App 上没有 Person 注解");
        }
    }

    public static void main(String[] args) {
        App.print(App.class);
    }

}

package com.momolela.customannotation.annotationdemo1;

@Person(name = "sunzj", age = 24)
public class App {

    public static void print(Class cls) {
        System.out.println("class name is：" + cls.getName());
        Person person = (Person) cls.getAnnotation(Person.class);
        if (person != null) {
            System.out.println("name：" + person.name() + "，age：" + person.age());
        } else {
            System.out.println("person is null");
        }
    }

    public static void main(String[] args) {
        App.print(App.class);
    }

}

package com.momolela.reflect.getclass;

public class GetClass {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1、Class.forName("全限定类名")
        Class personClass1 = Class.forName("com.momolela.reflect.getclass.Person");

        // 2、class 属性
        Class<Person> personClass2 = Person.class;

        // 3、getClass() 方法
        Person person = new Person("sunzj");
        Class aClass = person.getClass();
    }
}

package com.momolela.createobject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CreateObjByReflect {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Person> personClass = Person.class;
        Constructor<Person> personConstructor = personClass.getDeclaredConstructor(String.class, int.class);
        Person person = personConstructor.newInstance("sunzj", 26);
        System.out.println(person);
    }
}

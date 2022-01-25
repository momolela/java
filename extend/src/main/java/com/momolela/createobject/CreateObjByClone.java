package com.momolela.createobject;

public class CreateObjByClone {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person("sunzj", 26);
        Object clonePerson = person.clone();
        System.out.println(person == clonePerson);
        System.out.println(clonePerson.toString());
    }

}

package com.momolela.io.stream;

import java.io.*;

class Person implements Serializable {
    private String name;
    private int age;

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

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "===" + age;
    }
}

public class IO18ObjectStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        writeObj();
        readObj();
    }

    public static void readObj() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("person.object"));
        Person person = (Person) objectInputStream.readObject();
        System.out.println(person);
        objectInputStream.close();
    }

    public static void writeObj() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("person.object"));
        objectOutputStream.writeObject(new Person("zhangsan", 25));
        objectOutputStream.close();
    }
}

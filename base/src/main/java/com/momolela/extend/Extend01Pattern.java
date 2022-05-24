package com.momolela.extend;

public class Extend01Pattern {

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        new Method().method(teacher);
    }

}

class Person {
    protected String name = "Person";

    void print() {
        System.out.println(this.name);
    }
}

class Teacher extends Person {
    public String name = "Teacher";
    public int age = 36;

    void teach() {
        System.out.println("Teacher teaching ...");
    }
}

class Student extends Person {
    public String name = "Student";
    public int age = 26;

    void study() {
        System.out.println("Student studying ...");
    }
}

class Method {
    public void method(Person person) {
        // 这里能接收 Person 以及他的所有子类类型
        // 但是在明确类型的情况下，尽量要进行类型强转，因为 Person 父类自己本身不具备子类的特有数据和方法
    }
}

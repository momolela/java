package com.momolela.jdk8.lambda;

public class Lambda01Base {
    public static void main(String[] args) {
        Lambda01Base lambda01Base = new Lambda01Base();

        // lambda 表达式免去了使用匿名方法的麻烦

        // 参数类型声明，可以通过 Lambda 表达式来创建该接口的对象
        MathOperation addition = (int a, int b) -> a + b;

        // 不声明参数类型，可以通过 Lambda 表达式来创建该接口的对象
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句，可以通过 Lambda 表达式来创建该接口的对象
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句，可以通过 Lambda 表达式来创建该接口的对象
        MathOperation division = (int a, int b) -> a / b;

        // 测试
        System.out.println("10 + 5 = " + lambda01Base.operate(10, 5, addition)); // 10 + 5 = 15
        System.out.println("10 - 5 = " + lambda01Base.operate(10, 5, subtraction)); // 10 - 5 = 5
        System.out.println("10 * 5 = " + lambda01Base.operate(10, 5, multiplication)); // 10 * 5 = 50
        System.out.println("10 / 5 = " + lambda01Base.operate(10, 5, division)); // 10 / 5 = 2

        // 不用括号
        GreetingService greetingService1 = msg -> System.out.println("HELLO " + msg);

        // 用括号
        GreetingService greetingService2 = (msg) -> System.out.println("HELLO " + msg);

        // 测试
        greetingService1.sayMsg("SUNZJ"); // HELLO SUNZJ
        greetingService2.sayMsg("HUFY"); // HELLO HUFY
    }

    // 一个接口只定义一个待实现的方法
    interface MathOperation {
        int operation(int a, int b);
    }

    // 一个接口只定义一个待实现的方法
    interface GreetingService {
        void sayMsg(String msg);
    }

    // mathOperation 作为参数传递
    public int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}

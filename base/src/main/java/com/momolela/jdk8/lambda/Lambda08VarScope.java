package com.momolela.jdk8.lambda;

interface GreetingService {
    void sayMessage(String msg);
}

/**
 * lambda 表达式的变量作用域
 */
public class Lambda08VarScope {
    public static void main(String[] args) {
        String msg = "hello"; // 这里其实隐性的带上了 final 的修饰符

        //GreetingService greetingService = (msg) -> System.out.println(msg); // 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量，会报异常
        GreetingService greetingService = (a) -> System.out.println(msg + " " + a); // 我们可以直接在 lambda 表达式中访问外层的局部变量 msg，其实 msg 隐性的带上了 final 的修饰符
        greetingService.sayMessage("sunzj");

        //msg = "world!"; // 写了这一行，上面第 12 行会报错：Variable used in lambda expression should be final or effectively final
    }
}

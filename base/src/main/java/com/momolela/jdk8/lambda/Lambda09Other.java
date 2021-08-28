package com.momolela.jdk8.lambda;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 自定义的一个函数式接口，模拟 Runnable
 */
@FunctionalInterface
interface MyRunnable {
    void run();
}

public class Lambda09Other {
    public static void main(String[] args) throws Exception {
        // 这样是 ok 的
        Runnable r1 = () -> System.out.println("hello");
        Object o1 = r1;


        // 直接这样写会报错 Target type of a lambda conversion must be an interface
        //Object o2 = () -> System.out.println("hello");


        // 但是可以这样写，显示转型
        Object o3 = (Runnable) () -> System.out.println("hello");


        // 从上面得知一个 lambda 表达式只有在转型成一个函数接口后才能被当做 Object 使用
        //System.out.println(() -> {});
        System.out.println((Runnable) () -> {});


        // 下面都是正确的写法。这说明一个 lambda 表达式可以有多个目标类型（函数接口），只要函数匹配成功即可。
        // 1.参数数量和类型要与接口中定义的一致
        // 2.返回值类型要与接口中定义的一致
        Runnable r2 = () -> System.out.println("hello");
        MyRunnable r3 = () -> System.out.println("hello");


        // jdk 中提供的一些 函数式接口
        //@FunctionalInterface
        //public interface Function<T, R> { // 函数型接口
        //    R apply(T t);
        //}

        //@FunctionalInterface
        //public interface Consumer<T> { // 消费型接口
        //    void accept(T t);
        //}

        //@FunctionalInterface
        //public interface Supplier<T> { // 供给型接口
        //    T get();
        //}

        //@FunctionalInterface
        //public interface Predicate<T> { // 断言型接口
        //    boolean test(T t);
        //}

        //@FunctionalInterface
        //public interface Runnable {
        //    void run();
        //}

        //@FunctionalInterface
        //public interface Comparator<T> {
        //    int compare(T var1, T var2);
        //    boolean equals(Object var1);
        //}

        //@FunctionalInterface
        //public interface Callable<V> {
        //    V call() throws Exception;
        //}

        //@FunctionalInterface
        //public interface UnaryOperator<T> extends Function<T, T> {
        //    static <T> java.util.function.UnaryOperator<T> identity() {
        //        return t -> t;
        //    }
        //}


        // 其他的应用
        // 嵌套的λ表达式
        Callable<Runnable> c1 = () -> () -> { System.out.println("Nested lambda"); };
        c1.call().run();

        // 用在条件表达式中
        Callable<Integer> c2 = true ? (() -> 42) : (() -> 24);
        System.out.println(c2.call());

        // 定义一个递归函数，注意须用this限定
        //UnaryOperator<Integer> factorial = i -> i == 0 ? 1 : i * this.factorial.apply( i - 1 );
        //System.out.println(factorial.apply(3));

        // 在 Java 中，随声明随调用的方式是不行的，比如下面这样，声明了一个λ表达式(x, y) -> x + y，同时企图通过传入实参(2, 3)来调用它：
        // 这在C++中是可以的，但Java中不行。Java的λ表达式只能用作赋值、传参、返回值等。
        //int five = ( (x, y) -> x + y ) (2, 3); // ERROR! try to call a lambda in-place
    }
}

package com.momolela.wraps.aboutinteger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Integer01Base {
    public static Integer d;

    private Integer b;

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public static void main(String[] args) {
        System.out.println(Integer01Base.d); // null，Integer 的默认值是 null，int 的默认值是0


        // Integer a;
        // System.out.println(a); // 这里会报错，Integer 变量必须对象实例化后才能使用


        // int c;
        // System.out.println(c); // 这里会报错，int 变量不用实例化，但必须初始化后才能使用


        // Integer 实际是对象的引用，当 new 一个 Integer 时，实际上是生成一个指针指向此对象，里面定义了一个 private final int value 去存储；而 int 则是直接存储数据值
        Integer e1 = new Integer(100);
        Integer e2 = new Integer(100);
        System.out.println(e1 == e2); // false，两个对象肯定不会相等


        // Integer 变量和 int 变量比较时，只要两个变量的值是相等的，则结果为 true（因为包装类 Integer 和基本数据类型 int 比较时，java 会自动拆包装为 int ，然后进行比较，实际上就变为两个 int 变量的比较）
        Integer f1 = new Integer(100);
        int f2 = 100;
        System.out.println(f1 == f2); // true


        // 非 new 生成的 Integer 变量和 new Integer() 生成的变量比较时，结果为 false。
        // ①当变量值在-128~127之间时，非 new 生成的 Integer 变量指向的是 java 常量池中的对象，而 new Integer() 生成的变量指向堆中新建的对象，两者在内存中的地址不同；
        // ②当变量值不在-128~127之间时，非 new 生成的 Integer 变量时，java API中最终会按照 new Integer(i) 进行处理，最终两个 Integer 的地址同样是不相同的
        // 其实是：非 new 生成的 Integer 变量如果值在-128~127之间，会指向整数缓存池，不在这个区间会 new 一个 Integer 对象（valueOf方法的自动装箱），而new Integer()生成的变量指向堆中新建的对象，两者在内存中的地址不同。
        Integer g1 = new Integer(100); // 堆中新建的对象
        Integer g2 = 100; // 常量池中的对象
        System.out.println(g1 == g2); // false


        // 对于两个非 new 生成的 Integer 对象，进行比较时，如果两个变量的值在区间-128~127之间，则比较结果为 true ，如果两个变量的值不在此区间，则比较结果为 false
        // Integer 内部有一个内部类 IntegerCache
        Integer h1 = 128;
        Integer h2 = 128;
        System.out.println(h1 == h2); // false
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2); // true


        Integer01Base h = new Integer01Base();
        System.out.println(h.getB()); // null，Integer 的默认值是 null，int 的默认值是0


        int a = 46;
        double b = 46.0;
        System.out.println(a == b); // true，变量提升


        // System.out.println(Integer.parseInt(null)); // 抛出异常，NumberFormatException


        // 60 秒，消耗了一些时间，还剩多长时间
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long mid = System.currentTimeMillis();
        System.out.println((60 * 1000 - (mid - start)) / 1000);


        List<Integer> intList = new ArrayList<>(1);
        intList.add(null);
        System.out.println(intList.get(0));
        Integer intObj = intList.get(0); // 不会抛出异常，Integer 对象可以保存 null
        int intVal = intList.get(0); // 如果 intList.get(0) 结果是 null，然后用 int 去接，会报空指针异常
    }
}

package com.momolela.customannotation.annotationdemo3;

import java.lang.reflect.Method;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws NoSuchMethodException {
        Class sonClass = Son.class;

        // 子类继承父类，如果父类上的注解是被 @Inherited 修饰，子类也会有该注解，只有这种情况才会继承父类注解
        System.out.println("子类会继承父类中被 @Inherited 修饰的注解：" + Arrays.asList(sonClass.getAnnotations()));

        // 子类方法不会继承注解
        Method method = sonClass.getMethod("loveWorld");
        System.out.println("子类 Son 方法 " + method.getName() + " 有这些注解：" + Arrays.asList(method.getAnnotations()));
    }
}

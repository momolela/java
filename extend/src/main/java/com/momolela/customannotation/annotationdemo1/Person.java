package com.momolela.customannotation.annotationdemo1;

import java.lang.annotation.*;

/**
 * 使用 @interface 自定义注解时，自动继承了 java.lang.annotation.Annotation 接口，由编译程序自动完成其他细节
 * 在定义注解时，不能继承其他的注解或接口
 * @interface 用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数
 * 可以通过 default 来声明参数的默认值
 *
 *
 * @Target(ElementType.TYPE) 注解的使用范围
 * @Retention(RetentionPolicy.RUNTIME) 描述注解的生命周期
 * @Documented 表明这个注解是由 javadoc 记录
 * @Inherited 类继承关系中，子类会继承父类使用的注解中被 @Inherited 修饰的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Person {

    // 只能用 public 或者 default 修饰
    // 方法的名称就是参数的名称，
    // 返回值类型就是参数的类型，
    // 返回值类型只能是 基本类型（int, float, boolean, byte, double, char, long, short）, Class, String, enum, Annotation 以及前面类型的数组
    String name() default "sunzj";

    int age();

    // 如果只有一个参数，最好设置参数名称为 value
    // 注解元素必须有确定的值，要么在定义注解的默认值中指定，要么在使用注解时指定，（约束）
    // 非基本类型的注解元素的值不可为 null
    // 使用空 字符串 或 0 或者 负数 作为默认值是一种常用的做法
    String value() default "";
//    int value() default 0;
}

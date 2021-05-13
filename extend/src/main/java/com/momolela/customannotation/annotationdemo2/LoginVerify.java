package com.momolela.customannotation.annotationdemo2;

import java.lang.annotation.*;

/**
 * 判断是否已经登录
 *
 * @Target(ElementType.TYPE)
 * 注解的使用范围
 *
 * @Retention(RetentionPolicy.RUNTIME)
 * 描述注解的生命周期
 *
 * @Documented
 * 表明这个注解是由 javadoc 记录
 *
 * @Inherited
 * 类继承关系中，子类会继承父类使用的注解中被 @Inherited 修饰的注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoginVerify {
}

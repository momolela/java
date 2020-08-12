package com.momolela.customannotation.annotationdemo2;

import java.lang.annotation.*;

/**
 * 判断是否已经登录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoginVerify {
}

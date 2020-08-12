package com.momolela.customannotation.annotationdemo1;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Person {
    String name();

    int age();
}

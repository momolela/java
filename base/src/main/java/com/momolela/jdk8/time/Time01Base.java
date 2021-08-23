package com.momolela.jdk8.time;

import java.time.LocalDate;

public class Time01Base {
    public static void main(String[] args) {

        // localDate
        // 这个类与 java.util.Date 略有不同，因为它只包含日期，没有时间。
        // 并且格式化了日期
        LocalDate today = LocalDate.now();
        System.out.println(today);

        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.println("今天是：" + year + "-" + month + "-" + day);

        LocalDate birth = LocalDate.of(1995, 11, 1);
        System.out.println("我的生日是：" + birth);

        System.out.println(today.equals(birth));


    }
}

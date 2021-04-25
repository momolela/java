package com.momolela.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Date01Base {

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date);

        // 格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));

        System.out.println(LocalDate.parse("2020-09-18"));



        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long dayNums = ChronoUnit.DAYS.between(LocalDate.parse("2021-04-11", pattern), LocalDate.parse("2021-04-17", pattern));
        System.out.println(dayNums);

        DateTimeFormatter pattern1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long monthNums = ChronoUnit.MONTHS.between(LocalDate.parse("2021-01-10", pattern1), LocalDate.parse("2021-04-16", pattern1));
        System.out.println(monthNums);

    }

}

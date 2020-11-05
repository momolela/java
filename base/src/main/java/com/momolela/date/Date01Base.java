package com.momolela.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Date01Base {

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date);

        // 格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));

        System.out.println(LocalDate.parse("2020-09-18"));

    }

}

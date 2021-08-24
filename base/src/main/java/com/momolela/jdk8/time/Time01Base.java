package com.momolela.jdk8.time;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Time01Base {
    public static void main(String[] args) {

        // ---------- localDate
        // 这个类与 java.util.Date 略有不同，因为它只包含日期，没有时间。
        // 并且格式化了日期
        LocalDate today = LocalDate.now();
        System.out.println(today);

        // localDate 获取年月日，月从 1 开始
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.println("今天是：" + year + "-" + month + "-" + day);

        // localDate 获取某个特定的日期
        LocalDate birth = LocalDate.of(1995, 11, 1);
        System.out.println("我的生日是：" + birth);

        // localDate 的日期比较
        System.out.println(today.isAfter(birth));
        System.out.println(today.isBefore(birth));
        System.out.println(today.isEqual(LocalDate.now()));
        System.out.println(today.equals(birth));

        // localDate 检查重复事件，比如生日
        // MonthDay只存储了月日，对比两个日期的月日即可知道是否重复
        LocalDate birthDate = LocalDate.of(1995, 11, 1);
        //MonthDay monthDay = MonthDay.from(birthDate);
        MonthDay monthDay = MonthDay.of(birthDate.getMonth(), birthDate.getDayOfMonth());
        LocalDate nowDate = LocalDate.now();
        MonthDay monthDayFrom = MonthDay.from(nowDate);
        if (monthDay.equals(monthDayFrom)) {
            System.out.println("今天是我的生日");
        } else {
            System.out.println("今天不是我的生日");
        }

        // localDate 获取一周后的日期
        // ChronoUnit 是时间单位
        // LocalDate也是不可变的，因此任何修改操作都会返回一个新的实例
        LocalDate oneWeekAfterDay = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("一周后的日期是：" + oneWeekAfterDay);

        // 获取一年前后的日期
        LocalDate oneYearBeforeDay = today.minus(1, ChronoUnit.YEARS);
        System.out.println("一年前的日期是：" + oneYearBeforeDay);
        LocalDate oneYearAfterDay = today.plus(1, ChronoUnit.YEARS);
        System.out.println("一年后的日期是：" + oneYearAfterDay);


        // ---------- localTime
        // 这个类与 java.util.Date 略有不同，因为它只包含时间，没有日期。
        // 默认的格式是hh:mm:ss.nnn
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);

        // 计算两个小时后的时间
        System.out.println("两个小时后是：" + nowTime.plusHours(2));


        // ---------- Clock
        // 时钟
        System.out.println("当前的时区是：" + Clock.systemUTC());
        System.out.println("当前的时区是：" + Clock.systemDefaultZone());


        // ---------- zoneId
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId act = ZoneId.of(ZoneId.SHORT_IDS.get("ACT"));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, act);
        System.out.println("当前特定时区的时间是：" + zonedDateTime);
    }
}

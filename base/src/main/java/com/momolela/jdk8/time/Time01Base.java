package com.momolela.jdk8.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

        // localDate 获取一周后的日期
        // ChronoUnit 是时间单位
        // LocalDate也是不可变的，因此任何修改操作都会返回一个新的实例
        LocalDate oneWeekAfterDay = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("一周后的日期是：" + oneWeekAfterDay);

        // localDate 获取一年前后的日期
        // ChronoUnit 是时间单位
        LocalDate oneYearBeforeDay = today.minus(1, ChronoUnit.YEARS);
        System.out.println("一年前的日期是：" + oneYearBeforeDay);
        LocalDate oneYearAfterDay = today.plus(1, ChronoUnit.YEARS);
        System.out.println("一年后的日期是：" + oneYearAfterDay);

        // localDate 判断是否为闰年
        boolean leapYear = today.isLeapYear();
        if (leapYear) {
            System.out.println("今年是闰年");
        } else {
            System.out.println("今年不是闰年");
        }

        // localDate 格式化日期
        // 在 java8 之前，时间日期的格式化非常麻烦，经常使用 SimpleDateFormat 来进行格式化，但是 SimpleDateFormat 并不是线程安全的
        // 在 java8 中，引入了一个全新的线程安全的日期与时间格式器。并且预定义好了格式。比如，本例中使用的 BASIC_ISO_DATE 格式会将 19951101 格式化成 1995-11-01
        // 在 DateTimeFormatter 中还有很多定义好的格式
        System.out.println(LocalDate.parse("19951101", DateTimeFormatter.BASIC_ISO_DATE)); // 1995-11-01

        // LocalDate 按照自己定义的格式格式化日期
        // DateTimeFormatter.ofPattern 可以自定义自己要输出的格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        System.out.println(LocalDate.parse("11 01 1995", dateTimeFormatter)); // 1995-11-01

        // localDateTime 时间转成字符串
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm a");
        System.out.println(dateTimeFormatter1.format(LocalDateTime.now())); // 08 24 2021 23:03 下午

        // ChronoUnit 算日期的间隔
        System.out.printf("日期：%s，和日期：%s 之间相隔 %s 天 %n", birth, today, ChronoUnit.DAYS.between(birth, today));


        // ---------- MonthDay
        // 只存储了月日，对比两个日期的月日即可知道是否重复
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


        // ---------- YearMonth
        // 只存储年月
        YearMonth yearMonth = YearMonth.now();
        System.out.printf("你输入的年月是：%s，有 %d 天 %n", yearMonth, yearMonth.lengthOfMonth());
        YearMonth yearMonth1 = YearMonth.of(2008, Month.FEBRUARY);
        System.out.printf("你输入的年月是：%s，有 %d 天 %n", yearMonth1, yearMonth1.lengthOfMonth()); // 可以用于判断是否为闰 2 月


        // ---------- Period
        // 日期相隔，Period.between只能算月份之内的数据，要算完整的间隔要用 ChronoUnit
        Period period = Period.between(birth, today);
        System.out.printf("日期：%s，和日期：%s 之间在月内相隔 %s 天 %n", birth, today, period.getDays());


        // ---------- localTime
        // 这个类与 java.util.Date 略有不同，因为它只包含时间，没有日期。
        // 默认的格式是hh:mm:ss.nnn
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);

        // 计算两个小时后的时间
        System.out.println("两个小时后是：" + nowTime.plusHours(2));

        // 解析时间
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);


        // ---------- LocalDateTime
        // 日期时间
        // 日期时间转日期，也可以转时间
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime.toLocalDate());


        // ---------- Clock
        // 时钟
        System.out.println("当前的时区是：" + Clock.systemUTC());
        System.out.println("当前的时区是：" + Clock.systemDefaultZone());


        // ---------- zoneId
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId act = ZoneId.of(ZoneId.SHORT_IDS.get("ACT"));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, act);
        System.out.println("当前特定时区的时间是：" + zonedDateTime);


        // ---------- Instant
        // 时间戳
        // 可以看到，当前时间戳是包含日期和时间的，与 java.util.Date 很类似，事实上 Instant 就是 java8 以前的 Date
        // 可以使用这个两个类中的方法在这两个类型之间进行转换，比如 Date.from(Instant) 就是用来把 Instant 转换成 java.util.date 的
        // 而 Date.toInstant() 就是将 Date 转换成 Instant 的
        Instant nowTimeStamp = Instant.now();
        Date instantToDate = Date.from(nowTimeStamp);
        Instant dateToInstant = new Date().toInstant();
        System.out.println("当前时间戳是：" + nowTimeStamp);
    }
}

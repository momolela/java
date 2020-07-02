package com.momolela.calendar;

import java.util.Calendar;

public class Calendar01Base {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance(); // 获取当前系统所在时区的日历对象，也可以指定时区
        System.out.println("timezone is ==== " + calendar.getTimeZone());
        printCalendar(calendar);

        calendar.set(2012, 11, 23);
        printCalendar(calendar);

        calendar.add(Calendar.YEAR, 10);
        printCalendar(calendar);

        calendar.add(Calendar.DAY_OF_MONTH, 8);
        printCalendar(calendar);
        calendar.add(Calendar.MONTH, -1);
        printCalendar(calendar);

    }

    private static void printCalendar(Calendar calendar) {
        String[] monthArr = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        String[] weekArr = new String[]{"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("日期是：" + year + "年" + monthArr[month] + day + "日 " + weekArr[week]);
    }

}

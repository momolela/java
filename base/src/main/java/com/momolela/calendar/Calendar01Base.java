package com.momolela.calendar;

import java.util.Calendar;
import java.util.Date;

public class Calendar01Base {

    public static void main(String[] args) {

        // 获取当前系统所在时区的日历对象，也可以指定时区
        Calendar calendar = Calendar.getInstance();


        // 打印时区
        System.out.println("时区是 ==== " + calendar.getTimeZone());
        printCalendar(calendar, null);


        // 重置日历时间
        calendar.set(2012, 11, 23);
        printCalendar(calendar, null);


        // 加 10 年
        calendar.add(Calendar.YEAR, 10);
        printCalendar(calendar, null);


        // 加 8 月
        calendar.add(Calendar.DAY_OF_MONTH, 8);
        printCalendar(calendar, null);


        // 减 1 月
        calendar.add(Calendar.MONTH, -1);
        printCalendar(calendar, null);


        // 加 10 ms
        calendar.add(Calendar.MILLISECOND, 10);


        // 获取当前月的第一天
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        printCalendar(calendar, "本月第一天是：");


        // 获取当前月的最后一天
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        printCalendar(calendar, "本月最后一天是：");

    }

    private static void printCalendar(Calendar calendar, String label) {
        String[] monthArr = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        String[] weekArr = new String[]{"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println((label == null ? "日期是：" : label) + year + "年" + monthArr[month] + day + "日 " + weekArr[week]);
    }

}

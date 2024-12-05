package com.momolela.date;

import org.apache.http.client.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Date01Base {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getLocalTime(String strTime) {
        if (strTime == null) {
            return null;
        }

        if (strTime.length() < 23 && strTime.length() > 19) {
            strTime = strTime.substring(0, 19) + ".000";
            strTime = strTime.substring(0, 23).replace('T', ' ');
        } else {
            strTime = strTime.substring(0, 23).replace('T', ' ');
        }

        ZonedDateTime zdt = ZonedDateTime.parse(strTime, FORMAT.withZone(ZoneOffset.UTC));
        ZonedDateTime localTime = zdt.withZoneSameInstant(ZoneOffset.ofHours(8));

        return FORMAT.format(localTime);

        // if (strTime == null) {
        //     return null;
        // }
        //
        // Calendar now = Calendar.getInstance();
        // Date date = null;
        // try {
        //     if (strTime.length() < 23 && strTime.length() > 19) {
        //         strTime = strTime.substring(0, 19) + ".000";
        //         strTime = strTime.substring(0, 23).replace('T', ' ');
        //     } else {
        //         strTime = strTime.substring(0, 23).replace('T', ' ');
        //     }
        //     date = FORMAT.parse(strTime);
        //
        //     now.setTime(date);
        //     if (!false) {
        //         now.set(10, now.get(10) + 8);
        //     }
        // } catch (ParseException e) {
        //     return FORMAT.format(new Date());
        // }
        // return FORMAT.format(now.getTime());
    }


    public static void main(String[] args) {

        System.out.println(getLocalTime("2024-07-22 02:15:46.744"));

        Date date = new Date();
        System.out.println(date);


        // 格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
        System.out.println(simpleDateFormat.format(date)); // 2021年06月11日 星期五 14:24:01


        System.out.println(LocalDate.parse("2020-09-18")); // 2020-09-18


        // 取天数相隔，ChronoUnit.DAYS.between(LocalDate, LocalDate)
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long dayNums = ChronoUnit.DAYS.between(LocalDate.parse("2021-04-11", pattern), LocalDate.parse("2021-04-17", pattern));
        System.out.println(dayNums); // 6


        // 取月份相隔，ChronoUnit.MONTHS.between(LocalDate, LocalDate)
        DateTimeFormatter pattern1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long monthNums = ChronoUnit.MONTHS.between(LocalDate.parse("2021-01-10", pattern1), LocalDate.parse("2021-04-16", pattern1));
        System.out.println(monthNums); // 3


        // LocalDateTime，增加毫秒值
        DateTimeFormatter pattern3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime msgDate = LocalDateTime.parse("2021-06-01 00:00:00", pattern3);
        LocalDateTime startDate = msgDate.plus(1000, ChronoUnit.MILLIS);
        System.out.println(startDate.format(pattern3)); // 2021-06-01 00:00:00


        // Calendar，增加毫秒值
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MILLISECOND, 1000);
        Date nowDate = calendar.getTime();
        System.out.println(DateUtils.formatDate(nowDate, "yyyy-MM-dd HH:mm:ss"));
    }

}

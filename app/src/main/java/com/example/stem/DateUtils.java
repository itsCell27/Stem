package com.example.stem;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int year1 = cal1.get(Calendar.YEAR);
        int month1 = cal1.get(Calendar.MONTH);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

        return year1 == year2 && month1 == month2 && day1 == day2;
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int week1 = cal1.get(Calendar.WEEK_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int week2 = cal2.get(Calendar.WEEK_OF_YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        return week1 == week2 && year1 == year2;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}

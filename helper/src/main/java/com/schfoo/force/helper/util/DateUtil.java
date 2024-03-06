package com.schfoo.force.helper.util;

import jakarta.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addSecond(@Nullable Date date, int second) {
        return add(date, Calendar.SECOND, second);
    }

    public static Date addDay(@Nullable Date date, int day) {
        return add(date, Calendar.DAY_OF_MONTH, day);
    }

    public static Date add(@Nullable Date date, int field, int amount) {
        if (date == null) {
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

}

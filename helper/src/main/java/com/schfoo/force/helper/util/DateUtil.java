package com.schfoo.force.helper.util;

import jakarta.annotation.Nullable;
import org.apache.logging.log4j.util.Strings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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

    public static String format(Date date, @Nullable String format) {
        if (Strings.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String intTimeToHHmm(Integer time) {
        String s = String.valueOf(time);
        String hh = "00";
        String mm = "00";
        if (s.length() == 3) {
            hh = "0" + s.charAt(0);
            mm = s.substring(1, 3);
        } else if (s.length() == 4) {
            hh = s.substring(0, 2);
            mm = s.substring(2, 4);
        }
        return hh + ":" + mm;
    }

    public static Date trunc(Date date) {
        if (Objects.isNull(date)) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}

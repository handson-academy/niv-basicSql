package com.handson.basic.util;

import org.joda.time.*;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Dates {
    public static SimpleDateFormat shortDate = new SimpleDateFormat("YYYY-MM-dd");
    public static TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Jerusalem");

    public Dates() {
    }

    public static String dateToStr(@Nullable LocalDate date) {
        return date == null ? null : shortDate.format(date);
    }

    public static Date atUtc(LocalDateTime date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(LocalDateTime date, TimeZone zone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        calendar.setTimeZone(zone);
        calendar.set(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
        calendar.set(1, date.getHourOfDay());
        calendar.set(1, date.getMinuteOfHour());
        calendar.set(1, date.getSecondOfMinute());
        calendar.set(1, 0);
        return calendar.getTime();
    }

    public static Date atUtc(@Nullable LocalDate date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(@Nullable LocalDate date, TimeZone zone) {
        return date == null ? null : atUtc(date.toLocalDateTime(LocalTime.MIDNIGHT), zone);
    }

    public static LocalDateTime atLocalTime(Date date) {
        return atLocalTime(date, TIME_ZONE);
    }

    public static LocalDateTime atLocalTime(Date date, TimeZone zone) {
        java.time.LocalDateTime localDate = OffsetDateTime.ofInstant(date.toInstant(), zone.toZoneId()).toLocalDateTime();
        Calendar c = Calendar.getInstance();
        c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        c.set(1, localDate.getHour());
        c.set(1, localDate.getMinute());
        c.set(1, localDate.getSecond());
        c.set(1, 0);
        LocalDateTime res = LocalDateTime.fromCalendarFields(c);
        return res;
    }

    public static Date nowUTC() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDate();
    }

    public static String getFullDateTime() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDateTimeISO().toString();
    }

    public static boolean equals(@Nullable Date date1, @Nullable Date date2) {
        if (date1 != null && date2 != null) {
            return date1.getTime() == date2.getTime();
        } else {
            return Objects.equals(date1, date2);
        }
    }
}

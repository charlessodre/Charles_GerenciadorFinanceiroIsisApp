package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by charl on 10/09/2016.
 */
public class DateUtils {

    //Current datetime
    public static Date getCurrentDatetime() {
        return Calendar.getInstance().getTime();
    }

    public static Date getCurrentDateAddHours(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, amount);

        return calendar.getTime();
    }

    public static Date getCurrentDateAddDays(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, amount);

        return calendar.getTime();
    }

    public static Date getCurrentDateAddMonth(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);

        return calendar.getTime();
    }

    public static Date getCurrentDateAddYears(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, amount);

        return calendar.getTime();
    }

    public static String getCurrentDateShort() {
        Date data = Calendar.getInstance().getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

        return format.format(data);
    }

    public static int getLastDayOfCurrentMonth() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getFirtDayOfCurrentMonth() {
        return Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentYearAndMonth() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat yearMonth = new SimpleDateFormat("yyyyMM");

        return Integer.parseInt(yearMonth.format(cal.getTime()));

    }


    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static String getCurrentMonthNameLong() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        return month_date.format(cal.getTime());
    }

    public static String getCurrentMonthNameShort() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");

        return month_date.format(cal.getTime());
    }


    //Custom Datetime

    public static Date getDateAddHours(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, amount);

        return calendar.getTime();
    }

    public static Date getDateAddDays(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);

        return calendar.getTime();
    }

    public static Date getDateAddMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);

        return calendar.getTime();
    }

    public static Date getDateAddYears(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, amount);

        return calendar.getTime();
    }

    public static int getYearAndMonth(Date date) {

        SimpleDateFormat yearMonth = new SimpleDateFormat("yyyyMM");

        return Integer.parseInt(yearMonth.format(date.getTime()));

    }

    public static String getWeekNameAndDay(Date date) {

        SimpleDateFormat yearMonth = new SimpleDateFormat("EEEE, dd");

        return yearMonth.format(date.getTime());

    }

    public static int getLastDayOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }


    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        return calendar.getTime();
    }

    public static String dateToString(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date data = calendar.getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

        return format.format(data);

    }

    public static String dateToString(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        return format.format(date);
    }


    public static Date longToDate(long longDate) {
        Date date = null;

        if (longDate > 0) {
            try {
                date = new Date(longDate);
            } catch (Exception e) {
                e.printStackTrace();
                date = null;
            }
        }
        return date;
    }

    public static Date stringToDate(String stringDate) {
        Date date = null;

        if (stringDate != null || stringDate.length() != 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            try {
                date = new Date();
                date = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
                date = null;
            }
        }

        return date;
    }

    public static String getMonthNameLong(Date date) {

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        return month_date.format(date);
    }

    public static String getMonthNameShort(Date date) {

        SimpleDateFormat month_date = new SimpleDateFormat("MMM");

        return month_date.format(date);
    }

}

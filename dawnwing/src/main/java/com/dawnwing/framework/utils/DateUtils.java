package com.dawnwing.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtils {

    /**
     * @param now
     * @return
     * @description 根据当前日期获取后一天日期
     */
    public static Date getNextDateWithNow(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上一个月的月初和月末
     * @param now
     * @return
     */
    public static Map<String, Object> getPrevMonthFirstLast(Date now) {
        Map<String, Object> firstLast = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);
        int lastMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 月末
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                lastMonthMaxDay, 23, 59, 59);
        firstLast.put("monthlast", calendar.getTime());
        // 月初
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                01, 00, 00, 00);
        firstLast.put("monthfirst", calendar.getTime());
        firstLast.put("year", calendar.get(Calendar.YEAR));
        firstLast.put("month", calendar.get(Calendar.MONTH));
        return firstLast;
    }

    /**
     * 获取当前日期的前五年年份（包含当前年）, 有小到大排序
     * @return
     */
    public static List<Integer> beforeFiveYear() {
        List<Integer> fiveYear = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fiveYear.add(calendar.get(Calendar.YEAR));
        for (int i = 1; i < 5; i++) {
            calendar.add(Calendar.YEAR, -1);
            fiveYear.add(calendar.get(Calendar.YEAR));
        }
        // 顺序排序
        Collections.reverse(fiveYear);
        return fiveYear;
    }

    /**
     * 获取当前年份
     * @return
     */
    public static Integer getNowYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }

    /**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static int differDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年            
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differDaysByMs(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 字符串转时间
     * @param sdfStr
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String sdfStr, String str)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(sdfStr);
        Date date = format.parse(str);
        return date;
    }

    /**
     * 日期格式化
     * @param sdfStr
     * @param date
     * @return
     */
    public static String dateFormat(String sdfStr, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(sdfStr);
        return format.format(date);
    }

    public static void main(String[] args) {
        /*Map<String, Object> firstLast = DateUtils
                .getPrevMonthFirstLast(new Date());
        System.out.println(firstLast);

        System.out.println(beforeFiveYear());*/
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.DAY_OF_WEEK, 0);

        int differ = differDays(cal1.getTime(), new Date());
        System.out.println(differ);
    }
}

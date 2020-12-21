package com.drools.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* *
 * 日期时间工具
 * @author ly
 * @modifyTime 2020/6/16 11:11:00
 */
public class DateUtil {
    private static final String DEFAULT_FOMRT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String[] DF_PATTERN_08 = new String[]{"yyyyMMdd", "yyyyMMddHHmmss"};
    private static final String[] DF_PATTERN_10 = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
    private static final String[] DF_PATTERN_14 = new String[]{"yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分ss秒"};

    private static final String[] DF_SYMBOL_10 = new String[]{"-", ":"};
    private static final String[] DF_SYMBOL_14 = new String[]{"年", "时"};

    public static final String DATESTYLE_8 = "yyyyMMdd";
    public static final String DATE_TIMESTAMP_FORMAT = "yyyy-MM-dd 00:00:00";

    /**
     * 日期转化为字符串
     *
     * @param date       时间
     * @param datestyle8
     * @return yyyy-MM-dd HH:mm:ss 格式化的时间字符串
     */
    public static String dateToString(Date date, String datestyle8) {
        if (date == null)
            return "";
        return FormatDate(date, datestyle8);
    }

    /**
     * 对日期进行格式化
     *
     * @param date 日期
     * @param sf   日期格式
     * @return 字符串
     */
    public static String FormatDate(Date date, String sf) {
        if (date == null)
            return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    /**
     * 字符串转换为日期
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date string2Date(String str) throws Exception {
        if (null == str || "".equals(str)) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return format.parse(str);
        }
    }

    /**
     * 字符串转换为时间戳
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date string2Timestamp(String str) throws Exception {
        if (null == str || "".equals(str)) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
            return format.parse(str);
        }
    }

    /**
     * 日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        if (null == date) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return format.format(date);
        }
    }

    /**
     * 时间戳转换为字符串
     *
     * @param date
     * @return
     */
    public static String timestamp2String(Date date) {
        if (null == date) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
            return format.format(date);
        }
    }

    /**
     * 时间转换为字符串
     *
     * @param date
     * @return
     */
    public static String time2String(Date date) {
        if (null == date) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
            return format.format(date);
        }
    }

    /**
     * 比较日期时间是否相等
     *
     * @param front
     * @param later
     * @return
     */
    public static boolean isEqual(Date front, Date later) {
        if (null == front || null == later)
            return false;
        else if (front.before(later))
            return false;
        else if (front.after(later))
            return false;
        else
            return true;
    }

    /**
     * 得到当前的时间
     *
     * @return
     */
    public static Date getDate() {
        Calendar canlendar = Calendar.getInstance();
        return canlendar.getTime();
    }

    /**
     * 提到指定的millis得到时间
     *
     * @param millis
     * @return
     */
    public static Date getDate(long millis) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.setTimeInMillis(millis);
        return canlendar.getTime();
    }

    public static long getMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 得到指定日期的字符串(yyyy-MM-dd HH:mm:ss.SSS)
     *
     * @param date
     * @param formate
     * @return
     */
    public static String getDateFormate(Date date, String formate) {
        try {
            SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
            return simpleDateFormate.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 根据日期得到YYYY-MM-DD HH:MM:SS.SSS格式字符串
     *
     * @param date
     * @return
     */
    public static String get4yMdHmsS(Date date) {
        return DateUtil.getDateFormate(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }
    public static String getyMdHms(Date date) {
        return DateUtil.getDateFormate(date, "yyyyMMddHHmmss");
    }

    /**
     * 根据日期得到YYYY-MM-DD HH:MM:SS格式MM格式字符串字符串
     *
     * @param date
     * @return
     */
    public static String get4yMdHms(Date date) {
        return DateUtil.getDateFormate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据日期得到YYYY-MM-DD HH:
     *
     * @param date
     * @return
     */
    public static String get4yMdHm(Date date) {
        return DateUtil.getDateFormate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 根据日期得到YYYY-MM-DD格式字符串
     *
     * @param date
     * @return
     */
    public static String get4yMd(Date date) {
        return DateUtil.getDateFormate(date, "yyyy-MM-dd");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm:ss.SSS)串转成Date
     *
     * @param sDate
     * @return
     */
    public static Date parse4yMdHmsS(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm:ss)串转成Date
     *
     * @param sDate
     * @return
     */
    public static Date parse4yMdHms(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 把指定字符(yyyy-MM-dd HH:mm)串转成Date
     *
     * @param sDate
     * @return
     */
    public static Date parse4yMdHm(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd HH:mm");
    }

    /**
     * 把指定字符(yyyy-MM-dd)串转成Date
     *
     * @param sDate
     * @return
     */
    public static Date parse4yMd(String sDate) {
        return DateUtil.parseDate(sDate, "yyyy-MM-dd");
    }

    /**
     * 根据指定格式,把字符串转成日期
     *
     * @param sDate
     * @param formate
     * @return
     */
    public static Date parseDate(String sDate, String formate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        try {
            return simpleDateFormate.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
     *
     * @param minuendTime    [被减去的时间]
     * @param subtrahendTime [减去的时间]
     * @param tdatestr       [part可选值["D","H","M","S","MS"]
     * @return[minuendTime-subtrahendTime] @return
     */
    public static double getDifTwoTime(Date minuendTime, Date subtrahendTime, String tdatestr) {
        if (minuendTime == null || subtrahendTime != null) {
            return DateUtil.getDifTwoTime(minuendTime.getTime(), subtrahendTime.getTime(), tdatestr);
        }
        return 0;
    }

    /**
     * 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
     *
     * @param minuendTime    [被减去的时间]
     * @param subtrahendTime [减去的时间]
     * @param tdatestr       [part可选值["D","H","M","S","MS"] @return[minuendTime-
     *                       subtrahendTime]
     */
    public static double getDifTwoTime(long minuendTime, long subtrahendTime, String tdatestr) {
        if (tdatestr == null || tdatestr.equals("")) {
            tdatestr = "MS";
        }
        double temp = 1;
        /** 毫秒数 */
        if ("MS".equalsIgnoreCase(tdatestr)) {
            temp = 1;
        }
        /** 得到秒 */
        if ("S".equalsIgnoreCase(tdatestr)) {
            temp = 1000;
        }
        /** 得到分 */
        if ("M".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60;
        }
        /** 得到小时 */
        if ("H".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60 * 60;
        }
        /** 得到天 */
        if ("D".equalsIgnoreCase(tdatestr)) {
            temp = 1000 * 60 * 60 * 24;
        }
        return (minuendTime - subtrahendTime) / temp;
    }

    /**
     * 从日期中得到指定部分(YYYY/MM/DD/HH/SS/SSS)数字
     *
     * @param date
     * @param part [part可选值["Y","M","D","H","M","S","MS"]
     * @return
     */
    public static int getPartOfTime(Date date, String part) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.clear();
        canlendar.setTime(date);
        /** 得到年 */
        if (part.equalsIgnoreCase("Y")) {
            return canlendar.get(Calendar.YEAR);
        }
        /** 得到月 */
        if (part.equalsIgnoreCase("M")) {
            return canlendar.get(Calendar.MONTH) + 1;
        }
        /** 得到日 */
        if (part.equalsIgnoreCase("D")) {
            return canlendar.get(Calendar.DAY_OF_MONTH);
        }
        /** 得到时 */
        if (part.equalsIgnoreCase("H")) {
            return canlendar.get(Calendar.HOUR_OF_DAY);
        }
        /** 得到分 */
        if (part.equalsIgnoreCase("M")) {
            return canlendar.get(Calendar.MINUTE);
        }
        /** 得到秒 */
        if (part.equalsIgnoreCase("S")) {
            return canlendar.get(Calendar.SECOND);
        }
        /** 得到毫秒 */
        if (part.equalsIgnoreCase("MS")) {
            return canlendar.get(Calendar.MILLISECOND);
        }
        return -1;
    }

    /**
     * 获取指定日期的前一天
     *
     * @param specifiedDay
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获取指定日期的后面天数的日期
     *
     * @param specifiedDay 指定的日期字符串
     * @param duration     天数
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int duration) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + duration);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 设置日期格式
     *
     * @param format
     */
    public static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 获取指定日期格式
     *
     * @param date    日期
     * @param pattern 格式[yyyyMMdd,yyyyMMddHHmmss,yyyyMMddHHmmssSSS]
     * @return
     */
    public static String getFormatDatePattern(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期，其格式为[yyyy年MM月dd日]
     *
     * @param date
     * @return
     */
    public static String getFormatDate_14(Date date) {
        return getDateFormat(DF_PATTERN_14[0]).format(date);
    }

    /**
     * 格式化日期，其格式为[yyyy年MM月dd月 HH时mm分ss秒]
     *
     * @param date
     * @return
     */
    public static String getFormatDate_14H(Date date) {
        return getDateFormat(DF_PATTERN_14[1]).format(date);
    }

    @SuppressWarnings("deprecation")
    public static Long calAge(Date birthDate, Date secondDate) {
        if (null == birthDate || null == secondDate)
            return 0L;
        int month = birthDate.getMonth();
        int offset = (secondDate.getMonth() < month
            || secondDate.getMonth() == month && secondDate.getDate() < birthDate.getDate()) ? 1 : 0;
        int age = secondDate.getYear() - birthDate.getYear() - offset;
        return Long.valueOf(age);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            if (hour > 0) {
                min = min + hour * 60;
            }
            if (day > 0) {
                min = min + day * 24 * 60;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    public static boolean between(Date sourDate, Date date1, Date date2) {
        Boolean flag = false;
        if (sourDate.after(date1) && sourDate.before(date2)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 把date时间往前/后递增/递减minute分钟
     *
     * @param date   被修改时间
     * @param minute 分钟
     * @return
     */
    public static Date calendarDate(Date date, int minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE, minute);
        // calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
        // calendar.add(calendar.DAY_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        // calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        // calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * date2比date1多的天数
     *
     * @return
     * @throws ParseException
     */
    public static int differentDays(String d1, String d2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(d1);
        Date date2 = sdf.parse(d2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 367;
                } else    //不是闰年
                {
                    timeDistance += 366;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //同一年
        {
            if (day2 - day1 == 0) {
                return 1;
            } else {
                return day2 - day1;
            }

        }
    }

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            String format = "dd-MM-yyyy";
            System.setProperty("drools.dateformat", format);
            return new SimpleDateFormat(format);
        }
    };

    public static Date parse() throws ParseException {
        return threadLocal.get().parse(DEFAULT_FOMRT);
    }

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }

    public static void compare() {
        Calendar cn = Calendar.getInstance();
        cn.add(Calendar.DATE, 1);
        Date date = cn.getTime();
        System.out.println(date);

        LocalDate.now();
    }

    public static String getToday() {
        return threadLocal.get().format(new Date());
    }

    public static void main(String[] args) {
        compare();
    }

    /**
     * 根据op指定的操作，对日期day进行增减指定value的值
     *
     * @param day
     * @param op 0-年,1-月,2-日
     * @param value
     * @return
     */
    public static int getSepcialDay(int day,int op,int value){
        if(day == 0)
            return 0;

        Calendar cal = Calendar.getInstance();
        try{
            Date d = null;
            if(day == 0)
                d = new SimpleDateFormat("yyyyMMdd").parse("19800101");
            else
                d = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(day));
            cal.setTime(d);
            switch (op) {
                case 0:
                    cal.add(Calendar.YEAR, value);
                    break;
                case 1:
                    cal.add(Calendar.MONTH, value);
                    break;
                case 2:
                    cal.add(Calendar.DAY_OF_YEAR, value);
                    break;
                default:
                    break;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(cal.getTime())).intValue();
    }
}

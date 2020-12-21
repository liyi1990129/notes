package com.drools.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 用于日期处理的工具类
 *
 * @author 叶扬圻
 *
 */
public class TimeUtil implements Serializable{
    //public static SimpleDateFormat DATE_FORMAT_INT = new SimpleDateFormat("yyyyMMdd");
    //public static SimpleDateFormat DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     *
     */
    private static final long serialVersionUID = -719580897898654750L;

    public static final Log log= LogFactory.getLog(TimeUtil.class);

    /**
     * 得到当前日期
     *
     * @return
     */
    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到业务时间
     *
     * @param busiDate  8位数字格式日期
     * @return
     */
    public static Date getBusiDate(Integer busiDate){
        int date = busiDate.intValue();
        int year = date /10000;
        int month = (date % 10000) / 100;
        int day = date % 100;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 得到当前时间(到毫秒)
     *
     * @return
     */
    public static String getCurrentTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    }

    /**
     * 把日期转换为8位数字形式
     *
     * @param day
     * @return
     */
    public static int getSepcialDay(Date day){
        if(day == null)
            return 0;
        else
            return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(day)).intValue();
    }

    /**
     * 把输入的8位日期转为Date,输入0，返回Date(0)
     *
     * @param day
     * @return
     */
    public static Date getSepcialDay(int day){
        try{
            if(day == 0){
                return new Date(0);
            }else{
                return new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(day));
            }
        }catch (Exception e) {
            throw new RuntimeException("转换8位数字日期为Date对象发生异常");
        }

    }

    /**
     * 对指定的日期进行指定的操作
     *
     * @param day
     * @param op 0加1年，1减1年 2加一个月  3减一个月
     * @return
     */
    public static int getSepcialDay(int day,int op){
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
                    cal.add(Calendar.YEAR, 1);
                    break;
                case 1:
                    cal.add(Calendar.YEAR, -1);
                    break;
                case 2:
                    cal.add(Calendar.MONTH, 1);
                    break;
                case 3:
                    cal.add(Calendar.MONTH, -1);
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

    /**
     * 根据录入的保单起保日期，判断指定事故日的当年的保单起期
     *
     * @param currFrom
     * @param evtDte
     * @return
     */
    public static int getPolicyBgnDay(int currFrom,int evtDte){
        if(evtDte == 0)
            return currFrom;

        Date bgn = getSepcialDay(currFrom);
        Date evt = getSepcialDay(evtDte);

        Calendar cal = Calendar.getInstance();
        cal.setTime(evt);
        int year1 = cal.get(Calendar.YEAR); // 事故当年年份
        cal.setTime(bgn);
        int year2 = cal.get(Calendar.YEAR); // 保单终止年份

        if(year1 == year2)  // 同首年年份，取保单起保年份
            return currFrom;

        cal.set(Calendar.YEAR, year1);  // 事故当年保单年度起期

        Date tmp = cal.getTime();

        if(tmp.getTime() <= evt.getTime()){  // 年度起期在事故日前，取该年度起期
            return getSepcialDay(tmp);
        }else{   // // 年度起期在事故日后，取上年年度
            cal.set(Calendar.YEAR, year1 - 1);
            return getSepcialDay(cal.getTime());
        }
    }

    /**
     * 根据录入的保单终止日期，判断指定事故日的当年的保单止期
     *
     * @param currTo
     * @param evtDte
     * @return
     */
    public static int getPolicyEndDay(int currFrom, int currTo,int evtDte){
        if(evtDte == 0)
            return currTo;

        Date from = getSepcialDay(currFrom);
        Date to = getSepcialDay(currTo);
        Date evt = getSepcialDay(evtDte);

        Calendar cal = Calendar.getInstance();
        cal.setTime(evt);
        int year1 = cal.get(Calendar.YEAR);  // 事故当年年份
        cal.setTime(to);
        int year2 = cal.get(Calendar.YEAR);  // 保单终止年份

        // 2011-06-08 xuwei update
        //if(year1 == year2)   // 同年份，取保单终止年份
        if(year1 == year2 && currTo >= evtDte)   // 同年份，取保单终止年份
            return currTo;

        cal.setTime(from);
        cal.set(Calendar.YEAR, year1);  // 事故当年保单年度止期
        cal.add(Calendar.DAY_OF_YEAR, -1);

        Date tmp = cal.getTime();

        if(tmp.getTime() < evt.getTime()){ // 年度止期在事故日前，取下年年度
            cal.add(Calendar.YEAR, 1);
            return getSepcialDay(cal.getTime());
        }else{
            return getSepcialDay(tmp);
        }
    }

    /**
     * 判断日期与指定日期相隔天数是否在指定数内
     *
     * @param s
     * @param c
     * @param days
     * @return 如果2个日期相隔天数在days内，返回true,否则返回false
     */
    public static boolean isDateBetween(final Date s,final Date c,final int days){
        long limit = days * 1000l * 60l * 60l * 24l;
        long l = Math.abs(s.getTime() - c.getTime());

        return l <= limit;
    }

    /**
     * 判断2个日期是否间隔指定天数，不分前后
     *
     * @param s
     * @param c
     * @param days
     * @return
     */
    public static boolean isDateBetween(final int s,final int c,final int days){
        if(s == 0 || c == 0)
            return false;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));

            return isDateBetween(sd,cd,days);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 判断日期c是否在日期s的前days天后
     * 满足(s-days) <= c <= s返回true
     *
     * @param s
     * @param c
     * @param days
     * @return
     */
    public static boolean isDateBefore(final Date s,final Date c,final int days){

        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
        cal.add(Calendar.DAY_OF_YEAR, 0-days);

        Date s2 = cal.getTime();

        return c.getTime() > s2.getTime();
    }

    /**
     * 判断日期c是否在日期s的前days天后
     * 满足(s-days) <= c <= s返回true
     *
     * @param s
     * @param c
     * @param days
     * @return
     */
    public static boolean isDateBefore(final int s,final int c,final int days){
        if(s == 0 || c == 0)
            return false;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));

            return isDateBefore(sd,cd,days);
        }
        catch (ParseException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    /**
     * 判断日期c是否在日期s的后days天前
     * 满足s <= c <=(s+days)返回true
     *
     * @param s
     * @param c
     * @param days
     * @return
     */
    public static boolean isDateAfter(final Date s,final Date c,final int days){

        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
        cal.add(Calendar.DAY_OF_YEAR, days);

        Date s2 = cal.getTime();

        return c.getTime() < s2.getTime();
    }

    /**
     * 判断日期c是否在日期s的后days天前
     * s<=c<=(s+days)
     * @param s
     * @param c
     * @param days
     * @return
     */
    public static boolean isDateAfter(final int s,final int c,final int days){
        if(s == 0 || c == 0)
            return false;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));

            return isDateAfter(sd,cd,days);
        }
        catch (ParseException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    /**
     * 计算周岁
     *
     * @param s 起始日期
     * @param c 截止日期
     * @return
     */
    public static int calYeay(final Date s,final Date c){
        if(s.getTime() > c.getTime())
            return 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
        int i=0;
        while(true){
            if(cal.getTime().getTime() <= c.getTime()){
                cal.setTime(s);
                i++;
                cal.add(Calendar.YEAR, i);
            }else{
                break;
            }
        }

        return i-1;
    }

    /**
     * 计算周岁
     *
     * @param s 起始日期
     * @param c 截止日期
     * @return
     */
    public static int calYear(final int s,final int c){
        if(s == 0 || c == 0)
            return 0;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));
            return calYeay(sd,cd);
        }catch (ParseException  e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 计算周岁，计算出生日期到判断日期1时的周岁，判断日期1为0，则用判断日期2
     *
     * @param s 出生日期
     * @param c1 判断日期1
     * @param c2 判断日期2
     * @return
     */
    public static int calYear(final int s,final int c1,final int c2){
        if(c1 > 0)
            return calYear(s,c1);
        else
            return calYear(s,c2);
    }

    /**
     * 计算2日期间隔天数
     *
     * @param s 起期
     * @param c 止期
     * @return
     */
    public static int calDay(final Date s,final Date c){
        if(s.getTime() > c.getTime())
            return 0;

        return (int)((c.getTime() - s.getTime()) / (1000*60*60*24));
    }

    /**
     * 计算2日期间隔天数
     *
     * @param s 起期
     * @param c 止期
     * @return
     */
    public static int calDay(final int s,final int c){
        if(s == 0 || c == 0)
            return 0;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));
            return calDay(sd,cd);
        }catch (ParseException  e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 计算2日期间隔天数
     * 不分前后
     * @param s 起期
     * @param c 止期
     * @return
     */
    public static int calDayBetween(final Date s,final Date c){
        if(c.getTime() > s.getTime()){
            return (int)((c.getTime() - s.getTime()) / (1000*60*60*24));
        }else{
            return (int)((s.getTime() - c.getTime()) / (1000*60*60*24));
        }
    }

    /**
     * 计算2日期间隔天数
     * 不分前后
     * @param s 起期
     * @param c 止期
     * @return
     */
    public static int calDayBetween(final int s,final int c){
        if(s == 0 || c == 0)
            return 0;

        try{
            Date sd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(s));
            Date cd = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(c));
            return calDayBetween(sd,cd);
        }catch (ParseException  e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 取得两个日期之间的年数，日期2减日期1
     *   开始日期 格式为"YYYYMMDD"
     *   结束日期 格式为"YYYYMMDD"
     */
    public static int getYearDiff(int sd, int ed) {
        String sdate =String.valueOf(sd);
        String edate =String.valueOf(ed);
        int year = 0;
        if (sdate == null || (sdate = sdate.trim()).equals(""))
            return 0;
        if (edate == null || (edate = edate.trim()).equals(""))
            return 0;
        try {
            Integer yearA = new Integer(sdate.substring(0, 4));
            Integer yearB = new Integer(edate.substring(0, 4));
            Integer mdA = new Integer(sdate.substring(4, 8));
            Integer mdB = new Integer(edate.substring(4, 8));

            if (mdB.compareTo(mdA) >= 0){
                year = yearB.intValue() - yearA.intValue() + 1;
            } else {
                year = yearB.intValue() - yearA.intValue();
            }
            return year;
        } catch (Exception e) {
            return 0;
        }
    }

    /** xuwei 2013-06-04 百万随行新需求 add
     * 计算保单周年日
     *
     *  sdate 开始日期 格式为"YYYYMMDD"
     *  sdate 结束日期 格式为"YYYYMMDD"
     * @return
     */
    public static int getPlyYear(final int sd, final int ed){
        String sdate =String.valueOf(sd);
        String edate =String.valueOf(ed);
        if (sdate == null || (sdate = sdate.trim()).equals(""))
            return 0;
        if (edate == null || (edate = edate.trim()).equals(""))
            return 0;
        try {
            String yearA = sdate.substring(0, 4);
            String mdA = sdate.substring(4, 8);
            String mdB = edate.substring(4, 8);

            int year = 0;
            if (mdA.compareTo(mdB) >= 0) {
                year = new Integer(yearA).intValue() + 1;
            } else {
                year = new Integer(yearA).intValue();
            }

            int date = new Integer(String.valueOf(year) + mdB).intValue();

            return date;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * add by yinruirong 20180904 金悦行两全新需求 计算到达年龄
     * @param anbAtCcd --被保人投保时的年龄
     * @param crrcd	--周年日
     * @param eventDte	--保险事故发生日
     * @return
     */
    public static int calArrivalAge(int anbAtCcd, int crrcd, int eventDte) {

        //到达年龄的计算逻辑：被保险人的投保年龄 + 保险事故发生时的保单年度数 - 1
        //保险事故发生时的保单年度数计算逻辑：保险事故发生日-周年日
        int yearDiff = getYearDiff(crrcd,eventDte);
//		return anbAtCcd + (eventDte - crrcd) - 1;
        int years = anbAtCcd + yearDiff - 1;
        log.info("被保人投保时的年龄anbAtCcd："+anbAtCcd+",周年日crrcd:"+crrcd+",保险事故发生日eventDte:"+eventDte);
        log.info("到达年龄："+years);
        return years;
    }

    public static void main(String[] args) {
        //System.out.println(isDateBefore(20090609, 20090525,14));
        System.out.println(calYear(20090709, 20100709));
    }
}


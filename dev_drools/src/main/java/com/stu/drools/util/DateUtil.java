package com.stu.drools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/* *
 * 日期时间工具
 * @author ly
 * @modifyTime 2020/6/16 11:11:00
 */
public class DateUtil {
  private static final String DEFAULT_FOMRT = "yyyy-MM-dd HH:mm:ss";

  private static final  ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
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

  public static void compare(){
    Calendar cn = Calendar.getInstance();
    cn.add(Calendar.DATE,1);
    Date date = cn.getTime();
    System.out.println(date);

    LocalDate.now();
  }

  public static String getToday(){
    return threadLocal.get().format(new Date());
  }

  public static void main(String[] args) {
    compare();
  }
}


package com.example.demo.beanUtils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateUtils {

    /**
     * 获取当前时间
     *
     * @return 返回java Date
     */
    public static Date getNow() {
        return new DateTime().toDate();
    }

    /**
     * 获取当前时间
     *
     * @return 返回JODA DateTime
     */
    public static DateTime getDateTimeNow() {
        return new DateTime();
    }

    public static DateTime dateToDateTime(Date date) {
        return new DateTime(date);
    }

    /**
     * 字符串时间 转换为时间
     *
     * @param date
     * @return
     */
    public static Date toDate(String date, DateEnum format) {
        ServiceValidate.notNull(format);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format.getText());
        return fmt.parseDateTime(date.trim()).toDate();
    }

    /**
     * 毫秒转换为Date
     *
     * @param ms
     * @return
     */
    public static Date msToDate(long ms) {
        ServiceValidate.notNull(ms);
        return new DateTime(ms).toDate();
    }

    /**
     * 格式化时间
     *
     * @param date     时间
     * @param template 格式
     * @return
     */
    public static String dateToStr(Date date, DateEnum template) {
        ServiceValidate.notNull(date);
        ServiceValidate.notNull(template);
        SimpleDateFormat format = new SimpleDateFormat(template.getText());
        return format.format(date);
    }

    /**
     * 传入日期增加几天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date plusDay(Date date, int day) {
        ServiceValidate.notNull(date);
        return new DateTime(date).plusDays(day).toDate();
    }

    /**
     * 传入日期增加几个月
     *
     * @param date
     * @param day
     * @return
     */
    public static Date plusMonth(Date date, int month) {
        ServiceValidate.notNull(month);
        return new DateTime(date).plusMonths(month).toDate();
    }

    /**
     * 传入日期减少几天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date minusDay(Date date, int day) {
        ServiceValidate.notNull(date);
        return new DateTime(date).minusDays(day).toDate();
    }

    /**
     * 传入日期减少几分钟
     *
     * @param date
     * @param day
     * @return
     */
    public static Date minusMinutes(Date date, int minutes) {
        ServiceValidate.notNull(date);
        return new DateTime(date).minusMinutes(minutes).toDate();
    }
    
    /**
     * 传入日期减少几小时
     * @param date
     * @param hours
     * @return
     */
    public static Date minusHours(Date date, int hours) {
        ServiceValidate.notNull(date);
        return new DateTime(date).minusHours(hours).toDate();
    }
    
    public static Date longToDate(long millis) {
        return new DateTime(millis).toDate();
    }

    /**
     * 返回传入两个date相差的天数
     * END 大于 start 忽略时分秒
     * 例：start = 2015-06-05 10:40:30
     * end = 2015-06-06 10:40:20
     * 返回值 1
     *
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(Date start, Date end) {
        int days = Days.daysBetween(new LocalDate(start), new LocalDate(end)).getDays();
        return days;
    }

    /**
     * 返回两个日期之间 月数
     *
     * @param start
     * @param end
     * @return
     */
    public static int monthsBetween(Date start, Date end) {
        return monthsBetween(new DateTime(start), new DateTime(end));
    }

    /**
     * 返回两个日期之间 月数
     *
     * @param start
     * @param end
     * @return
     */
    public static int monthsBetween(DateTime start, DateTime end) {
        int m = Months.monthsBetween(start, end).getMonths();
        return m;
    }

    /**
     * 返回当前日期距离N月之后之后的天数
     *
     * 例如：计算1月5日，距离2个月之后的3月5日 返回相差天数
     *
     * @param date
     * @param addmonths
     * @return
     */
    public static int daysAfterMonths(Date date, int addmonths) {
        DateTime dateTime = new DateTime(date);
        DateTime dateTimeAfter = dateTime.plusMonths(addmonths);
        return daysBetween(date, dateTimeAfter.toDate());
    }

    /**
     * 返回传入日期+N月之后的日期
     *
     * @param date
     * @param addmonths
     * @return
     */
    public static Date dateAfterMonths(Date date, int addmonths) {
        DateTime dateTime = new DateTime(date);
        DateTime dateTimeAfter = dateTime.plusMonths(addmonths);
        return dateTimeAfter.toDate();
    }

    public static Date handleDateMix(Date date) {
        String str = DateUtils.dateToStr(date, DateEnum.DATE_SIMPLE) + " 00:00:00";
        return DateUtils.toDate(str, DateEnum.DATE_FORMAT);
    }

    public static Date handleDateMax(Date date) {
        String str = DateUtils.dateToStr(date, DateEnum.DATE_SIMPLE) + " 23:59:59";
        return DateUtils.toDate(str, DateEnum.DATE_FORMAT);
    }

    /**
     * 传入日期和当前日期比较（参数会转换成年月日格式）
     *
     * @param dt
     * @return 小于-1，等于0，大于1
     */
    public static int compareDateToNow(DateTime dt) {
        return dt.toLocalDate().compareTo(new DateTime().toLocalDate());
    }

    /**
     * 返回当前时间格式yyyy-MM-dd字符串
     *
     * @return
     */
    public static String getSimpleDate() {
        return dateToStr(getNow(), DateEnum.DATE_SIMPLE);
    }
    /**
     * 返回365天后的日期，格式yyyy-MM-dd字符串
     *
     * @return
     */
    public static String getYearAfterDate() {
        return dateToStr(plusDay(getNow(),365) , DateEnum.DATE_SIMPLE);
    }
    /**
     * 返回当前时间格式yyyyMMddHHmmss字符串
     *
     * @return
     */
    public static String getSimpleDateStr() {
        return dateToStr(getNow(), DateEnum.DATE_BANK_SEQ);
    }

    /**
     * 返回当前时间格式HHmmss字符串
     *
     * @return
     */
    public static String getMinTime() {
        return dateToStr(getNow(), DateEnum.DATE_TIME_MIN);
    }

    /**
     * @param date
     * @return 当前月第几天
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 传入日期减少几月
     *
     * @param date
     * @param month
     * @return
     */
    public static Date minusMonths(Date date, int month) {
        ServiceValidate.notNull(date);
        return new DateTime(date).minusMonths(month).toDate();
    }

    /**
     * 传入两个日期比较（参数会转换成年月日格式）
     * start 大于 end 忽略时分秒
     * 例：start = 2015-06-06 10:40:20
     * end = 2015-06-05 10:40:20
     * 返回值 1
     * @param start
     * @param end
     * @return 小于-1，等于0，大于1
     */
    public static int compareDate(Date start, Date end) {
        return new DateTime(start).toLocalDate().compareTo(new DateTime(end).toLocalDate());
    }

    /**
     * 获取两个日期之间的所有日期
     * @Description TODO
     * @param begin
     * @param end
     * @return
     */
     public static  List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime()<=end.getTime()){
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_MONTH, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**
     * 获取两个日期之间的都有日期(日期字符串格式为yyyy-MM-dd)
     * @param beginStr
     * @param endStr
     * @return
     */
    public static  List<String> getBetweenDateStrs(String beginStr,String endStr){
        List<String> result = new ArrayList<String>();
        try{
            Date begin = toDate(beginStr,DateEnum.DATE_SIMPLE);
            Date end = toDate(endStr,DateEnum.DATE_SIMPLE);
            List<Date> dates = getBetweenDates(begin,end);
            for(Date date:dates){
               result.add(dateToStr(date,DateEnum.DATE_SIMPLE));
            }
        }  catch (Exception e){
            
        }

        return result;
    }

    /**
     * 将java.util.Date转换为java.time.LocalDate
     * @param dt
     * @return
     */
    public static java.time.LocalDate dateToLocalDate(Date dt){
        java.time.LocalDate localDate = null;
        if (dt == null) {
            localDate = java.time.LocalDate.now();
        } else {
            Instant instant = dt.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            //atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
            localDate = instant.atZone(zoneId).toLocalDate();
        }
        return localDate;
    }

    public static java.time.LocalDateTime dateToLocalDateTime(Date dt){
        java.time.LocalDateTime localDate = null;
        if (dt == null) {
            localDate = java.time.LocalDateTime.now();
        } else {
            Instant instant = dt.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            //atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
            localDate = LocalDateTime.ofInstant(instant, zoneId);
        }
        return localDate;
    }

    public static java.time.LocalDateTime stringToLocalDateTime(String date){
        Date date1 = toDate(date, DateEnum.DATE_FORMAT);
        java.time.LocalDateTime localDate = dateToLocalDateTime(date1);
        return localDate;
    }

    public static java.time.LocalDate stringToLocalDate(String date){
        Date date1 = toDate(date, DateEnum.DATE_SIMPLE);
        java.time.LocalDate localDate = dateToLocalDate(date1);
        return localDate;
    }
}

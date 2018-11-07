package com.d1m.wechat.util;

import org.springframework.util.ObjectUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil2 {

    private DateUtil2() {
        throw new UnsupportedOperationException("Utils class can not be new!");
    }

    /**
     * yyyyMMddHHmmss
     */

    public static final DateTimeFormatter formatter_DateTimestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * yyyy-MM-dd HH:mm:ss
     */

    public static final DateTimeFormatter formatter_DateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     *
     */
    public static final DateTimeFormatter formatter_mmdd = DateTimeFormatter.ofPattern("MM-dd");


    private static ZoneId zoneId = ZoneId.systemDefault();


    /**
     * 获取当前日期
     *
     * @return LocalDate
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();

    }

    /**
     * 获取当前时间
     *
     * @return LocalTime
     */


    public static LocalTime getLocalTime() {
        return LocalTime.now();

    }

    /**
     * 获取当前日期时间
     *
     * @return LocalDateTime
     */


    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();

    }

    /**
     * 获取当前的微秒数
     *
     * @return 微秒数
     */


    public static long getClockMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();

    }

    /**
     * 返回当前时间yyyyMMddHHmmss
     *
     * @return yyyyMMddHHmmss
     */


    public static String getDateTimestamp() {
        return getLocalDateTime().format(formatter_DateTimestamp);

    }

    /**
     * 返回当前时间yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */


    public static String getDate() {
        return getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);

    }

    /**
     * @param date 需要转换的日期
     * @return yyyy-MM-dd
     */

    public static String getDate(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        final Instant instant = date.toInstant();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }


    /**
     * 返回当前系统时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前系统时间
     */


    public static String getDateTime() {
        return getLocalDateTime().format(formatter_DateTime);

    }

    /**
     * @param date 需要转换的时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), zoneId).format(formatter_DateTime);
    }

    /**
     * 获取当月第一天 yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */


    public static String getFirstDayOfMonth() {
        return getLocalDate().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

    }

    /**
     * 获取本月最后一天 yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */


    public static String getLastDayOfMonth() {
        LocalDate localDate = getLocalDate();
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);

    }

    /**
     * 将yyyyMMddHHmmss转为 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTimestamp
     * @return yyyy-MM-dd HH:mm:ss
     */


    public static String formatDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, formatter_DateTimestamp).format(formatter_DateTime);

    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 yyyyMMddHHmmss
     */


    public static String formatDateTime(String dateTime) {
        return parseLocalDateTime(dateTime).format(formatter_DateTimestamp);

    }

    /**
     * @param date 时间
     * @return yyyyMMddHHmmss
     */
    public static String formatDateTime(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), zoneId).format(formatter_DateTimestamp);
    }

    /**
     * @param date 时间
     * @return MM-dd
     */

    public static String formatMMdd(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), zoneId).format(formatter_mmdd);
    }

    /**
     * @param date 时间
     * @return MM-dd
     */

    public static String formatMMdd(LocalDateTime date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return formatter_mmdd.format(date);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 LocalDateTime
     */


    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter_DateTime);

    }

    /**
     * 将yyyyMMddHHmmss转为 LocalDateTime
     */


    public static LocalDateTime parseLocalDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, formatter_DateTimestamp);

    }

    /**
     * yyyy-MM-dd字符串转LocalDate
     *
     * @param dateString 需要格式化的字符串
     * @return LocalDate
     */


    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

    }

    /**
     * yyyy-MM-dd 增加日期
     *
     * @param date 增加前字符串日期
     * @param days 增加天数
     * @return yyyy-MM-dd
     */


    public static String plusDays(String date, int days) {
        LocalDate localDate = parseLocalDate(date);
        return localDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE);

    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate 较小的时间 yyyy-MM-dd
     * @param endDate   较大的时间 yyyy-MM-dd
     * @return 相差天数
     */


    public static int dateCompareTo(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(startLocalDate, endLocalDate);
        return period.getDays();

    }

    public static int getHour(Date date) {
        final LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return dateTime.getHour();
    }

    /**
     * 获取刺客是周几
     *
     * @return DayOfWeek
     */
    public static DayOfWeek getDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek();
    }

    public static boolean isWeekend() {
        return getDayOfWeek() == DayOfWeek.SUNDAY || getDayOfWeek() == DayOfWeek.SATURDAY;
    }

}

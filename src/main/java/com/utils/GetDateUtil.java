package com.utils;

import com.entity.PubCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.entity.PubCommon.DEFAULT_YEAR_MON_DAY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/8/24 16:16
 * Time: 14:15
 */
public class GetDateUtil {

    /**
     * @param strDate 参数日期 20140404
     * @param pattern 日期格式
     * @param days    天数
     * @return String
     */
    public static String getNextDay_YYYYMMDD(String strDate, String pattern, int days) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date newDate = null;
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.get(Calendar.DAY_OF_MONTH) + days);
            newDate = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format.format(newDate);
    }


    /**
     * 格式化日期
     *
     * @param date    日期实例
     * @param pattern 格式
     * @return
     */
    public static String getDateStr(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }


    /**
     * 日期字符串转换成Date
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return
     * @throws Exception
     */
    public static Date parse(String dateStr, String pattern) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.parse(dateStr,format);
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取指定时间所在月的第一天
     *
     * @return
     */
    public static String firstDayOfAssignMonth(Date date, int months, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的第一天(不加时分秒)， 默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String firstDayOfLastMonth(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获取上个月的最后一天(不加时分秒)， 默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String lastDayOfLastMonth(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前月第一天
     *
     * @param pattern 默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String firstDayOfCurrentMonth(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前月最后一天
     *
     * @param pattern 时间格式,传""默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String lastDayOfCurrentMonth(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }


    /**
     * 获取增加月数以后的日期
     */
    public static String getDateAddMonths(int months) {
        try {
            Calendar date = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    DEFAULT_YEAR_MON_DAY);
            date.add(Calendar.MONTH, months);
            return dateFormat.format(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取所传日期前后天数的日期
     *
     * @param date
     * @param days   往后传正数往前传负数
     * @param format 时间格式,为空为默认"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getDateAddDays(Date date, int days, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            format = StringUtils.isNotBlank(format) ? format : DEFAULT_YEAR_MON_DAY;
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * long 转为 日期String
     *
     * @param time
     * @return
     */
    public static String formatLongToStr(long time, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_YEAR_MON_DAY;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        String sDateTime = sdf.format(date);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }


    /**
     * 获取一个星期之前的时间戳
     *
     * @param weeknum 传入几个星期
     * @return
     */
    public static Long getweektime(Integer weeknum) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.WEEK_OF_MONTH, curr.get(Calendar.WEEK_OF_MONTH) - weeknum);
        Date date = curr.getTime();
        return date.getTime();
    }


    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Long getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Long getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }


    /**
     * 获取上周一时间(不加时分秒)
     *
     * @return
     */
    public static String getLastMonday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(PubCommon.DEFAULT_FORMAT_YYYY_MM_DD);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        return df.format(cal.getTime());
    }


    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     * @param localDateTime
     * @param format
     * @return
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 将long类型的timestamp转为LocalDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     * @param time
     * @param format
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time,String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 时间差计算
     */
    public static Map<String, Double> timeDifference(Long starTime, Long endTime) {
        //时间差的毫秒数
        Long date3 = endTime - starTime;
        //计算出相差天数
        Double allDays = Math.ceil(date3 / (24 * 3600 * 1000));
        Double days = Math.floor(date3 / (24 * 3600 * 1000));
        //计算出小时数
        Long leave1 = date3 % (24 * 3600 * 1000);
        //计算天数后剩余的毫秒数
        Double hours = Math.floor(leave1 / (3600 * 1000));
        //计算相差分钟数
        Long leave2 = leave1 % (3600 * 1000);
        //计算小时数后剩余的毫秒数
        Double minutes = Math.floor(leave2 / (60 * 1000));
        //计算相差秒数
        Long leave3 = leave2 % (60 * 1000);
        //计算分钟数后剩余的毫秒数
        Double seconds = Math.floor(leave3 / 1000);

        Map<String, Double> map = new HashMap<>();
        map.put("allDays", allDays);
        map.put("days", days);
        map.put("hours", hours);
        map.put("minutes", minutes);
        map.put("seconds", seconds);

        return map;
    }

}

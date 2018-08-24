package com.utils;

import com.entity.BaseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/8/23 18:30
 * Time: 14:15
 */
public class TimeCompareUtil {

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_FORMAT_STRING = "yyyyMMddHHmmss";
    public static final String DEFAULT_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DEFAULT_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";
    public static final String DEFAULT_FORMAT_YYYY = "yyyy";
    public static final String DEFAULT_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DEFAULT_YEAR_MON_DAY = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_YEAR_MON_DAY2 = "yyyy/MM/dd HH:mm:ss";
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /***********************************************日使者 ( • ̀ω•́ )✧ start****************************************************************/
    /**
     * 取两个日期里早的
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getEarlyDay(String date1, String date2) {
        if (date1 != null && date2 == null) {
            return date1;
        }
        if (date1 == null && date2 == null) {
            return date2;
        }
        if (date1 == null && date2 == null) {
            return null;
        }
        String result = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            if (d1.equals(d2)) {
                result = date1;
            } else if (d1.before(d2)) {
                result = date1;
            } else if (d1.after(d2)) {
                result = date2;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("方法——compareDate（{}，{}）异常" + date1 + "," + date2);
        }
        return result;
    }

    /**
     * 取两个日期里晚的
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getLaterDay(String date1, String date2) {
        String result = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            if (d1.equals(d2)) {
                result = date1;
            }
            if (d1.before(d2)) {
                result = date2;
            } else if (d1.after(d2)) {
                result = date1;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("方法——compareDate（{}，{}）异常" + date1 + "," + date2);
        }
        return result;
    }


    /**
     * 日期补0，包括中间空余的日期
     *
     * @param dataEntityList
     * @param begin
     * @param end
     * @return
     */
    public static List<BaseEntity> completionDate(List<BaseEntity> dataEntityList, String begin, String end) {
        //计算开始和结束的时间间隔
        List<String> dateList = getBetweenDate(begin,end);
        //时间填充
        List<BaseEntity> newEntityList = fillTheTime(dataEntityList, dateList);
        return newEntityList;
    }


    /**
     * 计算两日期的时间间隔
     *
     * @param strDate1
     * @param strDate2
     * @return
     * @throws ParseException
     */
    private static int dayDistance(String strDate1, String strDate2) {
        LocalDate beginDateTime = LocalDate.parse(strDate1, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDateTime = LocalDate.parse(strDate2, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        long day = ChronoUnit.DAYS.between(beginDateTime, endDateTime);
        int result = (int) Math.abs(day);
        return result;
    }

    /**
     * 获取两个日期之间所有的日期
     * @param start
     * @param end
     * @return
     */
    public static List<String> getBetweenDate(String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.toString());
        });
        return list;
    }
    /***********************************************日使者 ( • ̀ω•́ )✧ end****************************************************************/




    /***********************************************月使者 =￣ω￣= start****************************************************************/
    /**
     * 月份补0，包括中间空余的日期
     *
     * @param dataEntityList
     * @param begin
     * @param end
     * @return
     */
    public static List<BaseEntity> completionMonth(List<BaseEntity> dataEntityList, String begin, String end) {
        //计算开始和结束的时间间隔
        List<String> monthList = getBetweenMonth(begin,end);
        //时间填充
        List<BaseEntity> newEntityList = fillTheTime(dataEntityList, monthList);

        return newEntityList;
    }

    /**
     * 计算两月份的时间间隔
     * @param strDate1
     * @param strDate2
     * @return
     */
    private static int monthDistance(String strDate1, String strDate2) {
        LocalDate beginDateTime = LocalDate.parse(strDate1, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDateTime = LocalDate.parse(strDate2, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        long month = ChronoUnit.MONTHS.between(beginDateTime, endDateTime);
        int result = (int) Math.abs(month);
        return result;
    }

    /**
     * 获取两个月份之间的所有月份
     * @param start
     * @param end
     * @return
     */
    public static List<String> getBetweenMonth(String start, String end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_YYYY_MM);
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));

        long distance = ChronoUnit.MONTHS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, m -> {
            return m.plusMonths(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.format(dtf));
        });
        return list;
    }
    /***********************************************月使者 =￣ω￣= end****************************************************************/


    /***********************************************年使者 ヾ(◍°∇°◍)ﾉﾞ start****************************************************************/
    /**
     * 计算两年的时间间隔
     * @param strDate1
     * @param strDate2
     * @return
     */
    private static int yearDistance(String strDate1, String strDate2) {
        LocalDate beginDateTime = LocalDate.parse(strDate1, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDateTime = LocalDate.parse(strDate2, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        long year = ChronoUnit.YEARS.between(beginDateTime, endDateTime);
        int result = (int) Math.abs(year);
        return result;
    }

    /**
     * 获取两个年之间的所有年
     * @param start
     * @param end
     * @return
     */
    public static List<String> getBetweenYear(String start, String end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_YYYY);
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern(DEFAULT_YEAR_MON_DAY));
        long distance = ChronoUnit.YEARS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, y -> {
            return y.plusYears(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.format(dtf));
        });
        return list;
    }





    /***********************************************年使者 ヾ(◍°∇°◍)ﾉﾞ end****************************************************************/
    /**
     * 时间填充者 (　 ´-ω ･)▄︻┻┳══━一
     * @param dataEntityList    旧时间结构list
     * @param timeList
     */
    private static List<BaseEntity> fillTheTime(List<BaseEntity> dataEntityList, List<String> timeList) {
        List<BaseEntity> newEntityList = new ArrayList<>();
        if (timeList !=null){
            for (String tempStr : timeList) {
                BaseEntity baseEChartsDataEntity = new BaseEntity();
                baseEChartsDataEntity.setName(tempStr);
                if (dataEntityList!=null){
                    //是否dataEntity里存在
                    boolean isExist = false;
                    for (BaseEntity outEntity : dataEntityList) {
                        if (outEntity.getName().equals(tempStr)){
                            isExist = true;
                            baseEChartsDataEntity.setValue(outEntity.getValue());
                        }
                    }
                    //如果出了循环之后发现不存在当前日期的数据，则补0
                    if (isExist==false){
                        baseEChartsDataEntity.setValue(0);
                    }
                }
                newEntityList.add(baseEChartsDataEntity);
            }
        }
        return newEntityList;
    }

    public static void main(String[] args) {
        String begin = "2018-05-01 00:00:00";
        String end = "2018-09-12 00:00:00";
//        List<BaseEChartsDataEntity> dataEntityList = new ArrayList<>();
//        completionDate(dataEntityList, begin, end);
        System.out.println(yearDistance(begin,end));

    }


}

package com.utils;

import com.entity.BaseEntity;
import com.entity.DateRangeEntity;
import com.entity.PubCommon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.entity.PubCommon.DEFAULT_FORMAT_YYYY_MM_DD;
import static com.entity.PubCommon.DEFAULT_YEAR_MON_DAY;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: weicaijia
 * Date: 2018/8/23 18:30
 * Time: 14:15
 */
public class TimeCompareUtil {


    /***********************************************日 start****************************************************************/
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
        //判断日期格式
        DateRangeEntity dateRangeEntity = patternDate(start,end);
        if (dateRangeEntity!=null) {
            LocalDate startDate = dateRangeEntity.getStart();
            LocalDate endDate = dateRangeEntity.getEnd();
            long distance = ChronoUnit.DAYS.between(startDate, endDate);
            if (distance < 1) {
                return list;
            }
            Stream.iterate(startDate, d -> {
                return d.plusDays(1);
            }).limit(distance + 1).forEach(f -> {
                list.add(f.toString());
            });
        }
        return list;
    }
    /***********************************************日 end****************************************************************/




    /***********************************************月 start****************************************************************/
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PubCommon.DEFAULT_FORMAT_YYYY_MM);
        List<String> list = new ArrayList<>();
        //判断日期格式
        DateRangeEntity dateRangeEntity = patternDate(start,end);
        if (dateRangeEntity!=null){
            LocalDate startDate = dateRangeEntity.getStart();
            LocalDate endDate = dateRangeEntity.getEnd();
            long distance = ChronoUnit.MONTHS.between(startDate, endDate);
            if (distance < 1) {
                return list;
            }
            Stream.iterate(startDate, m -> {
                return m.plusMonths(1);
            }).limit(distance + 1).forEach(f -> {
                list.add(f.format(dtf));
            });
        }
        return list;
    }
    /***********************************************月使者 end****************************************************************/





    /***********************************************年使者 ヾ(◍°∇°◍)ﾉﾞ start****************************************************************/

    /**
     * 年补0，包括中间空余的日期
     *
     * @param dataEntityList
     * @param begin
     * @param end
     * @return
     */
    public static List<BaseEntity> completionYear(List<BaseEntity> dataEntityList, String begin, String end) {
        //计算开始和结束的时间间隔
        List<String> yearList = getBetweenYear(begin,end);
        //时间填充
        List<BaseEntity> newEntityList = fillTheTime(dataEntityList, yearList);
        return newEntityList;
    }


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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PubCommon.DEFAULT_FORMAT_YYYY);
        List<String> list = new ArrayList<>();
        DateRangeEntity dateRangeEntity = patternDate(start,end);
        if (dateRangeEntity!=null) {
            LocalDate startDate = dateRangeEntity.getStart();
            LocalDate endDate = dateRangeEntity.getEnd();
            long distance = ChronoUnit.YEARS.between(startDate, endDate);
            if (distance < 1) {
                return list;
            }
            Stream.iterate(startDate, y -> {
                return y.plusYears(1);
            }).limit(distance + 1).forEach(f -> {
                list.add(f.format(dtf));
            });
        }
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


    /**
     * 正则监管者  (〝▼皿▼)
     * 正则判断传过来的日期的格式
     * @param start
     * @param end
     * @return
     */
    private static DateRangeEntity patternDate(String start, String end){
        LocalDate startDate = null;
        LocalDate endDate = null;
        //yyyy-MM-dd
        String regexDate = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        //yyyy-MM-dd HH:mm:ss
        String regexDateDetail = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
        DateRangeEntity dateRangeEntity = new DateRangeEntity();
        if (Pattern.compile(regexDate).matcher(start).matches() && Pattern.compile(regexDate).matcher(end).matches()){
            //符合yyyy-MM-dd
            startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern(PubCommon.DEFAULT_FORMAT_YYYY_MM_DD));
            endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern(PubCommon.DEFAULT_FORMAT_YYYY_MM_DD));

        }else if (Pattern.compile(regexDateDetail).matcher(start).matches() && Pattern.compile(regexDateDetail).matcher(end).matches()){
            //符合yyyy-MM-dd HH:mm:ss
            startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern(PubCommon.DEFAULT_YEAR_MON_DAY));
            endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern(PubCommon.DEFAULT_YEAR_MON_DAY));
        }
        dateRangeEntity.setStart(startDate);
        dateRangeEntity.setEnd(endDate);
        return dateRangeEntity;
    }


    public static void main(String[] args) {
        String begin = "2018-05-01 00:00:00";
        String end = "2018-09-12 00:00:00";
//        List<BaseEChartsDataEntity> dataEntityList = new ArrayList<>();
//        completionDate(dataEntityList, begin, end);
        System.out.println(getBetweenMonth(begin,end));

    }


}

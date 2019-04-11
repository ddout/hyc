package com.ddout.hyc.text;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期格式化与解析工具
 */
public class DateFmtUtil {
    private static final List<String> formarts = new ArrayList<String>(4);

    static {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd hh:mm");
        formarts.add("yyyy-MM-dd hh:mm:ss");
        formarts.add("yyyy-MM-dd hh:mm:ss.SSS");
    }

    /**
     * 自动解析日期字符串，支持
     * formarts.add("yyyy-MM");
     * formarts.add("yyyy-MM-dd");
     * formarts.add("yyyy-MM-dd hh:mm");
     * formarts.add("yyyy-MM-dd hh:mm:ss");
     * formarts.add("yyyy-MM-dd hh:mm:ss.SSS");
     * 8-13位数字的时间戳格式;
     *
     * @param source String 日期字符串
     * @return Date 日期
     */
    public static Date parse(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }

        if (value.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(value, formarts.get(0));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(value, formarts.get(1));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(2));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, formarts.get(3));
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
            return parseDate(value, formarts.get(3));
        } else if (value.matches("^\\d{8,13}$")) {
            return new Date(Long.parseLong(value));
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }

    }


    /**
     * 功能描述：解析日期字符串
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 格式化日期
     *
     * @param date   Date 日期
     * @param format 格式
     * @return String 日期字符串
     */
    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String fmt = dateFormat.format(date);
        return fmt;
    }

    /**
     * 格式化日期;格式yyyy-MM-dd hh:mm:ss
     *
     * @param date Date 日期
     * @return String 日期字符串
     */
    public static String format(Date date) {
        return formatDate(date, formarts.get(3));
    }


    /**
     * 比较时间大小
     *
     * @param dt1
     * @param dt2
     * @return dt1>dt2=1,dt1<dt2=-1,dt1=dt2=0
     */
    public static int compareDate(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 比较时间大小
     *
     * @param dt1
     * @param dt2
     * @return dt1>dt2=1,dt1<dt2=-1,dt1=dt2=0
     */
    public static int compareDate(String dt1, String dt2) {
        try {
            Date dtt1 = parse(dt1);
            Date dtt2 = parse(dt2);
            if (dtt1.getTime() > dtt2.getTime()) {
                return 1;
            } else if (dtt1.getTime() < dtt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

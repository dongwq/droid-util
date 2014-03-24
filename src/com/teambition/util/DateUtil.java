package com.teambition.util;


import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: Administrator
 * Date: 2013/12/03
 * Time: 下午2:08
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";


    public static final String DATE_FORMAT_TO_MIN = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static final String DATE_FORMAT_JSON = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String DATE_FORMAT_A = "HH:mm";

    public static final String DATE_FORMAT_Short = "M月dd日";

    public static final String DATE_FORMAT_Title = "%s(%s)";
    public final static String GMT = "GMT";

    static SimpleDateFormat dateFormat;
    private final static String month = "月";
    private final static String day = "日";

    private DateUtil() {
    }

    public static boolean isToday(Date date) {
        return DateUtils.isToday(date.getTime());
    }

    public static boolean isBeforeToday(Date date) {
        return date != null && date.before(DateUtil.getDateStart(new Date()));
    }

    public static boolean isTodayOrBefore(Date date) {
        return DateUtils.isToday(date.getTime()) || date.before(new Date());
    }

    public static String dateFormat(Date date, int format) {
        DateFormat dateFormat1 = DateFormat.getDateInstance(format);
        return dateFormat1.format(date);
    }

    public static String dateFormatShort(Date date) {
        DateFormat dateFormat1 = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormat1.format(date);
    }

    public static String timeFormat(Date date, int format) {
        DateFormat dateFormat1 = DateFormat.getTimeInstance(format);
        return dateFormat1.format(date);
    }

    public static String dateTimeFormat(Date date, int dateFormat, int timeFormat) {
        DateFormat dateFormat1 = DateFormat.getDateTimeInstance(dateFormat, timeFormat);
        return dateFormat1.format(date);
    }

    public static String mediumFormat(Date date) {
        DateFormat dateFormat1 = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return dateFormat1.format(date);
    }

    public static String mediumFormatWithTodayText(Context context, Date date, int txtToday, int txtTomorrow) {
        if (date == null) return "";

        if (DateUtil.isTodayOrBefore(date)) {
            if (DateUtil.isToday(date)) {
                return String.format(DATE_FORMAT_Title, context.getString(txtToday), DateUtil.mediumFormat(date));
            } else {
                return DateUtil.mediumFormat(date);
            }
        }
        if (DateUtil.isBeforeTomorrow(date)) {
            return String.format(DATE_FORMAT_Title, context.getString(txtTomorrow), DateUtil.mediumFormat(date));
        }

        return DateUtil.mediumFormat(date);
    }

    public static String commonDateFormat(Context context, Date date, int txtToday, int txtTomorrow) {
        if (date == null) return "";

        if (DateUtil.isTodayOrBefore(date)) {
            if (DateUtil.isToday(date)) {
                return context.getString(txtToday);
            } else {
                if (DateUtil.isThisYear(date)) {
                    return DateUtil.formatDate(date, DATE_FORMAT_Short);
                }
                return DateUtil.mediumFormat(date);
            }
        }

        if (DateUtil.isBeforeTomorrow(date)) {
            return context.getString(txtTomorrow);
        }

        return DateUtil.mediumFormat(date);
    }

    public static boolean isThisYear(Date date1) {
        Date date2 = new Date();

        if (date1 != null && date2 != null) {
            return DateUtil.getDateElement(date1, Calendar.YEAR) == DateUtil.getDateElement(date2, Calendar.YEAR);
        }

        return false;
    }

    public static String timeFormat(Date date) {
        return DateUtil.formatLocale(date, DateUtil.DATE_FORMAT_A);
    }


    public static boolean isSameDay(Date aDate, Date bDate) {
        if (aDate != null && bDate != null) {
            if (DateUtil.format_YYYY_MM_DD(aDate).equals(DateUtil.format_YYYY_MM_DD(bDate)))
                return true;
        }

        return false;
    }

    public static boolean isBeforeTomorrow(Date date) {
        return date.before(getNextDayEnd());
    }

    private static Date getNextDayEnd() {
        Date date1 = datePlusNDay(new Date(), 1);//明天
        date1 = getDateEnd(date1);
        return date1;
    }

    public static Date datePlusNDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, n);

        return calendar.getTime();
    }

    public static Date hourRoundPlusHour(Date date, int n) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);

        calendar.add(Calendar.HOUR_OF_DAY, n);

        return calendar.getTime();
    }

    public static Date getDateEnd(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    public static Date getDateStart(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }


    /**
     * 返回年月日
     */
    public static String format_YYYY_MM_DD(int year, int month, int day) {
        return String.format("%d-%d-%d", year, month + 1, day);
    }

    public static String format_YYYY_MM_DD(Date date) {
        return formatDate(date, DateUtil.YYYY_MM_DD);
    }

    public static String formatFullFormat(Date date) {
        return formatDate(date, DateUtil.DATE_FORMAT_FULL);
    }

    /**
     * @param date
     * @param ele, the Field of Calendar
     * @return
     */
    public static int getDateElement(Date date, int ele) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(ele);
    }

    public static String formatDate(Date date, String format) {
        String result = "";
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            try {
                result = dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }

    public static String formatLocale(Date date, String format) {
        String result = "";
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.applyLocalizedPattern(format);

            try {
                result = dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }

    public static String formatISO8601(Date date, String format) {
        String result = "";
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            dateFormat.setTimeZone(timeZone);
            try {
                result = dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        }
        return result;
    }

    public static Date parseISO8601(String dateStr, String format) {
        Date date = null;
        if (StringUtil.isNotBlank(dateStr)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            dateFormat.setTimeZone(timeZone);
            try {
                date = dateFormat.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static CharSequence getRelativeTimeSpanString(Context context, Date date) {
        if (date != null)
            return DateUtils.getRelativeTimeSpanString(context, date.getTime());
        return "";
    }

    public static Date parse_YYYY_MM_DD(String date) {
        return parseDate(date, YYYY_MM_DD);
    }

    public static Date parseDate(String date, String pattern) {
        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            result = dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回年月日
     */
    public String getTimeByString(String startTime) {
        String result = "";
        dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date start = dateFormat.parse(startTime);
            Calendar calTemp = Calendar.getInstance();
            calTemp.setTime(start);
            result = (calTemp.get(Calendar.YEAR)) + "-" + (calTemp.get(Calendar.MONTH) + 1) + "-" +
                    calTemp.get(Calendar.DAY_OF_MONTH);
            android.util.Log.d("Format", "" + result);
        } catch (ParseException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 日期比较，返回值为：昨，今，明，年，月日*
     */
    public static String timeRecent(Date date) {
        String result = "";
        try {
            Date endTime = new Date(System.currentTimeMillis());

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(endTime);
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(date);
            Calendar calYes = Calendar.getInstance();
            Calendar calTom = Calendar.getInstance();
            calYes.roll(Calendar.DAY_OF_YEAR, -1);
            calTom.roll(Calendar.DAY_OF_YEAR, 1);
            switch (dateCompareTo(calEnd, calStart)) {
                case -1:
                    if (dateCompareTo(calStart, calTom) == 0) {
                        result = "Tomorrow";
                    } else {
                        if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
                            result = (calStart.get(Calendar.MONTH) + 1) + "月" +
                                    calStart.get(Calendar.DAY_OF_MONTH) + "日";
                        } else {
//                            result = (calStart.get(Calendar.YEAR) - 1900) + "";
                            result = calStart.get(Calendar.YEAR) + "";
                        }
                    }
                    break;
                case 0:
                    result = "Today";
                    break;
                case 1:
                    if (dateCompareTo(calStart, calYes) == 0) {
                        result = "Yesterday";
                    } else {
                        if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
                            result = (calStart.get(Calendar.MONTH) + 1) + "月" +
                                    calStart.get(Calendar.DAY_OF_MONTH) + "日";
                        } else {
                            result = (calStart.get(Calendar.YEAR)) + "";
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int dateCompareTo(Calendar calEnd, Calendar calStart) {
        if (calEnd.get(Calendar.DAY_OF_YEAR) == calStart.get(Calendar.DAY_OF_YEAR)) {
            if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
                return 0;
            } else {
                if (calEnd.get(Calendar.YEAR) > calStart.get(Calendar.YEAR)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else {
            if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
                if (calEnd.get(Calendar.DAY_OF_YEAR) > calStart.get(Calendar.DAY_OF_YEAR)) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (calEnd.get(Calendar.YEAR) > calStart.get(Calendar.YEAR)) {
                return 1;
            } else {
                return -1;
            }

        }
    }


    /**
     * 分钟，小时，一天，二天，三天，日期（本年内不带年），日期全，
     *
     * @param startTime
     * @return
     */
    public static String timeRelative(Date startTime) {

        String result = "";
        if (startTime == null) return result;

        Date endTime = new Date(System.currentTimeMillis());
        long between = (endTime.getTime() - startTime.getTime()) / 1000;
        long dayTemp = between / (24 * 3600);
        long hourTemp = between % (24 * 3600) / 3600;
        long minuteTemp = between % 3600 / 60;
        long secondTemp = between % 60 / 60;

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(startTime);
//        if (dayTemp == 0) {
//            if (hourTemp == 0) {
//                if (minuteTemp == 0) {
//                    if (secondTemp == 0) {
//                        result = "刚刚";
//                    } else {
//                        result = secondTemp + "秒前";
//                    }
//                } else {
//                    result = minuteTemp + "分钟前";
//                }
//            } else {
//                result = hourTemp + "小时前";
//            }
//        } else {
//            if (dayTemp >= 30) {
//                if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
//                    result = (calStart.get(Calendar.MONTH) + 1) + "月" + calStart.get(Calendar.DAY_OF_MONTH) + "日";
//                } else {
//                    result = (calStart.get(Calendar.YEAR)) + "";
//                }
//
//            } else {
//                result = dayTemp + "天前";
//            }
//        }

        if (dayTemp > 0) {
            if (dayTemp >= 30) {
                if (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR)) {
                    result = (calStart.get(Calendar.MONTH) + 1) + "月" + calStart.get(Calendar.DAY_OF_MONTH) + "日";
                } else {
                    result = (calStart.get(Calendar.YEAR)) + "年" + (calStart.get(Calendar.MONTH) + 1) + "月";
                }
            } else {
                result = dayTemp + "天前";
            }
        } else {
            if (hourTemp > 0) {
                result = hourTemp + "小时前";
            } else {
                if (minuteTemp > 0) {
                    result = minuteTemp + "分钟前";
                } else {
                    if (secondTemp > 0) {
                        result = secondTemp + "秒前";
                    } else {
                        result = "刚刚";
                    }
                }
            }
        }

        return result;
    }


//    public String getTaskDueStatus(Context context, Date end) {
//
//        String overDue = context.getResources().getString(R.string.task_list_date_over_due);
//
//        if (end != null) {
//            try {
//                Date startTime = new Date(System.currentTimeMillis());
//                Calendar calStart = Calendar.getInstance();
//                Calendar calEnd = Calendar.getInstance();
//                calStart.setTime(startTime);
//                calEnd.setTime(end);
//                int intDayEnd = calEnd.get(Calendar.DAY_OF_MONTH);
//                int intDayStart = calStart.get(Calendar.DAY_OF_MONTH);
//                int intMonthEnd = calEnd.get(Calendar.MONTH);
//                int intMonthStart = calEnd.get(Calendar.MONTH);
//                int intYearEnd = calEnd.get(Calendar.YEAR);
//                int intYearStart = calStart.get(Calendar.YEAR);
//                if (startTime.compareTo(end) > 0) {
//                    if (intDayEnd == intDayStart) {
//                        if (intMonthEnd != intMonthStart || intYearEnd != intYearStart) return "overdue";
//                        return "due";
//                    }
//                    return "overdue";
//                } else {
//                    if (intDayEnd == intDayStart + 1) {
//                        return "tomorrow";
//                    } else {
//                        if (intYearEnd == intYearStart) {
//                            return (intMonthEnd + 1) + month + intDayEnd + day;
//                        }
//                        return (intYearEnd) + "";
//                    }
//                }
//            } catch (Exception e) {
//                return "";
//            }
//        }
//        return "";
//    }

//    /**
//     * 将日期时间字符串根据转换为指定时区的日期时间.
//     *
//     * @param srcFormater 待转化的日期时间的格式.
//     * @param srcDateTime 待转化的日期时间.
//     * @param dstFormater 目标的日期时间的格式.
//     * @return 转化后的日期时间.
//     */
//    public static String string2Timezone(String srcFormater,
//                                         String srcDateTime, String dstFormater) {
//        if (srcFormater == null || "".equals(srcFormater))
//            return null;
//        if (srcDateTime == null || "".equals(srcDateTime))
//            return null;
//        if (dstFormater == null || "".equals(dstFormater))
//            return null;
//        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
//        try {
//            int diffTime = getDiffTimeZoneRawOffset();
//            Date d = sdf.parse(srcDateTime);
//            long nowTime = d.getTime();
//            long newNowTime = nowTime - diffTime;
//            d = new Date(newNowTime);
//            return date2String(dstFormater, d);
//        } catch (ParseException e) {
//            return null;
//        } finally {
//            sdf = null;
//        }
//    }

    /**
     * 获取系统当前默认时区与UTC时区的时间差.(单位:毫秒)
     *
     * @return 系统当前默认时区与UTC时区的时间差.(单位:毫秒)
     */
    private static int getDiffTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(GMT).getRawOffset();
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

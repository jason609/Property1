package com.ctrl.android.property.jason.util;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jason on 2015/10/29.
 */
public class TimeUtil {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static String dateTime(String ctime){
        Long time=Long.parseLong(ctime);
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "刚刚";
        } else if (diff < 2 * MINUTE) {
            return "1分钟前";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " 分钟前";
        } else if (diff < 90 * MINUTE) {
            return "1小时前";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " 小时前";
        } else if (diff < 48 * HOUR) {
            return "昨天";
        } else {
            return diff / DAY + " 天前";
        }

    }

    /*
    * 毫秒转日期
    * */

    public static String date(Long ltiem){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltiem);
        return formatter.format(calendar.getTime());
    }




}


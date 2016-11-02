package com.ctrl.android.property.staff.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 时间格式处理的相关方法
 * Created by Eric on 2015/9/14.
 */
public class D {

    /**
     * 获取当前时间 并 转化为规定格式
     * @param strFormate 例: yyyy-MM-dd HH:mm:ss
     * */
    public static String getCurrentDateStr(String strFormate){
        SimpleDateFormat sdf = new SimpleDateFormat(strFormate);
        String dateStr = sdf.format(new Date());
        return dateStr;
    }

    /**
     * 将时间戳类型的时间 转换为规定格式的时间
     * @param strFormate 例: yyyy-MM-dd HH:mm:ss
     * */
    public static String getDateStrFromStamp(String strFormate, String timeStamp){

        if(timeStamp == null || timeStamp.equals("")){
            return "";
        } else {
            SimpleDateFormat sdf=new SimpleDateFormat(strFormate);
            String dateStr = sdf.format(new Date(Long.parseLong(timeStamp)));
            return dateStr;
        }

    }

    /**
     * 判断时间time是不是在pre跟last之间的时间
     * @return
     * @throws ParseException
     */
    public static boolean compare_date(String timeStr, String preStr, String lastStr) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean flag = false;
        Date timeDat = f.parse(timeStr);
        Date preDat = f.parse(preStr);
        Date lastDat = f.parse(lastStr);
        if (timeDat.after(preDat) && timeDat.before(lastDat))
            flag = true;
        return flag;
    }

    /**
     * 获取一个前N年的年份列表
     * @param yearNum 需要几年
     * */
    public static ArrayList<String> getRecentYears(int yearNum){
        ArrayList<String> list = new ArrayList<>();
        String yearStr = getCurrentDateStr("yyyy");

        for(int i = (yearNum - 1) ; i >= 0 ; i -- ){
            list.add(String.valueOf(Integer.parseInt(yearStr) - i));
        }

        return list;
    }

    /**
     * 获取一个前5年的年份列表
     * */
    public static ArrayList<String> getRecent5Years(){
        ArrayList<String> list = new ArrayList<>();
        String yearStr = getCurrentDateStr("yyyy");
        for(int i = 4 ; i >= 0 ; i -- ){
            list.add(String.valueOf(Integer.parseInt(yearStr) - i));
        }

        return list;
    }

    /**
     * 获取一个所有月份的列表
     * */
    public static ArrayList<String> getAllMonths(){
        ArrayList<String> list = new ArrayList<>();

        for(int i = 1 ; i <= 12 ; i ++ ){
            list.add(String.valueOf(i));
        }

        return list;
    }

}

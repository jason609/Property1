package com.ctrl.android.property.eric.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 关于数字处理的相关方法
 * Created by Eric on 2015/9/14.
 */
public class N {

    /**
     * 使数字按最小保留小数位数格式化,3个为一组添加逗号
     * @param num 需要格式化数字
     * @param digits 最小保留小数位数
     * @return
     */
    public static String fractionDigits(float num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    public static String fractionDigits(double num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    /**
     * 获取精确到小数点后两位的价格
     * */
    public static String toPriceFormate(double price){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(price);
    }

    /**
     * 获取精确到小数点后两位的价格
     * */
    public static String toPriceFormate(String priceStr){
        DecimalFormat df = new DecimalFormat("0.00");
        if(priceStr == null || priceStr.equals("")){
            priceStr = "0.00";
        }
        return df.format(Double.parseDouble(priceStr));
    }

}

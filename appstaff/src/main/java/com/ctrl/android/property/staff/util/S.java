package com.ctrl.android.property.staff.util;

import java.util.List;
import java.util.Map;

/**
 * 字符串的相关处理方法
 * Created by Eric on 2015/9/14.
 */
public class S {

    /**
     * 判断字符串是否为 NULL 或者 "",
     * @return
     * 为 NULL 或者 "" 时返回true , 不为 NULL 或者 "" 时返回false
     * */
    public static boolean isNull(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为 NULL 或者 "",
     * @return
     * 为 NULL 或者 "" 时返回 "" , 不为 NULL 或者 "" 时返回字符串
     * */
    public static String getStr(String str){
        if (str == null || str.equals("") || str.length() <= 0) {
            return "";
        }
        return str;
    }

    /**
     * 判断字符串是否为 NULL 或者 "",
     * @param n 整形
     * @return
     * 为 NULL 或者 "" 时返回 "" , 不为 NULL 或者 "" 时返回字符串
     * */
    public static String getStr(int n){
        return String.valueOf(n);
    }


    public static String MESSAGEID = "questionnaireMessageId";
    public static String OPTIONNUM = "optionNo";
    /**
     * 创建一个Json字符串
     * @param listMap 需要转换的map
     * */
    public static String getJsonStr(List<Map<String,String>> listMap){
        StringBuilder sb = new StringBuilder();
//        List<Map<String,String>> listMap = new ArrayList<>();
//        for(int i = 0 ; i < 10 ; i ++){
//            Map<String,String> map = new HashMap<>();
//            map.put(MESSAGEID,MESSAGEID+i);
//            map.put(OPTIONNUM,OPTIONNUM+i);
//            listMap.add(map);
//        }
        if(listMap != null){
            if(listMap.size() == 1){
                sb.append("[{\"");
                sb.append(MESSAGEID);
                sb.append("\":\"");
                sb.append(listMap.get(0).get(MESSAGEID));
                sb.append("\",\"");
                sb.append(OPTIONNUM);
                sb.append("\":\"");
                sb.append(listMap.get(0).get(OPTIONNUM));
                sb.append("\"}]");
            } else {
                for(int i = 0 ; i < listMap.size() ; i ++){
                    if(i == 0){
                        sb.append("[{\"");
                        sb.append(MESSAGEID);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(MESSAGEID));
                        sb.append("\",\"");
                        sb.append(OPTIONNUM);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(OPTIONNUM));
                        sb.append("\"}");
                    } else if(i == (listMap.size()-1)){
                        sb.append(",{\"");
                        sb.append(MESSAGEID);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(MESSAGEID));
                        sb.append("\",\"");
                        sb.append(OPTIONNUM);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(OPTIONNUM));
                        sb.append("\"}]");
                    } else {
                        sb.append(",{\"");
                        sb.append(MESSAGEID);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(MESSAGEID));
                        sb.append("\",\"");
                        sb.append(OPTIONNUM);
                        sb.append("\":\"");
                        sb.append(listMap.get(i).get(OPTIONNUM));
                        sb.append("\"}");
                    }
                }
            }

        }
        //Log.d("demo",sb.toString());
        return sb.toString();
    }


    /**
     * 根据预约状态编号  返回预约状态文本
     * @param status 状态
     * （0：已预约 1：已结束 2：同意到访 3：其他）
     * */
//    public static String getVisitStatus(int status){
//        String str = "";
//
//        if(status == 0){
//            str = "已预约";
//        }
//
//        if(status == 1){
//            str = "已结束";
//        }
//
//        if(status == 2){
//            str = "同意到访";
//        }
//
//        if(status == 3){
//            str = "其他";
//        }
//        return str;
//    }

    /**
     * 根据订单状态编号  返回订单状态文本
     * @param status 状态
     * 订单状态（
     * "0"：已取消、
     * "1"：未付款、
     * "2"：未接单/等待接单、
     * "3"：商家取消订单、
     * "4"：已接单/等待送达、
     * "5"：配送中、
     * "6"：待退款、
     * "7"：已退款、
     * "8"：卖家拒绝退款、
     * "9"：退款中、
     * "10"：待退货、
     * "11"：已退货、
     * "12"：卖家拒绝退货、
     * "13"：已送达(交易成功)、）
     * */
    public static String getOrderStatus(int status){
        String str = "";

        if(status == 0){
            str = "已取消";
        }

        if(status == 1){
            str = "未付款";
        }

        if(status == 2){
            str = "未接单/等待接单";
        }

        if(status == 3){
            str = "商家取消订单";
        }

        if(status == 4){
            str = "已接单/等待送达";
        }

        if(status == 5){
            str = "配送中";
        }

        if(status == 6){
            str = "待退款";
        }

        if(status == 7){
            str = "已退款";
        }

        if(status == 8){
            str = "卖家拒绝退款";
        }

        if(status == 9){
            str = "退款中";
        }

        if(status == 10){
            str = "待退货";
        }

        if(status == 11){
            str = "已退货";
        }

        if(status == 12){
            str = "卖家拒绝退货";
        }

        if(status == 13){
            str = "已送达(交易成功)";
        }

        return str;
    }

    /**返回支付类型文本
     * @param status 状态（
     * 1：余额支付、
     * 2：线下支付【货到付款】、
     * 3、支付宝、
     * 4：微信、
     * 5：银联卡）*/
    public static String getPayTypeStr(int status){
        String str = "";

        if(status == 1){
            str = "余额支付";
        }

        if(status == 2){
            str = "线下支付【货到付款】";
        }

        if(status == 3){
            str = "支付宝";
        }

        if(status == 4){
            str = "微信";
        }

        if(status == 5){
            str = "银联卡";
        }
        return str;
    }

}

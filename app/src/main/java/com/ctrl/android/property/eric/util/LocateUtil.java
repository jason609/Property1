package com.ctrl.android.property.eric.util;

/**
 * 百度地图定位工具类
 * Created by Eric on 2015/10/21.
 */
public class LocateUtil {

    /**
     * 获得定位错误码时的信息
     * */
    public static String getLocateErrStr(int locType){
        String errStr = "";
        if(61 == locType){
            errStr = "GPS定位结果，GPS定位成功。";
        }

        if(62 == locType){
            errStr = "无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。";
        }

        if(63 == locType){
            errStr = "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。";
        }

        if(65 == locType){
            errStr = "定位缓存的结果。";
        }

        if(66 == locType){
            errStr = "离线定位结果。";
        }

        if(67 == locType){
            errStr = "离线定位失败。";
        }

        if(68 == locType){
            errStr = "网络连接失败时，查找本地离线定位时对应的返回结果。";
        }

        if(161 == locType){
            errStr = "网络定位结果，网络定位定位成功。";
        }

        if(162 == locType){
            errStr = "请求串密文解析失败。";
        }

        if(167 == locType){
            errStr = "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。";
        }

        if(locType >= 501 && locType <= 700){
            errStr = "key验证失败";
        }
        return errStr;
    }

}

package com.ctrl.android.property.staff.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 关于应用的一些方法
 * Created by Eric on 2015/9/14.
 */
public class AppUtil {

    /**获取当前app的版本名*/
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**获取当前app的版本号*/
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**获取当前app的打包信息*/
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}

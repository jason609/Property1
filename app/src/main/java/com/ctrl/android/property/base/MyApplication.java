package com.ctrl.android.property.base;

import android.app.Activity;

import com.baidu.mapapi.SDKInitializer;
import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;
import com.beanu.arad.http.HttpConfig;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * 实例化 Arad
 * Created by Eric on 2015/9/25.
 */
public class MyApplication extends AradApplication {
    //public LocationClient mLocationClient = null;

    //运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static MyApplication instance;
    //构造方法
    //private MyApplication(){}
    //实例化一次
    public synchronized static MyApplication getInstance(){
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }
    //关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity:mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    //杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate();
        //SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + Constant.XUNFEI_APPID); //科大讯飞
        //SDKInitializer.initialize(getApplicationContext());
        //LocationUtil.getInstance().init(getApplicationContext());

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        config.httpConfig = new HttpConfig("succeed");
    }

    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }

}

package com.ctrl.android.property;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.InitDao;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 快显页面
 * Created by Eric on 2015/9/22.
 */
public class StartActivity extends AppToolBarActivity{

    private InitDao initDao;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.start_activity);

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        ButterKnife.inject(this);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            //MessageUtils.showShortToast(this, "请求成功");
            String username = Arad.preferences.getString(Constant.USERNAME);
            String password = Arad.preferences.getString(Constant.PASSWORD);

            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(StartActivity.this);

            finish();
        }

    }


    /**
     * 启动定位的方法
     * */
    private void locate(){
        initLocation();
        mLocationClient.start();
    }

    /**
     * 初始化定位设置
     * */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span=1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
       // option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 返回BDLocationListener 的监听
     * */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location != null){
                AppHolder.getInstance().setBdLocation(location);
                AppHolder.getInstance().setBdLocationMall(location);

                AppHolder.getInstance().getAddress().setName(location.getAddrStr());
                AppHolder.getInstance().getAddress().setAddress(location.getAddrStr());
                AppHolder.getInstance().getAddress().setLatitude(location.getLatitude());
                AppHolder.getInstance().getAddress().setLongitude(location.getLongitude());
                AppHolder.getInstance().getAddress().setProvince(location.getProvince());
                AppHolder.getInstance().getAddress().setCity(location.getCity());
                AppHolder.getInstance().getAddress().setDistrict(location.getDistrict());

                Log.d("demo", "Name: " + AppHolder.getInstance().getAddress().getName());
                Log.d("demo","Address: " + AppHolder.getInstance().getAddress().getAddress());
                Log.d("demo","Latitude: " + AppHolder.getInstance().getAddress().getLatitude());
                Log.d("demo","Longitude: " + AppHolder.getInstance().getAddress().getLongitude());
                Log.d("demo","Province: " + AppHolder.getInstance().getAddress().getProvince());
                Log.d("demo","City: " + AppHolder.getInstance().getAddress().getCity());
                Log.d("demo","District: " + AppHolder.getInstance().getAddress().getDistrict());

            } else {
                AppHolder.getInstance().setBdLocation(new BDLocation());
                AppHolder.getInstance().setBdLocationMall(new BDLocation());
            }

            String username = Arad.preferences.getString(Constant.USERNAME);
            String password = Arad.preferences.getString(Constant.PASSWORD);
            if(isFirst) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(StartActivity.this);
                finish();
            }
            isFirst=false;
        }
    }

    protected void toHomePage(){
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }


    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mLocationClient != null){
            mLocationClient.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class splashhandler implements Runnable{
        public void run() {
            locate();
        }

    }

}

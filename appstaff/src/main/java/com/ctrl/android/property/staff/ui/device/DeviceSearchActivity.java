package com.ctrl.android.property.staff.ui.device;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.DeviceDao;
import com.ctrl.android.property.staff.entity.DeviceDetail;
import com.ctrl.android.property.staff.entity.DeviceRecord;
import com.ctrl.android.property.staff.ui.adapter.DeviceHistoryAdapter;
import com.ctrl.android.property.staff.ui.adapter.ListItemAdapter;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *  设备养护查询  activity
 * Created by Eric on 2015/10/22
 */
public class DeviceSearchActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.device_name)//设备名称
    TextView device_name;
    @InjectView(R.id.device_locate)//设备位置
    TextView device_locate;
    @InjectView(R.id.device_time)//购置时间
    TextView device_time;
    @InjectView(R.id.device_cycle)//养护周期
    TextView device_cycle;
    @InjectView(R.id.device_man)//责任人
    TextView device_man;
    @InjectView(R.id.device_provider)//维护厂家
    TextView device_provider;
    @InjectView(R.id.device_tel)//厂家电话
    TextView device_tel;
    @InjectView(R.id.tel_btn)//电话按钮
    ImageView tel_btn;

    @InjectView(R.id.year_btn)//年份
    TextView year_btn;
    @InjectView(R.id.month_btn)//月份
    TextView month_btn;
    @InjectView(R.id.search_btn)//搜索
    TextView search_btn;

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private ArrayList<String> listYear;
    private ArrayList<String> listMonth;

    private ListItemAdapter itemAdapter;
    private DeviceHistoryAdapter deviceHistoryAdapter;

    private String TITLE = "设备养护";

    private View mMenuView;//显示pop的view

    private DeviceDao deviceDao;
    private String deviceId;

    private DeviceDetail deviceDetail;
    private List<DeviceRecord> listDeviceRecord;

    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.device_search_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        year_btn.setOnClickListener(this);
        month_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);

        deviceId = getIntent().getStringExtra("id");


        deviceDao = new DeviceDao(this);
        showProgress(true);
        deviceDao.requestDeviceDetail(deviceId);

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(2 == requestCode){
            deviceDetail = deviceDao.getDeviceDetail();

            device_name.setText(S.getStr(deviceDetail.getName()));
            device_locate.setText(S.getStr(deviceDetail.getLocation()));
            //device_time.setText(D.getDateStrFromStamp("yyyy-MM-dd", deviceDetail.getPurchaseTime()));
            device_time.setText(S.getStr(deviceDetail.getPurchaseTime()));
            device_cycle.setText(S.getStr(deviceDetail.getCuringCycle()));
            device_man.setText(S.getStr(deviceDetail.getManagerName()));
            device_provider.setText(S.getStr(deviceDetail.getFactoryName()));
            device_tel.setText(S.getStr(deviceDetail.getFactoryTelephone()));

            tel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!S.isNull(deviceDetail.getFactoryTelephone())) {
                        AndroidUtil.dial(DeviceSearchActivity.this, deviceDetail.getFactoryTelephone());
                    } else {
                        MessageUtils.showShortToast(DeviceSearchActivity.this, "未获得电话号码");
                    }
                }
            });

        }

        if(3 == requestCode){
            listDeviceRecord = deviceDao.getListDeviceRecord();

            mListView = mPullToRefreshListView.getRefreshableView();
            deviceHistoryAdapter = new DeviceHistoryAdapter(this);
            deviceHistoryAdapter.setList(listDeviceRecord);
            mListView.setAdapter(deviceHistoryAdapter);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    deviceDao.getListDeviceRecord().clear();

                    int year = Integer.parseInt(year_btn.getText().toString());
                    int month = Integer.parseInt(month_btn.getText().toString());
                    //int day = Integer.parseInt(getDaysNum(year, month));

                    String startTime = "";
                    String endTime = "";

                    startTime = year + "-" + month + "-01 00:00:00";
                    if((month + 1) > 12){
                        endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
                    } else {
                        endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
                    }

                    //String currentPage,
                    //String rowCountPerPage
                    Log.d("demo", "startTime : " + startTime);
                    Log.d("demo","endTime : " + endTime);
                    showProgress(true);
                    deviceDao.requestDeviceRecordList(deviceId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;

                    int year = Integer.parseInt(year_btn.getText().toString());
                    int month = Integer.parseInt(month_btn.getText().toString());
                    //int day = Integer.parseInt(getDaysNum(year, month));

                    String startTime = "";
                    String endTime = "";

                    startTime = year + "-" + month + "-01 00:00:00";
                    if((month + 1) > 12){
                        endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
                    } else {
                        endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
                    }

                    //String currentPage,
                    //String rowCountPerPage
                    Log.d("demo", "startTime : " + startTime);
                    Log.d("demo","endTime : " + endTime);
                    showProgress(true);
                    deviceDao.requestDeviceRecordList(deviceId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DeviceSearchActivity.this, DeviceRecordDetailActivity.class);
                    intent.putExtra("id", deviceId);
                    intent.putExtra("recordId", listDeviceRecord.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(DeviceSearchActivity.this);
                }
            });
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
    }

    @Override
    public void onClick(View v) {
        if(v == year_btn){
            showYearListPop();
        }

        if(v == month_btn){
            showMonthListPop();
        }

        if(v == search_btn){
            //MessageUtils.showShortToast(this, year_btn.getText().toString() + "-" + month_btn.getText().toString());

            if(checkInput()){
                int year = Integer.parseInt(year_btn.getText().toString());
                int month = Integer.parseInt(month_btn.getText().toString());
                //int day = Integer.parseInt(getDaysNum(year, month));

                String startTime = "";
                String endTime = "";

                startTime = year + "-" + month + "-01 00:00:00";
                if((month + 1) > 12){
                    endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
                } else {
                    endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
                }

                //String currentPage,
                //String rowCountPerPage
                Log.d("demo", "startTime : " + startTime);
                Log.d("demo","endTime : " + endTime);
                showProgress(true);
                deviceDao.requestDeviceRecordList(deviceId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
            }


        }
    }

    private boolean checkInput(){
        if(S.isNull(year_btn.getText().toString()) || (year_btn.getText().toString()).equals("年份")){
            MessageUtils.showShortToast(this,"请选择年份");
            return false;
        }

        if(S.isNull(month_btn.getText().toString()) || (month_btn.getText().toString()).equals("月份")){
            MessageUtils.showShortToast(this,"请选择月份");
            return false;
        }

        return true;
    }


    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
                onBackPressed();
            }
        });
        return true;
    }

    /**
     * 显示年份的 popwindow
     * */
    private void showYearListPop(){

        listYear = new ArrayList<>();
        listYear = D.getRecent5Years();
        itemAdapter = new ListItemAdapter(this);
        itemAdapter.setList(listYear);

        mMenuView = LayoutInflater.from(DeviceSearchActivity.this).inflate(R.layout.choose_list_pop, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(year_btn.getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(DeviceSearchActivity.this, listYear.get(position));
                year_btn.setText(S.getStr(listYear.get(position)));
                pop.dismiss();
            }
        });

        int[] location = new int[2];
        year_btn.getLocationOnScreen(location);
        //Pop.showAtLocation(year_btn, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());
        pop.showAsDropDown(year_btn);
        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    /**
     * 显示月份的 popwindow
     * */
    private void showMonthListPop(){

        listMonth = new ArrayList<>();
        listMonth = D.getAllMonths();
        itemAdapter = new ListItemAdapter(this);
        itemAdapter.setList(listMonth);

        mMenuView = LayoutInflater.from(DeviceSearchActivity.this).inflate(R.layout.choose_list_pop, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);



        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(month_btn.getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(DeviceSearchActivity.this, listMonth.get(position));
                month_btn.setText(S.getStr(listMonth.get(position)));
                pop.dismiss();
            }
        });

        int[] location = new int[2];
        month_btn.getLocationOnScreen(location);
        //Pop.showAtLocation(year_btn, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());
        pop.showAsDropDown(month_btn);
        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    private String getDaysNum(int year,int month){

        ArrayList<String> listDay = new ArrayList<>();

        listDay = new ArrayList<>();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        String dayStr = sdf1.format(new Date());
        int day = Integer.parseInt(dayStr);

        if(2 == month){
            if(year % 4 == 0){
                for(int i = 1 ; i <= 29 ; i ++){
                    if(i < 10){
                        listDay.add("0"+String.valueOf(i));
                    } else {
                        listDay.add(String.valueOf(i));
                    }
                    //listDay.add(String.valueOf(i));
                }
            } else {
                for(int i = 1 ; i <= 28 ; i ++){
                    if(i < 10){
                        listDay.add("0"+String.valueOf(i));
                    } else {
                        listDay.add(String.valueOf(i));
                    }
                    //listDay.add(String.valueOf(i));
                }
            }
        }

        if(month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12 ){
            for(int i = 1 ; i <= 31 ; i ++){
                if(i < 10){
                    listDay.add("0"+String.valueOf(i));
                } else {
                    listDay.add(String.valueOf(i));
                }
                //listDay.add(String.valueOf(i));
            }
        }

        if(month == 4 || month == 6 || month == 9 || month == 11 ){
            for(int i = 1 ; i <= 30 ; i ++){
                if(i < 10){
                    listDay.add("0"+String.valueOf(i));
                } else {
                    listDay.add(String.valueOf(i));
                }
                //listDay.add(String.valueOf(i));
            }
        }

        return String.valueOf(listDay.size());
    }



    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name", "条目" + i);
            map.put("time", "10月" + i + "日");
            map.put("amount","20" + i + ".5" + i + "9");
            list.add(map);
        }
        return list;
    }



}

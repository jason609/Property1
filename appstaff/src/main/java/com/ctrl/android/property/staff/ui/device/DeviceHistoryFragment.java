package com.ctrl.android.property.staff.ui.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarFragment;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.DeviceDao;
import com.ctrl.android.property.staff.entity.DeviceRecord;
import com.ctrl.android.property.staff.ui.adapter.DeviceHistoryAdapter;
import com.ctrl.android.property.staff.util.D;
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
 * 设备养护 历史记录 fragment
 * Created by Eric on 2015/9/29.
 */
public class DeviceHistoryFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private DeviceHistoryAdapter mAdapter;
    private String type;//类型

    private DeviceDao deviceDao;
    private String equipmentId;

    private List<Map<String,String>> listMap;

    private List<DeviceRecord> listDeviceRecord;

    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;

    public static DeviceHistoryFragment newInstance(String type){
        DeviceHistoryFragment fragment = new DeviceHistoryFragment();
        fragment.type = type;
        return fragment;
    }

    public static DeviceHistoryFragment newInstance(String equipmentId,String type){
        DeviceHistoryFragment fragment = new DeviceHistoryFragment();
        fragment.type = type;
        fragment.equipmentId = equipmentId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_history_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        deviceDao = new DeviceDao(this);
        //String equipmentId,

        int year = Integer.parseInt(D.getCurrentDateStr("yyyy"));
        int month = Integer.parseInt(D.getCurrentDateStr("MM"));
        int day = Integer.parseInt(getDaysNum(year, month));

        String startTime = "";
        String endTime = "";

        if(type.equals(Constant.DEVICE_NOW_MONTH)){
            startTime = year + "-" + month + "-01 00:00:00";
            if((month + 1) > 12){
                endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
            } else {
                endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
            }

        } else {

            if((month - 1) < 1){
                startTime = (year - 1) + "-" + 12 + "-01" + " 00:00:00";
            } else {
                startTime = year + "-" + (month-1) + "-01" + " 00:00:00";
            }

            endTime = year + "-" + month + "-01 00:00:00";
        }

        Log.d("demo","startTime : " + startTime);
        Log.d("demo","endTime : " + endTime);
        showProgress(true);
        deviceDao.requestDeviceRecordList(equipmentId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        mPullToRefreshListView.onRefreshComplete();
        showProgress(false);

        if(3 == requestCode){

            listDeviceRecord = deviceDao.getListDeviceRecord();

            mListView = mPullToRefreshListView.getRefreshableView();
            mAdapter = new DeviceHistoryAdapter(getActivity());
            mAdapter.setList(listDeviceRecord);
            mListView.setAdapter(mAdapter);

            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = ((getActivity().getWindowManager().getDefaultDisplay().getHeight())/3);
            mListView.setLayoutParams(params);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    deviceDao.getListDeviceRecord().clear();
                    int year = Integer.parseInt(D.getCurrentDateStr("yyyy"));
                    int month = Integer.parseInt(D.getCurrentDateStr("MM"));
                    int day = Integer.parseInt(getDaysNum(year, month));

                    String startTime = "";
                    String endTime = "";

                    if(type.equals(Constant.DEVICE_NOW_MONTH)){
                        startTime = year + "-" + month + "-01 00:00:00";
                        if((month + 1) > 12){
                            endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
                        } else {
                            endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
                        }

                    } else {

                        if((month - 1) < 1){
                            startTime = (year - 1) + "-" + 12 + "-01" + " 00:00:00";
                        } else {
                            startTime = year + "-" + (month-1) + "-01" + " 00:00:00";
                        }

                        endTime = year + "-" + month + "-01 00:00:00";
                    }

                    Log.d("demo","startTime : " + startTime);
                    Log.d("demo","endTime : " + endTime);
                    showProgress(true);
                    deviceDao.requestDeviceRecordList(equipmentId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    int year = Integer.parseInt(D.getCurrentDateStr("yyyy"));
                    int month = Integer.parseInt(D.getCurrentDateStr("MM"));
                    int day = Integer.parseInt(getDaysNum(year, month));

                    String startTime = "";
                    String endTime = "";

                    if(type.equals(Constant.DEVICE_NOW_MONTH)){
                        startTime = year + "-" + month + "-01 00:00:00";
                        if((month + 1) > 12){
                            endTime = (year + 1) + "-" + 1 + "-01" + " 00:00:00";
                        } else {
                            endTime = year + "-" + (month+1) + "-01" + " 00:00:00";
                        }

                    } else {

                        if((month - 1) < 1){
                            startTime = (year - 1) + "-" + 12 + "-01" + " 00:00:00";
                        } else {
                            startTime = year + "-" + (month-1) + "-01" + " 00:00:00";
                        }

                        endTime = year + "-" + month + "-01 00:00:00";
                    }

                    Log.d("demo","startTime : " + startTime);
                    Log.d("demo","endTime : " + endTime);
                    showProgress(true);
                    deviceDao.requestDeviceRecordList(equipmentId, startTime, endTime, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DeviceRecordDetailActivity.class);
                    intent.putExtra("id",equipmentId);
                    intent.putExtra("recordId",listDeviceRecord.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(getActivity());
                }
            });

        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        //super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();
        if(errorNo.equals("002")){

        } else {
            MessageUtils.showShortToast(getActivity(),errorMessage);
        }
    }

    @Override
    public void onClick(View v) {

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
    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name", str + "条目" + i);
            map.put("time", "10月" + i + "日");
            map.put("amount","20" + i + ".5" + i + "9");
            list.add(map);
        }
        return list;
    }
}

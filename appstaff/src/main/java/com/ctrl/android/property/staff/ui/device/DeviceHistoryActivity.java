package com.ctrl.android.property.staff.ui.device;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.DeviceDao;
import com.ctrl.android.property.staff.entity.DeviceDetail;
import com.ctrl.android.property.staff.ui.adapter.ViewPagerAdapter;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 设备养护 历史记录 activity
 * Created by Eric on 2015/10/22
 */
public class DeviceHistoryActivity extends AppToolBarActivity implements View.OnClickListener{

    //@InjectView(R.id.scroll_view)//滚动view
    //ScrollView scroll_view;

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

    @InjectView(R.id.device_add_record)
    TextView device_add_record;

    @InjectView(R.id.radio_group)//表头
    RadioGroup radio_group;
    @InjectView(R.id.radio_month_now)//本月
    RadioButton radio_month_now;
    @InjectView(R.id.radio_month_last)//上月
    RadioButton radio_month_last;
    @InjectView(R.id.radio_month_more)//更多
    RadioButton radio_month_more;

    @InjectView(R.id.viewpager)//列表内容
    ViewPager viewpager;

    private String deviceId;
    private DeviceDetail deviceDetail;

    SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private DeviceDao deviceDao;
    private String TITLE = "设备养护";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.device_history_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        device_add_record.setVisibility(View.GONE);
        deviceId = getIntent().getStringExtra("id");

        device_add_record.setOnClickListener(this);

        deviceDao = new DeviceDao(this);
        showProgress(true);
        deviceDao.requestDeviceDetail(deviceId);

        //house_pay_history_btn.setOnClickListener(this);
        //house_pay_btn.setOnClickListener(this);

        fragments.put(0, DeviceHistoryFragment.newInstance(deviceId,Constant.DEVICE_NOW_MONTH));
        fragments.put(1, DeviceHistoryFragment.newInstance(deviceId,Constant.DEVICE_LAST_MONTH));
        //fragments.put(2, DeviceHistoryFragment.newInstance(Constant.DEVICE_MORE_MONTH));

        /**设置默认选择为全部*/
        radio_month_now.setChecked(true);

        //radio_month_now.setText("三月");
        //radio_month_last.setText("二月");
        //radio_month_more.setText("一月");

        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.setOnPageChangeListener(new FragmentOnPageChangeListener());
        //指定加载页数(可避免页面的重复加载)
        viewpager.setOffscreenPageLimit(6);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_month_now:
                        viewpager.setCurrentItem(0);
                        //scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
                        break;
                    case R.id.radio_month_last:
                        viewpager.setCurrentItem(1);
                        //scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
                        break;
                    case R.id.radio_month_more:
                        //viewpager.setCurrentItem(2);
                        Intent intent = new Intent(DeviceHistoryActivity.this, DeviceSearchActivity.class);
                        intent.putExtra("id",deviceId);
                        startActivity(intent);
                        AnimUtil.intentSlidIn(DeviceHistoryActivity.this);
                        viewpager.setCurrentItem(0);
                        radio_month_now.setChecked(true);
                        radio_month_more.setChecked(false);
                        break;
                }
            }
        });

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

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

            if(S.isNull(deviceDetail.getStaffId())){
                device_add_record.setVisibility(View.GONE);
            } else if((deviceDetail.getStaffId()).equals(AppHolder.getInstance().getStaffInfo().getStaffId())){
                device_add_record.setVisibility(View.VISIBLE);
            } else {
                device_add_record.setVisibility(View.GONE);
            }

            tel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!S.isNull(deviceDetail.getFactoryTelephone())) {
                        AndroidUtil.dial(DeviceHistoryActivity.this, deviceDetail.getFactoryTelephone());
                    } else {
                        MessageUtils.showShortToast(DeviceHistoryActivity.this, "未获得电话号码");
                    }
                }
            });

        }

    }

    public class FragmentOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch(position){
                case 0:
                    radio_group.check(R.id.radio_month_now);
                    //scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
                    break;
                case 1:
                    radio_group.check(R.id.radio_month_last);
                    //scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
                    break;
//                case 2:
//                    radio_group.check(R.id.radio_month_more);
//                    Intent intent = new Intent(DeviceHistoryActivity.this, DeviceSearchActivity.class);
//                    startActivity(intent);
//                    AnimUtil.intentSlidIn(DeviceHistoryActivity.this);
//                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public void onClick(View v) {
        if(v == device_add_record){

            if(S.isNull(deviceDetail.getStaffId())){
                //
            } else if((deviceDetail.getStaffId()).equals(AppHolder.getInstance().getStaffInfo().getStaffId())){
                Intent intent = new Intent(DeviceHistoryActivity.this, DeviceAddRecordActivity.class);
                intent.putExtra("id",deviceId);
                startActivity(intent);
                AnimUtil.intentSlidIn(DeviceHistoryActivity.this);
            } else {
                //
            }


        }
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
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("owner", i + "业主名称");
            map.put("name", "中润世纪广场");
            map.put("time", "2015-10-22");
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }



}

package com.ctrl.android.property.staff.ui.device;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.DeviceDao;
import com.ctrl.android.property.staff.entity.DeviceDetail;
import com.ctrl.android.property.staff.entity.DeviceRecordDetail;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.ui.widget.ImageZoomActivity;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 设备养护记录 activity
 * Created by Eric on 2015/10/22
 */
public class DeviceRecordDetailActivity extends AppToolBarActivity implements View.OnClickListener{

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
    @InjectView(R.id.device_tel)//电话
    TextView device_tel;
    @InjectView(R.id.tel_btn)//电话按钮
    ImageView tel_btn;

    @InjectView(R.id.device_record_name)//养护人
    TextView device_record_name;
    @InjectView(R.id.device_record_time)//维护厂家
    TextView device_record_time;
    @InjectView(R.id.device_record_status)//设备状态
    TextView device_record_status;
    @InjectView(R.id.device_record_content)//养护内容
    TextView device_record_content;

    @InjectView(R.id.img_01)
    ImageView img_01;
    @InjectView(R.id.img_02)
    ImageView img_02;
    @InjectView(R.id.img_03)
    ImageView img_03;

    private String deviceId;
    private String recordId;
    private DeviceDetail deviceDetail;

    private DeviceDao deviceDao;
    private String TITLE = "设备养护";

    private DeviceRecordDetail deviceRecordDetail;

    private List<Img> listImg;

    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.device_record_detail_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        deviceId = getIntent().getStringExtra("id");
        recordId = getIntent().getStringExtra("recordId");

        deviceDao = new DeviceDao(this);
        showProgress(true);
        deviceDao.requestDeviceDetail(deviceId);

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

            tel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!S.isNull(deviceDetail.getFactoryTelephone())) {
                        AndroidUtil.dial(DeviceRecordDetailActivity.this, deviceDetail.getFactoryTelephone());
                    } else {
                        MessageUtils.showShortToast(DeviceRecordDetailActivity.this, "未获得电话号码");
                    }
                }
            });

            showProgress(true);
            deviceDao.requestDeviceRecordDetail(recordId);


        }

        if(5 == requestCode){
            deviceRecordDetail = deviceDao.getDeviceRecordDetail();
            listImg = deviceDao.getListImg();

            device_record_name.setText(S.getStr(deviceRecordDetail.getName()));
            device_record_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm",deviceRecordDetail.getMaintainTime()));
            device_record_status.setText(S.getStr(deviceRecordDetail.getKindName()));
            device_record_content.setText(S.getStr(deviceRecordDetail.getContent()));

            if(listImg != null && listImg.size() > 0){

                Log.d("demo","size : " + listImg.size());

                if(listImg.size() == 1){
                    Arad.imageLoader.load((listImg.get(0).getOriginalImg()) == null || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
//                    Arad.imageLoader.load(listImg.get(0).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_01);
                    Log.d("demo", "pic1 : " + listImg.get(0).getOriginalImg());
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.GONE);
                    img_03.setVisibility(View.GONE);
                }
                if(listImg.size() == 2){
                    Arad.imageLoader.load((listImg.get(0).getOriginalImg() == null) || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
                    Arad.imageLoader.load((listImg.get(1).getOriginalImg() == null) || (listImg.get(1).getOriginalImg()).equals("") ? "aa" : listImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_02);
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.VISIBLE);
                    img_03.setVisibility(View.GONE);
                }

                if(listImg.size() >= 3){
                    Arad.imageLoader.load((listImg.get(0).getOriginalImg() == null) || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
                    Arad.imageLoader.load((listImg.get(1).getOriginalImg() == null) || (listImg.get(1).getOriginalImg()).equals("") ? "aa" : listImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_02);
                    Arad.imageLoader.load((listImg.get(2).getOriginalImg() == null) || (listImg.get(2).getOriginalImg()).equals("") ? "aa" : listImg.get(2).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_03);
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.VISIBLE);
                    img_03.setVisibility(View.VISIBLE);
                }
            }

        }


    }



    @Override
    public void onClick(View v) {
       if(v == img_01){
           imagelist = new ArrayList<String>();
           for(int i = 0 ; i < listImg.size() ; i ++){
               imagelist.add(listImg.get(i).getOriginalImg());
           }
           position = 0;
           Intent intent = new Intent(DeviceRecordDetailActivity.this, ImageZoomActivity.class);
           Bundle bundle = new Bundle();
           bundle.putSerializable("imageList", imagelist);
           bundle.putInt("position", position);
           intent.putExtras(bundle);
           startActivity(intent);
       }

        if(v == img_02){
            imagelist = new ArrayList<String>();
            for(int i = 0 ; i < listImg.size() ; i ++){
                imagelist.add(listImg.get(i).getOriginalImg());
            }
            position = 1;
            Intent intent = new Intent(DeviceRecordDetailActivity.this, ImageZoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageList", imagelist);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(v == img_03){
            imagelist = new ArrayList<String>();
            for(int i = 0 ; i < listImg.size() ; i ++){
                imagelist.add(listImg.get(i).getOriginalImg());
            }
            position = 2;
            Intent intent = new Intent(DeviceRecordDetailActivity.this, ImageZoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageList", imagelist);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
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

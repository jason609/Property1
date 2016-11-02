package com.ctrl.android.property.staff.ui.complaint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ComplaintDao;
import com.ctrl.android.property.staff.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.staff.util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 投诉 （处理后）activity
* */

public class ComplaintAftertreatmentActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_my_repairs_aftertreament_progress)
    TextView tv_my_repairs_aftertreament_progress;
    @InjectView(R.id.tv_my_repairs_aftertreament_time)
    TextView tv_my_repairs_aftertreament_time;
    @InjectView(R.id.tv_my_repairs_aftertreament_type)
    TextView tv_my_repairs_aftertreament_type;
    @InjectView(R.id.tv_my_repairs_aftertreament_content)
    TextView tv_my_repairs_aftertreament_content;
    @InjectView(R.id.tv_my_repairs_aftertreament_wuye)
    TextView tv_my_repairs_aftertreament_wuye;
    @InjectView(R.id.tv_my_repairs_aftertreament_result)
    TextView tv_my_repairs_aftertreament_result;
    @InjectView(R.id.tv_my_repairs_aftertreament_pingjia)
    TextView tv_my_repairs_aftertreament_pingjia;
    @InjectView(R.id.tv_my_repairs_aftertreament_pingjia_content)
    TextView tv_my_repairs_aftertreament_pingjia_content;
    @InjectView(R.id.iv01_my_repairs_aftertreament)
    ImageView iv01_my_repairs_aftertreament;
    @InjectView(R.id.iv02_my_repairs_aftertreament)
    ImageView iv02_my_repairs_aftertreament;
    @InjectView(R.id.iv03_my_repairs_aftertreament)
    ImageView iv03_my_repairs_aftertreament;
    @InjectView(R.id.iv04_my_repairs_aftertreament)
    ImageView iv04_my_repairs_aftertreament;
    @InjectView(R.id.iv05_my_repairs_aftertreament)
    ImageView iv05_my_repairs_aftertreament;
    @InjectView(R.id.iv06_my_repairs_aftertreament)
    ImageView iv06_my_repairs_aftertreament;
    @InjectView(R.id.tv_baoxiu_image)
    TextView tv_baoxiu_image;
    @InjectView(R.id.tv_wuye_image)
    TextView tv_wuye_image;

    @InjectView(R.id.tv_repairs_room)//报修房间
    TextView tv_repairs_room;
    private ComplaintDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_complaint_aftertreatment);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv01_my_repairs_aftertreament.setOnClickListener(this);
        iv02_my_repairs_aftertreament.setOnClickListener(this);
        iv03_my_repairs_aftertreament.setOnClickListener(this);
        iv04_my_repairs_aftertreament.setOnClickListener(this);
        iv05_my_repairs_aftertreament.setOnClickListener(this);
        iv06_my_repairs_aftertreament.setOnClickListener(this);
        dao=new ComplaintDao(this);
        dao.requestComplait(getIntent().getStringExtra("repairDemandId"));
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            tv_my_repairs_aftertreament_progress.setText("投诉进度：已结束");
            tv_my_repairs_aftertreament_wuye.setText("物业处理：已结束");
            tv_my_repairs_aftertreament_time.setText("投诉时间："+ TimeUtil.date(Long.parseLong(dao.getComplaint().getCreateTime())));
            tv_repairs_room.setText("投诉房间：" + dao.getComplaint().getCommunityName() + " " + dao.getComplaint().getBuilding() + "-" + dao.getComplaint().getUnit() + "-" + dao.getComplaint().getRoom());
            tv_my_repairs_aftertreament_type.setText("投诉类型："+dao.getComplaint().getComplaintKindName());
            tv_my_repairs_aftertreament_content.setText(dao.getComplaint().getContent());
            tv_my_repairs_aftertreament_result.setText(dao.getComplaint().getResult());
            if(dao.getComplaint().getEvaluateLevel().equals("0")){tv_my_repairs_aftertreament_pingjia.setText("非常满意");}
            if(dao.getComplaint().getEvaluateLevel().equals("1")){tv_my_repairs_aftertreament_pingjia.setText("基本满意");}
            if(dao.getComplaint().getEvaluateLevel().equals("2")){tv_my_repairs_aftertreament_pingjia.setText("不满意");}
            tv_my_repairs_aftertreament_pingjia_content.setText(dao.getComplaint().getEvaluateContent());
            if(dao.getGoodPicList().size()<1){
                tv_baoxiu_image.setVisibility(View.GONE);
                iv01_my_repairs_aftertreament.setVisibility(View.GONE);
                iv02_my_repairs_aftertreament.setVisibility(View.GONE);
                iv03_my_repairs_aftertreament.setVisibility(View.GONE);
            }
            if(dao.getGoodPicResultList().size()<1){
                tv_wuye_image.setVisibility(View.GONE);
                iv04_my_repairs_aftertreament.setVisibility(View.GONE);
                iv05_my_repairs_aftertreament.setVisibility(View.GONE);
                iv06_my_repairs_aftertreament.setVisibility(View.GONE);
            }
            if(dao.getGoodPicList()!=null){
                if(dao.getGoodPicList().size()>=1){
                    Arad.imageLoader.load(dao.getGoodPicList().get(0).getOriginalImg()==null || dao.getGoodPicList().get(0).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicList().get(0).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv01_my_repairs_aftertreament);
                }
                if(dao.getGoodPicList().size()>=2){
                    Arad.imageLoader.load(dao.getGoodPicList().get(1).getOriginalImg()==null || dao.getGoodPicList().get(1).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicList().get(1).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv02_my_repairs_aftertreament);
                }
                if(dao.getGoodPicList().size()>=3){
                    Arad.imageLoader.load(dao.getGoodPicList().get(2).getOriginalImg()==null || dao.getGoodPicList().get(2).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicList().get(2).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv03_my_repairs_aftertreament);
                }
            }

            if(dao.getGoodPicResultList()!=null){
                if(dao.getGoodPicResultList().size()>=1){
                    Arad.imageLoader.load(dao.getGoodPicResultList().get(0).getOriginalImg()==null || dao.getGoodPicResultList().get(0).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicResultList().get(0).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv04_my_repairs_aftertreament);
                }
                if(dao.getGoodPicResultList().size()>=2){
                    Arad.imageLoader.load(dao.getGoodPicResultList().get(1).getOriginalImg()==null || dao.getGoodPicResultList().get(1).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicResultList().get(1).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv05_my_repairs_aftertreament);
                }
                if(dao.getGoodPicResultList().size()>=3){
                    Arad.imageLoader.load(dao.getGoodPicResultList().get(2).getOriginalImg()==null || dao.getGoodPicResultList().get(2).getOriginalImg().equals("")?"aa":
                            dao.getGoodPicResultList().get(2).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv06_my_repairs_aftertreament);
                }


        }

        }
    }

    @Override
    public void onClick(View v) {
        if(v==iv01_my_repairs_aftertreament&&dao.getGoodPicList().size()>=1){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv01_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_my_repairs_aftertreament&&dao.getGoodPicList().size()>=2){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv02_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_my_repairs_aftertreament&&dao.getGoodPicList().size()>=3){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv03_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv04_my_repairs_aftertreament&&dao.getGoodPicResultList().size()>=1){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicResultList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv04_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv04_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv04_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv05_my_repairs_aftertreament&&dao.getGoodPicResultList().size()>=2){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicResultList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv05_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv05_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv05_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv06_my_repairs_aftertreament&&dao.getGoodPicResultList().size()>=3){
            Intent intent=new Intent(ComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicResultList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv06_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv06_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv06_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
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
        return "投诉详情";
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        if(Arad.preferences.getString("grade").equals("2")) {
            mRightText.setText("完成");
            mRightText.setTextColor(Color.WHITE);
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*Intent intent=new Intent(MyRepairsAftertreatmentActivity.this,MyRepairsAddActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidOut(MyRepairsAftertreatmentActivity.this);*/
                    finish();

                }
            });
        }else {
            mRightText.setText("");
        }
        return true;
    }
}

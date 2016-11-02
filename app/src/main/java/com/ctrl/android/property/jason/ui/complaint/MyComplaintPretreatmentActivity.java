package com.ctrl.android.property.jason.ui.complaint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.ComplaintDao;
import com.ctrl.android.property.jason.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.jason.util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 投诉详情 （已处理）activity
* */

public class MyComplaintPretreatmentActivity extends AppToolBarActivity implements View.OnClickListener {
@InjectView(R.id.tv_my_complaint_pretreament_progress)
TextView tv_my_complaint_pretreament_progress;
   @InjectView(R.id.tv_my_complaint_pretreament_time)
   TextView tv_my_complaint_pretreament_time;
   @InjectView(R.id.tv_my_complaint_pretreament_type)
   TextView tv_my_complaint_pretreament_type;
   @InjectView(R.id.tv_my_complaint_pretreament_content)
   TextView tv_my_complaint_pretreament_content;
   @InjectView(R.id.tv_my_complaint_pretreament_progress_wuye)
   TextView tv_my_complaint_pretreament_progress_wuye;
   @InjectView(R.id.tv_my_complaint_pretreament_progress_result)
   TextView tv_my_complaint_pretreament_progress_result;
   @InjectView(R.id.et_my_complaint_pretreament_content)
    EditText et_my_complaint_pretreament_content;
   @InjectView(R.id.iv01_my_complaint_pretreament)
   ImageView iv01_my_complaint_pretreament;
   @InjectView(R.id.iv02_my_complaint_pretreament)
   ImageView iv02_my_complaint_pretreament;
   @InjectView(R.id.iv03_my_complaint_pretreament)
   ImageView iv03_my_complaint_pretreament;
   @InjectView(R.id.iv04_my_complaint_pretreament)
   ImageView iv04_my_complaint_pretreament;
   @InjectView(R.id.iv05_my_complaint_pretreament)
   ImageView iv05_my_complaint_pretreament;
   @InjectView(R.id.iv06_my_complaint_pretreament)
   ImageView iv06_my_complaint_pretreament;
   @InjectView(R.id.tv_tousu_image)
   TextView tv_tousu_image;
   @InjectView(R.id.tv_wuye_image)
   TextView tv_wuye_image;
   @InjectView(R.id.rb_complaint_01)
    RadioButton rb_complaint_01;
   @InjectView(R.id.rb_complaint_02)
    RadioButton rb_complaint_02;
   @InjectView(R.id.rb_complaint_03)
    RadioButton rb_complaint_03;
   @InjectView(R.id.rg_complaint)
    RadioGroup rg_complaint;
    @InjectView(R.id.ll_my_complaint_wuye)
    LinearLayout ll_my_complaint_wuye;
    @InjectView(R.id.ll_my_complaint_pinjia)
    LinearLayout ll_my_complaint_pinjia;
    @InjectView(R.id.tv_my_complaint_pretreament_room)
    TextView tv_my_complaint_pretreament_room;



    private ComplaintDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint_pretreatment);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv01_my_complaint_pretreament.setOnClickListener(this);
        iv02_my_complaint_pretreament.setOnClickListener(this);
        iv03_my_complaint_pretreament.setOnClickListener(this);
        iv04_my_complaint_pretreament.setOnClickListener(this);
        iv05_my_complaint_pretreament.setOnClickListener(this);
        iv06_my_complaint_pretreament.setOnClickListener(this);
        dao=new ComplaintDao(this);
        dao.requestComplaint(getIntent().getStringExtra("complaintId"));
        rg_complaint.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId==rb_complaint_01.getId()){
                            rb_complaint_01.setTextColor(Color.WHITE);
                            rb_complaint_01.setText("非常满意");
                        }else {
                            rb_complaint_01.setTextColor(Color.GRAY);
                            rb_complaint_01.setText("非常满意");

                        }
                        if(checkedId==rb_complaint_02.getId()){
                            rb_complaint_02.setTextColor(Color.WHITE);
                            rb_complaint_02.setText("基本满意");
                        }else {
                            rb_complaint_02.setTextColor(Color.GRAY);
                            rb_complaint_02.setText("基本满意");

                        }
                        if(checkedId==rb_complaint_03.getId()){
                            rb_complaint_03.setTextColor(Color.WHITE);
                            rb_complaint_03.setText("不满意");
                        }else {
                            rb_complaint_03.setTextColor(Color.GRAY);
                            rb_complaint_03.setText("不满意");



                        }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==iv01_my_complaint_pretreament&&dao.getComplaintPicList().size()>=1){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv01_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_my_complaint_pretreament&&dao.getComplaintPicList().size()>=2){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv02_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_my_complaint_pretreament&&dao.getComplaintPicList().size()>=3){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv03_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv04_my_complaint_pretreament&&dao.getComplaintResultPicList().size()>=1){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(0).getZipImg());
            int[] location = new int[2];
            iv04_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv04_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv04_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv05_my_complaint_pretreament&&dao.getComplaintResultPicList().size()>=2){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(1).getZipImg());
            int[] location = new int[2];
            iv05_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv05_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv05_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv06_my_complaint_pretreament&&dao.getComplaintResultPicList().size()>=3){
            Intent intent=new Intent(MyComplaintPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(2).getZipImg());
            int[] location = new int[2];
            iv06_my_complaint_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv06_my_complaint_pretreament.getWidth());
            intent.putExtra("height", iv06_my_complaint_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }


    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            if(getIntent().getStringExtra("progressState").equals("0")){
                ll_my_complaint_pinjia.setVisibility(View.GONE);
                ll_my_complaint_wuye.setVisibility(View.GONE);
                tv_my_complaint_pretreament_progress.setText("投诉进度：待处理");
                tv_my_complaint_pretreament_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(dao.getComplaint().getCreateTime())));
                tv_my_complaint_pretreament_type.setText("投诉类型："+dao.getComplaint().getComplaintKindName());
                tv_my_complaint_pretreament_room.setText("投诉房间："+dao.getComplaint().getCommunityName()+" " +dao.getComplaint().getBuilding()+"-"+dao.getComplaint().getUnit()+"-"+dao.getComplaint().getRoom());
                tv_my_complaint_pretreament_content.setText(dao.getComplaint().getContent());
                if(dao.getComplaintPicList().size()<1){
                    tv_tousu_image.setVisibility(View.GONE);
                    iv01_my_complaint_pretreament.setVisibility(View.GONE);
                    iv02_my_complaint_pretreament.setVisibility(View.GONE);
                    iv03_my_complaint_pretreament.setVisibility(View.GONE);

                }

                if(dao.getComplaintResultPicList().size()<1){
                    tv_wuye_image.setVisibility(View.GONE);
                    iv04_my_complaint_pretreament.setVisibility(View.GONE);
                    iv05_my_complaint_pretreament.setVisibility(View.GONE);
                    iv06_my_complaint_pretreament.setVisibility(View.GONE);

                }
                if (dao.getComplaintPicList().size() >= 1) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(0).getOriginalImg() == null || dao.getComplaintPicList().get(0).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_complaint_pretreament);
                }
                if (dao.getComplaintPicList().size() >= 2) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(1).getOriginalImg() == null || dao.getComplaintPicList().get(1).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_complaint_pretreament);
                }
                if (dao.getComplaintPicList().size() >= 3) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(2).getOriginalImg() == null || dao.getComplaintPicList().get(2).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_complaint_pretreament);
                }
            }

            if(getIntent().getStringExtra("progressState").equals("1")){
                ll_my_complaint_pinjia.setVisibility(View.GONE);
                ll_my_complaint_wuye.setVisibility(View.GONE);
                tv_my_complaint_pretreament_progress.setText("投诉进度：处理中");
                tv_my_complaint_pretreament_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(dao.getComplaint().getCreateTime())));
                tv_my_complaint_pretreament_type.setText("投诉类型："+dao.getComplaint().getComplaintKindName());
                tv_my_complaint_pretreament_room.setText("投诉房间："+dao.getComplaint().getCommunityName()+" " +dao.getComplaint().getBuilding()+"-"+dao.getComplaint().getUnit()+"-"+dao.getComplaint().getRoom());
                tv_my_complaint_pretreament_content.setText(dao.getComplaint().getContent());
                if(dao.getComplaintPicList().size()<1){
                    tv_tousu_image.setVisibility(View.GONE);
                    iv01_my_complaint_pretreament.setVisibility(View.GONE);
                    iv02_my_complaint_pretreament.setVisibility(View.GONE);
                    iv03_my_complaint_pretreament.setVisibility(View.GONE);

                }

                if(dao.getComplaintResultPicList().size()<1){
                    tv_wuye_image.setVisibility(View.GONE);
                    iv04_my_complaint_pretreament.setVisibility(View.GONE);
                    iv05_my_complaint_pretreament.setVisibility(View.GONE);
                    iv06_my_complaint_pretreament.setVisibility(View.GONE);

                }
                if (dao.getComplaintPicList().size() >= 1) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(0).getOriginalImg() == null || dao.getComplaintPicList().get(0).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_complaint_pretreament);
                }
                if (dao.getComplaintPicList().size() >= 2) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(1).getOriginalImg() == null || dao.getComplaintPicList().get(1).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_complaint_pretreament);
                }
                if (dao.getComplaintPicList().size() >= 3) {
                    Arad.imageLoader.load(dao.getComplaintPicList().get(2).getOriginalImg() == null || dao.getComplaintPicList().get(2).getOriginalImg().equals("") ? "aa" :
                            dao.getComplaintPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_complaint_pretreament);
                }
            }
            if(getIntent().getStringExtra("progressState").equals("2")){
            tv_my_complaint_pretreament_progress.setText("投诉进度：已处理");
            tv_my_complaint_pretreament_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(dao.getComplaint().getCreateTime())));
                tv_my_complaint_pretreament_room.setText("投诉房间："+dao.getComplaint().getCommunityName()+" " +dao.getComplaint().getBuilding()+"-"+dao.getComplaint().getUnit()+"-"+dao.getComplaint().getRoom());
                tv_my_complaint_pretreament_type.setText("投诉类型："+dao.getComplaint().getComplaintKindName());
            tv_my_complaint_pretreament_content.setText(dao.getComplaint().getContent());
                if(dao.getComplaintPicList().size()<1){
                    tv_tousu_image.setVisibility(View.GONE);
                    iv01_my_complaint_pretreament.setVisibility(View.GONE);
                    iv02_my_complaint_pretreament.setVisibility(View.GONE);
                    iv03_my_complaint_pretreament.setVisibility(View.GONE);
                }
                if(dao.getComplaintResultPicList().size()<1){
                    tv_wuye_image.setVisibility(View.GONE);
                    iv04_my_complaint_pretreament.setVisibility(View.GONE);
                    iv05_my_complaint_pretreament.setVisibility(View.GONE);
                    iv06_my_complaint_pretreament.setVisibility(View.GONE);
                }
                if(dao.getComplaintPicList().size()>=1){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(0).getOriginalImg()==null || dao.getComplaintPicList().get(0).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_complaint_pretreament);

                }
                if(dao.getComplaintPicList().size()>=2){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(1).getOriginalImg()==null || dao.getComplaintPicList().get(1).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_complaint_pretreament);
                }
                if(dao.getComplaintPicList().size()>=3){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(2).getOriginalImg()==null || dao.getComplaintPicList().get(2).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_complaint_pretreament);
                }
            tv_my_complaint_pretreament_progress_wuye.setText("物业处理：已处理");
            tv_my_complaint_pretreament_progress_result.setText(dao.getComplaint().getResult());
                if(dao.getComplaintResultPicList().size()>=1){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(0).getOriginalImg()==null || dao.getComplaintResultPicList().get(0).getOriginalImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv04_my_complaint_pretreament);
                }
                if(dao.getComplaintResultPicList().size()>=2){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(1).getOriginalImg()==null || dao.getComplaintResultPicList().get(1).getOriginalImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv05_my_complaint_pretreament);
                }
                if(dao.getComplaintResultPicList().size()>=3){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(2).getOriginalImg()==null || dao.getComplaintResultPicList().get(2).getOriginalImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv06_my_complaint_pretreament);
                }


            }
        }

      if(requestCode==3){
          MessageUtils.showShortToast(this, "评价成功");
          Intent intent=new Intent();
          setResult(2501,intent);
          finish();
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
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("progressState").equals("0")) {
                    finish();
                }
                if (getIntent().getStringExtra("progressState").equals("1")) {
                    finish();
                }
                if (getIntent().getStringExtra("progressState").equals("2")) {
                    if (rb_complaint_01.isChecked()) {
                        rb_complaint_01.setText("非常满意");
                        dao.getComplaint().setEvaluateLevel("0");
                    }
                    if (rb_complaint_02.isChecked()) {
                        dao.getComplaint().setEvaluateLevel("1");

                    }
                    if (rb_complaint_03.isChecked()) {
                        dao.getComplaint().setEvaluateLevel("2");
                    }

                    if (TextUtils.isEmpty(et_my_complaint_pretreament_content.getText().toString())) {
                        MessageUtils.showShortToast(MyComplaintPretreatmentActivity.this, "评价内容为空");
                    } else {
                        dao.requestComplaintEvaluate(getIntent().getStringExtra("complaintId"), dao.getComplaint().getEvaluateLevel(), et_my_complaint_pretreament_content.getText().toString());
                    }

                }



/*
                Intent intent = new Intent(MyComplaintPretreatmentActivity.this, MyComplaintAftertreatmentActivity.class);
                intent.putExtra("complaintId",getIntent().getStringExtra("complaintId"));
                startActivity(intent);
                AnimUtil.intentSlidIn(MyComplaintPretreatmentActivity.this);*/

            }
        });
        return true;
    }
}

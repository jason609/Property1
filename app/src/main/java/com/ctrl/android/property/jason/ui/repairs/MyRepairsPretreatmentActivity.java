package com.ctrl.android.property.jason.ui.repairs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.ctrl.android.property.jason.dao.RepairDao;
import com.ctrl.android.property.jason.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.jason.util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*我的报修（处理前）activity*/

public class MyRepairsPretreatmentActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_my_repairs_pretreament_progress)
    TextView tv_my_repairs_pretreament_progress;
    @InjectView(R.id.tv_my_repairs_pretreament_time)
    TextView tv_my_repairs_pretreament_time;
    @InjectView(R.id.tv_my_repairs_pretreament_type)
    TextView tv_my_repairs_pretreament_type;
    @InjectView(R.id.tv_my_repairs_pretreament_content)
    TextView tv_my_repairs_pretreament_content;
    @InjectView(R.id.tv_my_repairs_pretreament_wuye)
    TextView tv_my_repairs_pretreament_wuye;
    @InjectView(R.id.tv_my_repairs_pretreament_result)
    TextView tv_my_repairs_pretreament_result;
    @InjectView(R.id.iv01_my_repairs_pretreament)
    ImageView iv01_my_repairs_pretreament;
    @InjectView(R.id.iv02_my_repairs_pretreament)
    ImageView iv02_my_repairs_pretreament;
    @InjectView(R.id.iv03_my_repairs_pretreament)
    ImageView iv03_my_repairs_pretreament;
    @InjectView(R.id.iv04_my_repairs_pretreament)
    ImageView iv04_my_repairs_pretreament;
    @InjectView(R.id.iv05_my_repairs_pretreament)
    ImageView iv05_my_repairs_pretreament;
    @InjectView(R.id.iv06_my_repairs_pretreament)
    ImageView iv06_my_repairs_pretreament;
    @InjectView(R.id.ll_my_repairs_wuye)
    LinearLayout ll_my_repairs_wuye;
    @InjectView(R.id.ll_my_repairs_pingjia)
    LinearLayout ll_my_repairs_pingjia;
    @InjectView(R.id.tv_baoxiu_image)
    TextView tv_baoxiu_image;
    @InjectView(R.id.tv_wuye_image)
    TextView tv_wuye_image;
    @InjectView(R.id.tv_my_repairs_pretreament_room)//报修房间
    TextView tv_my_repairs_pretreament_room;

    @InjectView(R.id.rb_repairs_01)
    RadioButton rb_repairs_01;
    @InjectView(R.id.rb_repairs_02)
    RadioButton rb_repairs_02;
    @InjectView(R.id.rb_repairs_03)
    RadioButton rb_repairs_03;
    @InjectView(R.id.rg_repairs)
    RadioGroup rg_repairs;

    @InjectView(R.id.et_my_repairs_pretreament_pingjia)
    EditText et_my_repairs_pretreament_pingjia;
    private RepairDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs_pretreatment);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv01_my_repairs_pretreament.setOnClickListener(this);
        iv02_my_repairs_pretreament.setOnClickListener(this);
        iv03_my_repairs_pretreament.setOnClickListener(this);
        iv04_my_repairs_pretreament.setOnClickListener(this);
        iv05_my_repairs_pretreament.setOnClickListener(this);
        iv06_my_repairs_pretreament.setOnClickListener(this);
        dao=new RepairDao(this);
        dao.requestRepair(getIntent().getStringExtra("repairDemandId"));
        rg_repairs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==rb_repairs_01.getId()){
                    rb_repairs_01.setTextColor(Color.WHITE);
                    rb_repairs_01.setText("非常满意");
                }else {
                    rb_repairs_01.setTextColor(Color.GRAY);
                    rb_repairs_01.setText("非常满意");

                }
                if(checkedId==rb_repairs_02.getId()){
                    rb_repairs_02.setTextColor(Color.WHITE);
                    rb_repairs_02.setText("基本满意");
                }else {
                    rb_repairs_02.setTextColor(Color.GRAY);
                    rb_repairs_02.setText("基本满意");

                }
                if(checkedId==rb_repairs_03.getId()){
                    rb_repairs_03.setTextColor(Color.WHITE);
                    rb_repairs_03.setText("不满意");
                }else {
                    rb_repairs_03.setTextColor(Color.GRAY);
                    rb_repairs_03.setText("不满意");

                }
            }
        });
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            if(getIntent().getStringExtra("progressState").equals("0")){
                ll_my_repairs_wuye.setVisibility(View.GONE);
                ll_my_repairs_pingjia.setVisibility(View.GONE);
                tv_my_repairs_pretreament_progress.setText("报修进度：待处理");
                tv_my_repairs_pretreament_time.setText("报修时间："+TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_my_repairs_pretreament_type.setText("报修类型："+dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_room.setText("报修房间："+dao.getRepair().getCommunityName()+""+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());
                if(dao.getRepairPicList().size()<1){
                tv_baoxiu_image.setVisibility(View.GONE);
                tv_wuye_image.setVisibility(View.GONE);
                iv01_my_repairs_pretreament.setVisibility(View.GONE);
                iv02_my_repairs_pretreament.setVisibility(View.GONE);
                iv03_my_repairs_pretreament.setVisibility(View.GONE);
                iv04_my_repairs_pretreament.setVisibility(View.GONE);
                iv05_my_repairs_pretreament.setVisibility(View.GONE);
                iv06_my_repairs_pretreament.setVisibility(View.GONE);
            }
            if (dao.getRepairPicList().size() >= 1) {
                Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                        dao.getRepairPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
            }
            if (dao.getRepairPicList().size() >= 2) {
                Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                        dao.getRepairPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
            }
            if (dao.getRepairPicList().size() >= 3) {
                Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                        dao.getRepairPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
            }
        }

            if(getIntent().getStringExtra("progressState").equals("1")){
                ll_my_repairs_wuye.setVisibility(View.GONE);
                ll_my_repairs_pingjia.setVisibility(View.GONE);
                tv_my_repairs_pretreament_progress.setText("报修进度：处理中");
                tv_my_repairs_pretreament_time.setText("报修时间："+TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_my_repairs_pretreament_room.setText("报修房间："+dao.getRepair().getCommunityName()+""+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_type.setText(dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());
                if(dao.getRepairPicList().size()<1){
                    tv_baoxiu_image.setVisibility(View.GONE);
                    iv01_my_repairs_pretreament.setVisibility(View.GONE);
                    iv02_my_repairs_pretreament.setVisibility(View.GONE);
                    iv03_my_repairs_pretreament.setVisibility(View.GONE);
                }
                if(dao.getRepairResultPicList().size()<1){
                    tv_baoxiu_image.setVisibility(View.GONE);
                    iv04_my_repairs_pretreament.setVisibility(View.GONE);
                    iv05_my_repairs_pretreament.setVisibility(View.GONE);
                    iv06_my_repairs_pretreament.setVisibility(View.GONE);
                }
                if (dao.getRepairPicList() != null) {
                    if (dao.getRepairPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
                    }
                }

            }
            if(getIntent().getStringExtra("progressState").equals("2")) {
                if(dao.getRepairPicList().size()<1){
                    tv_baoxiu_image.setVisibility(View.GONE);
                    iv01_my_repairs_pretreament.setVisibility(View.GONE);
                    iv02_my_repairs_pretreament.setVisibility(View.GONE);
                    iv03_my_repairs_pretreament.setVisibility(View.GONE);
                }
                if(dao.getRepairResultPicList().size()<1){
                    tv_wuye_image.setVisibility(View.GONE);
                    iv04_my_repairs_pretreament.setVisibility(View.GONE);
                    iv05_my_repairs_pretreament.setVisibility(View.GONE);
                    iv06_my_repairs_pretreament.setVisibility(View.GONE);
                }
                tv_my_repairs_pretreament_progress.setText("报修进度：已处理");
                tv_my_repairs_pretreament_wuye.setText("物业处理：已处理");
                tv_my_repairs_pretreament_room.setText("报修房间："+dao.getRepair().getCommunityName()+""+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_time.setText("报修时间："+TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_my_repairs_pretreament_type.setText(dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());

                if (dao.getRepairPicList() != null) {
                    if (dao.getRepairPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
                    }
                }

                if (dao.getRepairResultPicList() != null) {
                    if (dao.getRepairResultPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(0).getOriginalImg() == null || dao.getRepairResultPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv04_my_repairs_pretreament);
                    }
                    if (dao.getRepairResultPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(1).getOriginalImg() == null || dao.getRepairResultPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv05_my_repairs_pretreament);
                    }
                    if (dao.getRepairResultPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(2).getOriginalImg() == null || dao.getRepairResultPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv06_my_repairs_pretreament);
                    }
                }
            }
        }

        if(requestCode==3){
            MessageUtils.showShortToast(this, "评价成功");
            Intent intent =new Intent();
            setResult(2001,intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==iv01_my_repairs_pretreament&&dao.getRepairPicList().size()>=1){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv01_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_my_repairs_pretreament&&dao.getRepairPicList().size()>=2){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv02_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_my_repairs_pretreament&&dao.getRepairPicList().size()>=3){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv03_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv04_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=1){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairResultPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv04_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv04_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv04_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv05_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=2){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairResultPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv05_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv05_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv05_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv06_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=3){
            Intent intent=new Intent(MyRepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairResultPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv06_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv06_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv06_my_repairs_pretreament.getHeight());
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
        return "报修详情";
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
              /*  Intent intent=new Intent(MyRepairsPretreatmentActivity.this,MyRepairsAftertreatmentActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidOut(MyRepairsPretreatmentActivity.this);*/
                if (getIntent().getStringExtra("progressState").equals("0")) {
                    finish();
                }
                if (getIntent().getStringExtra("progressState").equals("1")) {
                    finish();
                }
                if (getIntent().getStringExtra("progressState").equals("2")) {

                    if (rb_repairs_01.isChecked()) {
                        dao.getRepair().setEvaluateLevel("0");
                    }
                    if (rb_repairs_02.isChecked()) {
                        dao.getRepair().setEvaluateLevel("1");
                    }
                    if (rb_repairs_03.isChecked()) {
                        dao.getRepair().setEvaluateLevel("2");
                    }

                    dao.requestRepairEvaluate(getIntent().getStringExtra("repairDemandId"), dao.getRepair().getEvaluateLevel(), et_my_repairs_pretreament_pingjia.getText().toString());


                }
            }
        });
        return true;
    }
}

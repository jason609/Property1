package com.ctrl.android.property.jason.ui.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.jason.dao.ComplaintDao;
import com.ctrl.android.property.jason.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.jason.util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyComplaintAftertreatmentActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_my_complaint_aftertreament_progress)
    TextView tv_my_complaint_aftertreament_progress;
    @InjectView(R.id.tv_my_complaint_aftertreament_time)
    TextView tv_my_complaint_aftertreament_time;
    @InjectView(R.id.tv_my_complaint_aftertreament_type)
    TextView tv_my_complaint_aftertreament_type;
    @InjectView(R.id.tv_my_complaint_aftertreament_wuye)
    TextView tv_my_complaint_aftertreament_wuye;
    @InjectView(R.id.tv_my_complaint_aftertreament_result)
    TextView tv_my_complaint_aftertreament_result;
    @InjectView(R.id.tv_my_complaint_aftertreament_pingjia_content)
    TextView tv_my_complaint_aftertreament_pingjia_content;
    @InjectView(R.id.tv_my_complaint_aftertreament_content)
    TextView tv_my_complaint_aftertreament_content;
    @InjectView(R.id.tv_my_complaint_aftertreament_pingjia)
    TextView tv_my_complaint_aftertreament_pingjia;
    @InjectView(R.id.iv01_my_complaint_aftertreamen)
    ImageView iv01_my_complaint_aftertreamen;
    @InjectView(R.id.iv02_my_complaint_aftertreamen)
    ImageView iv02_my_complaint_aftertreamen;
    @InjectView(R.id.iv03_my_complaint_aftertreamen)
    ImageView iv03_my_complaint_aftertreamen;
    @InjectView(R.id.iv04_my_complaint_aftertreamen)
    ImageView iv04_my_complaint_aftertreamen;
    @InjectView(R.id.iv05_my_complaint_aftertreamen)
    ImageView iv05_my_complaint_aftertreamen;
    @InjectView(R.id.iv06_my_complaint_aftertreamen)
    ImageView iv06_my_complaint_aftertreamen;
    @InjectView(R.id.tv_tousu_image)
    TextView tv_tousu_image;
    @InjectView(R.id.tv_wuye_image)
    TextView tv_wuye_image;
    private ComplaintDao dao;
    @InjectView(R.id.tv_my_complaint_aftertreament_room)
    TextView tv_my_complaint_aftertreament_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint_aftertreatment);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv01_my_complaint_aftertreamen.setOnClickListener(this);
        iv02_my_complaint_aftertreamen.setOnClickListener(this);
        iv03_my_complaint_aftertreamen.setOnClickListener(this);
        iv04_my_complaint_aftertreamen.setOnClickListener(this);
        iv05_my_complaint_aftertreamen.setOnClickListener(this);
        iv06_my_complaint_aftertreamen.setOnClickListener(this);
        dao=new ComplaintDao(this);
        dao.requestComplaint(getIntent().getStringExtra("complaintId"));
    }


    @Override
    public void onClick(View v) {
        if(v==iv01_my_complaint_aftertreamen&&dao.getComplaintPicList().size()>=1){//放大图片
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv01_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_my_complaint_aftertreamen&&dao.getComplaintPicList().size()>=2){
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv02_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_my_complaint_aftertreamen&&dao.getComplaintPicList().size()>=3){
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv03_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv04_my_complaint_aftertreamen&&dao.getComplaintResultPicList().size()>=1){
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(0).getZipImg());
            int[] location = new int[2];
            iv04_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv04_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv04_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv05_my_complaint_aftertreamen&&dao.getComplaintResultPicList().size()>=2){
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(1).getZipImg());
            int[] location = new int[2];
            iv05_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv05_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv05_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv06_my_complaint_aftertreamen&&dao.getComplaintResultPicList().size()>=3){
            Intent intent=new Intent(MyComplaintAftertreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getComplaintResultPicList().get(2).getZipImg());
            int[] location = new int[2];
            iv06_my_complaint_aftertreamen.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv06_my_complaint_aftertreamen.getWidth());
            intent.putExtra("height", iv06_my_complaint_aftertreamen.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            tv_my_complaint_aftertreament_progress.setText("投诉进度：已结束");
            tv_my_complaint_aftertreament_wuye.setText("物业处理：已结束");
            tv_my_complaint_aftertreament_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(dao.getComplaint().getCreateTime())));
            tv_my_complaint_aftertreament_room.setText("投诉房间："+dao.getComplaint().getCommunityName()+" "+dao.getComplaint().getBuilding()+"-"+dao.getComplaint().getUnit()+"-"+dao.getComplaint().getRoom());
            tv_my_complaint_aftertreament_type.setText("投诉类型："+dao.getComplaint().getComplaintKindName());
            tv_my_complaint_aftertreament_result.setText(S.getStr(dao.getComplaint().getResult()));
            tv_my_complaint_aftertreament_content.setText(dao.getComplaint().getContent());
            if(dao.getComplaint().getEvaluateLevel().equals("0")) {
                tv_my_complaint_aftertreament_pingjia.setText("非常满意");
            }
                if (dao.getComplaint().getEvaluateLevel().equals("1")) {
                    tv_my_complaint_aftertreament_pingjia.setText("基本满意");
                }
                    if (dao.getComplaint().getEvaluateLevel().equals("2")) {
                        tv_my_complaint_aftertreament_pingjia.setText("不满意");
                    }

            tv_my_complaint_aftertreament_pingjia_content.setText(dao.getComplaint().getEvaluateContent());
            if(dao.getComplaintPicList().size()<1){
                tv_tousu_image.setVisibility(View.GONE);
                iv01_my_complaint_aftertreamen.setVisibility(View.GONE);
                iv02_my_complaint_aftertreamen.setVisibility(View.GONE);
                iv03_my_complaint_aftertreamen.setVisibility(View.GONE);
            }
            if(dao.getComplaintResultPicList().size()<1){
                tv_wuye_image.setVisibility(View.GONE);
                iv04_my_complaint_aftertreamen.setVisibility(View.GONE);
                iv05_my_complaint_aftertreamen.setVisibility(View.GONE);
                iv06_my_complaint_aftertreamen.setVisibility(View.GONE);
            }

            if(dao.getComplaintPicList()!=null){
                if(dao.getComplaintPicList().size()>=1){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(0).getOriginalImg()==null || dao.getComplaintPicList().get(0).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_complaint_aftertreamen);
                }
                if(dao.getComplaintPicList().size()>=2){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(1).getOriginalImg()==null || dao.getComplaintPicList().get(1).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_complaint_aftertreamen);
                }
                if(dao.getComplaintPicList().size()>=3){
                    Arad.imageLoader.load(dao.getComplaintPicList().get(2).getOriginalImg()==null || dao.getComplaintPicList().get(2).getOriginalImg().equals("")?"aa":
                            dao.getComplaintPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_complaint_aftertreamen);
                }
            }

            if(dao.getComplaintResultPicList()!=null){
                if(dao.getComplaintResultPicList().size()>=1){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(0).getZipImg()==null || dao.getComplaintResultPicList().get(0).getZipImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(0).getZipImg()).placeholder(R.drawable.default_image).into(iv04_my_complaint_aftertreamen);
                }
                if(dao.getComplaintResultPicList().size()>=2){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(1).getZipImg()==null || dao.getComplaintResultPicList().get(1).getZipImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(1).getZipImg()).placeholder(R.drawable.default_image).into(iv05_my_complaint_aftertreamen);
                }
                if(dao.getComplaintResultPicList().size()>=3){
                    Arad.imageLoader.load(dao.getComplaintResultPicList().get(2).getZipImg()== null || dao.getComplaintResultPicList().get(2).getZipImg().equals("")?"aa":
                            dao.getComplaintResultPicList().get(2).getZipImg()).placeholder(R.drawable.default_image).into(iv06_my_complaint_aftertreamen);
                }


            }

        }




                }

                /**
                 * header 左侧按钮
                 * */
                @Override
                public boolean setupToolBarLeftButton (ImageView leftButton){
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
                public String setupToolBarTitle () {
                    return "投诉详情";
                }

            }

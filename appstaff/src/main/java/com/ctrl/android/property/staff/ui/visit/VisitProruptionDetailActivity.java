package com.ctrl.android.property.staff.ui.visit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.VisitDao;
import com.ctrl.android.property.staff.ui.CustomActivity.TestanroidpicActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VisitProruptionDetailActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.visit_name)//到访人
    TextView visit_name;
    @InjectView(R.id.visit_people)//拜访人
    TextView visit_people;
    @InjectView(R.id.visit_room)//拜访房间
    TextView visit_room;
    @InjectView(R.id.visit_count)//到访人数
    TextView visit_count;
    @InjectView(R.id.visit_car)//车牌号
    TextView visit_car;
    @InjectView(R.id.visit_tel)//拜访人电话
    TextView visit_tel;
    @InjectView(R.id.visit_stop_time)//预计停留时间
    TextView visit_stop_time;
    @InjectView(R.id.tv_visit_handstatus)//处理状态
    TextView tv_visit_handstatus;
    @InjectView(R.id.iv01_second_hand_transfer)//图片1
            ImageView iv01_second_hand_transfer;
    @InjectView(R.id.iv02_second_hand_transfer)//图片2
            ImageView iv02_second_hand_transfer;
    @InjectView(R.id.iv03_second_hand_transfer)//图片3
            ImageView iv03_second_hand_transfer;

    @InjectView(R.id.tv_photo)//拜访人照片文本
    TextView tv_photo;


    private VisitDao vdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_proruption_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        vdao=new VisitDao(this);
        vdao.requestVisitDetail("1",getIntent().getStringExtra("communityVisitId"));
        iv01_second_hand_transfer.setOnClickListener(this);
        iv02_second_hand_transfer.setOnClickListener(this);
        iv03_second_hand_transfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==iv01_second_hand_transfer&&vdao.getListImg().size()>=1){
            Intent intent=new Intent(VisitProruptionDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", vdao.getListImg().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_second_hand_transfer.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_second_hand_transfer.getWidth());
            intent.putExtra("height", iv01_second_hand_transfer.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_second_hand_transfer&&vdao.getListImg().size()>=2){
            Intent intent=new Intent(VisitProruptionDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", vdao.getListImg().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_second_hand_transfer.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_second_hand_transfer.getWidth());
            intent.putExtra("height", iv02_second_hand_transfer.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_second_hand_transfer&&vdao.getListImg().size()>=3){
            Intent intent=new Intent(VisitProruptionDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", vdao.getListImg().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_second_hand_transfer.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_second_hand_transfer.getWidth());
            intent.putExtra("height", iv03_second_hand_transfer.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(1==requestCode){
            visit_name.setText(vdao.getVisitDetail().getVisitorName());
            visit_people.setText(vdao.getVisitDetail().getMemberName());
            visit_room.setText(getIntent().getStringExtra("room"));
            visit_count.setText(vdao.getVisitDetail().getPeopleNum()+"");
            visit_car.setText(vdao.getVisitDetail().getNumberPlates());
            visit_tel.setText(vdao.getVisitDetail().getVisitorMobile());
            visit_stop_time.setText(vdao.getVisitDetail().getResidenceTime());
            if(vdao.getVisitDetail().getHandleStatus()==0){
                tv_visit_handstatus.setText("待处理");
                tv_visit_handstatus.setBackgroundResource(R.drawable.visit_notice);
            }
            if(vdao.getVisitDetail().getHandleStatus()==1){
                tv_visit_handstatus.setText("同意到访");
                tv_visit_handstatus.setBackgroundResource(R.drawable.visit_agreed);

            }
            if(vdao.getVisitDetail().getHandleStatus()==2){
                tv_visit_handstatus.setText("拒绝到访");
                tv_visit_handstatus.setBackgroundResource(R.drawable.visit_refused);

            }
            if(vdao.getVisitDetail().getHandleStatus()==3){
                tv_visit_handstatus.setText("业主不在家");
                tv_visit_handstatus.setBackgroundResource(R.drawable.visit_none);

            }
            ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
            int w=iv01_second_hand_transfer.getWidth();
            //android.util.Log.d("demo", "width : " + w);
            params.height=w;
            // android.util.Log.d("demo", "height : " + params.height);
            iv01_second_hand_transfer.setLayoutParams(params);
            iv02_second_hand_transfer.setLayoutParams(params);
            iv03_second_hand_transfer.setLayoutParams(params);
            if(vdao.getListImg().size()<1){
                tv_photo.setVisibility(View.GONE);
                iv01_second_hand_transfer.setVisibility(View.GONE);
                iv02_second_hand_transfer.setVisibility(View.GONE);
                iv03_second_hand_transfer.setVisibility(View.GONE);
            }
            Log.i("Tag", "Tag"+vdao.getListImg().size());
            if(vdao.getListImg()!=null) {
                if (vdao.getListImg().size() >= 1) {
                    Arad.imageLoader.load(vdao.getListImg().get(0).getZipImg() == null ||
                            vdao.getListImg().get(0).getZipImg().equals("") ? "aa" :
                            vdao.getListImg().get(0).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv01_second_hand_transfer);
                }
                if (vdao.getListImg().size() >= 2) {
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(vdao.getListImg().get(1).getZipImg() == null ||
                            vdao.getListImg().get(1).getZipImg().equals("") ? "aa" :
                            vdao.getListImg().get(1).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv02_second_hand_transfer);
                }
                if (vdao.getListImg().size() >= 3) {
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(vdao.getListImg().get(2).getZipImg() == null ||
                            vdao.getListImg().get(2).getZipImg().equals("") ? "aa" :
                            vdao.getListImg().get(2).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv03_second_hand_transfer);
                }
            }


        }
    }

    @Override
    public String setupToolBarTitle() {
        return "突发到访";
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

}

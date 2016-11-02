package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ComplaintDao;
import com.ctrl.android.property.staff.entity.GoodPic;
import com.ctrl.android.property.staff.ui.contact.ContactActivity;
import com.ctrl.android.property.staff.util.D;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 投诉已派工详情 activity
 * Created by Eric on 2015/10/22
 */
public class ComplaintEndDetailActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.tv_repairs_room)//投诉房间
    TextView tv_repairs_room;
    @InjectView(R.id.tv_my_repairs_aftertreament_time)//投诉时间
    TextView tv_my_repairs_aftertreament_time;
    @InjectView(R.id.tv_my_repairs_aftertreament_type)//投诉类型
    TextView tv_my_repairs_aftertreament_type;
    @InjectView(R.id.tv_my_repairs_aftertreament_content)//投诉内容
    TextView tv_my_repairs_aftertreament_content;
    @InjectView(R.id.tv_excute_name)//执行人
    TextView tv_excute_name;
    @InjectView(R.id.tv_appiont_time)//派工时间
    TextView tv_appiont_time;
    @InjectView(R.id.iv01_my_repairs_aftertreament)//实景照片1
    ImageView iv01_my_repairs_aftertreament;
    @InjectView(R.id.iv02_my_repairs_aftertreament)//实景照片2
    ImageView iv02_my_repairs_aftertreament;
    @InjectView(R.id.iv03_my_repairs_aftertreament)//实景照片3
    ImageView iv03_my_repairs_aftertreament;
    @InjectView(R.id.tv_baoxiu_image)//实景照片
            TextView tv_baoxiu_image;
    private static final String TITLE ="投诉详情" ;
    private ComplaintDao cdao;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.activity_complaint_end_detail);
        ButterKnife.inject(this);
        initData();


    }


    /**
     * 初始化执行的 方法
     * */
    private void initData(){
        String id = getIntent().getStringExtra("repairDemandId");
        cdao=new ComplaintDao(this);
        cdao.requestComplait(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==3){
            MessageUtils.showShortToast(ComplaintEndDetailActivity.this,"下达任务成功");
            finish();
        }

        if(requestCode==1){
           tv_repairs_room.setText("投诉房间："+cdao.getComplaint().getBuilding()+"号楼"+cdao.getComplaint().getUnit()+"单元"+cdao.getComplaint().getRoom());
           if(cdao.getComplaint().getCreateTime()!=null){
               tv_my_repairs_aftertreament_time.setText("投诉时间："+D.getDateStrFromStamp("yyyy-MM-dd",cdao.getComplaint().getCreateTime()));
           }
           tv_my_repairs_aftertreament_type.setText("投诉类型："+cdao.getComplaint().getComplaintKindName());
           tv_my_repairs_aftertreament_content.setText(cdao.getComplaint().getContent());
           if(cdao.getGoodPicList()!=null){
               if(cdao.getGoodPicList().size()>0) {
                   setImage(cdao.getGoodPicList());
               }else {
                   tv_baoxiu_image.setVisibility(View.GONE);
                   iv01_my_repairs_aftertreament.setVisibility(View.GONE);
                   iv02_my_repairs_aftertreament.setVisibility(View.GONE);
                   iv03_my_repairs_aftertreament.setVisibility(View.GONE);
               }
           }
           tv_excute_name.setText("执行人："+cdao.getComplaint().getStaffName());
           if(cdao.getComplaint().getAssignTime()!=null){
               tv_appiont_time.setText("派工时间："+D.getDateStrFromStamp("yyyy-MM-dd HH:mm",cdao.getComplaint().getAssignTime()));
           }
        }
    }

    private void setImage(List<GoodPic> goodPicList) {
        if(goodPicList.size()>=1){
            Arad.imageLoader.load(goodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_aftertreament);
        }
        if(goodPicList.size()>=2){
            Arad.imageLoader.load(goodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_aftertreament);
        }
        if(goodPicList.size()>=3){
            Arad.imageLoader.load(goodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_aftertreament);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==RESULT_OK){
            tv_excute_name.setText(data.getStringExtra("name"));
            id=data.getStringExtra("id");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_excute_add:
                intent=new Intent(ComplaintEndDetailActivity.this, ContactActivity.class);
                startActivityForResult(intent, 200);
                AnimUtil.intentSlidIn(ComplaintEndDetailActivity.this);
                break;
            case R.id.tv_done:
                if(!TextUtils.isEmpty(tv_excute_name.getText().toString())){
                    cdao.requestAssignComplaint(id,cdao.getComplaint().getId());
                }
                break;
        }

    }
}

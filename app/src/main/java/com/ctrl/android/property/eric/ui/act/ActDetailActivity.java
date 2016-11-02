package com.ctrl.android.property.eric.ui.act;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.ActDao;
import com.ctrl.android.property.eric.entity.ActDetail;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.Participant;
import com.ctrl.android.property.eric.ui.adapter.ActGridListAdapter;
import com.ctrl.android.property.eric.ui.widget.GridViewForScrollView;
import com.ctrl.android.property.eric.ui.widget.ImageZoomActivity;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 活动详细 activity
 * Created by Eric on 2015/10/13.
 */
public class ActDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.scroll_view)
    ScrollView scroll_view;

    @InjectView(R.id.act_title)//标题
    TextView act_title;
    @InjectView(R.id.act_writer)//作者
    TextView act_writer;
    @InjectView(R.id.act_img)//图片
    ImageView act_img;
    @InjectView(R.id.act_content)//内容
    TextView act_content;
    @InjectView(R.id.act_name)//发起人姓名
    TextView act_name;
    @InjectView(R.id.take_part_in_btn)//参加活动
    TextView take_part_in_btn;
    @InjectView(R.id.act_gridview)//参与成员列表
    GridViewForScrollView act_gridview;

    /**
     * 活动详细
     * */
    private ActDetail actDetail = new ActDetail();

    /**
     * 参与者列表
     * */
    private List<Participant> listParticipant = new ArrayList<>();

    /**
     * 图片列表
     * */
    private List<Img> listImg = new ArrayList<>();

    private String actionId;
    private String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
    private ActDao actDao;

    private ActGridListAdapter actGridListAdapter;

    private String TITLE = StrConstant.COMMUNITY_ACTIVITY_DETAIL_DETAIL;

    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.act_detail_activity);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        actionId = getIntent().getStringExtra("actionId");

        take_part_in_btn.setOnClickListener(this);

        actDao = new ActDao(this);
        showProgress(true);
        actDao.requestActDetail(actionId,AppHolder.getInstance().getMemberInfo().getMemberId());

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(1 == requestCode){
            actDetail = actDao.getActDetail();
            listParticipant = actDao.getListParticipant();
            listImg = actDao.getListImg();
            if(actDetail.getActionPeopleName()!=null&&!actDetail.getActionPeopleName().equals("")) {
                act_name.setText("发起人：" + actDetail.getActionPeopleName());
            }else {
                act_name.setVisibility(View.GONE);
            }
            act_title.setText(S.getStr(actDetail.getTitle()));
            act_writer.setText("时间: " + D.getDateStrFromStamp("yyyy.MM.dd", actDetail.getStartTime()) + " - " + D.getDateStrFromStamp("MM.dd", actDetail.getEndTime()));
            if(listImg.size() > 0){
                Arad.imageLoader.load(listImg.get(0).getOriginalImg() == null || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                        .placeholder(R.drawable.default_image)
                        .into(act_img);

                act_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagelist = new ArrayList<String>();
                        imagelist.add(listImg.get(0).getOriginalImg());
                        Intent intent = new Intent(ActDetailActivity.this, ImageZoomActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("imageList", imagelist);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            } else {
                act_img.setVisibility(View.GONE);
//                Arad.imageLoader.load("aa")
//                        .placeholder(R.drawable.default_image)
//                        .into(act_img);
            }

            act_content.setText(S.getStr(actDetail.getContent()));

            actGridListAdapter = new ActGridListAdapter(this);
            if(listParticipant != null){
                actGridListAdapter.setList(listParticipant);
            } else {
                actGridListAdapter.setList(new ArrayList<Participant>());
            }
            act_gridview.setAdapter(actGridListAdapter);

            //参与状态(0:参与,1:未参与)
            if(actDetail.getStatus() == 1){
                take_part_in_btn.setEnabled(false);
                take_part_in_btn.setClickable(false);
                take_part_in_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                take_part_in_btn.setText("已参与");
            } else if(actDetail.getStatus() == 0){
                take_part_in_btn.setEnabled(true);
                take_part_in_btn.setClickable(true);
                take_part_in_btn.setBackgroundResource(R.drawable.green_bg_shap);
                take_part_in_btn.setText("参加活动");
            }

            scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部

        }

        if(2 == requestCode){
            MessageUtils.showShortToast(this, "参加成功");
            showProgress(true);
            actDao.requestActDetail(actionId,AppHolder.getInstance().getMemberInfo().getMemberId());
        }
    }

    @Override
    public void onClick(View v) {
        if(v == take_part_in_btn){
            showProgress(true);
            actDao.requestTakePartInAct(actionId,memberId);
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
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.more_info_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
//                showProStyleListPop();
//            }
//        });
//        return true;
//    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("pic","aa" + i);
            map.put("name","用户名" + i);
            list.add(map);
        }
        return list;
    }





}

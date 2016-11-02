package com.ctrl.android.property.staff.ui.forum;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ForumDao;
import com.ctrl.android.property.staff.entity.ForumCategory;
import com.ctrl.android.property.staff.ui.adapter.ForumCategaryAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛 主页面
 * Created by Eric on 2015/10/26
 */
public class ForumActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    private ForumCategaryAdapter forumCategaryAdapter;
    private List<ForumCategory> listForumCategory;

    private String TITLE = "内部论坛";

    private ForumDao forumDao;

    private String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();//社区id 具体问题具体分析

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        initBtnLictener();

        forumDao = new ForumDao(this);
        showProgress(true);
        forumDao.requestForumAreaList(communityId);


    }

    @Override
    public void onClick(View v) {
        //
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            //MessageUtils.showShortToast(this,"请求成功");
            listForumCategory = forumDao.getListForumCategory();
            forumCategaryAdapter = new ForumCategaryAdapter(this);
            forumCategaryAdapter.setList(listForumCategory);
            listView.setAdapter(forumCategaryAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(ForumActivity.this,listForumCategory.get(position).getName());
                    Intent intent = new Intent(ForumActivity.this, ForumeListActivity.class);
                    intent.putExtra("title",listForumCategory.get(position).getName());
                    intent.putExtra("categoryId",listForumCategory.get(position).getForumCategoryId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(ForumActivity.this);
                    finish();
                }
            });

        }

    }

    /**
     * 数据请求失败后
     * */
    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        MessageUtils.showShortToast(this,errorMessage);
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
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }

    /**
     * 初始化中设置监听
     * */
    private void initBtnLictener(){
//        UiUtil.clickToActivityWithArg(forum_exp_area_btn, Constant.ARG_EXP_AREA, ForumActivity.this, ForumeListActivity.class);
//        UiUtil.clickToActivityWithArg(version_update_notice_area_btn,Constant.ARG_VER_UPDATE, ForumActivity.this, ForumeListActivity.class);
//        UiUtil.clickToActivityWithArg(green_hand_notice_btn,Constant.ARG_GREEN_HAND, ForumActivity.this, ForumeListActivity.class);
//        UiUtil.clickToActivityWithArg(essence_area_btn,Constant.ARG_SSENCE_AREA, ForumActivity.this, ForumeListActivity.class);
//        UiUtil.clickToActivityWithArg(community_survey_area_btn,Constant.ARG_COMMUNITY_SURVEY, ForumActivity.this, SurveyActivity.class);
//        UiUtil.clickToActivityWithArg(community_activity_area_btn,Constant.ARG__COMMUNITY_ACT, ForumActivity.this, ForumeListActivity.class);
    }

}

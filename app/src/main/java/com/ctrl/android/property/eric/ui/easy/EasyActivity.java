package com.ctrl.android.property.eric.ui.easy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 便民服务 activity
 * Created by Eric on 2015/10/13.
 */
public class EasyActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.easy_bus_btn)//公交出行
    TextView easy_bus_btn;
    @InjectView(R.id.easy_shop_btn)//便民商家
    TextView easy_shop_btn;
    @InjectView(R.id.easy_edu_btn)//教育资源
    TextView easy_edu_btn;
    @InjectView(R.id.easy_community_recommend_btn)//社区推荐
    TextView easy_community_recommend_btn;
    @InjectView(R.id.easy_search_info_btn)//信息查询
    TextView easy_search_info_btn;

    private String TITLE = StrConstant.EASY_SERVICE_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.easy_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        easy_bus_btn.setOnClickListener(this);
        easy_shop_btn.setOnClickListener(this);
        easy_edu_btn.setOnClickListener(this);
        easy_community_recommend_btn.setOnClickListener(this);
        easy_search_info_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == easy_bus_btn){
            //MessageUtils.showShortToast(this,"公交出行");
            Intent intent = new Intent(EasyActivity.this, EasyBusActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(EasyActivity.this);
        }

        if(v == easy_shop_btn){
            //MessageUtils.showShortToast(this,"便民商家");
            Intent intent = new Intent(EasyActivity.this, EasyShopAroundActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(EasyActivity.this);
        }

        if(v == easy_edu_btn){
            MessageUtils.showShortToast(this, "尚未开通 敬请期待");
        }

        if(v == easy_community_recommend_btn){
            MessageUtils.showShortToast(this, "尚未开通 敬请期待");
//            Intent intent = new Intent(EasyActivity.this, EasyShopAroundServiceActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(EasyActivity.this);
        }

        if(v == easy_search_info_btn){
            MessageUtils.showShortToast(this, "尚未开通 敬请期待");
//            Intent intent = new Intent(EasyActivity.this, SurveyListActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(EasyActivity.this);
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
//        rightButton.setImageResource(R.drawable.white_cross_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(EasyActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }





}

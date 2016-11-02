package com.ctrl.android.property.eric.ui.easy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.UiUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 便民商家 activity
 * Created by Eric on 2015/10/13.
 */
public class EasyShopAroundActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.keyword_text)//关键词
    EditText keyword_text;
    @InjectView(R.id.search_btn)//搜索按钮
    Button search_btn;

    @InjectView(R.id.btn_easy_hotel)//酒店
    TextView btn_easy_hotel;
    @InjectView(R.id.btn_easy_life)//生活服务
    TextView btn_easy_life;
    @InjectView(R.id.btn_easy_landry)//洗衣
    TextView btn_easy_landry;
    @InjectView(R.id.btn_easy_entertain)//休闲娱乐
    TextView btn_easy_entertain;
    @InjectView(R.id.btn_easy_movie)//电影
    TextView btn_easy_movie;
    @InjectView(R.id.btn_easy_food)//美食
    TextView btn_easy_food;
    @InjectView(R.id.btn_easy_ktv)//KTV
    TextView btn_easy_ktv;
    @InjectView(R.id.btn_easy_coffee)//咖啡
    TextView btn_easy_coffee;
    @InjectView(R.id.btn_easy_gym)//健身
    TextView btn_easy_gym;
    @InjectView(R.id.btn_easy_parking)//停车
    TextView btn_easy_parking;
    @InjectView(R.id.btn_easy_facial)//美容
    TextView btn_easy_facial;
    @InjectView(R.id.btn_easy_traval)//旅游
    TextView btn_easy_traval;

    private String TITLE = StrConstant.EASY_SHOP_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.easy_shop_around_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        initBtnLictener();

    }

    @Override
    public void onClick(View v) {
        if(v == search_btn){
            String key_word = keyword_text.getText().toString();

            if(!S.isNull(key_word)){
                Intent intent = new Intent(EasyShopAroundActivity.this, EasyShopAroundListActivity.class);
                intent.putExtra(Constant.ARG_FLG,key_word);
                startActivity(intent);
                AnimUtil.intentSlidIn(EasyShopAroundActivity.this);
            } else {
                MessageUtils.showShortToast(this,getString(R.string.pls_input_keyword));
            }


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
//                MessageUtils.showShortToast(EasyShopAroundActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }

    /**
     * 初始化中设置 每个text按钮的监听
     * */
    private void initBtnLictener(){

        UiUtil.clickToActivityWithArg(btn_easy_hotel, Constant.ARG_HOTEL, EasyShopAroundActivity.this, EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_life, Constant.ARG_LIFE, EasyShopAroundActivity.this, EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_landry,Constant.ARG_LANDRY,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_entertain,Constant.ARG_ENTERTAIN,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_movie,Constant.ARG_MOVIE,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_food,Constant.ARG_FOOD,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_ktv,Constant.ARG_KTV,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_coffee,Constant.ARG_COFFEE,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_gym,Constant.ARG_GYM,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_parking,Constant.ARG_PARKING,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_facial,Constant.ARG_FACIAL,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);
        UiUtil.clickToActivityWithArg(btn_easy_traval,Constant.ARG_TRAVAL,EasyShopAroundActivity.this,EasyShopAroundListActivity.class);

        search_btn.setOnClickListener(this);

    }



}

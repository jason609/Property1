package com.ctrl.android.property.eric.ui.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.ui.secondmarket.SecondHandActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class InfoMainActivity extends AppToolBarActivity implements View.OnClickListener {

    @InjectView(R.id.btn_survey)
    Button btn_survey;
    @InjectView(R.id.btn_market)
    Button btn_market;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        btn_survey.setOnClickListener(this);
        btn_market.setOnClickListener(this);
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
        return "社区分类信息";
    }


    @Override
    public void onClick(View v) {

        if(v==btn_survey){
            startActivity(new Intent(InfoMainActivity.this, SurveyListActivity.class));
            AnimUtil.intentSlidIn(this);
        }

        if(v==btn_market){
            startActivity(new Intent(InfoMainActivity.this, SecondHandActivity.class));
            AnimUtil.intentSlidIn(this);
        }

    }
}

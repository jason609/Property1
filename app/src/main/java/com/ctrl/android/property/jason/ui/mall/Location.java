package com.ctrl.android.property.jason.ui.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.util.StrConstant;

/**
 * 选择城市 activity
 * Created by Jason on 2015/10/10.
 * */
public class Location extends AppToolBarActivity implements View.OnClickListener {
    private String TITLE= StrConstant.CHOOSE_CITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }


    @Override
    public void onClick(View v) {

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


}

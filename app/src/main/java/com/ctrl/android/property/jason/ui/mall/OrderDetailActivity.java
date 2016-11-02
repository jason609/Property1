package com.ctrl.android.property.jason.ui.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;

public class OrderDetailActivity extends AppToolBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
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
        return "订单详情";
    }

}

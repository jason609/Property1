package com.ctrl.android.property.staff.ui.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 到访处理  activity
* */

public class VisitHandleActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_visit_order)//预约到访
    TextView tv_visit_order;
    @InjectView(R.id.tv_visit_proruption)//突发到访
    TextView tv_visit_proruption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_handle);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        tv_visit_order.setOnClickListener(this);
        tv_visit_proruption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tv_visit_order){
            Intent intent=new Intent(VisitHandleActivity.this,VisitOrderActivity.class);
            intent.putExtra("visitType","0");
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }
        if(v==tv_visit_proruption){
            Intent intent=new Intent(VisitHandleActivity.this,VisitProruptionActivity.class);
            intent.putExtra("visitType","1");
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
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
        return "到访处理";
    }
}

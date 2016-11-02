package com.ctrl.android.property.jason.ui.famillyhotline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintActivity;
import com.ctrl.android.property.jason.ui.repairs.MyRepairsActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FamillyHotLineListActivity extends AppToolBarActivity implements View.OnClickListener {

    @InjectView(R.id.btn_complaint)
    Button btn_complaint;
    @InjectView(R.id.btn_repairs)
    Button btn_repairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familly_hotline_list);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        btn_complaint.setOnClickListener(this);
        btn_repairs.setOnClickListener(this);
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
        return "报修投诉";
    }


    @Override
    public void onClick(View v) {
        if(v==btn_complaint){
            startActivity(new Intent(FamillyHotLineListActivity.this, MyComplaintActivity.class));
            AnimUtil.intentSlidIn(this);
        }
        if(v==btn_repairs){
            startActivity(new Intent(FamillyHotLineListActivity.this, MyRepairsActivity.class));
            AnimUtil.intentSlidIn(this);
        }
    }
}

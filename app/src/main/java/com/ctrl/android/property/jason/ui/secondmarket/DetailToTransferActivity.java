package com.ctrl.android.property.jason.ui.secondmarket;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 转让详情 activity
* */

public class DetailToTransferActivity extends AppToolBarActivity implements View.OnClickListener {
     @InjectView(R.id.iv_want_transfer_tel)
     ImageView iv_want_transfer_tel;
     @InjectView(R.id.et_contact_telephone)
     TextView et_contact_telephone;
    @InjectView(R.id.iv_want_transfer_baobei_image01)
    ImageView iv_want_transfer_baobei_image01;
    @InjectView(R.id.iv_want_transfer_baobei_image02)
    ImageView iv_want_transfer_baobei_image02;
    @InjectView(R.id.iv_want_transfer_baobei_image03)
    ImageView iv_want_transfer_baobei_image03;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_transfer);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv_want_transfer_tel.setOnClickListener(this);
        iv_want_transfer_baobei_image01.setOnClickListener(this);
        iv_want_transfer_baobei_image02.setOnClickListener(this);
        iv_want_transfer_baobei_image03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==iv_want_transfer_tel){
            AndroidUtil.dial(DetailToTransferActivity.this,et_contact_telephone.getText().toString());
        }
     /*   if(v==iv_want_transfer_baobei_image01){
            Intent intent=new Intent(DetailToTransferActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_repairs_aftertreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_repairs_aftertreament.getWidth());
            intent.putExtra("height", iv02_my_repairs_aftertreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }*/


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
        return "转让";
    }


    /**
     * header 右侧按钮
     * */

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return true;
    }
}

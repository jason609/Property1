package com.ctrl.android.property.jason.ui.secondmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.entity.UsedGoodInfo;
import com.ctrl.android.property.jason.util.StrConstant;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*求购详情 activity*/

public class DetailToBuyActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.iv_detail_to_buy_tel)
    ImageView iv_detail_to_buy_tel;
    @InjectView(R.id.et_contact_telephone)
    TextView et_contact_telephone;
    @InjectView(R.id.tv_detail_to_buy_name)
    TextView tv_detail_to_buy_name;
    @InjectView(R.id.tv_detail_to_buy_time)
    TextView tv_detail_to_buy_time;
    @InjectView(R.id.tv_detail_to_buy_baobei_title_right)
    TextView tv_detail_to_buy_baobei_title_right;
    @InjectView(R.id.tv_detail_to_buy_baobei_price_right)
    TextView tv_detail_to_buy_baobei_price_right;
    @InjectView(R.id.tv_detail_to_buy_baobei_detail_right)
    TextView tv_detail_to_buy_baobei_detail_right;
    @InjectView(R.id.tv_detail_to_buy_baobei_location_right)
    TextView tv_detail_to_buy_baobei_location_right;
    @InjectView(R.id.et_contact)
    TextView et_contact;
    @InjectView(R.id.tv_baby_detail_delete)
    TextView tv_baby_detail_delete;

    private UsedGoodsDao dao;
    private UsedGoodInfo mUsedGoodInfo;
    private String usedGoodsId;
    private int position;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_to_buy);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iv_detail_to_buy_tel.setOnClickListener(this);
        tv_baby_detail_delete.setOnClickListener(this);
        usedGoodsId=getIntent().getStringExtra("usedGoodsId");
        dao=new UsedGoodsDao(this);
        dao.requestGoodsGet(usedGoodsId);
        intent=getIntent();
        if(getIntent().getFlags()== StrConstant.MY_BABY_BUY_DETAIL){
            tv_baby_detail_delete.setVisibility(View.VISIBLE);
            position=getIntent().getIntExtra("position",-1);
            Log.i("Tag", "Tag1008" + position);
        }


    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
          // MessageUtils.showShortToast(this, "连接成功");
           mUsedGoodInfo=dao.getUsedGoodInfo();
           tv_detail_to_buy_name.setText(mUsedGoodInfo.getContactName());
           tv_detail_to_buy_baobei_detail_right.setText(mUsedGoodInfo.getContent());
            tv_detail_to_buy_baobei_location_right.setText(mUsedGoodInfo.getCommunityName() + " " +mUsedGoodInfo.getBuilding()+"-"+mUsedGoodInfo.getUnit()+"-"+mUsedGoodInfo.getRoom());
           String releastTime = getIntent().getStringExtra("releaseTime");
           tv_detail_to_buy_time.setText("发布时间："+ TimeUtil.date(Long.parseLong(releastTime)));
           tv_detail_to_buy_baobei_title_right.setText(mUsedGoodInfo.getTitle());
           DecimalFormat df = new DecimalFormat("#.00");
           tv_detail_to_buy_baobei_price_right.setText("￥"+df.format(mUsedGoodInfo.getSellingPrice()));
           et_contact.setText(mUsedGoodInfo.getContactName());
           et_contact_telephone.setText(mUsedGoodInfo.getContactMobile());
        }
        if(requestCode==4){
            MessageUtils.showShortToast(this, "信息删除成功");
            if(getIntent().getFlags()==StrConstant.MY_BABY_BUY_DETAIL){
                intent.putExtra("position",position);
              //  Log.i("Tag", "Tag1001" + position);
                setResult(1003, intent);
                finish();
            }

            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if(v==iv_detail_to_buy_tel){
            AndroidUtil.dial(DetailToBuyActivity.this,et_contact_telephone.getText().toString());
        }
        if(v==tv_baby_detail_delete){
            dao.requestGoodsDelete(usedGoodsId);
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
        return "求购";
    }



}

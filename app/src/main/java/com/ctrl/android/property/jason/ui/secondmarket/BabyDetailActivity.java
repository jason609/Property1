package com.ctrl.android.property.jason.ui.secondmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.UsedGoodInfo;
import com.ctrl.android.property.jason.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.jason.util.StrConstant;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BabyDetailActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.iv_baby_detail_tel)
    ImageView iv_baby_detail_tel;
    @InjectView(R.id.tv_contact_telephone_right)
    TextView tv_contact_telephone_right;
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
    @InjectView(R.id.tv_contact_right)
    TextView tv_contact_right;
    @InjectView(R.id.iv_want_transfer_baobei_image01)
    ImageView iv_want_transfer_baobei_image01;
    @InjectView(R.id.iv_want_transfer_baobei_image02)
    ImageView iv_want_transfer_baobei_image02;
    @InjectView(R.id.iv_want_transfer_baobei_image03)
    ImageView iv_want_transfer_baobei_image03;
    @InjectView(R.id.ll_image)
    LinearLayout ll_image;
    @InjectView(R.id.tv_baobei_image)
    TextView tv_baobei_image;
    @InjectView(R.id.tv_baby_detail_delete)
    TextView tv_baby_detail_delete;

    private UsedGoodsDao dao;
    private UsedGoodInfo useGoodInfo;
    private List<GoodPic>list;
    private String usedGoodsId;
    private int position;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        intent=getIntent();
        iv_want_transfer_baobei_image01.setOnClickListener(this);
        iv_want_transfer_baobei_image02.setOnClickListener(this);
        iv_want_transfer_baobei_image03.setOnClickListener(this);
        iv_baby_detail_tel.setOnClickListener(this);
        tv_baby_detail_delete.setOnClickListener(this);
        dao=new UsedGoodsDao(this);
        usedGoodsId=getIntent().getStringExtra("usedGoodsId");
        dao.requestGoodsGet(usedGoodsId);
        if(getIntent().getFlags()== StrConstant.MY_BABY_DETAIL){
            tv_baby_detail_delete.setVisibility(View.VISIBLE);
            position=getIntent().getIntExtra("position",-1);
            Log.i("Tag", "Tag1111" + position);

        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
           // MessageUtils.showShortToast(this,"连接成功");
            useGoodInfo=dao.getUsedGoodInfo();
            list=dao.getGoodPicList();
            tv_detail_to_buy_name.setText(useGoodInfo.getContactName());
            String releastTime = getIntent().getStringExtra("releaseTime");
           /* Log.i("Tag", "releaseTime02" + releastTime);
            Log.i("Tag", "systemTime" + System.currentTimeMillis());*/
           // long nowTime = Long.parseLong(releastTime);
            tv_detail_to_buy_time.setText("发布时间："+ TimeUtil.date(Long.parseLong(releastTime)));
            tv_detail_to_buy_baobei_title_right.setText(useGoodInfo.getTitle());
            DecimalFormat df = new DecimalFormat("#.00");
            tv_detail_to_buy_baobei_price_right.setText("￥"+df.format(useGoodInfo.getSellingPrice()));
            tv_detail_to_buy_baobei_detail_right.setText(useGoodInfo.getContent());
            tv_detail_to_buy_baobei_location_right.setText(useGoodInfo.getCommunityName() + " " +useGoodInfo.getBuilding()+"-"+useGoodInfo.getUnit()+"-"+useGoodInfo.getRoom());
            tv_contact_right.setText(useGoodInfo.getContactName());
            tv_contact_telephone_right.setText(useGoodInfo.getContactMobile());
            if(list.size()==0){
                ll_image.setVisibility(View.GONE);
                tv_baobei_image.setVisibility(View.GONE);
            }
           // if(list.size()>0){
               /* if(list.size()==1)
                    Arad.imageLoader.load(S.getStr(list.get(0).getOriginalImg()))
                            .placeholder(R.drawable.default_activity_img)
                            .into(iv_want_transfer_baobei_image01);
                else if(list.size()==2){
                    Arad.imageLoader.load(S.getStr(list.get(0).getOriginalImg()))
                            .placeholder(R.drawable.default_activity_img)
                            .into(iv_want_transfer_baobei_image01);
                    Arad.imageLoader.load(S.getStr(list.get(1).getOriginalImg()))
                            .placeholder(R.drawable.default_activity_img)
                            .into(iv_want_transfer_baobei_image02);
                }else if(list.size()==3){*/
                if(list.size()==1) {
                    Arad.imageLoader.load(S.getStr(list.get(0).getOriginalImg()))
                            .placeholder(R.drawable.default_image)
                            .into(iv_want_transfer_baobei_image01);
                    iv_want_transfer_baobei_image02.setVisibility(View.INVISIBLE);
                    iv_want_transfer_baobei_image03.setVisibility(View.INVISIBLE);
                }
                    if(list.size()==2) {
                        Arad.imageLoader.load(S.getStr(list.get(0).getOriginalImg()))
                                .placeholder(R.drawable.default_image)
                                .into(iv_want_transfer_baobei_image01);
                        Arad.imageLoader.load(S.getStr(list.get(1).getOriginalImg()))
                                .placeholder(R.drawable.default_image)
                                .into(iv_want_transfer_baobei_image02);
                        iv_want_transfer_baobei_image03.setVisibility(View.INVISIBLE);
                    }if(list.size()==3){
                    Arad.imageLoader.load(S.getStr(list.get(0).getOriginalImg()))
                            .placeholder(R.drawable.default_image)
                            .into(iv_want_transfer_baobei_image01);
                    Arad.imageLoader.load(S.getStr(list.get(1).getOriginalImg()))
                            .placeholder(R.drawable.default_image)
                            .into(iv_want_transfer_baobei_image02);
                    Arad.imageLoader.load(S.getStr(list.get(2).getOriginalImg()))
                            .placeholder(R.drawable.default_image)
                            .into(iv_want_transfer_baobei_image03);

                }
           // }


        /*    }else{
                iv_want_transfer_baobei_image01.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.default_activity_img));
                iv_want_transfer_baobei_image02.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.default_activity_img));
                iv_want_transfer_baobei_image03.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.default_activity_img));

            }*/
        }
        if(requestCode==4){
            MessageUtils.showShortToast(this,"信息删除成功");
            if(getIntent().getFlags()==StrConstant.MY_BABY_DETAIL){
                intent.putExtra("position", position);
                setResult(1009,intent);
                finish();
            }
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if(v==tv_baby_detail_delete){
            dao.requestGoodsDelete(usedGoodsId);

        }
        if(v==iv_baby_detail_tel){
            AndroidUtil.dial(BabyDetailActivity.this,tv_contact_telephone_right.getText().toString());
        }
        if(v==iv_want_transfer_baobei_image01){
            Intent intent=new Intent(BabyDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv_want_transfer_baobei_image01.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv_want_transfer_baobei_image01.getWidth());
            intent.putExtra("height", iv_want_transfer_baobei_image01.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv_want_transfer_baobei_image02){
            Intent intent=new Intent(BabyDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv_want_transfer_baobei_image02.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv_want_transfer_baobei_image02.getWidth());
            intent.putExtra("height", iv_want_transfer_baobei_image02.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv_want_transfer_baobei_image03){
            Intent intent=new Intent(BabyDetailActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getGoodPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv_want_transfer_baobei_image03.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv_want_transfer_baobei_image03.getWidth());
            intent.putExtra("height", iv_want_transfer_baobei_image03.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
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
        return "宝贝详情";
    }

  /*  @Override
    public boolean setupToolBarRightText(TextView mRightText) {
       *//* if(getIntent().getFlags()==StrConstant.MY_BABY_DETAIL){
            mRightText.setText("修改");
            mRightText.setTextColor(Color.WHITE);
            return true;
        }*//*
        return false;
    }*/
}

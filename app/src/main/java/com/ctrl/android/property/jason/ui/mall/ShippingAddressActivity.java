package com.ctrl.android.property.jason.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.MemberDao;
import com.ctrl.android.property.jason.entity.MemberAddress;
import com.ctrl.android.property.jason.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 添加 收货地址 activity
 * Created by Jason on 2015/10/10.
 * */
public class ShippingAddressActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.tv_shipping_adress_consignee_right)
    TextView tv_shipping_adress_consignee_right;
    @InjectView(R.id.tv_shipping_adress_phonenumber_right)
    TextView tv_shipping_adress_phonenumber_right;
    @InjectView(R.id.tv_shipping_adress_postalcode_right)
    TextView tv_shipping_adress_postalcode_right;
    @InjectView(R.id.tv_shipping_adress_userarea_right)
    TextView tv_shipping_adress_userarea_right;
    @InjectView(R.id.tv_shipping_adress_streetroad_right)
    TextView tv_shipping_adress_streetroad_right;
    @InjectView(R.id.tv_shipping_adress_detailedadress_right)
    TextView tv_shipping_adress_detailedadress_right;
    @InjectView(R.id.tv_shippping_adress_deleteshippingadress)
    TextView tv_shippping_adress_deleteshippingadress;
    @InjectView(R.id.management_shipping_adress_btn)
    Button management_shipping_adress_btn;


    private List<MemberAddress>listMap=new ArrayList();
    private String TITLE= StrConstant.SHIPPING_ADDRESS;
    private MemberDao dao;
    private Bundle bundle;
    private MemberAddress memberAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_adress);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        tv_shippping_adress_deleteshippingadress.setOnClickListener(this);
        management_shipping_adress_btn.setOnClickListener(this);
        dao=new MemberDao(this);
        /*dao.requestMemberAddressList(AppHolder.getInstance().getMemberInfo().getMemberId(), "");*/
        bundle = getIntent().getExtras();
        memberAddress=(MemberAddress)bundle.getSerializable("mMemberAddress");
        tv_shipping_adress_consignee_right.setText(memberAddress.getReceiveName());
        tv_shipping_adress_phonenumber_right.setText(memberAddress.getMobile());
        tv_shipping_adress_userarea_right.setText(memberAddress.getProvinceName()+memberAddress.getCityName()+memberAddress.getAreaName());
        tv_shipping_adress_streetroad_right.setText(memberAddress.getStreetName());
        tv_shipping_adress_detailedadress_right.setText(memberAddress.getAddress());
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==3){
            MessageUtils.showShortToast(ShippingAddressActivity.this,"地址设置成功");
        }
        if(requestCode==4){
            MessageUtils.showShortToast(ShippingAddressActivity.this,"删除地址成功");
        }
    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.green_arrow_left_none);
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
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("修改");
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShippingAddressActivity.this,ModifyShippingAddressActivity.class);
                intent.addFlags(1000);
                intent.putExtras(bundle);
                startActivity(intent);
                AnimUtil.intentSlidIn(ShippingAddressActivity.this);
                finish();
            }
        });
        return true;
    }

        @Override
        public void onClick(View v) {
            if(v==tv_shippping_adress_deleteshippingadress){
                dao.requestMemberAddressDelete(memberAddress.getReceiveAddressId());
                startActivity(new Intent(ShippingAddressActivity.this, ChooseShippingAddressActivity.class));
                finish();
            }
            if(v==management_shipping_adress_btn){
                dao.requestMemberAddressDefault(AppHolder.getInstance().getMemberInfo().getMemberId(),memberAddress.getReceiveAddressId());
                startActivity(new Intent(ShippingAddressActivity.this,ChooseShippingAddressActivity.class));
                finish();

            }


        }

}


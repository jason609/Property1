package com.ctrl.android.property.eric.ui.mall;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.ViewUtil;
import com.ctrl.android.property.weixin.WeixinPayUtil;
import com.ctrl.android.property.wxapi.WXPayEntryActivity;
import com.ctrl.android.property.zhifubao.AliplyPayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 支付方式 activity
 * Created by Eric on 2015/9/22.
 */
public class PayStyleActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.company_txt)
    TextView company_txt;

    @InjectView(R.id.zhi_fu_bao_btn)//支付宝按钮
    LinearLayout zhi_fu_bao_btn;
    @InjectView(R.id.zhi_fu_bao_checkBox)//支付宝checkBox
    CheckBox zhi_fu_bao_checkBox;

    @InjectView(R.id.wei_xin_btn)//微信按钮
    LinearLayout wei_xin_btn;
    @InjectView(R.id.wei_xin_checkBox)//微信checkBox
    CheckBox wei_xin_checkBox;

    @InjectView(R.id.pay_amount)//
    TextView pay_amount;

    @InjectView(R.id.pay_btn)
    TextView pay_btn;

//    @InjectView(R.id.yin_lian_btn)//银联按钮
//    LinearLayout yin_lian_btn;
//    @InjectView(R.id.yin_lian_checkBox)//银联checkBox
//    CheckBox yin_lian_checkBox;



    private String TITLE = StrConstant.PAY_STYLE_TITLE;

    private String cartIdStr;
    private double totalPrice;
    private String companyName;

    private List<CheckBox> listCheckBox ;

    private MallDao mallDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.pay_style_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        cartIdStr = getIntent().getStringExtra("cartIdStr");
        totalPrice = Double.parseDouble(getIntent().getStringExtra("totalPrice"));
        companyName = getIntent().getStringExtra("companyName");

        Log.d("demo","companyName: " + companyName);

        pay_btn.setOnClickListener(this);

        listCheckBox = new ArrayList<>();
        listCheckBox.add(zhi_fu_bao_checkBox);
        listCheckBox.add(wei_xin_checkBox);
        //listCheckBox.add(yin_lian_checkBox);

        zhi_fu_bao_checkBox.setChecked(true);

        zhi_fu_bao_btn.setOnClickListener(this);
        zhi_fu_bao_checkBox.setOnClickListener(this);
        wei_xin_btn.setOnClickListener(this);
        wei_xin_checkBox.setOnClickListener(this);
        //yin_lian_btn.setOnClickListener(this);
        //yin_lian_checkBox.setOnClickListener(this);

//        if(AppHolder.getInstance().getPayType().getId() == 0){
//            //zhi_fu_bao_checkBox.setChecked(true);
//            ViewUtil.setOneChecked(listCheckBox, zhi_fu_bao_checkBox);
//        } else {
//            //wei_xin_checkBox.setChecked(true);
//            ViewUtil.setOneChecked(listCheckBox, wei_xin_checkBox);
//        }
        company_txt.setText(S.getStr(companyName));
        pay_amount.setText("￥ " + String.valueOf(totalPrice));



    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(13 == requestCode){
            for(int i = 0 ; i < listCheckBox.size() ; i ++){
                //支付宝支付
                if(listCheckBox.get(i) == zhi_fu_bao_checkBox){
                    if(zhi_fu_bao_checkBox.isChecked()){
                        AliplyPayUtil aliplyPayUtil = new AliplyPayUtil(PayStyleActivity.this, new AliplyPayUtil.PayStateListener() {
                            @Override
                            public void doAfterAliplyPay(boolean isSuccess) {
                                if (isSuccess) {
                                    MessageUtils.showShortToast(PayStyleActivity.this,"支付成功");
                                    finish();
                                } else {
                                    MessageUtils.showShortToast(PayStyleActivity.this,"支付失败");
                                    finish();
                                }
                            }
                        });
                        /***   此处金额 写了0.01元  实际情况具体问题具体分析   ***/
                        aliplyPayUtil.pay(cartIdStr, Constant.ALIPLY_URL, "诚信行商城商品", "诚信行物业社区商城商城商品", totalPrice);
                    }
                }

                //微信支付
                if(listCheckBox.get(i) == wei_xin_checkBox){
                    if(wei_xin_checkBox.isChecked()){

                        //MessageUtils.showLongToast(this, "key值尚未申请, 支付暂未开通");

                        WeixinPayUtil weixinPayUtil = new WeixinPayUtil(this);
                        WXPayEntryActivity.setPayStateListener(new WXPayEntryActivity.PayStateListener() {
                            @Override
                            public void doAfterWeixinPay(int payStatus) {

                                if (payStatus == Constant.PAY_STATUS_SUCCESS) {
                                    MessageUtils.showLongToast(PayStyleActivity.this, "支付成功");
                                    finish();
                                    //showProgress(true);
                                    //orderDao.requestAfterOrderPay_weixin(orderIdStr, totalCost, payStatus_X, payMent, dealType);
                                } else if (payStatus == Constant.PAY_STATUS_FAILED) {
                                    MessageUtils.showLongToast(PayStyleActivity.this, "支付失败");
                                    finish();
                                    //goToOrderDetail();
                                } else if (payStatus == Constant.PAY_STATUS_CANCLE) {
                                    MessageUtils.showLongToast(PayStyleActivity.this, "支付被取消");
                                    finish();
                                    //goToOrderDetail();
                                }
                            }
                        });
                        //(int)Double.parseDouble(totalPrice)*100
                        weixinPayUtil.pay(cartIdStr, Constant.NOTICE_URL,"诚信行物业社区商城商城商品", (int)(totalPrice*100));
                    }
                }
            }
        }

    }

    @Override
    public void onClick(View v) {

        if(v == zhi_fu_bao_btn || v == zhi_fu_bao_checkBox){
            ViewUtil.setOneChecked(listCheckBox, zhi_fu_bao_checkBox);
            //MessageUtils.showLongToast(this, "key值尚未申请, 支付暂未开通");
            //AppHolder.getInstance().setPayType(new PayType(0, "支付宝支付"));
            //finish();

//            AliplyPayUtil aliplyPayUtil = new AliplyPayUtil(PayStyleActivity.this, new AliplyPayUtil.PayStateListener() {
//                @Override
//                public void doAfterAliplyPay(boolean isSuccess) {
//                    if (isSuccess) {
//                        MessageUtils.showShortToast(PayStyleActivity.this,"支付成功");
//                        finish();
//                    } else {
//                        MessageUtils.showShortToast(PayStyleActivity.this,"支付失败");
//                    }
//                }
//            });
//            /***   此处金额 写了0.01元  实际情况具体问题具体分析   ***/
//            aliplyPayUtil.pay("orderId", Constant.ALIPLY_URL, "诚信行商城商品", "诚信行物业社区商城商城商品", 0.01);

        }

        if(v == wei_xin_btn || v == wei_xin_checkBox){
            ViewUtil.setOneChecked(listCheckBox, wei_xin_checkBox);
            //MessageUtils.showLongToast(this, "key值尚未申请, 支付暂未开通");

//            WeixinPayUtil weixinPayUtil = new WeixinPayUtil(this);
//            WXPayEntryActivity.setPayStateListener(new WXPayEntryActivity.PayStateListener() {
//                @Override
//                public void doAfterWeixinPay(int payStatus) {
//
//                    if (payStatus == Constant.PAY_STATUS_SUCCESS) {
//                        MessageUtils.showLongToast(PayStyleActivity.this,"支付成功");
//                        //showProgress(true);
//                        //orderDao.requestAfterOrderPay_weixin(orderIdStr, totalCost, payStatus_X, payMent, dealType);
//
//                    } else if (payStatus == Constant.PAY_STATUS_FAILED) {
//                        MessageUtils.showLongToast(PayStyleActivity.this, "支付失败");
//                        //goToOrderDetail();
//                    } else if (payStatus == Constant.PAY_STATUS_CANCLE) {
//                        MessageUtils.showLongToast(PayStyleActivity.this, "支付被取消");
//                        //goToOrderDetail();
//                    }
//                }
//            });
//            //(int)Double.parseDouble(totalPrice)*100
//            weixinPayUtil.pay("orderId", Constant.NOTICE_URL,"诚信行物业社区商城商城商品", 1);

            //AppHolder.getInstance().setPayType(new PayType(1, "微信支付"));
            //finish();
        }

//        if(v == yin_lian_btn || v == yin_lian_checkBox){
//            ViewUtil.setOneChecked(listCheckBox,yin_lian_checkBox);
//        }

        if(v == pay_btn){
            mallDao = new MallDao(this);
            showProgress(true);
            mallDao.requestBeforePay(AppHolder.getInstance().getMemberInfo().getMemberId(),cartIdStr);
        }

    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.green_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //MessageUtils.showShortToast(PayStyleActivity.this,"返回");
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
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }
}

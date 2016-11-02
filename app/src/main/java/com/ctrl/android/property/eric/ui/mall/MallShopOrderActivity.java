package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.OrderDao;
import com.ctrl.android.property.eric.entity.OrderAddress;
import com.ctrl.android.property.eric.entity.OrderPro;
import com.ctrl.android.property.eric.ui.adapter.MallShopOrderProListAdapter;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.jason.ui.mall.ChooseShippingAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城的订单页面 activity
 * Created by Eric on 2015/10/10.
 */
public class MallShopOrderActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.scrollView)//滑动页面
    ScrollView scrollView;
    @InjectView(R.id.order_no_address_layout)//选择收货地址(没有地址时)
    LinearLayout order_no_address_layout;
    @InjectView(R.id.order_choose_address_layout)//选择收货地址
    LinearLayout order_choose_address_layout;

    @InjectView(R.id.receiver_and_mobile)//收货人和电话
    TextView receiver_and_mobile;
    @InjectView(R.id.order_address)//收货地址
    TextView order_address;

    @InjectView(R.id.listView)//商品列表
    ListView listView;

    @InjectView(R.id.pay_online_btn)//在线支付
    TextView pay_online_btn;
    @InjectView(R.id.pay_get_goods_btn)//货到付款
    TextView pay_get_goods_btn;

    //@InjectView(R.id.zhifu_fangshi_txt)//
    //TextView zhifu_fangshi_txt;
    //@InjectView(R.id.pay_style_layout)//支付方式按钮
    //LinearLayout pay_style_layout;

    @InjectView(R.id.order_amount)
    TextView order_amount;
    @InjectView(R.id.submit_btn)//确认
    TextView submit_btn;

    private int payType = 1;//1:在线支付 ; 2:货到付款

    private String TITLE = StrConstant.COMFIRM_ORDER_TITLE;
    private MallShopOrderProListAdapter mallShopOrderProListAdapter;

    private OrderDao orderDao;

    private String cartIdStr = "";
    /**商品数量*/
    private int productSum;
    /**商品总金额*/
    private double totalPrice;
    /**商品列表*/
    private List<OrderPro> listOrderPro = new ArrayList<>();
    /**订单 地址*/
    private OrderAddress orderAddress = new OrderAddress();
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_order_activity);
        ButterKnife.inject(this);
        //AppHolder.getInstance().setPayType(new PayType(0, "支付宝支付"));
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        cartIdStr = getIntent().getStringExtra("cartIdStr");
        Log.d("demo", "cartIdStr: " + cartIdStr);

        //pay_style_layout.setOnClickListener(this);
        order_choose_address_layout.setOnClickListener(this);
        submit_btn.setOnClickListener(this);

        pay_online_btn.setOnClickListener(this);
        pay_get_goods_btn.setOnClickListener(this);

        if(payType == 1){
            pay_online_btn.setTextColor(getResources().getColor(R.color.dark_green));
            pay_online_btn.setBackgroundResource(R.drawable.green_border_shap);
            pay_get_goods_btn.setTextColor(getResources().getColor(R.color.text_gray));
            pay_get_goods_btn.setBackgroundResource(R.drawable.gray_border_shap2);
        } else if(payType == 2){
            pay_online_btn.setTextColor(getResources().getColor(R.color.text_gray));
            pay_online_btn.setBackgroundResource(R.drawable.gray_border_shap2);
            pay_get_goods_btn.setTextColor(getResources().getColor(R.color.dark_green));
            pay_get_goods_btn.setBackgroundResource(R.drawable.green_border_shap);
        }

        orderDao = new OrderDao(this);
        showProgress(true);
        orderDao.requestFillOrder(AppHolder.getInstance().getMemberInfo().getMemberId(),cartIdStr);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(AppHolder.getInstance().getPayType() == null){
            //
        } else {
            //zhifu_fangshi_txt.setText(AppHolder.getInstance().getPayType().getName());
        }

        if(AppHolder.getInstance().getOrderAddress() == null){
            order_no_address_layout.setVisibility(View.VISIBLE);
            order_choose_address_layout.setVisibility(View.GONE);
        } else {
            order_no_address_layout.setVisibility(View.GONE);
            order_choose_address_layout.setVisibility(View.VISIBLE);
            receiver_and_mobile.setText("收货人: " + AppHolder.getInstance().getOrderAddress().getReceiveName() + "   " + AppHolder.getInstance().getOrderAddress().getMobile());
            order_address.setText(S.getStr(AppHolder.getInstance().getOrderAddress().getReceiveAddress()));
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            productSum = orderDao.getProductSum();
            totalPrice = orderDao.getTotalPrice();
            listOrderPro = orderDao.getListOrderPro();
            orderAddress = orderDao.getOrderAddress();

            if(orderAddress != null && orderAddress.getAddressId() != null){
                order_no_address_layout.setVisibility(View.GONE);
                order_choose_address_layout.setVisibility(View.VISIBLE);

                AppHolder.getInstance().setOrderAddress(orderAddress);
                //receiver_and_mobile.setText("收货人: " + orderAddress.getReceiveName() + "   " + orderAddress.getMobile());
                //order_address.setText(S.getStr(orderAddress.getReceiveAddress()));
                receiver_and_mobile.setText("收货人: " + AppHolder.getInstance().getOrderAddress().getReceiveName() + "   " + AppHolder.getInstance().getOrderAddress().getMobile());
                order_address.setText(S.getStr(AppHolder.getInstance().getOrderAddress().getReceiveAddress()));

            } else {
                order_no_address_layout.setVisibility(View.VISIBLE);
                order_choose_address_layout.setVisibility(View.GONE);
            }

            order_amount.setText("¥" + N.toPriceFormate(totalPrice));

            mallShopOrderProListAdapter = new MallShopOrderProListAdapter(this);
            mallShopOrderProListAdapter.setListCartPro(listOrderPro);
            listView.setAdapter(mallShopOrderProListAdapter);

            scrollView.smoothScrollTo(10, 10);//设置scrollview的起始位置在顶部

        }

        if(1 == requestCode){

            if(payType == 1){
                //0：未分单 1：已分单
                if(orderDao.getSeparateStatus() == 1){
                    MessageUtils.showLongToast(this, "订单已分单, 请到订单列表支付");
                    Intent intent = new Intent(MallShopOrderActivity.this, OrderListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopOrderActivity.this);
                } else {
                    //MessageUtils.showShortToast(this,"去结算");
                    //MessageUtils.showShortToast(this,AppHolder.getInstance().getPayType().getName() + "" + AppHolder.getInstance().getOrderAddress().getReceiveName());
                    companyName = S.getStr(orderDao.getCompanyName());
                    String orderId = S.getStr(orderDao.getOrderIdStr());
                    Intent intent = new Intent(MallShopOrderActivity.this, PayStyleActivity.class);
                    intent.putExtra("cartIdStr",orderId);
                    Log.d("demo","orderId: " + orderId);
                    intent.putExtra("totalPrice",String.valueOf(totalPrice));
                    intent.putExtra("companyName", companyName);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopOrderActivity.this);
                }
            } else {
                Intent intent = new Intent(MallShopOrderActivity.this, OrderListActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MallShopOrderActivity.this);
            }

            finish();


        }

    }

    @Override
    public void onClick(View v) {

//        if(v == pay_style_layout){
//            Intent intent = new Intent(MallShopOrderActivity.this, PayStyleActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MallShopOrderActivity.this);
//        }

        if(v == pay_online_btn){
//            if(payType == 1){
//
//                pay_online_btn.setTextColor(getResources().getColor(R.color.text_gray));
//                pay_online_btn.setBackgroundResource(R.drawable.gray_border_shap2);
//                pay_get_goods_btn.setTextColor(getResources().getColor(R.color.dark_green));
//                pay_get_goods_btn.setBackgroundResource(R.drawable.green_border_shap);
//
//                payType = 2;
//            } else

            if(payType == 2){

                pay_online_btn.setTextColor(getResources().getColor(R.color.dark_green));
                pay_online_btn.setBackgroundResource(R.drawable.green_border_shap);
                pay_get_goods_btn.setTextColor(getResources().getColor(R.color.text_gray));
                pay_get_goods_btn.setBackgroundResource(R.drawable.gray_border_shap2);

                payType = 1;
            }
            //MessageUtils.showShortToast(this,"" + payType);
        }

        if(v == pay_get_goods_btn){
            if(payType == 1){

                pay_online_btn.setTextColor(getResources().getColor(R.color.text_gray));
                pay_online_btn.setBackgroundResource(R.drawable.gray_border_shap2);
                pay_get_goods_btn.setTextColor(getResources().getColor(R.color.dark_green));
                pay_get_goods_btn.setBackgroundResource(R.drawable.green_border_shap);

                payType = 2;
            }
//            else if(payType == 2){
//
//                pay_online_btn.setTextColor(getResources().getColor(R.color.dark_green));
//                pay_online_btn.setBackgroundResource(R.drawable.green_border_shap);
//                pay_get_goods_btn.setTextColor(getResources().getColor(R.color.text_gray));
//                pay_get_goods_btn.setBackgroundResource(R.drawable.gray_border_shap2);
//
//                payType = 1;
//            }
            //MessageUtils.showShortToast(this,"" + payType);
        }

        if(v == order_choose_address_layout){
            Intent intent = new Intent(MallShopOrderActivity.this, ChooseShippingAddressActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MallShopOrderActivity.this);
        }

        if(v == submit_btn){

            String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
            String settleType = "0";//0：购物车结算、1：商品直接结算
            String shoppingCartIdStr = cartIdStr;
            String productId = "";
            String productNum = "0";
            String companyId = "";
            String addressId = orderAddress.getAddressId();
            String sourceType = "1";//订单来源（0：Web、1：Android、2：IOS、3：电话下单、4：其他）
            String payMode = String.valueOf(payType);
            showProgress(true);
            orderDao.requestCreateOrder(memberId,settleType,shoppingCartIdStr,productId,productNum,companyId,addressId,sourceType,payMode);


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
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.member_info_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //toHomePage();
//                MessageUtils.showShortToast(MallShopOrderActivity.this, "XX");
//                Intent intent = new Intent(MallShopOrderActivity.this, PayStyleActivity.class);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(MallShopOrderActivity.this);
//            }
//        });
//        return true;
//    }

}

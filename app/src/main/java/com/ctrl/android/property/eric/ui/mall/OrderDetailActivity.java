package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.OrderDao;
import com.ctrl.android.property.eric.entity.OrderDetail;
import com.ctrl.android.property.eric.entity.OrderDetailItem;
import com.ctrl.android.property.eric.ui.adapter.OrderDetailItemAdapter;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单详细页面 activity
 * Created by Eric on 2015/10/10.
 */
public class OrderDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.scrollView)//滑动页面
    ScrollView scrollView;

    //@InjectView(R.id.order_comment_btn)//评价订单按钮
    //TextView order_comment_btn;
    @InjectView(R.id.order_status)//订单状态
    TextView order_status;
    //@InjectView(R.id.order_comment_text)//订单评论
    //TextView order_comment_text;
    @InjectView(R.id.listView)//商品列表
    ListView listView;

    @InjectView(R.id.order_num)//订单号
    TextView order_num;
    @InjectView(R.id.order_create_time)//下单时间
    TextView order_create_time;
    @InjectView(R.id.order_contactor)//联系人
    TextView order_contactor;
    @InjectView(R.id.order_address)//配送地址
    TextView order_address;
    @InjectView(R.id.order_pay_type)//支付方式
    TextView order_pay_type;
    @InjectView(R.id.order_deal_time)//成交时间
    TextView order_deal_time;

    @InjectView(R.id.comment_btn)//评价按钮
    TextView comment_btn;
    @InjectView(R.id.cancel_btn)//取消订单
    TextView cancel_btn;
    @InjectView(R.id.pay_btn)//支付按钮
    TextView pay_btn;

    @InjectView(R.id.toolbar_title)
    TextView toolbar_title;

    private OrderDetail orderDetail = new OrderDetail();
    private List<OrderDetailItem> listOrderDetailItem = new ArrayList<>();

    private String TITLE;

    private OrderDao orderDao;
    private OrderDetailItemAdapter orderDetailItemAdapter;

    private String orderId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.order_detail_activity);
        ButterKnife.inject(this);
        scrollView.smoothScrollTo(10, 10);//设置scrollview的起始位置在顶部
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        comment_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        pay_btn.setOnClickListener(this);

        orderId = getIntent().getStringExtra("orderId");
        orderDao = new OrderDao(this);
        showProgress(true);
        orderDao.requestOrderDetail(orderId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(3 == requestCode){
            orderDetail = orderDao.getOrderDetail();
            listOrderDetailItem = orderDao.getListOrderDetailItem();

            orderDetailItemAdapter = new OrderDetailItemAdapter(this);
            orderDetailItemAdapter.setList(listOrderDetailItem);
            listView.setAdapter(orderDetailItemAdapter);

            TITLE = orderDetail.getCompanyName();
            toolbar_title.setText(S.getStr(TITLE));
            order_status.setText(S.getOrderStatus(orderDetail.getOrderStatus()));
            order_num.setText("订单号: " + S.getStr(orderDetail.getOrderNum()));
            order_create_time.setText("下单时间: " + D.getDateStrFromStamp("yyyy-MM-dd HH:mm:ss", orderDetail.getCreateTime()));
            order_contactor.setText("联系人: " + S.getStr(orderDetail.getReceiverName()));
            order_address.setText("配送地址: " + S.getStr(orderDetail.getAddress()));
            order_pay_type.setText("支付方式: " + S.getPayTypeStr(orderDetail.getPayMode()));
            order_deal_time.setText("成交时间: " + D.getDateStrFromStamp("yyyy-MM-dd HH:mm:ss", orderDetail.getCreateTime()));
            List<Integer> comment_flg = new ArrayList<>();
            comment_flg.add(6);
            comment_flg.add(7);
            comment_flg.add(8);
            comment_flg.add(9);
            comment_flg.add(10);
            comment_flg.add(11);
            comment_flg.add(12);
            comment_flg.add(13);
            List<Integer> cancel_flg = new ArrayList<>();
            cancel_flg.add(1);
            cancel_flg.add(2);
            List<Integer> pay_flg = new ArrayList<>();
            pay_flg.add(1);

            if(comment_flg.contains(orderDetail.getOrderStatus()) && orderDetail.getEvaluationState() == 0){
                comment_btn.setVisibility(View.VISIBLE);
                comment_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetailActivity.this, OrderCommentActivity.class);
                        //intent.putExtra("title",TITLE);
                        //intent.putExtra("forumPostId",listNote.get(position-1).getForumPostId());
                        intent.putExtra("companyId",orderDetail.getCompanyId());
                        intent.putExtra("orderId",orderDetail.getOrderId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(OrderDetailActivity.this);
                    }
                });
            }

            if(cancel_flg.contains(orderDetail.getOrderStatus())){
                cancel_btn.setVisibility(View.VISIBLE);
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //MessageUtils.showShortToast(OrderDetailActivity.this, "取消");
                        showProgress(true);
                        orderDao.requestCancelOrder(orderDetail.getOrderId());
                    }
                });
            }

            if(pay_flg.contains(orderDetail.getOrderStatus())){
                //支付状态(0:未付款、1:已付款)
                if(orderDetail.getPayStatus() == 0){
                    pay_btn.setVisibility(View.VISIBLE);
                    pay_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            showProgress(true);
                            orderDao.requestBeforePayOrder(AppHolder.getInstance().getMemberInfo().getMemberId(),orderDetail.getOrderId());

                        }
                    });
                }
            }

//            //订单状态为 1 2 3 4 5 时, 不可评价; 评价状态为1时不可评价,0未评价
//            if(comment_flg.contains(orderDetail.getOrderStatus()) || orderDetail.getEvaluationState() == 1){
//                cancel_btn.setVisibility(View.VISIBLE);
//
//                pay_btn.setVisibility(View.VISIBLE);
//                pay_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(OrderDetailActivity.this, PayStyleActivity.class);
//                        intent.putExtra("totalPrice",String.valueOf(orderDetail.getTotalCost()));
//                        intent.putExtra("companyName","商家名称");
//                        startActivity(intent);
//                        AnimUtil.intentSlidIn(OrderDetailActivity.this);
//                    }
//                });
//                //order_comment_btn.setClickable(false);
//                //order_comment_btn.setBackgroundResource(R.drawable.gray_bg_shap);
//                //order_comment_text.setText("不可评价");
//            } else {
//                comment_btn.setVisibility(View.VISIBLE);
//                comment_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(OrderDetailActivity.this, OrderCommentActivity.class);
//                        //intent.putExtra("title",TITLE);
//                        //intent.putExtra("forumPostId",listNote.get(position-1).getForumPostId());
//                        startActivity(intent);
//                        AnimUtil.intentSlidIn(OrderDetailActivity.this);
//                    }
//                });
//            }


        }

        if(4 == requestCode){
            MessageUtils.showShortToast(this,"成功取消订单");
            finish();
        }

        if(5 == requestCode){
            Intent intent = new Intent(OrderDetailActivity.this, PayStyleActivity.class);
            intent.putExtra("cartIdStr",orderDetail.getOrderId());
            intent.putExtra("totalPrice",String.valueOf(orderDetail.getTotalCost()));
            intent.putExtra("companyName", TITLE);
            startActivity(intent);
            AnimUtil.intentSlidIn(OrderDetailActivity.this);
        }

    }

    @Override
    public void onClick(View v) {
        //
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
                onBackPressed();
            }
        });
        return true;
    }

    /**
     * 页面标题
     * */
//    @Override
//    public String setupToolBarTitle() {
//        return TITLE;
//    }


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

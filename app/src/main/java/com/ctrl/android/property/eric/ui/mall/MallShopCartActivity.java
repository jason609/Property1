package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.CartDao;
import com.ctrl.android.property.eric.entity.CartPro;
import com.ctrl.android.property.eric.ui.adapter.MallShopCartProListAdapter;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城购物车 activity
 * Created by Eric on 2015/9/23.
 */
public class MallShopCartActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//商品列表
    ListView listView;

    @InjectView(R.id.check_all)
    CheckBox check_all;
    @InjectView(R.id.cart_amount)
    TextView cart_amount;

    @InjectView(R.id.buy_btn)//结算按钮
    Button buy_btn;

    private String TITLE = StrConstant.CART_TITLE;

    private MallShopCartProListAdapter mallShopCartProListAdapter;
    private CartDao cartDao;
    private List<CartPro> listCartPro = new ArrayList<>();

    private final static int NUM_CHANGED_FLAG = 0;
    private final static int CHECK_CHANGED_FLAG = 1;

    private double amount;
    private List<Integer> listInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_cart_activity);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        buy_btn.setOnClickListener(this);
        check_all.setOnClickListener(this);
        check_all.setChecked(true);

        cartDao = new CartDao(this);
        showProgress(true);
        cartDao.requestCartProList(AppHolder.getInstance().getMemberInfo().getMemberId());


    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(111 == requestCode){
            listCartPro = cartDao.getListCartPro();

            mallShopCartProListAdapter = new MallShopCartProListAdapter(this);
            mallShopCartProListAdapter.setListCartPro(listCartPro);
            listView.setAdapter(mallShopCartProListAdapter);
            /**配置listview*/
            ViewUtil.settingListView(listView);

            cartDao.requestCartProAmount(getCartIdStr(listCartPro));
            buy_btn.setText("结算( " + listInt.size() + " )");
        }

        if(112 == requestCode){
            amount = Double.parseDouble(cartDao.getAmount());
            cart_amount.setText(" ¥" + N.toPriceFormate(amount));
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);

        if(errorNo.equals("001") || errorNo.equals("002")){
            listCartPro = new ArrayList<>();
            mallShopCartProListAdapter = new MallShopCartProListAdapter(this);
            mallShopCartProListAdapter.setListCartPro(listCartPro);
            listView.setAdapter(mallShopCartProListAdapter);
            /**配置listview*/
            ViewUtil.settingListView(listView);
            cart_amount.setText(" ¥ 0.00");
            buy_btn.setText("结算( " + 0 + " )");
        } else {
            MessageUtils.showShortToast(this, errorMessage);
        }

    }



    @Override
    public void onClick(View v) {

        if(v == buy_btn){
            Intent intent = new Intent(MallShopCartActivity.this, MallShopOrderActivity.class);
            intent.putExtra("cartIdStr",getCartIdStr(listCartPro));
            startActivity(intent);
            AnimUtil.intentSlidIn(MallShopCartActivity.this);
        }

        if(v == check_all){

            if(check_all.isChecked()){
                setAllCartProChecked(listCartPro);

                mallShopCartProListAdapter.setListCartPro(listCartPro);
                mallShopCartProListAdapter.notifyDataSetChanged();
                cartDao.requestCartProAmount(getCartIdStr(listCartPro));

                buy_btn.setText("结算( " + listInt.size() + " )");

            } else {
                setAllCartProUnchecked(listCartPro);
                mallShopCartProListAdapter.setListCartPro(listCartPro);
                mallShopCartProListAdapter.notifyDataSetChanged();
                cart_amount.setText(" ¥ 0.00");
                buy_btn.setText("结算(0)");
            }

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
//                MessageUtils.showShortToast(MallShopCartActivity.this, "XX");
//                Intent intent = new Intent(MallShopCartActivity.this, PayStyleActivity.class);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(MallShopCartActivity.this);
//            }
//        });
//        return true;
//    }


    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case NUM_CHANGED_FLAG:

                    //判断所传购物车id的String串是否为空
                    if(getCartIdStr(listCartPro).equals("") || getCartIdStr(listCartPro) == null){
                        check_all.setChecked(false);
                        cart_amount.setText(" ¥" + String.valueOf(0.00));
                    } else {
                        //cartDao.requestAmountCartPros(getCartIdStr(listCartPro));
                        cartDao.requestCartProAmount(getCartIdStr(listCartPro));
                    }

                    buy_btn.setText("结算( " + listInt.size() + " )");

                    break;
                case CHECK_CHANGED_FLAG:

                    if(ifAllChecked(listCartPro)){
                        check_all.setChecked(true);
                    } else {
                        check_all.setChecked(false);
                    }
                    Log.d("demo", "cartid字符串CHECK: " + getCartIdStr(listCartPro));
                    //判断所传购物车id的String串是否为空
                    if(getCartIdStr(listCartPro).equals("") || getCartIdStr(listCartPro) == null){
                        cart_amount.setText(" ¥" + String.valueOf(0.00));
                    } else {
                        cartDao.requestCartProAmount(getCartIdStr(listCartPro));
                    }
                    buy_btn.setText("结算( " + listInt.size() + " )");
                    break;
            }
        }
    };

    public void numChanged(){
        mHandler.sendEmptyMessage(NUM_CHANGED_FLAG);
    }

    public void checkChanged(){
        mHandler.sendEmptyMessage(CHECK_CHANGED_FLAG);
    }

    /**
     * 所有单选按钮  选中
     * */
    private void setAllCartProChecked(List<CartPro> listCartPro){
        if(listCartPro != null){
            for(CartPro c : listCartPro){
                if(c != null){
                    c.setCheck(true);
                }
            }
        }
    }

    /**
     * 所有单选按钮  未选中
     * */
    private void setAllCartProUnchecked(List<CartPro> listCartPro){
        if(listCartPro != null){
            for(CartPro c : listCartPro){
                if(c != null){
                    c.setCheck(false);
                }
            }
        }
    }

    /**
     * 判断 是否所有的单选按钮 都被选中
     * */
    private boolean ifAllChecked(List<CartPro> listCartPro){
        if(listCartPro != null){
            for(CartPro c : listCartPro){
                if(c != null){
                    if(c.isCheck()){

                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取所有选中的 单选按钮cartId的字符串
     * 例子:xxxxx,xxxxxxx,xxxxxx
     * */
    private String getCartIdStr(List<CartPro> listCartPro){
        listInt = new ArrayList<Integer>();
        StringBuilder sbu = new StringBuilder();
        List<String> listStr = new ArrayList<String>();
        int flag = 1;
        if(listCartPro  != null){
            for(int i = 0 ; i < listCartPro.size() ; i ++){
                CartPro c = listCartPro.get(i);
                if(c != null){
                    if(c.isCheck()){
                        listStr.add(c.getShoppingCartId());
                        listInt.add(i);
                    }
                }
            }
            for(int i = 0 ; i < listStr.size() ; i ++){
                String str = listStr.get(i);
                if(listStr.size() <= 1){
                    sbu.append(str);
                } else {
                    if(flag == 1){
                        sbu.append(str);
                        sbu.append(",");
                    } else if(flag == listStr.size()){
                        sbu.append(str);
                    } else {
                        sbu.append(str);
                        sbu.append(",");
                    }
                    flag++;
                }
            }
        }
        return sbu.toString();
    }

}

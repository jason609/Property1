package com.ctrl.android.property.eric.ui.pay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.PropertyPayDao;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.eric.ui.adapter.HousePayAdapter;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费 activity
 * Created by Eric on 2015/10/22
 */
public class HousePayActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.house_pay_history_btn)//缴费记录按钮
    TextView house_pay_history_btn;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.house_owner)
    TextView house_owner;
    @InjectView(R.id.amount_text)
    TextView amount_text;

//    @InjectView(R.id.radio_group)//表头
//    RadioGroup radio_group;
//    @InjectView(R.id.radio_month_one)//最近一月
//    RadioButton radio_month_one;
//    @InjectView(R.id.radio_month_two)//最近二月
//    RadioButton radio_month_two;
//    @InjectView(R.id.radio_month_three)//最近三月
//    RadioButton radio_month_three;

//    @InjectView(R.id.viewpager)//列表内容
//    ViewPager viewpager;

    @InjectView(R.id.house_pay_btn)//
    TextView house_pay_btn;

    private HousePayAdapter housePayAdapter;
    private String TITLE = StrConstant.HOUSE_PAY_TITLE;

    private String communityName;
    private String building_unit_room;
    private String proprietorId;
    private String addressId;

    private double debt;

    private PropertyPayDao propertyPayDao;
    //private List<PropertyPay> listPropertyPay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.house_pay_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        housePayAdapter = new HousePayAdapter(this);

        if(AppHolder.getInstance().getListPropertyPay() != null && AppHolder.getInstance().getListPropertyPay().size() > 0){
            housePayAdapter.setList(AppHolder.getInstance().getListPropertyPay());
            listView.setAdapter(housePayAdapter);
            housePayAdapter.notifyDataSetChanged();
        } else {
            requestData();
        }

    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        AppHolder.getInstance().setListPropertyPay(new ArrayList<PropertyPay>());
        amount_text.setText("0元");

        communityName = getIntent().getStringExtra("communityName");
        building_unit_room = getIntent().getStringExtra("building_unit_room");
        proprietorId = getIntent().getStringExtra("proprietorId");
        addressId = getIntent().getStringExtra("addressId");

        house_owner.setText(communityName + "    " + building_unit_room);

        house_pay_history_btn.setOnClickListener(this);
        house_pay_btn.setOnClickListener(this);

        //propertyPayDao = new PropertyPayDao(this);
        //showProgress(true);
        //propertyPayDao.requestPropertyPayList(proprietorId, addressId, "0", "", "");


    }

    private void requestData(){
        propertyPayDao = new PropertyPayDao(this);
        showProgress(true);
        propertyPayDao.requestPropertyPayList(proprietorId, addressId, "0", "", "");
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){

            AppHolder.getInstance().setListPropertyPay(propertyPayDao.getListPropertyPay());

            //housePayAdapter = new HousePayAdapter(this);
            housePayAdapter.setList(AppHolder.getInstance().getListPropertyPay());
            listView.setAdapter(housePayAdapter);

            listView.setDividerHeight(20);
        }

        if(1 == requestCode){
            debt = propertyPayDao.getDebt();
            amount_text.setText(N.toPriceFormate(debt) + "元");

            housePayAdapter.setList(AppHolder.getInstance().getListPropertyPay());
            listView.setAdapter(housePayAdapter);
            housePayAdapter.notifyDataSetChanged();

            MessageUtils.showShortToast(this, "成功加入结算");
        }

    }

    @Override
    public void onClick(View v) {
        if(v == house_pay_history_btn){
            Intent intent = new Intent(HousePayActivity.this, HousePayHistoryActivity.class);
            intent.putExtra("communityName",communityName);
            intent.putExtra("building_unit_room",building_unit_room);
            intent.putExtra("proprietorId",proprietorId);
            intent.putExtra("addressId",addressId);
            startActivity(intent);
            AnimUtil.intentSlidIn(HousePayActivity.this);
        }

        if(v == house_pay_btn){
            //MessageUtils.showShortToast(this,"结算");

            if(!S.isNull(getStrs(AppHolder.getInstance().getListPropertyPay()))){
                Intent intent = new Intent(HousePayActivity.this, HousePayCartActivity.class);
                intent.putExtra("communityName",communityName);
                intent.putExtra("building_unit_room",building_unit_room);
                startActivity(intent);
                AnimUtil.intentSlidIn(HousePayActivity.this);
            } else {
                MessageUtils.showShortToast(this,"请选择缴费项目");
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
     * 计算 总金额
     * */
    public void calAmount(int position){
        AppHolder.getInstance().getListPropertyPay().get(position).setPayFlg(1);
        //PropertyPay pay = AppHolder.getInstance().getListPropertyPay().get(position);
        //AppHolder.getInstance().getListPropertyPay().add(pay);
        Log.d("demo","ListPropertyPay: " + getStrs(AppHolder.getInstance().getListPropertyPay()));
        String strs = getStrs(AppHolder.getInstance().getListPropertyPay());
        showProgress(true);
        propertyPayDao.requestPropertyPayAmount(strs);
    }

    /**获得物业账单id串*/
    private String getStrs(List<PropertyPay> listPropertyPay){
        String str = "";
        if(listPropertyPay != null && listPropertyPay.size() > 0){
            if(listPropertyPay.size() == 1){
                if(listPropertyPay.get(0).getPayFlg() == 1){
                    str = listPropertyPay.get(0).getPropertyPaymentId();
                }
            } else {
                StringBuilder sb = new StringBuilder();
                List<PropertyPay> list = new ArrayList<>();

                for(int i = 0 ; i < listPropertyPay.size() ; i ++){
                    if(listPropertyPay.get(i).getPayFlg() == 1){
                        list.add(listPropertyPay.get(i));
                    }
                }

                for(int i = 0 ; i < list.size() ; i ++){
                    if(i == (list.size() - 1)){
                        sb.append(list.get(i).getPropertyPaymentId());
                    } else {
                        sb.append(list.get(i).getPropertyPaymentId());
                        sb.append(",");
                    }
                }
                str = sb.toString();
            }
        }
        return str;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppHolder.getInstance().setListPropertyPay(new ArrayList<PropertyPay>());
    }
}

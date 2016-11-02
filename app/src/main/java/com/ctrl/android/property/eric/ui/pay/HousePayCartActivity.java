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
import com.ctrl.android.property.eric.entity.HousePay;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.eric.ui.adapter.HousePayCartAdapter;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费 (购物车) activity
 * Created by Eric on 2015/10/22
 */
public class HousePayCartActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.amount_text)
    TextView amount_text;
    @InjectView(R.id.house_pay_btn)
    TextView house_pay_btn;

    private String communityName;
    private String building_unit_room;

    private HousePayCartAdapter housePayCartAdapter;

    private String TITLE = StrConstant.HOUSE_PAY_TITLE;

    private PropertyPayDao propertyPayDao;
    private List<PropertyPay> listPay = new ArrayList<>();

    private double debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.house_pay_cart_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        amount_text.setText("0元");
        house_pay_btn.setOnClickListener(this);

        communityName = getIntent().getStringExtra("communityName");
        building_unit_room = getIntent().getStringExtra("building_unit_room");

        housePayCartAdapter = new HousePayCartAdapter(this);
        if((AppHolder.getInstance().getListPropertyPay()) != null && (AppHolder.getInstance().getListPropertyPay()).size() > 0){

            for(int i = 0 ; i < AppHolder.getInstance().getListPropertyPay().size() ; i ++){
                if(AppHolder.getInstance().getListPropertyPay().get(i).getPayFlg() == 1){
                    listPay.add(AppHolder.getInstance().getListPropertyPay().get(i));
                }
            }

            housePayCartAdapter.setList(listPay);
        }
        listView.setAdapter(housePayCartAdapter);

        calAmount();

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(1 == requestCode){
            debt = propertyPayDao.getDebt();
            amount_text.setText(N.toPriceFormate(debt) + "元");

        }
    }

    @Override
    public void onClick(View v) {
        if(v == house_pay_btn){
            if(!S.isNull(getStrs(AppHolder.getInstance().getListPropertyPay()))){
                //MessageUtils.showShortToast(this,"去支付");
                Intent intent = new Intent(HousePayCartActivity.this, PayStyleActivity2.class);
                intent.putExtra("orderIds",getStrs(AppHolder.getInstance().getListPropertyPay()));
                intent.putExtra("communityName",communityName);
                intent.putExtra("building_unit_room",building_unit_room);
                intent.putExtra("debt",String.valueOf(debt));
                startActivity(intent);
                AnimUtil.intentSlidIn(HousePayCartActivity.this);
                finish();
            } else {
                MessageUtils.showShortToast(this,"支付项目为空");
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

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(StrConstant.CLEAR);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(HousePayCartActivity.this, "清空");
                //AppHolder.getInstance().setListPropertyPay(new ArrayList<PropertyPay>());

                for(int i = 0 ; i < AppHolder.getInstance().getListPropertyPay().size() ; i ++){
                    AppHolder.getInstance().getListPropertyPay().get(i).setPayFlg(0);
                }

                listPay = new ArrayList<PropertyPay>();
                housePayCartAdapter.setList(listPay);
                listView.setAdapter(housePayCartAdapter);
                housePayCartAdapter.notifyDataSetChanged();

                amount_text.setText("0元");

            }
        });
        return true;
    }

    /**
     * 计算 总金额
     * */
    public void calAmount(){
        propertyPayDao = new PropertyPayDao(this);
        //AppHolder.getInstance().getListPropertyPay().get(position).setPayFlg(1);
        //PropertyPay pay = AppHolder.getInstance().getListPropertyPay().get(position);
        //AppHolder.getInstance().getListPropertyPay().add(pay);
        Log.d("demo", "ListPropertyPay: " + getStrs(AppHolder.getInstance().getListPropertyPay()));
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

    /**
     * 测试 获取数据的方法
     * */
    private List<HousePay> getList(){
        List<HousePay> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            HousePay p = new HousePay();
            p.setCheck(true);
            p.setName(i + "中润世纪小区");
            p.setAddress("15楼-1单元-20" + i + "室");

            List<Map<String,String>> l = new ArrayList<>();
            for(int j = 0 ; j < 5 ; j ++){
                Map<String,String> map = new HashMap<>();
                map.put("check","" + (i%2));
                map.put("name",i + "水费");
                map.put("amount","102." + i + "654");
                l.add(map);
            }
            p.setListMap(l);
            list.add(p);
        }
        return list;
    }



}

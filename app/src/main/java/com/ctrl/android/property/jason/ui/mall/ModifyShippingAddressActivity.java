package com.ctrl.android.property.jason.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.MemberDao;
import com.ctrl.android.property.jason.entity.MemberAddress;
import com.ctrl.android.property.jason.ui.city.CityPicker;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ModifyShippingAddressActivity extends AppToolBarActivity implements View.OnClickListener {
  @InjectView(R.id.et_shipping_adress_userarea)
   TextView et_shipping_adress_userarea;//所在地区

  @InjectView(R.id.citypicker_layout)
    LinearLayout citypicker_layout;

  @InjectView(R.id.et_shipping_adress_consignee)
    EditText et_shipping_adress_consignee;
  @InjectView(R.id.et_shipping_adress_phonenumber)
    EditText et_shipping_adress_phonenumber;
    @InjectView(R.id.et_shipping_adress_detailedadress)
    EditText et_shipping_adress_detailedadress;
    @InjectView(R.id.et_shipping_adress_streetroad)
    EditText et_shipping_adress_streetroad;
    @InjectView(R.id.et_shipping_adress_postalcode)
    EditText et_shipping_adress_postalcode;

    private View mMenuView;
    private CityPicker cityPicker;
    private Button superman_ok_btn;
    private Button superman_cancel_btn;
    String province;
    String city;
    String couny;
    private MemberDao dao;
    private MemberAddress memberAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.activity_modify_shipping_address);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        dao=new MemberDao(this);
        et_shipping_adress_userarea.setOnClickListener(this);
        if(getIntent().getFlags()==1000) {
            Bundle bundle = getIntent().getExtras();
            memberAddress = (MemberAddress) bundle.getSerializable("mMemberAddress");
            et_shipping_adress_consignee.setText(memberAddress.getReceiveName());
            et_shipping_adress_phonenumber.setText(memberAddress.getMobile());
            et_shipping_adress_userarea.setText(memberAddress.getProvinceName() + memberAddress.getCityName() + memberAddress.getAreaName());
            et_shipping_adress_streetroad.setText(memberAddress.getStreetName());
            et_shipping_adress_detailedadress.setText(memberAddress.getAddress());
        }
    }

    @Override
    public void onClick(View v) {
        if(v==et_shipping_adress_userarea){
            //阻止输入法弹出
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_shipping_adress_userarea.getWindowToken(), 0);
            showPopupBottom();
        }



    }

    private void showPopupBottom() {
        mMenuView = LayoutInflater.from(this).inflate(R.layout.choose_city_bottom_pop, null);
        cityPicker = (CityPicker)mMenuView.findViewById(R.id.cityPicker);
        superman_ok_btn = (Button)mMenuView.findViewById(R.id.superman_ok_btn);
        superman_cancel_btn = (Button)mMenuView.findViewById(R.id.superman_cancel_btn);
        final PopupWindow Pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        Pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        Pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        Pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        Pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        Pop.setBackgroundDrawable(dw);


        superman_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(AddAddressActivity.this,"确定" + cityPicker.getCity_string());
                et_shipping_adress_userarea.setText(cityPicker.getCity_string());
                province = cityPicker.getProvince_Name();
                city = cityPicker.getCity_Name();
                couny = cityPicker.getCouny_Name();
                Pop.dismiss();
            }
        });

        superman_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        Pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        Pop.setFocusable(true);
        Pop.showAtLocation(citypicker_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode ==2){
            if(getIntent().getFlags()==1000){
                dao.requestMemberAddressDelete(memberAddress.getReceiveAddressId());

            }else {
                MessageUtils.showShortToast(this, "地址添加成功");
                startActivity(new Intent(ModifyShippingAddressActivity.this, ChooseShippingAddressActivity.class));
                AnimUtil.intentSlidIn(ModifyShippingAddressActivity.this);
                finish();
            }
        }
        if(requestCode==4){
            MessageUtils.showShortToast(this, "地址修改成功");
            startActivity(new Intent(ModifyShippingAddressActivity.this, ChooseShippingAddressActivity.class));
            AnimUtil.intentSlidIn(ModifyShippingAddressActivity.this);
            finish();
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
        return "收货地址";
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("保存");
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (checkInput()) {
                            String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                            String longitude = AppHolder.getInstance().getBdLocation().getLongitude() + "";
                            String latitude = AppHolder.getInstance().getBdLocation().getLatitude() + "";
                            //  String provinece_name = cityPicker.getProvince_Name().substring(0, cityPicker.getProvince_Name().indexOf("省"));
                            // String provinece_name = cityPicker.getProvince_Name();
                            // String city_name = cityPicker.getCity_Name().substring(0, cityPicker.getCity_Name().indexOf("市"));
                            //String city_name = cityPicker.getCity_Name();
                            // String couny_name = cityPicker.getCouny_Name().substring(0, cityPicker.getCouny_Name().indexOf("区"));
                            //  String couny_name = cityPicker.getCouny_Name();
                            String detailedadress = et_shipping_adress_detailedadress.getText().toString();
                            String phonenumber = et_shipping_adress_phonenumber.getText().toString();
                            String consignee = et_shipping_adress_consignee.getText().toString();

                            dao.requestMemberAddressAdd(memberId, longitude, latitude, province, city, couny, "", detailedadress, "", phonenumber, consignee);
                        }
                    }
        });
        return true;
    }

    /**校验输入的文本*/
    public boolean checkInput(){
        if(TextUtils.isEmpty(et_shipping_adress_consignee.getText().toString())){
            MessageUtils.showShortToast(this,"请输入收货人姓名");
            return false;
        }
        if(TextUtils.isEmpty(et_shipping_adress_phonenumber.getText().toString())){
            MessageUtils.showShortToast(this,"请输入手机号");
            return false;
        }
        if(TextUtils.isEmpty(et_shipping_adress_userarea.getText().toString())){
            MessageUtils.showShortToast(this,"请选择省、市、区");
            return false;
        }
        if(TextUtils.isEmpty(et_shipping_adress_detailedadress.getText().toString())){
            MessageUtils.showShortToast(this,"请输入手机号");
            return false;
        }

        return true;
    }


}

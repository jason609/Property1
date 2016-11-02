package com.ctrl.android.property.eric.ui.pay;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.CommunityDao;
import com.ctrl.android.property.eric.dao.ProprietorDao;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.eric.ui.adapter.ListCommunityAdapter;
import com.ctrl.android.property.eric.ui.adapter.ListItemAdapter;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.jason.ui.city.CityPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 家人认证 activity
 * Created by Eric on 2015/10/22
 */
public class HouseConfirmActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.mall_main_pop_layout)
    LinearLayout mall_main_pop_layout;

    @InjectView(R.id.owner_name)//户主姓名
    EditText owner_name;
    @InjectView(R.id.owner_mobile)//户主手机
    EditText owner_mobile;
    @InjectView(R.id.owner_P_c_d)//省市区
    EditText owner_P_c_d;
    @InjectView(R.id.owner_community)//小区
    TextView owner_community;

    @InjectView(R.id.building_btn)//楼栋/弄
    LinearLayout building_btn;
    @InjectView(R.id.building)//楼栋/弄
    TextView building;
    @InjectView(R.id.unit_btn)//单元/号
    LinearLayout unit_btn;
    @InjectView(R.id.unit)//楼栋/弄
    TextView unit;
    @InjectView(R.id.room_btn)//门牌/房号
    LinearLayout room_btn;
    @InjectView(R.id.room)//楼栋/弄
    TextView room;

    @InjectView(R.id.confirm_btn)//认证按钮
    Button confirm_btn;

    private int flg = 0;//flg为0时可以显示小区列表

    private View mMenuView;//显示pop的view

    private ProprietorDao proprietorDao;

    private CommunityDao communityDao;
    private List<Community> listCommunity;
    private ListCommunityAdapter listCommunityAdapter;
    private ListItemAdapter listItemAdapter;

    private String communityId = "";
    private String building_num = "";
    private String unit_num = "";
    private String room_num = "";

    private List<String> listBuilding = new ArrayList<>();
    private List<String> listUnit = new ArrayList<>();
    private List<String> listRoom = new ArrayList<>();

    //private View mMenuView;
    private CityPicker cityPicker;
    private Button superman_ok_btn;
    private Button superman_cancel_btn;
    String province = "";
    String city = "";
    String couny = "";

    private String TITLE = StrConstant.FAMILY_CONFIRM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.house_confirm_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        building_btn.setOnClickListener(this);
        unit_btn.setOnClickListener(this);
        room_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        confirm_btn.setBackgroundResource(R.drawable.orange_bg_shap);

        owner_P_c_d.setOnClickListener(this);

        //confirm_btn.setClickable(false);

        owner_community.setOnClickListener(this);

       /* owner_community.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {

                    String content = owner_community.getText().toString();

                    owner_community.setText("");
                    //int length = content.length();

                    //content.endsWith("-");

                }

                return false;

            }

        });*/

//        owner_name.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                if (checkInput2()) {
//                    confirm_btn.setBackgroundResource(R.drawable.orange_bg_shap);
//                    confirm_btn.setClickable(true);
//                } else {
//                    confirm_btn.setBackgroundResource(R.drawable.gray_bg_shap);
//                    confirm_btn.setClickable(false);
//                }
//
//            }
//        });
//
//        owner_mobile.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                if (checkInput2()) {
//                    confirm_btn.setBackgroundResource(R.drawable.orange_bg_shap);
//                    confirm_btn.setClickable(true);
//                } else {
//                    confirm_btn.setBackgroundResource(R.drawable.gray_bg_shap);
//                    confirm_btn.setClickable(false);
//                }
//
//            }
//        });

        owner_community.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

                if (flg == 0) {
                    communityDao = new CommunityDao(HouseConfirmActivity.this);

                    if(S.isNull(province) || S.isNull(city) || S.isNull(couny)){
                        MessageUtils.showShortToast(HouseConfirmActivity.this,"请选择省市区");
                    } else {
                        String provinceName = province;
                        String cityName = city;
                        String areaName = couny;
                        String keyword = owner_community.getText().toString();
                        Log.d("demo", "keyword: " + keyword);
                        communityDao.requestCommunityList(provinceName, cityName, areaName, keyword);
                    }

                } else {
                    flg = 0;
                }


            }
        });

//        room.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                if(checkInput()){
//                    confirm_btn.setBackgroundResource(R.drawable.orange_bg_shap);
//                    confirm_btn.setClickable(true);
//                } else {
//                    confirm_btn.setBackgroundResource(R.drawable.gray_bg_shap);
//                    confirm_btn.setClickable(false);
//                }
//
//            }
//        });



    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(requestCode == 0) {
            listCommunity = communityDao.getListCommunity();

            if(listCommunity != null && listCommunity.size() > 0){

                if(S.isNull(communityId) && (!S.isNull(owner_community.getText().toString()))){

                    for(int i = 0 ; i < listCommunity.size() ; i ++){
                        Community community = listCommunity.get(i);
                        if((owner_community.getText().toString()).equals(community.getCommunityName())){
                            communityId = community.getId();
                            break;
                        }
                    }

                }

                showCommunityListPop(listCommunity);

            }

        }

        if(requestCode == 1) {
            listBuilding = communityDao.getListBuilding();
            if(listBuilding != null || listBuilding.size() > 0){
                showBuildingListPop();
            }
        }

        if(requestCode == 2) {
            listUnit = communityDao.getListUnit();
            if(listUnit != null || listUnit.size() > 0){
                showUnitListPop();
            }
        }

        if(requestCode == 3) {
            listRoom = communityDao.getListRoom();
            if(listRoom != null || listRoom.size() > 0){
                showRoomListPop();
            }
        }

        if(requestCode == 4) {

            proprietorDao = new ProprietorDao(this);
            showProgress(true);
            String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
            String currentPage = "";
            String rowCountPerPage = "";
            proprietorDao.requestHouseList(memberId, currentPage, rowCountPerPage);

            MessageUtils.showShortToast(this,"提交成功");
            //finish();
        }

        if(98 == requestCode){

            proprietorDao = new ProprietorDao(this);
            showProgress(true);
            proprietorDao.requestProprietorInfo(AppHolder.getInstance().getCommunity().getId(), AppHolder.getInstance().getMemberInfo().getMemberId());

        }

        if(99 == requestCode){
            finish();
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        //super.onRequestFaild(errorNo, errorMessage);
        if(errorNo.equals("002")){
            //
        } else {
            MessageUtils.showShortToast(this,errorMessage);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == owner_community){
            communityDao = new CommunityDao(HouseConfirmActivity.this);

            if(S.isNull(province) || S.isNull(city) || S.isNull(couny)){
                MessageUtils.showShortToast(HouseConfirmActivity.this,"请选择省市区");
            } else {
                String provinceName = province;
                String cityName = city;
                String areaName = couny;
                String keyword = "";
                Log.d("demo", "keyword: " + keyword);
                communityDao.requestCommunityList(provinceName, cityName, areaName, keyword);
            }
        }

        if(v == building_btn){

            if(communityId == null || communityId.equals("")){
                MessageUtils.showShortToast(this,"请选择小区");
                unit.setText("");
                room.setText("");
            } else {
                showProgress(true);
                communityDao.requestBuildingList(communityId);
            }

        }

        if(v == unit_btn){
            if(communityId == null || communityId.equals("")){
                MessageUtils.showShortToast(this,"请选择小区");
                room.setText("");
            } else {
                building_num = building.getText().toString();
                if(building_num == null || building_num.equals("") || building_num.equals("请选择")){
                    MessageUtils.showShortToast(this,"请选择楼号");
                } else {
                    showProgress(true);
                    communityDao.requestUnitList(communityId, building_num);
                }

            }
        }

        if(v == room_btn){
            if(communityId == null || communityId.equals("")){
                MessageUtils.showShortToast(this,"请选择小区");
            } else {
                building_num = building.getText().toString();
                if(building_num == null || building_num.equals("") || building_num.equals("请选择")){
                    MessageUtils.showShortToast(this,"请选择楼号");
                } else {
                    unit_num = unit.getText().toString();
                    if(unit_num == null || unit_num.equals("") || unit_num.equals("请选择")){
                        MessageUtils.showShortToast(this,"请选择单元号");
                    } else {
                        showProgress(true);
                        communityDao.requestRoomList(communityId,building_num,unit_num);
                    }

                }

            }
        }

        if(v == confirm_btn){
            if(checkInput()){
                //MessageUtils.showShortToast(this,"提交");
                String communityId_arg = communityId;
                String memberId_arg = AppHolder.getInstance().getMemberInfo().getMemberId();
                String name_arg = owner_name.getText().toString();
                String mobile_arg = owner_mobile.getText().toString();
                String building_arg = building.getText().toString();
                String unit_arg = unit.getText().toString();
                String room_arg = room.getText().toString();
                showProgress(true);
                communityDao.requestProprietyVerify(communityId_arg,memberId_arg,name_arg,mobile_arg,building_arg,unit_arg,room_arg);
            }
        }

        if(v == owner_P_c_d){
            showPopupBottom();
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
     * header 右侧文本
     * */
//    public boolean setupToolBarRightText(TextView mRightText) {
//
//        mRightText.setText(StrConstant.ADD);
//        mRightText.setTextColor(getResources().getColor(R.color.text_white));
//        mRightText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(HouseConfirmActivity.this,"添加");
//            }
//        });
//
//        return true;
//    }

    /**
     * 验证 输入信息
     * */
    private boolean checkInput(){

        if(S.isNull(owner_name.getText().toString())){
            MessageUtils.showShortToast(this,"业主姓名不可为空");
            return false;
        }

        if(S.isNull(owner_mobile.getText().toString())){
            MessageUtils.showShortToast(this,"业主电话不可为空");
            return false;
        }

        if(S.isNull(owner_P_c_d.getText().toString())){
            MessageUtils.showShortToast(this,"省市区不可为空");
            return false;
        }

        if(S.isNull(owner_community.getText().toString())){
            MessageUtils.showShortToast(this,"小区不可为空");
            return false;
        }

        if(S.isNull(building.getText().toString())){
            MessageUtils.showShortToast(this,"楼号不可为空");
            return false;
        }

        if(S.isNull(unit.getText().toString())){
            MessageUtils.showShortToast(this,"单元号不可为空");
            return false;
        }

        if(S.isNull(room.getText().toString())){
            MessageUtils.showShortToast(this,"房号不可为空");
            return false;
        }

        return true;
    }

    /**
     * 验证 输入信息
     * */
    private boolean checkInput2(){

        if(S.isNull(owner_name.getText().toString())){
            //MessageUtils.showShortToast(this,"业主姓名不可为空");
            return false;
        }

        if(S.isNull(owner_mobile.getText().toString())){
            //MessageUtils.showShortToast(this,"业主电话不可为空");
            return false;
        }

        if(S.isNull(owner_P_c_d.getText().toString())){
            //MessageUtils.showShortToast(this,"省市区不可为空");
            return false;
        }

        if(S.isNull(owner_community.getText().toString())){
            //MessageUtils.showShortToast(this,"小区不可为空");
            return false;
        }

        if(S.isNull(building.getText().toString())){
            //MessageUtils.showShortToast(this,"楼号不可为空");
            return false;
        }

        if(S.isNull(unit.getText().toString())){
            //MessageUtils.showShortToast(this,"单元号不可为空");
            return false;
        }

        if(S.isNull(room.getText().toString())){
            //MessageUtils.showShortToast(this,"房号不可为空");
            return false;
        }

        return true;
    }

    /**
     * 显示小区列表的 popwindow
     * */
    private void showCommunityListPop(final List<Community> list){

        listCommunityAdapter = new ListCommunityAdapter(this);
        listCommunityAdapter.setList(list);

        mMenuView = LayoutInflater.from(HouseConfirmActivity.this).inflate(R.layout.choose_list_pop, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(listCommunityAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(owner_community.getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(HouseConfirmActivity.this, listYear.get(position));
                flg = 1;
                owner_community.setText(S.getStr(list.get(position).getCommunityName()));
                communityId = list.get(position).getId();
                pop.dismiss();
            }
        });

        int[] location = new int[2];
        owner_community.getLocationOnScreen(location);
        //Pop.showAtLocation(year_btn, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());
        pop.showAsDropDown(owner_community);
        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示楼号列表的 popwindow
     * */
    private void showBuildingListPop(){

        listItemAdapter = new ListItemAdapter(this);
        listItemAdapter.setList((ArrayList<String>)listBuilding);

        mMenuView = LayoutInflater.from(HouseConfirmActivity.this).inflate(R.layout.choose_list_pop2, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(listItemAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(HouseConfirmActivity.this, listYear.get(position));
                //owner_community.setText(S.getStr(list.get(position).getCommunityName()));
                //communityId = listBuilding.get(position).getId();
                building.setText(listBuilding.get(position));
                pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);
        pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示单元号列表的 popwindow
     * */
    private void showUnitListPop(){

        listItemAdapter = new ListItemAdapter(this);
        listItemAdapter.setList((ArrayList<String>)listUnit);

        mMenuView = LayoutInflater.from(HouseConfirmActivity.this).inflate(R.layout.choose_list_pop2, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(listItemAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(HouseConfirmActivity.this, listYear.get(position));
                //owner_community.setText(S.getStr(list.get(position).getCommunityName()));
                //communityId = listBuilding.get(position).getId();
                unit.setText(listUnit.get(position));
                pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);
        pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示房号列表的 popwindow
     * */
    private void showRoomListPop(){

        listItemAdapter = new ListItemAdapter(this);
        listItemAdapter.setList((ArrayList<String>)listRoom);

        mMenuView = LayoutInflater.from(HouseConfirmActivity.this).inflate(R.layout.choose_list_pop2, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(listItemAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(HouseConfirmActivity.this, listYear.get(position));
                //owner_community.setText(S.getStr(list.get(position).getCommunityName()));
                //communityId = listBuilding.get(position).getId();
                room.setText(listRoom.get(position));
                pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);
        pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 显示省市区pop
     * */
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
                owner_P_c_d.setText(cityPicker.getCity_string());
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
        Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }



}

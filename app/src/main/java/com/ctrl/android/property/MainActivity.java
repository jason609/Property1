package com.ctrl.android.property;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.MainDao;
import com.ctrl.android.property.eric.dao.ProprietorDao;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.eric.entity.Proprietor;
import com.ctrl.android.property.eric.ui.act.ActListActivity;
import com.ctrl.android.property.eric.ui.adapter.ListCommunityAdapter;
import com.ctrl.android.property.eric.ui.easy.EasyActivity;
import com.ctrl.android.property.eric.ui.express.ExpressActivity;
import com.ctrl.android.property.eric.ui.forum.ForumActivity;
import com.ctrl.android.property.eric.ui.house.HouseListActivity2;
import com.ctrl.android.property.eric.ui.mall.MallMainActivity;
import com.ctrl.android.property.eric.ui.mall.MallShopCartActivity;
import com.ctrl.android.property.eric.ui.my.MyActivity;
import com.ctrl.android.property.eric.ui.my.RegesteActivity;
import com.ctrl.android.property.eric.ui.notice.NoticeListActivity;
import com.ctrl.android.property.eric.ui.pay.HouseListActivity;
import com.ctrl.android.property.eric.ui.shop.ShopAroundActivity;
import com.ctrl.android.property.eric.ui.survey.InfoMainActivity;
import com.ctrl.android.property.eric.ui.visit.MyVisitActivity;
import com.ctrl.android.property.eric.ui.widget.CustomDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.UiUtil;
import com.ctrl.android.property.jason.ui.famillyhotline.FamillyHotLineListActivity;
import com.ctrl.android.property.jpush.ExampleUtil;
import com.ctrl.android.property.update.UpdateUtil;

import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 主页面
 * Created by Eric on 2015/9/22.
 */
public class MainActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.mall_main_pop_layout)
    RelativeLayout mall_main_pop_layout;

    @InjectView(R.id.now_community_name)//小区名称
    TextView now_community_name;
    //@InjectView(R.id.change_community_btn)//切换小区
    //TextView change_community_btn;

    @InjectView(R.id.btn_community_mall)//社区商城
    TextView btn_community_mall;
    @InjectView(R.id.btn_community_notice)//社区公告
    TextView btn_community_notice;
    @InjectView(R.id.btn_community_activity)//社区活动
    TextView btn_community_activity;
    @InjectView(R.id.btn_property_pay)//物业缴费
    TextView btn_property_pay;
    @InjectView(R.id.btn_complain_and_repair)//投诉及报修
    TextView btn_complain_and_repair;
    @InjectView(R.id.btn_appointment)//预约访客
    TextView btn_appointment;
    @InjectView(R.id.btn_easy_service)//便民服务
    TextView btn_easy_service;
    @InjectView(R.id.btn_communicate)//即时通讯
    TextView btn_communicate;
    @InjectView(R.id.btn_community_forum)//社区论坛
    TextView btn_community_forum;
    @InjectView(R.id.btn_community_info)//社区分类信息
    TextView btn_community_info;
    @InjectView(R.id.btn_shop_arround)//周边商家
    TextView btn_shop_arround;
    @InjectView(R.id.btn_express_receive)//快递代收
    TextView btn_express_receive;

    @InjectView(R.id.menu_main_page_btn)//footer 首页
    RelativeLayout menu_main_page_btn;
    @InjectView(R.id.menu_main_page_btn_img)//首页图标
    ImageView menu_main_page_btn_img;
    @InjectView(R.id.menu_main_page_btn_text)//首页文本
    TextView menu_main_page_btn_text;
    @InjectView(R.id.menu_service_btn)//footer 商家
    RelativeLayout menu_service_btn;
    @InjectView(R.id.menu_cart_btn)//footer 购物车
    RelativeLayout menu_cart_btn;
    @InjectView(R.id.menu_my_btn)//footer 我的
    RelativeLayout menu_my_btn;

    private View mMenuView;//显示pop的view
    private ListCommunityAdapter communityAdapter;
    private List<Community> listCommunity;

    private MainDao mainDao;
    private ProprietorDao proprietorDao;
    private String TITLE = StrConstant.MAIN_PAGE;

    private long waitTime = 2000;
    private long touchTime = 0;

    private int HANDLER_SHOW_POP = 0;


    /**极光推送 配置*/
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.ctrl.android.property.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);
        init();

        /**启动更新apk的 handler*/
        mHandler.sendEmptyMessage(HANDLER_SHOW_POP);

    }

    @Override
    protected void onResume() {

        isForeground = true;
        //JPushInterface.onResume(this);

        super.onResume();

        if(AppHolder.getInstance().getCommunity() != null && AppHolder.getInstance().getCommunity().getId() != null){
            now_community_name.setText("当前小区: " + S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));
        } else {
            MessageUtils.showShortToast(this,"没有获取到小区");
        }
//        init();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        //JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void init(){

//        if(AppHolder.getInstance().getCommunity() == null){
//            MessageUtils.showShortToast(this,"没有获取到小区");
//        }

        initJpush();

//        Intent intent = new Intent(MainActivity.this,JpushService.class);
//        startService(intent);

        initBtnLictener();

        menu_main_page_btn_img.setImageDrawable(getResources().getDrawable(R.drawable.menu_main_page_icon_press));
        menu_main_page_btn_text.setTextColor(getResources().getColor(R.color.text_green));

        //now_community_name.setText("当前小区: " + S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));

        //mainDao = new MainDao(this);
        //showProgress(true);
        //mainDao.requestTest();

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            //MessageUtils.showShortToast(this, "请求成功");
            listCommunity = mainDao.getListCommunity();
            showCommunityListPop(listCommunity);
        }

        if(99 == requestCode){
            now_community_name.setText("当前小区: " + S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));
            //AppHolder.getInstance().setProprietor(proprietorDao.getProprietor());
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);

        if(errorNo.equals("009")){
            now_community_name.setText("当前小区: " + S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));
            AppHolder.getInstance().setProprietor(new Proprietor());
        }

    }

    @Override
    public void onClick(View v) {

        if (v == now_community_name) {
            //MessageUtils.showShortToast(this,"切换小区");
            mainDao = new MainDao(this);
            String provinceName = AppHolder.getInstance().getBdLocation().getProvince();
            String cityName = AppHolder.getInstance().getBdLocation().getCity();
            String areaName = AppHolder.getInstance().getBdLocation().getDistrict();
            String keyword = "";
            showProgress(true);
            mainDao.requestCommunityList(provinceName, cityName, areaName, keyword);
        }

        //社区商城
        if (v == btn_community_mall) {
            //0:正常用户; 1:游客
//            if(AppHolder.getInstance().getVisiterFlg() == 1){
//                showMemberDialog();
//            } else if(AppHolder.getInstance().getVisiterFlg() == 0){
//                if(AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())){
//                    showPropertiorDialog();
//                }
//            } else {
            Intent intent = new Intent(MainActivity.this, MallMainActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MainActivity.this);
//            }
        }
        //社区公告
        if (v == btn_community_notice) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //社区活动
        if (v == btn_community_activity) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, ActListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //物业缴费
        if (v == btn_property_pay) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, HouseListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //投诉及报修
        if (v == btn_complain_and_repair) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, FamillyHotLineListActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //预约访客
        if (v == btn_appointment) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, MyVisitActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //便民服务
        if (v == btn_easy_service) {
            //0:正常用户; 1:游客
//            if(AppHolder.getInstance().getVisiterFlg() == 1){
//                showMemberDialog();
//            } else if(AppHolder.getInstance().getVisiterFlg() == 0){
//                if(AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())){
//                    showPropertiorDialog();
//                }
//            } else {
            Intent intent = new Intent(MainActivity.this, EasyActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MainActivity.this);
//            }
        }
        //即时通讯
        if (v == btn_communicate) {

            MessageUtils.showShortToast(this,"尚未开通, 敬请期待");

            //0:正常用户; 1:游客
//            if (AppHolder.getInstance().getVisiterFlg() == 1) {
//                showMemberDialog();
//            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
//                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
//                    showPropertiorDialog();
//                } else {
//                    Intent intent = new Intent(MainActivity.this, FamilyHotlineActivity.class);
//                    startActivity(intent);
//                    AnimUtil.intentSlidIn(MainActivity.this);
//                }
//            }
        }
        //社区论坛
        if (v == btn_community_forum) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //社区分类信息
        if (v == btn_community_info) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, InfoMainActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }
        //周边商家
        if (v == btn_shop_arround) {
            //0:正常用户; 1:游客
//            if(AppHolder.getInstance().getVisiterFlg() == 1){
//                showMemberDialog();
//            } else if(AppHolder.getInstance().getVisiterFlg() == 0){
//                if(AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())){
//                    showPropertiorDialog();
//                }
//            } else {
            Intent intent = new Intent(MainActivity.this, ShopAroundActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MainActivity.this);
//            }
        }
        //快递代收
        if (v == btn_express_receive) {
            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                    showPropertiorDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, ExpressActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                }
            }
        }

//        if (v == btn_community_mall) {
//            //MessageUtils.showShortToast(this, "社区商城");
//            Intent intent = new Intent(MainActivity.this, MallMainActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MainActivity.this);
//        }

        if (v == menu_main_page_btn) {
            MessageUtils.showShortToast(this, "主页");
        }

        if (v == menu_service_btn) {
            //MessageUtils.showShortToast(this,"服务");
            Intent intent = new Intent(MainActivity.this, EasyActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MainActivity.this);
        }

        if (v == menu_cart_btn) {
            //MessageUtils.showShortToast(this,"购物车");

            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else {
                Intent intent = new Intent(MainActivity.this, MallShopCartActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MainActivity.this);
            }

//            Intent intent = new Intent(MainActivity.this, MallShopCartActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MainActivity.this);
        }

        if (v == menu_my_btn) {
            //MessageUtils.showShortToast(this,"我的");

            //0:正常用户; 1:游客
            if (AppHolder.getInstance().getVisiterFlg() == 1) {
                showMemberDialog();
            } else if (AppHolder.getInstance().getVisiterFlg() == 0) {
                //if (AppHolder.getInstance().getProprietor() == null || S.isNull(AppHolder.getInstance().getProprietor().getProprietorId())) {
                //    showPropertiorDialog();
                //} else {
                    Intent intent = new Intent(MainActivity.this, MyActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MainActivity.this);
                //}
            }

//            Intent intent = new Intent(MainActivity.this, MyActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MainActivity.this);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                //System.exit(0);
                MyApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.toolbar_back);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtils.showShortToast(MainActivity.this, "返回");
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
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.toolbar_home);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomePage();
            }
        });
        return true;
    }

    /**
     * 显示小区列表的 popwindow
     * */
    private void showCommunityListPop(final List<Community> list){

        communityAdapter = new ListCommunityAdapter(this);
        communityAdapter.setList(list);

        mMenuView = LayoutInflater.from(MainActivity.this).inflate(R.layout.choose_list_pop2, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(communityAdapter);

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
                Community c = list.get(position);
                AppHolder.getInstance().setCommunity(c);

                //proprietorDao = new ProprietorDao(MainActivity.this);
                //showProgress(true);
                //proprietorDao.requestProprietorInfo(AppHolder.getInstance().getCommunity().getId(), AppHolder.getInstance().getMemberInfo().getMemberId());

                now_community_name.setText("当前小区: " + S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));
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
     * 初始化中设置 每个text按钮的监听
     * */
    private void initBtnLictener(){
//        UiUtil.clickToActivity(btn_community_mall, MainActivity.this, MallMainActivity.class);
//        UiUtil.clickToActivity(btn_community_notice, MainActivity.this, NoticeListActivity.class);
//        UiUtil.clickToActivity(btn_community_activity, MainActivity.this, ActListActivity.class);
//        UiUtil.clickToActivity(btn_property_pay, MainActivity.this, HouseListActivity.class);
//        UiUtil.clickToActivity(btn_complain_and_repair, MainActivity.this, FamillyHotLineListActivity.class);
//        UiUtil.clickToActivity(btn_appointment, MainActivity.this, MyVisitActivity.class);
//        UiUtil.clickToActivity(btn_easy_service, MainActivity.this, EasyActivity.class);
//        UiUtil.clickToActivity(btn_communicate, MainActivity.this, FamilyHotlineActivity.class);
//        UiUtil.clickToActivity(btn_community_forum, MainActivity.this, ForumActivity.class);
//        UiUtil.clickToActivity(btn_community_info, MainActivity.this, SecondHandActivity.class);
//        UiUtil.clickToActivity(btn_shop_arround, MainActivity.this, ShopAroundActivity.class);
//        UiUtil.clickToActivity(btn_express_receive, MainActivity.this, ExpressActivity.class);

        btn_community_mall.setOnClickListener(this);
        btn_community_notice.setOnClickListener(this);
        btn_community_activity.setOnClickListener(this);
        btn_property_pay.setOnClickListener(this);
        btn_complain_and_repair.setOnClickListener(this);
        btn_appointment.setOnClickListener(this);
        btn_easy_service.setOnClickListener(this);
        btn_communicate.setOnClickListener(this);
        btn_community_forum.setOnClickListener(this);
        btn_community_info.setOnClickListener(this);
        btn_shop_arround.setOnClickListener(this);
        btn_express_receive.setOnClickListener(this);

        //UiUtil.clickToActivity(menu_main_page_btn, MainActivity.this, MainActivity.class);
        UiUtil.clickToActivity(menu_service_btn, MainActivity.this, MallMainActivity.class);
        //UiUtil.clickToActivity(menu_cart_btn, MainActivity.this, MallShopCartActivity.class);
        //UiUtil.clickToActivity(menu_my_btn, MainActivity.this, MyActivity.class);

        now_community_name.setOnClickListener(this);
        menu_cart_btn.setOnClickListener(this);
        menu_my_btn.setOnClickListener(this);

    }

    private void showMemberDialog(){
        final CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("游客尚未注册,是否注册");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, RegesteActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MainActivity.this);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void showPropertiorDialog(){
        final CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("未找到业主信息,请添加房屋");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MessageUtils.showShortToast(LoginActivity.this, "未找到业主信息,请添加房屋");
                Intent intent = new Intent(MainActivity.this, HouseListActivity2.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MainActivity.this);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void initJpush(){
        JPushInterface.init(getApplicationContext());

        if(AppHolder.getInstance().getMemberInfo() == null || S.isNull(AppHolder.getInstance().getMemberInfo().getMemberId())){
            //
        } else {
            JPushInterface.setAlias(this,
                    AppHolder.getInstance().getMemberInfo().getMemberId(),
                    new TagAliasCallback(){
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            //MessageUtils.showShortToast(MainActivity.this,"" + i);
                            Log.d("demo","Alias: " + AppHolder.getInstance().getMemberInfo().getMemberId());
                        }
                    });
        }
        registerMessageReceiver();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg){
        MessageUtils.showShortToast(this, msg);
    }


    /**
     * 更新程序 handler
     * */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
           super.handleMessage(msg);
            if(msg.what == HANDLER_SHOW_POP){
               //while(!isLoginFinish){//执行空循环
                //}
                showProgress(false);
                if(!UpdateUtil.checkVersionByCode(MainActivity.this, AppHolder.getInstance().getVersionInfo(), false, true)){
                }
             //  chooseAreaBottomPop.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);//设置layout在PopupWindow中显示的位置
            }
        }
    };

}

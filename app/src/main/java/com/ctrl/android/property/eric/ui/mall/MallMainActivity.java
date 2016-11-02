package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.AdDao;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.Ad;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.ui.adapter.MallShopListAdapter;
import com.ctrl.android.property.eric.ui.adapter.ProductPicPagerAdapter;
import com.ctrl.android.property.eric.util.ViewUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城的主页面 activity
 * Created by Eric on 2015/9/23.
 */
public class MallMainActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.toolbar_title)//标题
    TextView toolbar_title;

    @InjectView(R.id.mall_main_pop_layout)//
    LinearLayout mall_main_pop_layout;

    @InjectView(R.id.mall_main_ad)//广告图片
    RelativeLayout mall_main_ad;
    @InjectView(R.id.mall_main_ad_close_cross)//关闭广告 X 号
    ImageView mall_main_ad_close_cross;
    @InjectView(R.id.viewpager)//商品图片
    ViewPager viewpager;
    @InjectView(R.id.indicator)//指示器
    CirclePageIndicator indicator;

    @InjectView(R.id.mall_food_btn)//美食
    LinearLayout mall_food_btn;
    @InjectView(R.id.mall_movie_btn)//电影
    LinearLayout mall_movie_btn;
    @InjectView(R.id.mall_takeout_btn)//外卖
    LinearLayout mall_takeout_btn;
    @InjectView(R.id.mall_hotel_btn)//酒店
    LinearLayout mall_hotel_btn;

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;
    private View mMenuView;//显示pop的view
    private MallShopListAdapter mAdapter;

    private MallDao mallDao;

    private String provinceName = AppHolder.getInstance().getAddress().getProvince();
    private String cityName = AppHolder.getInstance().getAddress().getCity();
    private String areaName = AppHolder.getInstance().getAddress().getDistrict();
    private Double longitude = AppHolder.getInstance().getAddress().getLongitude();
    private Double latitude = AppHolder.getInstance().getAddress().getLatitude();
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    private List<MallShop> listMallShop;

    private String TITLE = AppHolder.getInstance().getBdLocationMall().getAddrStr();

    private List<Ad> listAd = new ArrayList<>();
    private AdDao adDao;

    private ProductPicPagerAdapter picAdapter;
    ArrayList<String> listPics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_main_activity);
        ButterKnife.inject(this);

        //AppHolder.getInstance().getAddress().setName(TITLE);
        //AppHolder.getInstance().getAddress().setLatitude(latitude);
        //AppHolder.getInstance().getAddress().setLongitude(longitude);

        //init();

        adDao = new AdDao(this);
        adDao.requestAddList("PPT_MALL_HOME_TOP");

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

        //longitude = AppHolder.getInstance().getBdLocationMall().getLongitude();
        //latitude = AppHolder.getInstance().getBdLocationMall().getLatitude();
        //TITLE = AppHolder.getInstance().getBdLocationMall().getAddrStr();

        provinceName = AppHolder.getInstance().getAddress().getProvince();
        cityName = AppHolder.getInstance().getAddress().getCity();
        areaName = AppHolder.getInstance().getAddress().getDistrict();
        longitude = AppHolder.getInstance().getAddress().getLongitude();
        latitude = AppHolder.getInstance().getAddress().getLatitude();
        TITLE = AppHolder.getInstance().getAddress().getName();

        //AppHolder.getInstance().getAddress().setName(TITLE);
        //AppHolder.getInstance().getAddress().setLatitude(latitude);
        //AppHolder.getInstance().getAddress().setLongitude(longitude);

        //Log.d("demo", "TITLE: " + TITLE);
        toolbar_title.setText(TITLE);

        Log.d("demo", "Name: " + AppHolder.getInstance().getAddress().getName());
        Log.d("demo","Address: " + AppHolder.getInstance().getAddress().getAddress());
        Log.d("demo","Latitude: " + AppHolder.getInstance().getAddress().getLatitude());
        Log.d("demo","Longitude: " + AppHolder.getInstance().getAddress().getLongitude());
        Log.d("demo","Province: " + AppHolder.getInstance().getAddress().getProvince());
        Log.d("demo","City: " + AppHolder.getInstance().getAddress().getCity());
        Log.d("demo", "District: " + AppHolder.getInstance().getAddress().getDistrict());

        toolbar_title.setOnClickListener(this);

        mall_main_ad.setOnClickListener(this);
        mall_main_ad_close_cross.setOnClickListener(this);

        mall_food_btn.setOnClickListener(this);
        mall_movie_btn.setOnClickListener(this);
        mall_takeout_btn.setOnClickListener(this);
        mall_hotel_btn.setOnClickListener(this);

        mAdapter = new MallShopListAdapter(this);
        mListView = mPullToRefreshListView.getRefreshableView();

        //adDao = new AdDao(this);
        //showProgress(true);
        //adDao.requestAddList("PPT_MALL_HOME_TOP");

        mallDao = new MallDao(this);
        showProgress(true);
        mallDao.requestShopArroundList(provinceName, cityName, areaName, String.valueOf(longitude), String.valueOf(latitude), String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        Log.d("demo", "codeSuccess: " + mallDao.code);

        if(0 == requestCode) {

            listMallShop = mallDao.getListShopArround();

            //mListView = mPullToRefreshListView.getRefreshableView();
            //mAdapter = new MallShopListAdapter(this);
            mAdapter.setList(listMallShop);
            mListView.setAdapter(mAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    mallDao.getListShopArround().clear();
                    mallDao.requestShopArroundList(provinceName, cityName, areaName, String.valueOf(longitude), String.valueOf(latitude), String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    mallDao.requestShopArroundList(provinceName, cityName, areaName, String.valueOf(longitude), String.valueOf(latitude), String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MallMainActivity.this, MallShopMainActivity.class);
                    intent.putExtra("companyId",listMallShop.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallMainActivity.this);

                }
            });

            mallDao.code = 1000;

        }

        if(103 == requestCode){
            listAd = adDao.getListAd();

            if(listAd != null && listAd.size() > 0){
                for(int i = 0 ; i < listAd.size() ; i ++){
                    listPics.add(listAd.get(i).getImgUrl());
                }
                /**商品图片Viewpager的配置*/
                picAdapter = new ProductPicPagerAdapter(getSupportFragmentManager(),listPics);
                viewpager.setAdapter(picAdapter);
                viewpager.setOffscreenPageLimit(listPics.size() == 0 ? 1 : listPics.size() + 2);
                viewpager.setCurrentItem(0);
                /**配置viewpager的指示器*/
                //ViewUtil.settingIndicator(indicator, viewpager);
                indicator.setViewPager(viewpager);
                indicator.setRadius(5);
                indicator.setPageColor(0xffcccccc);
                indicator.setFillColor(0xff4ab9e7);

            } else {
                mall_main_ad.setVisibility(View.GONE);
            }


        }

    }

    /**
     * 数据请求失败后
     * */
    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();
        Log.d("demo","codeFaild: " + mallDao.code);

        if(errorNo.equals("002")){

            //Log.d("demo","XXXXXXXXXXXX>>>>>>>>>>>>");
            if(mallDao.code == 0){
                listMallShop = new ArrayList<>();
                mAdapter.setList(listMallShop);
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mallDao.code = 1000;
            }

            if(listAd != null && listAd.size() > 0){
                for(int i = 0 ; i < listAd.size() ; i ++){
                    listPics.add(listAd.get(i).getImgUrl());
                }
                /**商品图片Viewpager的配置*/
                picAdapter = new ProductPicPagerAdapter(getSupportFragmentManager(),listPics);
                viewpager.setAdapter(picAdapter);
                viewpager.setOffscreenPageLimit(listPics.size() == 0 ? 1 : listPics.size() + 2);
                viewpager.setCurrentItem(0);
                /**配置viewpager的指示器*/
                ViewUtil.settingIndicator(indicator, viewpager);

            } else {
                mall_main_ad.setVisibility(View.GONE);
            }
        } else {
            MessageUtils.showShortToast(this, errorMessage);
        }



    }

    @Override
    public void onClick(View v) {

        if(v == toolbar_title){
            Intent intent = new Intent(MallMainActivity.this, MallLocateActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MallMainActivity.this);
        }

        //美食
        if(v == mall_food_btn){
            //mAdapter.setList(getListMap("美食"));
            Intent intent = new Intent(MallMainActivity.this, MallShopListActivity.class);
            intent.putExtra("kind2","美食");
            startActivity(intent);
            AnimUtil.intentSlidIn(MallMainActivity.this);
        }

        //电影
        if(v == mall_movie_btn){
            //mAdapter.setList(getListMap("电影"));
            Intent intent = new Intent(MallMainActivity.this, MallShopListActivity.class);
            intent.putExtra("kind2","电影");
            startActivity(intent);
            AnimUtil.intentSlidIn(MallMainActivity.this);
        }

        //外卖
        if(v == mall_takeout_btn){
            //mAdapter.setList(getListMap("外卖"));
            Intent intent = new Intent(MallMainActivity.this, MallShopListActivity.class);
            intent.putExtra("kind2","外卖");
            startActivity(intent);
            AnimUtil.intentSlidIn(MallMainActivity.this);
        }

        //酒店
        if(v == mall_hotel_btn){
            //mAdapter.setList(getListMap("酒店"));
            Intent intent = new Intent(MallMainActivity.this, MallShopListActivity.class);
            intent.putExtra("kind2","酒店");
            startActivity(intent);
            AnimUtil.intentSlidIn(MallMainActivity.this);
        }

        //广告图片
        if(v == mall_main_ad){
            MessageUtils.showShortToast(this,"广告图片");
        }

        //关闭广告的 X 号
        if(v == mall_main_ad_close_cross){
            mall_main_ad.setVisibility(View.GONE);
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
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.search_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MallMainActivity.this, MallSearchActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MallMainActivity.this);
            }
        });
        return true;
    }

    /**
     * 排序方式下拉  列表
     * */
    private void showSortStyleListPop(){
        //setRoomData();
        mMenuView = LayoutInflater.from(MallMainActivity.this).inflate(R.layout.choose_sort_style_top_pop, null);

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
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        Pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        Pop.setFocusable(true);

        int[] location = new int[2];
        mall_main_pop_layout.getLocationOnScreen(location);
        Pop.showAtLocation(mall_main_pop_layout, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());

        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 店铺分类下拉  列表
     * */
    private void showShopStyleListPop(){
        //setRoomData();
        mMenuView = LayoutInflater.from(MallMainActivity.this).inflate(R.layout.choose_shop_style_top_pop, null);

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
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        Pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        Pop.setFocusable(true);

        int[] location = new int[2];
        mall_main_pop_layout.getLocationOnScreen(location);
        Pop.showAtLocation(mall_main_pop_layout, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());

        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("pic","aa" + i);
            map.put("name",str + "商家名称商家名称商家名称商家名称商家名称商家名称" + i);
            map.put("time","1" + i + ": 00 - " + "1" + i + ": 00");
            map.put("rate","" + (i*(0.5)));
            list.add(map);
        }
        return list;
    }

}

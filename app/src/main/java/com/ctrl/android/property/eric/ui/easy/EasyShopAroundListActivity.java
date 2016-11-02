package com.ctrl.android.property.eric.ui.easy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.ShopArround_Easy;
import com.ctrl.android.property.eric.ui.adapter.EasyShopArroundListAdapter;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 便民商家列表 activity
 * Created by Eric on 2015/10/20
 */
public class EasyShopAroundListActivity extends AppToolBarActivity implements View.OnClickListener,OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    /**经度*/
    private double longitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLongitude();
    /**纬度*/
    private double latitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLatitude();
    /**搜索关键字*/
    private String keyword = "";
    /**检索经纬度坐标*/
    private LatLng location = new LatLng(latitude,longitude);
    /**每页容量*/
    private int pageCapacity = Constant.PAGE_CAPACITY;
    /**分页编号*/
    private int pageNum = Constant.DEFAULT_PAGE_NUM;
    /**检索半径 单位:米*/
    private int radius = Constant.SEARCH_RADIUS;
    /**检索结果排序规则*/
    private PoiSortType sortType = PoiSortType.distance_from_near_to_far;

    /**搜索入口类, 定义此类开始搜索 */
    private PoiSearch mPoiSearch = null;
    /**建议查询接口*/
    private SuggestionSearch mSuggestionSearch = null;
    /**定义百度地图对象的操作方法与接口*/

    private String TITLE = StrConstant.EASY_SHOP_ARROUND_TITLE;
    /**本页所显示的主题关键字*/
    private String key_word_arg = "";

    private List<ShopArround_Easy> listShop;

    private ListView mListView;

    private EasyShopArroundListAdapter easyShopArroundListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.easy_shop_arround_list_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        /**初始化搜索模块*/
        mPoiSearch = PoiSearch.newInstance();
        /**注册搜索事件监听*/
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        /**获取建议检索实例*/
        mSuggestionSearch = SuggestionSearch.newInstance();
        /**设置建议请求结果监听器*/
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        listShop = new ArrayList<ShopArround_Easy>();

        key_word_arg = getIntent().getStringExtra(Constant.ARG_FLG);
        if(!S.isNull(key_word_arg)){
            keyword = key_word_arg;
            startSearch();
        }

        settingListView();
    }

    /**
     * 启动检索的方法
     * */
    private void startSearch(){
        //Log.d("demo", "startSearch");
        /**附近检索参数*/
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword(keyword);
        option.location(location);
        option.pageCapacity(pageCapacity);
        option.pageNum(pageNum);
        option.radius(radius);
        option.sortType(sortType);
        mPoiSearch.searchNearby(option);

//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                //.city("济南")
//                .keyword(keyword)
//                .pageNum(10));


    }

    /**
     * 根据条件搜索结束后 调用此方法
     * */
    public void onGetPoiResult(PoiResult result) {

        Log.d("demo", "onGetPoiResult");

        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            MessageUtils.showShortToast(this, getString(R.string.sorry_find_no_result));
            mPullToRefreshListView.onRefreshComplete();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            for(int i = 0 ; i < result.getAllPoi().size() ; i ++){
                ShopArround_Easy shop = new ShopArround_Easy();
                shop.setShopName(S.getStr(result.getAllPoi().get(i).name));
                shop.setShopAddress(S.getStr(result.getAllPoi().get(i).address));
                shop.setShopPhoneNum(S.getStr(result.getAllPoi().get(i).phoneNum));
                listShop.add(shop);

            }

            if(listShop != null || listShop.size() <= 0 ){
                //settingListView();
                easyShopArroundListAdapter.notifyDataSetChanged();
                mPullToRefreshListView.onRefreshComplete();
//                for(int i = 0 ; i < listShop.size() ; i ++){
//                    Log.d("demo","" + i + " : " + listShop.get(i).getShopName());
//                    Log.d("demo","" + i + " : " + listShop.get(i).getShopAddress());
//                    Log.d("demo","" + i + " : " + listShop.get(i).getShopPhoneNum());
//                }
            } else {
                MessageUtils.showShortToast(this, getString(R.string.no_more_data));
            }


            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            mPullToRefreshListView.onRefreshComplete();

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            MessageUtils.showShortToast(this, strInfo);
            //Toast.makeText(EasyBusActivity.this, strInfo, Toast.LENGTH_LONG).show();
        }

        mPullToRefreshListView.onRefreshComplete();
    }

    /**
     * 配置listView
     * */
    private void settingListView(){
        mListView = mPullToRefreshListView.getRefreshableView();
        easyShopArroundListAdapter = new EasyShopArroundListAdapter(this);
        easyShopArroundListAdapter.setList(listShop);
        mListView.setAdapter(easyShopArroundListAdapter);
        mListView.setDivider(null);
        mListView.setDividerHeight(10);
        //注册上下拉定义事件
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum = Constant.DEFAULT_PAGE_NUM;
                listShop.clear();
                startSearch();
            }
            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum = pageNum + 1;
                //listShop.clear();
                startSearch();
            }
        });

//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				//MessageUtils.showShortToast(EasyBusActivity.this,"XXXXXXXX");
//				MessageUtils.showShortToast(EasyShopAroundListActivity.this, "点击了: " + listShop.get(position - 1).getShopName());
//			}
//		});
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            MessageUtils.showShortToast(this, getString(R.string.sorry_find_no_result));
            mPullToRefreshListView.onRefreshComplete();
        } else {
            MessageUtils.showShortToast(this, result.getName() + ": " + result.getAddress());
        }
        mPullToRefreshListView.onRefreshComplete();
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
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }


    /**
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.white_cross_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(EasyShopAroundActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }





}

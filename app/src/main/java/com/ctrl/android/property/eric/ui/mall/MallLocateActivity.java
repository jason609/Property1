package com.ctrl.android.property.eric.ui.mall;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
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
import com.ctrl.android.property.eric.dao.MapDao;
import com.ctrl.android.property.eric.entity.Address;
import com.ctrl.android.property.eric.ui.adapter.ListAddressAdapter;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 支付方式 activity
 * Created by Eric on 2015/9/22.
 */
public class MallLocateActivity extends AppToolBarActivity implements View.OnClickListener,OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    @InjectView(R.id.search_btn)//
    ImageView search_btn;
    @InjectView(R.id.keyword_text)
    EditText keyword_text;
    @InjectView(R.id.locate_address)//定位地址
    TextView locate_address;

    @InjectView(R.id.listView)//
    ListView listView;

    /**经度*/
    private double longitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLongitude();
    /**纬度*/
    private double latitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLatitude();
    /**搜索关键字*/
    private String keyword = "";
    /**检索经纬度坐标*/
    private LatLng location = new LatLng(latitude,longitude);
    /**每页容量*/
    private int pageCapacity = 100;
    //private int pageCapacity = Constant.PAGE_CAPACITY;
    /**分页编号*/
    //private int pageNum = Constant.DEFAULT_PAGE_NUM;
    private int pageNum = 1;
    /**检索半径 单位:米*/
    private int radius = Constant.SEARCH_RADIUS;
    /**检索结果排序规则*/
    private PoiSortType sortType = PoiSortType.distance_from_near_to_far;

    /**搜索入口类, 定义此类开始搜索 */
    private PoiSearch mPoiSearch = null;
    /**建议查询接口*/
    private SuggestionSearch mSuggestionSearch = null;
    /**定义百度地图对象的操作方法与接口*/


    private ListAddressAdapter listAddressAdapter;

    private String TITLE = StrConstant.LOCATION_TITLE;

    private List<Address> listAddress = new ArrayList<>();

    private MapDao maoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_locate_activity);
        ButterKnife.inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }

    private void init(){

        maoDao = new MapDao(this);

        /**初始化搜索模块*/
        mPoiSearch = PoiSearch.newInstance();
        /**注册搜索事件监听*/
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        /**获取建议检索实例*/
        mSuggestionSearch = SuggestionSearch.newInstance();
        /**设置建议请求结果监听器*/
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

//        startSearch();
//        settingListView();

        if(AppHolder.getInstance().getAddress() == null || S.isNull(AppHolder.getInstance().getAddress().getId())){
            locate_address.setText(S.getStr(AppHolder.getInstance().getAddress().getName()));
        } else {
            locate_address.setText(S.getStr(AppHolder.getInstance().getBdLocationMall().getAddrStr()));
        }

        search_btn.setOnClickListener(this);

//        listAddressAdapter = new ListAddressAdapter(this);
//        listAddressAdapter.setList(getListAddress());
//        listView.setAdapter(listAddressAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MessageUtils.showShortToast(MallLocateActivity.this, getListAddress().get(position).getName());
//            }
//        });
    }

    /**
     * 启动检索的方法
     * */
    private void startSearch(){
        //Log.d("demo", "startSearch");
        listAddress = new ArrayList<Address>();
        /**附近检索参数*/
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword(keyword);
        option.location(location);
        option.pageCapacity(pageCapacity);
        option.pageNum(pageNum);
        option.radius(5000);
        //option.sortType(sortType);

        Log.d("demo", "keyword: " + keyword);
        Log.d("demo","latitude: " + location.latitude);
        Log.d("demo","longitude: " + location.longitude);

        showProgress(true);
        mPoiSearch.searchNearby(option);

//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city(AppHolder.getInstance().getBdLocationMall().getCity())
//                .keyword(keyword)
//                .pageNum(100));

    }

    @Override
    public void onClick(View v) {
        if(v == search_btn){
            //MessageUtils.showShortToast(this,"搜索");
            keyword = keyword_text.getText().toString();
            if(S.isNull(keyword)){
                MessageUtils.showShortToast(this,"请输入关键字");
            } else {
                startSearch();
            }

        }
    }

    /**
     * 根据条件搜索结束后 调用此方法
     * */
    public void onGetPoiResult(PoiResult result) {

        Log.d("demo", "onGetPoiResult");

        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            MessageUtils.showShortToast(this, getString(R.string.sorry_find_no_result));
            showProgress(false);
            //mPullToRefreshListView.onRefreshComplete();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            showProgress(false);
            for(int i = 0 ; i < result.getAllPoi().size() ; i ++){
                Address address = new Address();

                //if(S.getStr(result.getAllPoi().get(i).name).contains(keyword)){
                    address.setName(S.getStr(result.getAllPoi().get(i).name));
                    address.setAddress(S.getStr(result.getAllPoi().get(i).address));
                    address.setLatitude(result.getAllPoi().get(i).location.latitude);
                    address.setLongitude(result.getAllPoi().get(i).location.longitude);
                    //shop.setShopName(S.getStr(result.getAllPoi().get(i).name));
                    //shop.setShopAddress(S.getStr(result.getAllPoi().get(i).address));
                    //shop.setShopPhoneNum(S.getStr(result.getAllPoi().get(i).phoneNum));
                    listAddress.add(address);
                //}

            }

            if(listAddress != null || listAddress.size() <= 0 ){
                showProgress(false);
                settingListView();
                //easyShopArroundListAdapter.notifyDataSetChanged();
                //mPullToRefreshListView.onRefreshComplete();
//                for(int i = 0 ; i < listAddress.size() ; i ++){
//                    Log.d("demo","name: " + i + " : " + listAddress.get(i).getName());
//                    Log.d("demo","address: " + i + " : " + listAddress.get(i).getAddress());
//                }
            } else {
                MessageUtils.showShortToast(this, getString(R.string.no_more_data));
            }


            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            //mPullToRefreshListView.onRefreshComplete();

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

        //mPullToRefreshListView.onRefreshComplete();
    }

    /**
     * 配置listView
     * */
    private void settingListView(){

        listAddressAdapter = new ListAddressAdapter(this);
        if(listAddress != null && listAddress.size() > 0){
            locate_address.setText(S.getStr(AppHolder.getInstance().getAddress().getName()));
            //search_btn.setOnClickListener(this);
            listAddressAdapter.setList(listAddress);
            listView.setAdapter(listAddressAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(MallLocateActivity.this, listAddress.get(position).getAddress());
                    //AppHolder.getInstance().getBdLocationMall().setLatitude(listAddress.get(position).getLatitude());
                    //AppHolder.getInstance().getBdLocationMall().setLongitude(listAddress.get(position).getLongitude());
                    //AppHolder.getInstance().getBdLocationMall().setAddrStr(listAddress.get(position).getName() + "  " + listAddress.get(position).getAddress());

                    //AppHolder.getInstance().setAddress(listAddress.get(position));

                    LatLng latLng = new LatLng(listAddress.get(position).getLatitude(),listAddress.get(position).getLongitude());
                    reverseGeoCode(latLng, position);

                    //locate_address.setText(S.getStr(listAddress.get(position).getName()));

                    //Log.d("demo", "addess: " + AppHolder.getInstance().getAddress().getName());
                    //finish();
                }
            });
        }


//        mListView = mPullToRefreshListView.getRefreshableView();
//        easyShopArroundListAdapter = new EasyShopArroundListAdapter(this);
//        easyShopArroundListAdapter.setList(listShop);
//        mListView.setAdapter(easyShopArroundListAdapter);
//        mListView.setDivider(null);
//        mListView.setDividerHeight(10);
//        //注册上下拉定义事件
//        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
//        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            //下拉刷新
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                pageNum = Constant.DEFAULT_PAGE_NUM;
//                listShop.clear();
//                startSearch();
//            }
//            //上拉加载
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                pageNum = pageNum + 1;
//                //listShop.clear();
//                startSearch();
//            }
//        });

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
            //mPullToRefreshListView.onRefreshComplete();
        } else {
            MessageUtils.showShortToast(this, result.getName() + ": " + result.getAddress());
        }
        //mPullToRefreshListView.onRefreshComplete();
    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //MessageUtils.showShortToast(PayStyleActivity.this,"返回");
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
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }

    /**
     * 反地理编码得到地址信息
     */
    private void reverseGeoCode(LatLng latLng,final int position) {

        Log.d("demo","反编译1");

        // 创建地理编码检索实例
        GeoCoder geoCoder = GeoCoder.newInstance();
        //
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(MallLocateActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(MallLocateActivity.this, "位置：" + result.getAddress(), Toast.LENGTH_LONG).show();

                AppHolder.getInstance().setAddress(listAddress.get(position));

                AppHolder.getInstance().getAddress().setProvince(S.getStr(result.getAddressDetail().province));
                AppHolder.getInstance().getAddress().setCity(S.getStr(result.getAddressDetail().city));
                AppHolder.getInstance().getAddress().setDistrict(S.getStr(result.getAddressDetail().district));
                AppHolder.getInstance().getAddress().setLatitude(result.getLocation().latitude);
                AppHolder.getInstance().getAddress().setLatitude(result.getLocation().longitude);

                locate_address.setText(S.getStr(AppHolder.getInstance().getAddress().getName()));
                //Log.d("demo", "province: " + result.getAddressDetail().province);
                //Log.d("demo", "city: " + result.getAddressDetail().city);
                //Log.d("demo", "district: " + result.getAddressDetail().district);
                //Log.d("demo", "latitude: " + result.getLocation().latitude);
                //Log.d("demo", "longitude: " + result.getLocation().longitude);

                //Log.d("demo", "result: " + result.getAddressDetail().province+ result.getAddressDetail().city+ result.getAddressDetail().district);

                finish();
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        // 释放地理编码检索实例
        // geoCoder.destroy();
    }
}

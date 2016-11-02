package com.ctrl.android.property.eric.ui.easy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.BusStation;
import com.ctrl.android.property.eric.ui.adapter.EasyBusStationListAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公交出行 activity
 * Created by Eric on 2015/10/13
 */
public class EasyBusActivity extends AppToolBarActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

	@InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
	PullToRefreshListView mPullToRefreshListView;
	@InjectView(R.id.map_Layout)//地图所在layout
	LinearLayout map_Layout;
	@InjectView(R.id.list_Layout)//列表所在layout
	LinearLayout list_Layout;


	private ListView mListView;

	private EasyBusStationListAdapter easyBusStationListAdapter;


	/**经度*/
	private double longitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLongitude();
	/**纬度*/
	private double latitude = AppHolder.getInstance().getBdLocationMall() == null ? 0.0 : AppHolder.getInstance().getBdLocationMall().getLatitude();
	/**搜索关键字*/
	private String keyword = Constant.SEARCH_KEY_WORD;
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

	/**车站信息列表*/
	private List<BusStation> listBusStation;

	/**搜索入口类, 定义此类开始搜索 */
	private PoiSearch mPoiSearch = null;
	/**建议查询接口*/
	private SuggestionSearch mSuggestionSearch = null;
	/**定义百度地图对象的操作方法与接口*/
	private BaiduMap mBaiduMap = null;

	private String TITLE = StrConstant.EASY_BUS_TITLE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.easy_bus_activity);
		ButterKnife.inject(this);
		init();
		/**开启搜索*/
		startSearch();

	}


	private void init(){

		map_Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AndroidUtil.getDeviceHeight(this) * 1 / 2 - 100));
		list_Layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AndroidUtil.getDeviceHeight(this) * 1 / 2 - 100));

		/**初始化搜索模块*/
		mPoiSearch = PoiSearch.newInstance();
		/**注册搜索事件监听*/
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		/**获取建议检索实例*/
		mSuggestionSearch = SuggestionSearch.newInstance();
		/**设置建议请求结果监听器*/
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		/**获取百度地图的控件*/
		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getBaiduMap();

		/**开启定位图层*/
		mBaiduMap.setMyLocationEnabled(true);
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(AppHolder.getInstance().getBdLocation().getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(AppHolder.getInstance().getBdLocation().getLatitude())
				.longitude(AppHolder.getInstance().getBdLocation().getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);

		listBusStation = new ArrayList<BusStation>();

		settingListView();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		mBaiduMap.setMyLocationEnabled(false);
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
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


	}

	/**
	 * 影响搜索按钮点击事件
	 * 点击搜索后会调用此方法
	 * @param v
	 */
	public void searchButtonProcess(View v) {
		//EditText editCity = (EditText) findViewById(R.id.city);
		//EditText editSearchKey = (EditText) findViewById(R.id.searchkey);

		Log.d("demo", "searchButtonProcess");

		/**
		 * searchInBound(): 范围内搜索
		 * searchInCity(): 城市内搜索
		 * searchNearby(): 周边搜索
		 * */
//		mPoiSearch.searchInCity((new PoiCitySearchOption())
//				.city(editCity.getText().toString())
//				.keyword(editSearchKey.getText().toString())
//				.pageNum(load_Index));

		/**附近检索参数*/
		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.keyword(keyword);
		option.location(location);
		option.pageCapacity(pageCapacity);
		option.pageNum(pageNum);
		option.radius(radius);
		option.sortType(sortType);
		mPoiSearch.searchNearby(option);

	}

	public void goToNextPage(View v) {
		Log.d("demo", "goToNextPage");
		pageNum++;
		//searchButtonProcess(null);
	}

	/**
	 * 根据条件搜索结束后 调用此方法
	 * */
	public void onGetPoiResult(PoiResult result) {

		Log.d("demo", "onGetPoiResult");

		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			MessageUtils.showShortToast(this, getString(R.string.sorry_find_no_result));
			mPullToRefreshListView.onRefreshComplete();
			//Toast.makeText(EasyBusActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			//Log.d("demo", "onGetPoiResult_NO_ERROR");
			//Log.d("demo", "address" + result.getAllPoi().get(0).address);
			for(int i = 0 ; i < result.getAllPoi().size() ; i ++){
				Log.d("demo", " " + (i) + ": " + result.getAllPoi().get(i).type);
				Log.d("demo", " " + (i) + ": " + result.getAllPoi().get(i).address);

				BusStation bus = new BusStation();

				bus.setStationName(result.getAllPoi().get(i).name);
				bus.setStationDistance((int)(DistanceUtil.getDistance(location,result.getAllPoi().get(i).location)));
				bus.setStationAddress(result.getAllPoi().get(i).address);
				listBusStation.add(bus);

			}

			if(listBusStation != null || listBusStation.size() <= 0 ){
				//settingListView();
				easyBusStationListAdapter.notifyDataSetChanged();
				mPullToRefreshListView.onRefreshComplete();
			} else {
				MessageUtils.showShortToast(this, getString(R.string.no_more_data));
			}

			mBaiduMap.clear();
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
			/**用于显示poi的overly*/
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			/**设置地图marker点击事件监听*/
			mBaiduMap.setOnMarkerClickListener(overlay);
			/**设置POI数据*/
			overlay.setData(result);
			/**将所有的overly添加到地图上*/
			overlay.addToMap();
			/**
			 * 缩放地图，使所有Overlay都在合适的视野内
			 * 注: 该方法只对Marker类型的overlay有效
			 * */
			overlay.zoomToSpan();
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

	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			MessageUtils.showShortToast(this, getString(R.string.sorry_find_no_result));
			mPullToRefreshListView.onRefreshComplete();
			//Toast.makeText(EasyBusActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			MessageUtils.showShortToast(this, result.getName() + ": " + result.getAddress());
			//Toast.makeText(EasyBusActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT).show();
		}
		mPullToRefreshListView.onRefreshComplete();
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		//Log.d("demo", "onGetSuggestionResult");
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}

	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		/**
		 * 覆写此方法以改变默认点击行为
		 * */
		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			Log.d("demo", "MyPoiOverlay_onPoiClick");
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			/**POI的详情检索*/
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			// }
			return true;
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
//	@Override
//	public boolean setupToolBarRightButton(ImageView rightButton) {
//		rightButton.setImageResource(R.drawable.white_cross_icon);
//		rightButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				MessageUtils.showShortToast(EasyBusActivity.this, "MORE");
//				//showProStyleListPop();
//			}
//		});
//		return true;
//	}

	/**
	 * 配置listView
	 * */
	private void settingListView(){
		mListView = mPullToRefreshListView.getRefreshableView();
		easyBusStationListAdapter = new EasyBusStationListAdapter(this);
		easyBusStationListAdapter.setList(listBusStation);
		mListView.setAdapter(easyBusStationListAdapter);
		mListView.setDivider(null);
		mListView.setDividerHeight(10);
		//注册上下拉定义事件
		mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			//下拉刷新
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pageNum = Constant.DEFAULT_PAGE_NUM;
				listBusStation.clear();
				startSearch();
			}
			//上拉加载
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				pageNum = pageNum + 1;
				listBusStation.clear();
				startSearch();
			}
		});

//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				//MessageUtils.showShortToast(EasyBusActivity.this,"XXXXXXXX");
//				MessageUtils.showShortToast(EasyBusActivity.this, "点击了: " + listBusStation.get(position - 1).getStationName());
//			}
//		});
	}

}

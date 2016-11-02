package com.ctrl.android.property.eric.ui.easy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.ctrl.android.property.R;

/**
 * 演示poi搜索功能
 */
public class PoiSearchDemo extends FragmentActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

	/**搜索入口类, 定义此类开始搜索 */
	private PoiSearch mPoiSearch = null;
	/**建议查询接口*/
	private SuggestionSearch mSuggestionSearch = null;
	/**定义百度地图对象的操作方法与接口*/
	private BaiduMap mBaiduMap = null;
	/**搜索关键字输入窗口*/
	private AutoCompleteTextView keyWorldsView = null;
	private ArrayAdapter<String> sugAdapter = null;
	private int load_Index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poisearch);
		/**初始化搜索模块*/
		mPoiSearch = PoiSearch.newInstance();
		/**注册搜索事件监听*/
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		/**获取建议检索实例*/
		mSuggestionSearch = SuggestionSearch.newInstance();
		/**设置建议请求结果监听器*/
		mSuggestionSearch.setOnGetSuggestionResultListener(this);

		/**
		 * 获取AutoCompleteTextView控件
		 * 并 显示历史搜索
		 * */
		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
		sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		keyWorldsView.setAdapter(sugAdapter);

		Log.d("demo", "A01");
		/**获取百度地图的控件*/
		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getBaiduMap();

		/**
		 * 当输入关键字变化时，动态更新建议列表
		 */
		keyWorldsView.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() <= 0) {
					return;
				}
				Log.d("demo", "A02");
				String city = ((EditText) findViewById(R.id.city)).getText()
						.toString();
				/**
				 * 建议请求入口
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 * SuggestionSearchOption: 建议查询请求的参数
				 */
				mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs.toString()).city(city));
			}
		});

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
	 * 影响搜索按钮点击事件
	 * 点击搜索后会调用此方法
	 * @param v
	 */
	public void searchButtonProcess(View v) {
		EditText editCity = (EditText) findViewById(R.id.city);
		EditText editSearchKey = (EditText) findViewById(R.id.searchkey);

		Log.d("demo", "searchButtonProcess");

		/**
		 * searchInBound(): 范围内搜索
		 * searchInCity(): 城市内搜索
		 * searchNearby(): 周边搜索
		 * */
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(editCity.getText().toString())
				.keyword(editSearchKey.getText().toString())
				.pageNum(load_Index));
	}

	public void goToNextPage(View v) {
		Log.d("demo", "goToNextPage");
		load_Index++;
		searchButtonProcess(null);
	}

	/**
	 * 根据条件搜索结束后 调用此方法
	 * */
	public void onGetPoiResult(PoiResult result) {

		Log.d("demo", "onGetPoiResult");

		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(PoiSearchDemo.this, "未找到结果", Toast.LENGTH_LONG)
			.show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			Log.d("demo", "onGetPoiResult_NO_ERROR");
			Log.d("demo", "address" + result.getAllPoi().get(0).address);
			for(int i = 0 ; i < result.getAllPoi().size() ; i ++){
				Log.d("demo", " " + (i) + ": " + result.getAllPoi().get(i).type);
				Log.d("demo", " " + (i) + ": " + result.getAllPoi().get(i).address);

			}
			mBaiduMap.clear();
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

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(PoiSearchDemo.this, strInfo, Toast.LENGTH_LONG)
					.show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PoiSearchDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(PoiSearchDemo.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		Log.d("demo", "onGetSuggestionResult");
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		sugAdapter.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null)
				sugAdapter.add(info.key);
		}
		sugAdapter.notifyDataSetChanged();
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
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
						.poiUid(poi.uid));
			// }
			return true;
		}
	}
}

package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.Item;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.ui.adapter.ItemAdapter;
import com.ctrl.android.property.eric.ui.adapter.MallShopListAdapter;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.jason.dao.ClassDao;
import com.ctrl.android.property.jason.entity.Kind;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城  商家列表 activity
 * Created by Eric on 2015/9/23.
 */
public class MallShopListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.sort_shop_layout)//排序方式和商铺类型所在的layout
    LinearLayout sort_shop_layout;

    @InjectView(R.id.mall_main_sort_style_btn)//排序方式按钮
    LinearLayout mall_main_sort_style_btn;
    @InjectView(R.id.mall_main_sort_style_btn_left_icon)//排序方式左侧图标
    ImageView mall_main_sort_style_btn_left_icon;
    @InjectView(R.id.mall_main_sort_style_btn_text)//排序方式文字标题
    TextView mall_main_sort_style_btn_text;
    @InjectView(R.id.mall_main_sort_style_btn_right_icon)//排序方式右侧图标
    ImageView mall_main_sort_style_btn_right_icon;

    @InjectView(R.id.mall_main_shop_style_btn)//商铺分类按钮
    LinearLayout mall_main_shop_style_btn;
    @InjectView(R.id.mall_main_shop_style_btn_left_icon)//商铺分类左侧图标
    ImageView mall_main_shop_style_btn_left_icon;
    @InjectView(R.id.mall_main_shop_style_btn_text)//商铺分类文字标题
    TextView mall_main_shop_style_btn_text;
    @InjectView(R.id.mall_main_shop_style_btn_right_icon)//商铺分类右侧图标
    ImageView mall_main_shop_style_btn_right_icon;

    @InjectView(R.id.mall_main_pop_layout)//
    LinearLayout mall_main_pop_layout;
    @InjectView(R.id.sort_style_layout)//排序方式
    LinearLayout sort_style_layout;
    @InjectView(R.id.shop_style_layout)//商铺分类
    LinearLayout shop_style_layout;

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    @InjectView(R.id.sort_style_listView)
    ListView sort_style_listView;
    @InjectView(R.id.shop_style_listView)
    ListView shop_style_listView;

    @InjectView(R.id.toolbar_title)
    TextView toolbar_title;

    private ListView mListView;
    private View mMenuView;//显示pop的view
    private MallShopListAdapter mAdapter;

    private MallDao mallDao;

    private String provinceName = AppHolder.getInstance().getBdLocation().getProvince();
    private String cityName = AppHolder.getInstance().getBdLocation().getCity();
    private String areaName = AppHolder.getInstance().getBdLocation().getDistrict();
    private Double longitude = AppHolder.getInstance().getBdLocation().getLongitude();
    private Double latitude = AppHolder.getInstance().getBdLocation().getLatitude();
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;

    private String sortType;

    private List<MallShop> listMallShop = new ArrayList<>();

    private String TITLE = StrConstant.SHOP_LIST_TITLE;

    private String keyword = "";
    private String kindId1 = "0";
    private String kindId2 = "";

    private List<Item> listItem = new ArrayList<>();
    private ItemAdapter itemAdapter;

    private List<Item> listItem2 = new ArrayList<>();

    private ClassDao classDao;
    private List<Kind> listKind;

    private String kind2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_shop_list_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        kind2 = getIntent().getStringExtra("kind2");

        mAdapter = new MallShopListAdapter(this);
        mListView = mPullToRefreshListView.getRefreshableView();

        listItem.add(new Item("0","默认排序",true));
        listItem.add(new Item("1","销量从高到低",false));
        listItem.add(new Item("2","评价最高",false));

//        listItem2.add(new Item("0","全部",true));
//        listItem2.add(new Item("1","美食",false));
//        listItem2.add(new Item("2","服装",false));
//        listItem2.add(new Item("3","酒店",false));
//        listItem2.add(new Item("4","电影",false));
//        listItem2.add(new Item("5","KTV",false));
//        listItem2.add(new Item("6","外卖",false));
//        listItem2.add(new Item("7","足疗按摩",false));

        mall_main_sort_style_btn.setOnClickListener(this);
        mall_main_shop_style_btn.setOnClickListener(this);
        sort_style_layout.setOnClickListener(this);
        shop_style_layout.setOnClickListener(this);

        classDao = new ClassDao(this);
        showProgress(true);
        classDao.requestData("CMP");

        keyword = getIntent().getStringExtra("keyword");

        if(keyword == null){
            keyword = "";
        }

        Log.d("demo", "keyword: " + keyword);

        mallDao = new MallDao(this);
//        showProgress(true);
//        mallDao.requestShopListByKeyword(keyword,String.valueOf(longitude), String.valueOf(latitude), provinceName, cityName, areaName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(1 == requestCode){

            listMallShop = new ArrayList<>();
            listMallShop = mallDao.getListShop();

            mListView = mPullToRefreshListView.getRefreshableView();
            mAdapter = new MallShopListAdapter(this);
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
                    mallDao.getListShop().clear();
                    mallDao.requestShopListByKeyword(keyword, String.valueOf(longitude), String.valueOf(latitude), provinceName, cityName, areaName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    mallDao.requestShopListByKeyword(keyword, String.valueOf(longitude), String.valueOf(latitude), provinceName, cityName, areaName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MallShopListActivity.this, MallShopMainActivity.class);
                    intent.putExtra("companyId",listMallShop.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopListActivity.this);

                }
            });
        }

        if(4 == requestCode){
            listKind = classDao.getData();
            listItem2 = new ArrayList<>();
            listItem2.add(new Item("", "全部", false));

            for(int i = 0 ; i < listKind.size() ; i ++){
                listItem2.add(new Item(listKind.get(i).getId(),listKind.get(i).getKindName(),false));
            }

            listItem2.get(0).setCheck(true);

            kindId1 = "0";
            if(S.isNull(kind2)){
                kindId2 = listItem2.get(0).getId();
            } else {
                kindId2 = getKindId(listItem2,kind2);
            }

            Log.d("demo","keyword: " + keyword);
            Log.d("demo", "kindId1: " + kindId1);
            Log.d("demo","kindId2: " + kindId2);

            toolbar_title.setText(S.isNull(kind2) ? "" : kind2);

            //keyword = "";
            showProgress(true);
            mallDao.requestShopListBySort(false,kindId1, kindId2, keyword, String.valueOf(longitude), String.valueOf(latitude),
                    provinceName, cityName, areaName,
                    String.valueOf(currentPage), String.valueOf(rowCountPerPage));

        }

        if(9 == requestCode){

            //listMallShop = new ArrayList<>();
            listMallShop = mallDao.getListShopBySort();

            //mListView = mPullToRefreshListView.getRefreshableView();
            //mAdapter = new MallShopListAdapter(this);
            mAdapter.setList(listMallShop);
            mAdapter.notifyDataSetChanged();

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
                    mallDao.getListShopBySort().clear();
                    mallDao.requestShopListBySort(true,kindId1,kindId2,keyword, String.valueOf(longitude), String.valueOf(latitude),
                            provinceName, cityName, areaName,
                            String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    mallDao.requestShopListBySort(true,kindId1,kindId2,keyword,String.valueOf(longitude),String.valueOf(latitude),
                            provinceName,cityName,areaName,
                            String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MallShopListActivity.this, MallShopMainActivity.class);
                    intent.putExtra("companyId",listMallShop.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopListActivity.this);

                }
            });
        }
    }

    /**
     * 数据请求失败后
     * */
    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();
        MessageUtils.showShortToast(this, errorMessage);

        if(errorNo.equals("002")){

            MessageUtils.showShortToast(this,"无数据");

            if(mallDao.isFlgSta()){
                //
            } else {
                listMallShop = new ArrayList<>();
                mAdapter.setList(listMallShop);
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }


        }

    }

    private int sort_flg = 0;
    private int shop_flg = 0;

    @Override
    public void onClick(View v) {

        //排序方式
        if(v == mall_main_sort_style_btn){
            if(sort_flg == 0){

                itemAdapter = new ItemAdapter(this);
                itemAdapter.setList(listItem);
                sort_style_listView.setAdapter(itemAdapter);
                sort_style_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        listMallShop = new ArrayList<MallShop>();
                        mallDao.getListShopBySort().clear();

                        for (int i = 0; i < listItem.size(); i++) {
                            listItem.get(i).setCheck(false);
                        }
                        listItem.get(position).setCheck(true);

                        kindId1 = getKindId(listItem);
                        kindId2 = getKindId(listItem2);

                        Log.d("demo","keyword: " + keyword);
                        Log.d("demo","kindId1: " + kindId1);
                        Log.d("demo","kindId2: " + kindId2);

                        itemAdapter.setList(listItem);
                        itemAdapter.notifyDataSetChanged();
                        //sort_style_layout.requestFocus();
                        sort_style_layout.setVisibility(View.GONE);
                        shop_style_layout.setVisibility(View.GONE);
                        sort_flg = 0;
                        showProgress(true);
                        mallDao.requestShopListBySort(false,kindId1,kindId2,keyword, String.valueOf(longitude), String.valueOf(latitude),
                                provinceName, cityName, areaName,
                                String.valueOf(currentPage), String.valueOf(rowCountPerPage));

//                        currentPage = 1;
//                        mall_main_sort_style_btn_text.setText(S.getStr(listItem.get(position).getName()));
//                        sortType = String.valueOf(listItem.get(position).getFlg());
//                        showProgress(true);
//                        mallDao.requestShopListBySort(1,sortType,String.valueOf(longitude),String.valueOf(latitude),
//                                provinceName,cityName,areaName,
//                                String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                    }
                });

                setSortStyleBtnChecked();
                sort_style_layout.setVisibility(View.VISIBLE);
                shop_style_layout.setVisibility(View.GONE);
                sort_flg = 1;
                shop_flg = 0;
            } else if(sort_flg == 1){
                //sort_style_layout.clearFocus();
                sort_style_layout.setVisibility(View.GONE);
                shop_style_layout.setVisibility(View.GONE);
                sort_flg = 0;
            }


        }

        //商铺分类
        if(v == mall_main_shop_style_btn){

            if(shop_flg == 0){

                itemAdapter = new ItemAdapter(this);
                itemAdapter.setList(listItem2);
                shop_style_listView.setAdapter(itemAdapter);
                shop_style_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        listMallShop = new ArrayList<MallShop>();
                        mallDao.getListShopBySort().clear();

                        for (int i = 0; i < listItem2.size(); i++) {
                            listItem2.get(i).setCheck(false);
                        }

                        listItem2.get(position).setCheck(true);

                        toolbar_title.setText(S.getStr(listItem2.get(position).getName()));

                        kindId1 = getKindId(listItem);
                        kindId2 = getKindId(listItem2);

                        Log.d("demo", "keyword: " + keyword);
                        Log.d("demo","kindId1: " + kindId1);
                        Log.d("demo","kindId2: " + kindId2);

                        itemAdapter.setList(listItem2);
                        itemAdapter.notifyDataSetChanged();
                        //sort_style_layout.requestFocus();
                        sort_style_layout.setVisibility(View.GONE);
                        shop_style_layout.setVisibility(View.GONE);
                        shop_flg = 0;

                        mall_main_shop_style_btn_text.setText(S.getStr(listItem2.get(position).getName()));
                        showProgress(true);
                        mallDao.requestShopListBySort(false,kindId1, kindId2, keyword, String.valueOf(longitude), String.valueOf(latitude),
                                provinceName, cityName, areaName,
                                String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    }
                });

                setShopStyleBtnChecked();
                sort_style_layout.setVisibility(View.GONE);
                shop_style_layout.setVisibility(View.VISIBLE);
                shop_flg = 1;
                sort_flg = 0;
            } else if(shop_flg == 1){
                //sort_style_layout.clearFocus();
                sort_style_layout.setVisibility(View.GONE);
                shop_style_layout.setVisibility(View.GONE);
                shop_flg = 0;
            }
        }

        if(v == sort_style_layout){
            //
        }

        if(v == shop_style_layout){
            //
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
//    @Override
//    public String setupToolBarTitle() {
//        return TITLE;
//    }


    /**
     * header 右侧按钮
     * */
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.search_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MallShopListActivity.this, MallSearchActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MallShopListActivity.this);
            }
        });
        return true;
    }

    private String getKindId(List<Item> list){
        String id = "";
        for(int i = 0 ; i < list.size() ; i ++){
            if(list.get(i).isCheck()){
                return list.get(i).getId();
            }
        }
        return id;
    }

    private String getKindId(List<Item> list,String name){
        String id = "";
        for(int i = 0 ; i < list.size() ; i ++){
            list.get(i).setCheck(false);
            if(list.get(i).getName().equals(name)){
                list.get(i).setCheck(true);
                id = list.get(i).getId();
            }
        }
        return id;
    }

    /**
     * 排序方式按钮 点击后效果
     * */
    private void setSortStyleBtnChecked(){

        //排序方式左侧图标
         mall_main_sort_style_btn_left_icon.setImageDrawable(getResources().getDrawable(R.drawable.sort_icon_checked));
        //排序方式文字标题
         mall_main_sort_style_btn_text.setTextColor(getResources().getColor(R.color.text_green));
        //排序方式右侧图标
         mall_main_sort_style_btn_right_icon.setImageDrawable(getResources().getDrawable(R.drawable.gray_arrow_up));

        //商铺分类左侧图标
         mall_main_shop_style_btn_left_icon.setImageDrawable(getResources().getDrawable(R.drawable.shop_icon));
        //商铺分类文字标题
         mall_main_shop_style_btn_text.setTextColor(getResources().getColor(R.color.text_gray));
        //商铺分类右侧图标
         mall_main_shop_style_btn_right_icon.setImageDrawable(getResources().getDrawable(R.drawable.gray_arrow_down));

    }

    /**
     * 商铺分类按钮 点击后效果
     * */
    private void setShopStyleBtnChecked(){

        //排序方式左侧图标
        mall_main_sort_style_btn_left_icon.setImageDrawable(getResources().getDrawable(R.drawable.sort_icon));
        //排序方式文字标题
        mall_main_sort_style_btn_text.setTextColor(getResources().getColor(R.color.text_gray));
        //排序方式右侧图标
        mall_main_sort_style_btn_right_icon.setImageDrawable(getResources().getDrawable(R.drawable.gray_arrow_down));

        //商铺分类左侧图标
        mall_main_shop_style_btn_left_icon.setImageDrawable(getResources().getDrawable(R.drawable.shop_icon_checked));
        //商铺分类文字标题
        mall_main_shop_style_btn_text.setTextColor(getResources().getColor(R.color.text_green));
        //商铺分类右侧图标
        mall_main_shop_style_btn_right_icon.setImageDrawable(getResources().getDrawable(R.drawable.gray_arrow_up));

    }

}

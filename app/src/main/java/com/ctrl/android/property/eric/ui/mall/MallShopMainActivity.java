package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.entity.Pro;
import com.ctrl.android.property.eric.entity.ProCategary;
import com.ctrl.android.property.eric.ui.adapter.MallShopProListAdapter;
import com.ctrl.android.property.eric.ui.adapter.ProCategaryGridListAdapter;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.ViewUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城  商家 首页面 activity
 * Created by Eric on 2015/11/11.
 */
public class MallShopMainActivity extends AppToolBarActivity implements View.OnClickListener{

//    @InjectView(R.id.search_area)//搜索区域
//    LinearLayout search_area;
    @InjectView(R.id.search_btn)//搜索按钮
    ImageView search_btn;
    @InjectView(R.id.search_text)//搜索文本
    EditText search_text;
    @InjectView(R.id.search_del_btn)//删除搜索内容
    ImageView search_del_btn;

    @InjectView(R.id.shop_info_btn)//
    LinearLayout shop_info_btn;

    @InjectView(R.id.mall_main_pop_layout)//
    LinearLayout mall_main_pop_layout;

    @InjectView(R.id.shop_pic)//商家图片
    ImageView shop_pic;
    @InjectView(R.id.shop_name)//商家名称
    TextView shop_name;
    @InjectView(R.id.shop_business_time)//营业时间
    TextView shop_business_time;
    @InjectView(R.id.shop_ratingBar)//商家信用
    RatingBar shop_ratingBar;
    @InjectView(R.id.right_star)//右侧五角星
    ImageView right_star;

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private View mMenuView;//显示pop的view
    private String TITLE = StrConstant.TITLE_NONE;

    private MallDao mallDao;
    private String companyId = "";
    private MallShop mallShopDetail;
    private float sellersCredit = 0;
    private String collectingType;
    private String flgCategoryId = "000000";
    private String categoryId;

    private String memberId = "";

    //private String keyword = "";
    private String productName;

    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    private MallShopProListAdapter mallShopProListAdapter;

    private List<ProCategary> listProCategary = new ArrayList<>();

    private List<Pro> listPro = new ArrayList<>();
    private List<Pro> listProByCategary = new ArrayList<>();

    private ProCategaryGridListAdapter proCategaryGridListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_shop_main_activity);
        //禁止自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        //search_area.setOnClickListener(this);
        search_text.setOnClickListener(this);
        shop_info_btn.setOnClickListener(this);

        companyId = getIntent().getStringExtra("companyId");
        //Log.d("demo","companyId: " + companyId);
        search_btn.setOnClickListener(this);

        memberId = AppHolder.getInstance().getMemberInfo().getMemberId();

        if(S.isNull(memberId)){
            memberId = "";
        }

        mallDao = new MallDao(this);
        showProgress(true);
        mallDao.requestShopDetail(companyId, memberId);

        /**搜索框的配置代码*/
        ViewUtil.settingSearch(search_del_btn, search_text);
        right_star.setOnClickListener(this);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(2 == requestCode){
            mallShopDetail = mallDao.getMallShopDetail();
            sellersCredit = mallShopDetail.getEvaluatLevel();

            Log.d("demo","sellersCredit: " + sellersCredit);

            Arad.imageLoader.load(S.isNull(mallShopDetail.getLogoUrl()) ? "aa" : mallShopDetail.getLogoUrl())
                    .placeholder(R.drawable.default_image)
                    .into(shop_pic);
            shop_name.setText(S.getStr(mallShopDetail.getCompanyName()));
            shop_business_time.setText(StrConstant.BUSINESS_TIME_TITLE + mallShopDetail.getBusinessStartTime() + "-" + mallShopDetail.getBusinessEndTime());
            shop_ratingBar.setRating(sellersCredit/2);
            //收藏状态（0：未收藏、1：已收藏）
            if(1 == mallShopDetail.getStatus()){
                right_star.setImageResource(R.drawable.gray_star_none);
            } else {
                right_star.setImageResource(R.drawable.gray_star);
            }

            showProgress(true);
            productName = "";
            mallDao.requestProList(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
        }

        if(3 == requestCode){

            //收藏或取消收藏(0:收藏, 1:取消收藏)
            if(collectingType.equals("0")){
                MessageUtils.showShortToast(this,"收藏成功");
                mallShopDetail.setStatus(1);
                right_star.setImageResource(R.drawable.gray_star_none);
            } else {
                MessageUtils.showShortToast(this,"取消收藏");
                mallShopDetail.setStatus(0);
                right_star.setImageResource(R.drawable.gray_star);
            }
        }

        if(4 == requestCode){
            listPro = mallDao.getListPro();

            mListView = mPullToRefreshListView.getRefreshableView();
            mallShopProListAdapter = new MallShopProListAdapter(this);
            mallShopProListAdapter.setList(listPro);
            mListView.setAdapter(mallShopProListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    mallDao.getListPro().clear();
                    productName = "";
                    mallDao.requestProList(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    productName = "";
                    mallDao.requestProList(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MallShopMainActivity.this, MallShopProDetailActivity.class);
                    intent.putExtra("productId", listPro.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopMainActivity.this);

                }
            });
        }

        if(5 == requestCode){
            listProCategary = mallDao.getListProCategary();
            showProStyleListPop();
        }

        if(6 == requestCode){

            flgCategoryId = categoryId;

            if(listPro == null || listPro.size() < 1){
                MessageUtils.showShortToast(this,"没有获取到数据");
            } else {
                listProByCategary = mallDao.getListProByCategary();
                //listPro = listProByCategary;
                mallShopProListAdapter.setList(listProByCategary);
                mallShopProListAdapter.notifyDataSetChanged();
                //注册上下拉定义事件
                mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    //下拉刷新
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        currentPage = 1;
                        mallDao.getListProByCategary().clear();
                        showProgress(true);
                        mallDao.requestProListByCategary(1,categoryId, companyId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    }

                    //上拉加载
                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        currentPage = currentPage + 1;
                        showProgress(true);
                        mallDao.requestProListByCategary(1,categoryId, companyId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    }
                });

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MallShopMainActivity.this, MallShopProDetailActivity.class);
                        //intent.putExtra("orderId",dao.getOrders().get(position - 1).getOrderId());
                        intent.putExtra("productId",listPro.get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(MallShopMainActivity.this);

                    }
                });

            }


        }

        if(10 == requestCode){
            listPro = mallDao.getListProByKeyword();

            mListView = mPullToRefreshListView.getRefreshableView();
            mallShopProListAdapter = new MallShopProListAdapter(this);
            mallShopProListAdapter.setList(listPro);
            mListView.setAdapter(mallShopProListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    Log.d("demo","XXXXXXXXX");
                    currentPage = 1;
                    mallDao.getListProByKeyword().clear();
                    //productName = "";
                    mallDao.requestProList2(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    //productName = "";
                    mallDao.requestProList2(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MallShopMainActivity.this, MallShopProDetailActivity.class);
                    intent.putExtra("productId",listPro.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(MallShopMainActivity.this);

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
        if(errorNo.equals("002")){
            if(mallDao.isIfCategary()){

                if(flgCategoryId.equals(categoryId)){
                   //
                } else {
                    listPro.clear();
                    //listPro = new ArrayList<>();
                }
                mallShopProListAdapter = new MallShopProListAdapter(this);
                mallShopProListAdapter.setList(listPro);
                mallShopProListAdapter.notifyDataSetChanged();

            }

        } else if(errorNo.equals("023")){
            MessageUtils.showShortToast(this, "库存不足");
        }else {
            MessageUtils.showShortToast(this, errorMessage);
        }


    }

    @Override
    public void onClick(View v) {

//        if(v == search_area || v == search_btn || v == search_text){
//            Intent intent = new Intent(MallShopMainActivity.this, SearchGoodsActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MallShopMainActivity.this);
//        }

        if(v == search_btn){
            productName = search_text.getText().toString();
            MessageUtils.showShortToast(this,productName);
            mallDao.requestProList2(companyId, productName, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
        }


        if(v == right_star){
            if(1 == mallShopDetail.getStatus()){
                showProgress(true);
                collectingType = "1";//收藏或取消收藏(0:收藏, 1:取消收藏)
                mallDao.requestFavorShop(companyId,AppHolder.getInstance().getMemberInfo().getMemberId(),collectingType);
            } else {
                showProgress(true);
                collectingType = "0";//收藏或取消收藏(0:收藏, 1:取消收藏)
                mallDao.requestFavorShop(companyId, AppHolder.getInstance().getMemberInfo().getMemberId(), collectingType);
            }
        }

//        if(v == search_btn){
//            MessageUtils.showShortToast(this,"搜索" + search_text.getText().toString());
//        }

//        if(v == shop_pic){
//            Intent intent = new Intent(MallShopMainActivity.this, MallShopActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(MallShopMainActivity.this);
//        }

        if(v == shop_info_btn){
            Intent intent = new Intent(MallShopMainActivity.this, MallShopActivity.class);
            intent.putExtra("companyId",companyId);
            startActivity(intent);
            AnimUtil.intentSlidIn(MallShopMainActivity.this);
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
     * header 右侧按钮
     * */
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.more_info_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
                showProgress(true);
                mallDao.requestProCategary(companyId);
            }
        });
        return true;
    }

    /**
     * 排序方式下拉  列表
     * */
    private void showProStyleListPop(){
        //setRoomData();

        proCategaryGridListAdapter = new ProCategaryGridListAdapter(this);
        proCategaryGridListAdapter.setList(listProCategary);

        mMenuView = LayoutInflater.from(MallShopMainActivity.this).inflate(R.layout.choose_pro_style_top_pop, null);
        GridView gridView = (GridView)mMenuView.findViewById(R.id.gridview);
        gridView.setAdapter(proCategaryGridListAdapter);

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(MallShopMainActivity.this, listProCategary.get(position).getName());
                Pop.dismiss();
                categoryId = listProCategary.get(position).getId();
                currentPage = 1;
                rowCountPerPage = Constant.PAGE_CAPACITY;
                listProByCategary = new ArrayList<Pro>();
                showProgress(true);
                mallDao.requestProListByCategary(0,categoryId, companyId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
            }
        });

        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }



}

package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.CartDao;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.ProDetail;
import com.ctrl.android.property.eric.ui.adapter.MallCommentListAdapter;
import com.ctrl.android.property.eric.ui.adapter.ProductPicPagerAdapter;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.ViewUtil;
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
public class MallShopProDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.mall_main_pop_layout)
    RelativeLayout mall_main_pop_layout;

    @InjectView(R.id.search_area)//搜索区域
    LinearLayout search_area;
    @InjectView(R.id.search_btn)//搜索按钮
    ImageView search_btn;
    @InjectView(R.id.search_text)//搜索文本
    EditText search_text;
    @InjectView(R.id.search_del_btn)//删除搜索内容
    ImageView search_del_btn;

    @InjectView(R.id.scroll_view)
    ScrollView scroll_view;

    @InjectView(R.id.pro_name)//产品名称
    TextView pro_name;
    @InjectView(R.id.pro_favor)//产品收藏
    LinearLayout pro_favor;
    @InjectView(R.id.pro_favor_text)//产品收藏
    TextView pro_favor_text;
    @InjectView(R.id.pro_favor_img)//产品 图片
    ImageView pro_favor_img;
    @InjectView(R.id.pro_price)//产品价格
    TextView pro_price;

    @InjectView(R.id.feedback_btn)//右上角显示反馈
    LinearLayout feedback_btn;
    @InjectView(R.id.feedback_img_btn)//
    ImageView feedback_img_btn;
    @InjectView(R.id.feedback_nums)//右上角反馈数
    TextView feedback_nums;

    @InjectView(R.id.viewpager)//商品图片
    ViewPager viewpager;
    @InjectView(R.id.indicator)//指示器
    CirclePageIndicator indicator;

    @InjectView(R.id.add_to_cart_btn)//加入购物车
    TextView add_to_cart_btn;
    @InjectView(R.id.buy_right_now_btn)//立即购买
    TextView buy_right_now_btn;

    @InjectView(R.id.more_comment_btn)
    TextView more_comment_btn;

    @InjectView(R.id.listView)//评论列表
    ListView listView;

    @InjectView(R.id.detail_content)
    TextView detail_content;
    @InjectView(R.id.detail_img1)
    ImageView detail_img1;
    @InjectView(R.id.detail_img2)
    ImageView detail_img2;
    @InjectView(R.id.detail_img3)
    ImageView detail_img3;

    private View mMenuView;//显示pop的view

    private String productId = "";

    private final static int SET_DEATIL_FLAG = 1;

    private MallCommentListAdapter mallCommentListAdapter;

    ArrayList<String> listPics = new ArrayList<>();

    private ProductPicPagerAdapter mAdapter;

    private String TITLE = StrConstant.TITLE_NONE;

    private int feedBackNum = 5;//反馈数

    private MallDao mallDao;
    private ProDetail proDetail;
    private List<Img> listProImg = new ArrayList<>();
    private List<Img> listProDetailImg = new ArrayList<>();

    private CartDao cartDao;

    private int buyFlg = 0;//立即购买:0 ; 加入购物车:1


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.product_detail_activity);
        //禁止自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
        init();

    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        search_area.setOnClickListener(this);
        search_text.setOnClickListener(this);
        more_comment_btn.setOnClickListener(this);

        pro_favor.setOnClickListener(this);

        add_to_cart_btn.setOnClickListener(this);
        buy_right_now_btn.setOnClickListener(this);

        feedback_btn.setVisibility(View.VISIBLE);
        feedback_img_btn.setOnClickListener(this);
        feedback_nums.setText(String.valueOf(feedBackNum));

        search_btn.setOnClickListener(this);

        productId = getIntent().getStringExtra("productId");
        Log.d("demo", "productId: " + productId);

        mallDao = new MallDao(this);
        mallDao.requestProDetail(productId, AppHolder.getInstance().getMemberInfo().getMemberId());



        /**搜索框的配置代码*/
        //ViewUtil.settingSearch(search_del_btn,search_text);



    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(7 == requestCode){
            proDetail = mallDao.getProDetail();
            listProImg = mallDao.getListProImg();
            listProDetailImg = mallDao.getListProDetailImg();

            if(listProImg != null && listProImg.size() > 0){
                for(Img img : listProImg){
                    listPics.add(img.getOriginalImg());
                }
            } else {
                listPics.add("aa");
            }

            /**商品图片Viewpager的配置*/
            mAdapter = new ProductPicPagerAdapter(getSupportFragmentManager(),listPics);
            viewpager.setAdapter(mAdapter);
            viewpager.setOffscreenPageLimit(listPics.size() == 0 ? 1 : listPics.size() + 2);
            viewpager.setCurrentItem(0);
            /**配置viewpager的指示器*/
            ViewUtil.settingIndicator(indicator, viewpager);

            pro_name.setText(S.getStr(proDetail.getName()));
            pro_price.setText("￥ " + N.toPriceFormate(S.getStr(proDetail.getSellingPrice())));
            //是否收藏（0：未收藏 1：已收藏）
            if(proDetail.getCollect() == 0){
                pro_favor_text.setText("收藏");
                pro_favor_img.setImageResource(R.drawable.gray_favor_none);
            } else {
                pro_favor_text.setText("已收藏");
                pro_favor_img.setImageResource(R.drawable.gray_favor);
            }

            detail_content.setText(S.getStr(proDetail.getInfomation()));
            //Log.d("demo","size: " + listProDetailImg.size());
            if(listProDetailImg != null && listProDetailImg.size() > 0){

                if(listProDetailImg.size() == 1){
                    detail_img1.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(listProDetailImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img1);
                } else if(listProDetailImg.size() == 2){
                    detail_img1.setVisibility(View.VISIBLE);
                    detail_img2.setVisibility(View.VISIBLE);

                    Arad.imageLoader.load(listProDetailImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img1);

                    Arad.imageLoader.load(listProDetailImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img2);
                } else  if(listProDetailImg.size() >= 3){

                    detail_img1.setVisibility(View.VISIBLE);
                    detail_img2.setVisibility(View.VISIBLE);
                    detail_img3.setVisibility(View.VISIBLE);

                    Arad.imageLoader.load(listProDetailImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img1);

                    Arad.imageLoader.load(listProDetailImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img2);

                    Arad.imageLoader.load(listProDetailImg.get(2).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(detail_img3);
                }


            }

            mallCommentListAdapter = new MallCommentListAdapter(this);
            //mallCommentListAdapter.setList(getListMap());
            listView.setAdapter(mallCommentListAdapter);

            showProgress(true);
            mallDao.requestCartProNum(AppHolder.getInstance().getMemberInfo().getMemberId());

        }

        if(8 == requestCode){
            if(proDetail.getCollect() == 0){
                proDetail.setCollect(1);
                pro_favor_text.setText("已收藏");
                pro_favor_img.setImageResource(R.drawable.gray_favor);
            } else {
                proDetail.setCollect(0);
                pro_favor_text.setText("收藏");
                pro_favor_img.setImageResource(R.drawable.gray_favor_none);
            }
        }

        if(110 == requestCode){

            ////立即购买:0 ; 加入购物车:1
            if(buyFlg == 0){
                Intent intent = new Intent(this, MallShopCartActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(this);
            } else {
                MessageUtils.showShortToast(this, "成功加入购物车");
            }

        }

        if(12 == requestCode){
            feedBackNum = mallDao.getProNum();
            feedback_nums.setText(String.valueOf(feedBackNum));
        }


    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        //super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        //mallDao.requestCartProNum(AppHolder.getInstance().getMemberInfo().getMemberId());
        if(errorNo.equals("002")){
            //
        } else {
            MessageUtils.showShortToast(this,errorMessage);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == search_area || v == search_btn || v == search_text){
            Intent intent = new Intent(this, MallShopMainActivity.class);
            intent.putExtra("companyId",proDetail.getCompanyId());
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == search_btn){
            //MessageUtils.showShortToast(this, "搜索" + search_text.getText().toString());
        }

        if(v == feedback_img_btn){
            //MessageUtils.showShortToast(this, "MORE");
            Intent intent = new Intent(this, MallShopCartActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
            //setDetail();
        }

        if(v == more_comment_btn){
            Intent intent = new Intent(this, MallShopCommentActivity.class);
            intent.putExtra("companyId",proDetail.getCompanyId());
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == add_to_cart_btn){

            buyFlg = 1;//立即购买:0 ; 加入购物车:1

//            if((Integer.parseInt(proDetail.getStock())) < 1){
//                MessageUtils.showShortToast(this,"库存不足");
//            } else {

                String productId = proDetail.getId();
                String companyId = proDetail.getCompanyId();
                String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                String productNum = "1";

                cartDao = new CartDao(this);
                showProgress(true);
                cartDao.requestAddToCart(productId,companyId,memberId,productNum);
//            }



        }

        if(v == buy_right_now_btn){
            showBuyPop();
        }

        if(v == pro_favor){
            //是否收藏（0：未收藏 1：已收藏）
            if(proDetail.getCollect() == 0){
                //收藏或取消收藏(0:收藏, 1:取消收藏)
                mallDao.requestFavorPro(proDetail.getId(),AppHolder.getInstance().getMemberInfo().getMemberId(),"0");
            } else {
                //收藏或取消收藏(0:收藏, 1:取消收藏)
                mallDao.requestFavorPro(proDetail.getId(), AppHolder.getInstance().getMemberInfo().getMemberId(), "1");
            }
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
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.member_info_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //toHomePage();
//                MessageUtils.showShortToast(MallShopProDetailActivity.this, "XX");
//                Intent intent = new Intent(MallShopProDetailActivity.this, PayStyleActivity.class);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(MallShopProDetailActivity.this);
//            }
//        });
//        return true;
//    }


    /**
     * 购买 pop
     * */
    private void showBuyPop(){

        mMenuView = LayoutInflater.from(MallShopProDetailActivity.this).inflate(R.layout.choose_buy_pop, null);
        ImageView img = (ImageView)mMenuView.findViewById(R.id.pro_img);
        ImageView close = (ImageView)mMenuView.findViewById(R.id.close);
        ImageView minus = (ImageView)mMenuView.findViewById(R.id.num_minus_btn);
        ImageView add = (ImageView)mMenuView.findViewById(R.id.num_add_btn);
        final TextView price = (TextView)mMenuView.findViewById(R.id.pro_price);
        final TextView num = (TextView)mMenuView.findViewById(R.id.pro_num);
        final TextView submit = (TextView)mMenuView.findViewById(R.id.submit_btn);


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


        Arad.imageLoader.load(S.isNull(listPics.get(0)) ? "aa" : listPics.get(0))
                .placeholder(R.drawable.default_image)
                .into(img);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });

        num.setText("1");
        price.setText(N.toPriceFormate(proDetail.getSellingPrice()));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n = Integer.parseInt(num.getText().toString());
                num.setText("" + (n + 1));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n = Integer.parseInt(num.getText().toString());

                if (n <= 1) {
                    MessageUtils.showShortToast(MallShopProDetailActivity.this, "数量不可为0");
                } else {
                    num.setText("" + (n - 1));
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyFlg = 0;//立即购买:0 ; 加入购物车:1

                String productId = proDetail.getId();
                String companyId = proDetail.getCompanyId();
                String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                String productNum = num.getText().toString();

//                if((Integer.parseInt(productNum)) > (Integer.parseInt(proDetail.getStock()))){
//                    MessageUtils.showShortToast(MallShopProDetailActivity.this,"库存不足");
//                } else {
                    cartDao = new CartDao(MallShopProDetailActivity.this);
                    showProgress(true);
                    cartDao.requestAddToCart(productId, companyId, memberId, productNum);
                    pop.dismiss();
//                }
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
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("rate","" + (10 * 0.5));
            map.put("time","" + (int)(Math.random() * 30));
            map.put("tel","1550023025" + i);
            map.put("content","味道还不错, 送餐速度也挺快, 还有饮料.送货速度很快, 质量也不错" + i);
            map.put("date","2015-03-05 18:23");

            list.add(map);
        }
        return list;
    }

}

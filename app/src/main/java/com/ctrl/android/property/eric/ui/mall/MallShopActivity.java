package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.Comment;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.ui.adapter.MallCommentListAdapter;
import com.ctrl.android.property.eric.ui.my.MyActivity;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

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
public class MallShopActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.scroll_view)//滚动view
    ScrollView scroll_view;

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

    @InjectView(R.id.recent_order_nums)//最近订单数
    TextView recent_order_nums;
    @InjectView(R.id.recent_order_comment_nums)//全部评价人数
    TextView recent_order_comment_nums;

    @InjectView(R.id.shop_tel_btn)//电话号码按钮
    LinearLayout shop_tel_btn;
    @InjectView(R.id.shop_tel)//电话号码
    TextView shop_tel;

    @InjectView(R.id.shop_intro_content)//商铺介绍内容
    TextView shop_intro_content;

    @InjectView(R.id.shop_address_btn)//地址按钮
    LinearLayout shop_address_btn;
    @InjectView(R.id.shop_address)//商铺地址
    TextView shop_address;

    @InjectView(R.id.more_comment_btn)//
    LinearLayout more_comment_btn;
    @InjectView(R.id.listView)//评论列表
    ListView listView;

    private MallCommentListAdapter mAdapter;
    private List<Comment> listComment = new ArrayList<>();

    private String TITLE = StrConstant.SHOP_DETAIL_TITLE;

    private String companyId = "";
    private MallDao mallDao;
    private MallShop mallShopDetail = new MallShop();

    private String collectingType;
    private int evaluate = 0;
    private int orders = 0;
    private float sellersCredit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_shop_activity);
        ButterKnife.inject(this);
        scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        companyId = getIntent().getStringExtra("companyId");
        mallDao = new MallDao(this);
        showProgress(true);
        mallDao.requestShopDetail(companyId, AppHolder.getInstance().getMemberInfo().getMemberId());

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(2 == requestCode){
            mallShopDetail = mallDao.getMallShopDetail();
            sellersCredit = mallShopDetail.getEvaluatLevel();
            evaluate = mallDao.getEvaluate();
            orders = mallDao.getOrders();

            shop_pic.setOnClickListener(this);
            more_comment_btn.setOnClickListener(this);


            Arad.imageLoader.load(S.isNull(mallShopDetail.getLogoUrl()) ? "aa" : mallShopDetail.getLogoUrl())
                    .placeholder(R.drawable.default_image)
                    .into(shop_pic);
            shop_name.setText(S.getStr(mallShopDetail.getCompanyName()));
            shop_business_time.setText(StrConstant.BUSINESS_TIME_TITLE + mallShopDetail.getBusinessStartTime() + "-" + mallShopDetail.getBusinessEndTime());
            shop_ratingBar.setRating(sellersCredit/2);
            right_star.setOnClickListener(this);
            //收藏状态（0：未收藏、1：已收藏）
            if(1 == mallShopDetail.getStatus()){
                right_star.setImageResource(R.drawable.gray_star_none);
            } else {
                right_star.setImageResource(R.drawable.gray_star);
            }

            recent_order_nums.setText(String.valueOf(orders));
            recent_order_comment_nums.setText(String.valueOf(evaluate));

            shop_tel_btn.setOnClickListener(this);
            shop_tel.setText(S.getStr(mallShopDetail.getMobile()));

            shop_intro_content.setText(S.getStr(mallShopDetail.getIntroduction()));

            shop_address_btn.setOnClickListener(this);
            shop_address.setText(S.getStr(mallShopDetail.getAddress()));

            showProgress(true);
            mallDao.requestCommentList(companyId, "1", "10");

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

        if(11 == requestCode){

            listComment = mallDao.getListComment();

            mAdapter = new MallCommentListAdapter(this);
            mAdapter.setList(listComment);
            listView.setAdapter(mAdapter);

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = ((getWindowManager().getDefaultDisplay().getHeight())/3);
            listView.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == shop_tel_btn){
            //MessageUtils.showShortToast(this,"电话号码");
            AndroidUtil.dial(this, mallShopDetail.getMobile());
        }

        if(v == shop_address_btn){
            MessageUtils.showShortToast(this,"商铺地址");
        }

        if(v == right_star){
            //MessageUtils.showShortToast(this,"五角星");
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

        if(v == shop_pic){
            //Intent intent = new Intent(this, MallShopMainActivity.class);
            //startActivity(intent);
            //AnimUtil.intentSlidIn(this);
        }

        if(v == more_comment_btn){
            Intent intent = new Intent(this, MallShopCommentActivity.class);
            intent.putExtra("companyId",companyId);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }
    }


    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.green_arrow_left_none);
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
        rightButton.setImageResource(R.drawable.member_info_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toHomePage();
                //MessageUtils.showShortToast(MallShopActivity.this, "XX");
                Intent intent = new Intent(MallShopActivity.this, MyActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MallShopActivity.this);
            }
        });
        return true;
    }


    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("rate","" + (10 * 0.5));
            map.put("time","" + (int)(Math.random() * 30));
            map.put("tel","1550023025" + i);
            map.put("content","送货速度很快, 质量也不错送货速度很快, 质量也不错送货速度很快, 质量也不错 " + i);
            map.put("date","2015-03-05 18:23");

            list.add(map);
        }
        return list;
    }

}

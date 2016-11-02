package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.MallDao;
import com.ctrl.android.property.eric.entity.Comment;
import com.ctrl.android.property.eric.ui.adapter.MallCommentListAdapter;
import com.ctrl.android.property.eric.ui.adapter.MallShopListAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城的首页面 activity
 * Created by Eric on 2015/9/23.
 */
public class MallShopCommentActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.feedback_btn)//右上角显示反馈
    LinearLayout feedback_btn;
    @InjectView(R.id.feedback_img_btn)//
    ImageView feedback_img_btn;
    @InjectView(R.id.feedback_nums)//右上角反馈数
    TextView feedback_nums;

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private MallCommentListAdapter mallCommentListAdapter;

    private String TITLE = StrConstant.COMMENT_TITLE;

    private int feedBackNum = 5;//反馈数

    //private String companyId;

    private MallDao mallDao;
    private List<Comment> listComment;
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    //private int rowCountPerPage = 1;

    private String companyId;

    SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private MallShopListAdapter mallShopListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_shop_comment_activity);
        //禁止自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        feedback_btn.setVisibility(View.VISIBLE);
        feedback_img_btn.setOnClickListener(this);

        companyId = getIntent().getStringExtra("companyId");

        mallDao = new MallDao(this);
        showProgress(true);
        mallDao.requestCommentList(companyId,String.valueOf(currentPage),String.valueOf(rowCountPerPage));

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(11 == requestCode){
            listComment = mallDao.getListComment();

            mListView = mPullToRefreshListView.getRefreshableView();
            mallCommentListAdapter = new MallCommentListAdapter(this);
            mallCommentListAdapter.setList(listComment);
            mListView.setAdapter(mallCommentListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);
            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    mallDao.getListComment().clear();
                    mallDao.requestCommentList(companyId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    mallDao.requestCommentList(companyId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(getActivity(), "点击了: " + listMap.get(position - 1).get("content"));
                    //Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    //intent.putExtra("orderId",dao.getOrders().get(position - 1).getOrderId());
                    //startActivity(intent);
                    //AnimUtil.intentSlidIn(getActivity());
                }
            });

            showProgress(true);
            mallDao.requestCartProNum(AppHolder.getInstance().getMemberInfo().getMemberId());

        }

        if(12 == requestCode){
            feedBackNum = mallDao.getProNum();
            feedback_nums.setText(String.valueOf(feedBackNum));
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
        showProgress(true);
        mallDao.requestCartProNum(AppHolder.getInstance().getMemberInfo().getMemberId());
    }

    @Override
    public void onClick(View v) {
        if(v == feedback_img_btn){
            Intent intent = new Intent(this, MallShopCartActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
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
//        rightButton.setImageResource(R.drawable.more_info_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(MallShopCommentActivity.this, "MORE");
//            }
//        });
//        return true;
//    }


}

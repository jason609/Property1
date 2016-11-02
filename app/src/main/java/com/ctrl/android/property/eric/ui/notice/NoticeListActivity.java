package com.ctrl.android.property.eric.ui.notice;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.NoticeDao;
import com.ctrl.android.property.eric.entity.Notice;
import com.ctrl.android.property.eric.ui.adapter.NoticeListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告列表 activity
 * Created by Eric on 2015/10/13.
 */
public class NoticeListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private String TITLE = "温馨提示";

    private NoticeListAdapter noticeListAdapter;
    private ListView mListView;
    private NoticeDao dao;
    //private Notice notice;

    private List<Notice> listNotice;

    private String communityId = AppHolder.getInstance().getCommunity().getId();
    private String activityId = AppHolder.getInstance().getProprietor().getProprietorId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.notice_list_activity);
        ButterKnife.inject(this);
        //init();
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
        dao = new NoticeDao(this);
        showProgress(true);
        dao.requestNoticeList(communityId,activityId);//请求公告列表接口

    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(0 == requestCode){

            listNotice = dao.getNoticeList();

            mListView = mPullToRefreshListView.getRefreshableView();
            noticeListAdapter = new NoticeListAdapter(this);
            noticeListAdapter.setList(listNotice);
            mListView.setAdapter(noticeListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mPullToRefreshListView.onRefreshComplete();
                    //mPage = 1;
                    //dao.getOrders().clear();
                    //String memberId = AppHolder.getInstance().getUserInfo().getId();
                    //String reqType = orderType;
                    //showProgress(true);
                    dao.requestNoticeList(communityId,activityId);//请求公告列表接口
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mPullToRefreshListView.onRefreshComplete();
                    //String memberId = AppHolder.getInstance().getUserInfo().getId();
                    //String reqType = orderType;
                    //showProgress(true);
                    //mPage = mPage + 1;
                    dao.requestNoticeList(communityId,activityId);//请求公告列表接口
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(NoticeListActivity.this, NoticeDetailActivity.class);
                    intent.putExtra("title",listNotice.get(position - 1).getNoticeTitle());
                    intent.putExtra("propertNoticeId",listNotice.get(position - 1).getPropertyNoticeId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(NoticeListActivity.this);
                }
            });
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
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


    /**
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.more_info_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
//                showProStyleListPop();
//            }
//        });
//        return true;
//    }

    /**
     * 测试 获取数据的方法
     * */
/*    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("title", i + "公告名称公告名称公告名称公告名称公告名称公告名称公告名称公告名称公告名称");
            map.put("time", "2015-10-0" + (i+1));
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }*/



}

package com.ctrl.android.property.eric.ui.act;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.ActDao;
import com.ctrl.android.property.eric.entity.Act;
import com.ctrl.android.property.eric.ui.adapter.FamilyActListAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 活动列表 activity
 * Created by Eric on 2015/10/13.
 */
public class FamilyActListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private String TITLE = StrConstant.FAMILY_ACTIVITY_TITLE;

    private FamilyActListAdapter familyActListAdapter;
    private ListView mListView;

    private String communityId = "1";
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    //private int rowCountPerPage = 1;
    private ActDao actDao;
    private List<Act> listAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.family_act_list_activity);
        ButterKnife.inject(this);
        //init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        actDao = new ActDao(this);
        showProgress(true);
        //actDao.requestActList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            //MessageUtils.showShortToast(this,"获取成功");

            listAct = actDao.getListAct();

            mListView = mPullToRefreshListView.getRefreshableView();
            familyActListAdapter = new FamilyActListAdapter(this);
            familyActListAdapter.setList(listAct);
            mListView.setAdapter(familyActListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    actDao.getListAct().clear();
                    //actDao.requestActList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    //actDao.requestActList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FamilyActListActivity.this, ActDetailActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(FamilyActListActivity.this);

                }
            });
        }
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
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("title", i + "活动名称");
            map.put("time", "2015-10-0" + (i+1));
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }



}

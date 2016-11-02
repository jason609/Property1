package com.ctrl.android.property.eric.ui.forum;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.ui.notice.NoticeDetailActivity;
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
 * 公告列表 activity
 * Created by Eric on 2015/10/13.
 */
public class ForumeSsenceActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private String TITLE = StrConstant.SSENCE_AREA_TITLE;

    //private NoticeListAdapter noticeListAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_ssence_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        mListView = mPullToRefreshListView.getRefreshableView();

        //noticeListAdapter = new NoticeListAdapter(this);
        //noticeListAdapter.setList(getListMap());
        //mListView.setAdapter(noticeListAdapter);
        mListView.setDivider(null);
        mListView.setDividerHeight(20);

        //注册上下拉定义事件
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //mPage = 1;
                //dao.getOrders().clear();
                //String memberId = AppHolder.getInstance().getUserInfo().getId();
                //String reqType = orderType;
                //showProgress(true);
                //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //String memberId = AppHolder.getInstance().getUserInfo().getId();
                //String reqType = orderType;
                //showProgress(true);
                //mPage = mPage + 1;
                //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ForumeSsenceActivity.this, NoticeDetailActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(ForumeSsenceActivity.this);

            }
        });

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
    @Override
    public boolean setupToolBarRightButton(final ImageView rightButton) {
        rightButton.setImageResource(R.drawable.forum_write_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
                //showProStyleListPop();
                showForumPopWindow(rightButton);
            }
        });
        return true;
    }

    /**
     * 显示选项
     * */
    private void showForumPopWindow(View view) {
        View contentView= LayoutInflater.from(this).inflate(R.layout.pop_window_forum_write,null);
        TextView popwindow_start_note_btn=(TextView)contentView.findViewById(R.id.popwindow_start_note_btn);
        TextView popwindow_start_activity_btn=(TextView)contentView.findViewById(R.id.popwindow_start_activity_btn);

        popwindow_start_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(ForumeSsenceActivity.this, "发表帖子");
                Intent intent = new Intent(ForumeSsenceActivity.this, ForumStartNoteActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(ForumeSsenceActivity.this);
            }
        });
        popwindow_start_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(ForumeSsenceActivity.this, "发起活动");
                Intent intent = new Intent(ForumeSsenceActivity.this, ForumStartActActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(ForumeSsenceActivity.this);
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_shap_green));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, -140, 60);

    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("title", i + "公告名称公告名称公告名称公告名称公告名称公告名称公告名称公告名称公告名称");
            map.put("time", "2015-10-0" + (i+1));
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }



}

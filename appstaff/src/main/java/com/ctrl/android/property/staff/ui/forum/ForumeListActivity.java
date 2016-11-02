package com.ctrl.android.property.staff.ui.forum;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.ForumDao;
import com.ctrl.android.property.staff.entity.ForumNote;
import com.ctrl.android.property.staff.ui.adapter.ForumListtAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛帖子列表(通用) activity
 * Created by Eric on 2015/10/27.
 */
public class ForumeListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ForumListtAdapter forumListtAdapter;

    private ForumDao forumDao;

    private String TITLE = "";

    //private NoticeListAdapter noticeListAdapter;
    private ListView mListView;
    private List<ForumNote> listNote;
    private String categoryId_extra;

    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    //private int rowCountPerPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_list_activity);
        ButterKnife.inject(this);
        TITLE = getIntent().getStringExtra("title");
        categoryId_extra = getIntent().getStringExtra("categoryId");
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

        forumDao = new ForumDao(this);
        showProgress(true);
        String categoryId = categoryId_extra;
        String memberId = "";
        String handleStatus = "";
        String verifyStatus = "";
        forumDao.requestForumNoteList(categoryId, memberId, handleStatus, verifyStatus, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(1 == requestCode){

            listNote = forumDao.getListForumNote();

            mListView = mPullToRefreshListView.getRefreshableView();
            forumListtAdapter = new ForumListtAdapter(this);
            forumListtAdapter.setList(listNote);
            mListView.setAdapter(forumListtAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    forumDao.getListForumNote().clear();
                    String categoryId = categoryId_extra;
                    String memberId = "";
                    String handleStatus = "";
                    String verifyStatus = "";
                    //showProgress(true);
                    forumDao.requestForumNoteList(categoryId, memberId, handleStatus, verifyStatus, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    String categoryId = categoryId_extra;
                    String memberId = "";
                    String handleStatus = "";
                    String verifyStatus = "";
                    currentPage = currentPage + 1;
                    forumDao.requestForumNoteList(categoryId, memberId, handleStatus, verifyStatus, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(ForumeListActivity.this,listNote.get(position-1).getForumPostId());
                    Intent intent = new Intent(ForumeListActivity.this, ForumDetailActivity.class);
                    //intent.putExtra("title",TITLE);
                    intent.putExtra("forumPostId",listNote.get(position-1).getForumPostId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(ForumeListActivity.this);

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
                Intent intent = new Intent(ForumeListActivity.this, ForumStartNoteActivity.class);
                intent.putExtra("categoryId",categoryId_extra);
                startActivity(intent);
                AnimUtil.intentSlidIn(ForumeListActivity.this);
            }
        });
        return true;
    }

    /**
     * 显示选项
     * */
//    private void showForumPopWindow(View view) {
//        View contentView= LayoutInflater.from(this).inflate(R.layout.pop_window_forum_write,null);
//        TextView popwindow_start_note_btn=(TextView)contentView.findViewById(R.id.popwindow_start_note_btn);
//        TextView popwindow_start_activity_btn=(TextView)contentView.findViewById(R.id.popwindow_start_activity_btn);
//
//        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
//
//        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(dw);
//
//        // 设置好参数之后再show
//        popupWindow.showAsDropDown(view, 0, 0);
//        //popupWindow.showAsDropDown(view);
//        popwindow_start_note_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //MessageUtils.showShortToast(ForumeSsenceActivity.this, "发表帖子");
//                Intent intent = new Intent(ForumeListActivity.this, ForumStartNoteActivity.class);
//                intent.putExtra("categoryId",categoryId_extra);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(ForumeListActivity.this);
//                popupWindow.dismiss();
//            }
//        });
//        popwindow_start_activity_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //MessageUtils.showShortToast(ForumeSsenceActivity.this, "发起活动");
//                Intent intent = new Intent(ForumeListActivity.this, ForumStartActActivity.class);
//                intent.putExtra("categoryId",categoryId_extra);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(ForumeListActivity.this);
//                popupWindow.dismiss();
//            }
//        });
//
//
//    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("title", i + "帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称帖子名称");
            map.put("content", "\u3000\u3000帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容帖子内容");
            map.put("member",i + "发表人");
            map.put("time", "2015-10-0" + (i+1));
            map.put("favor",i + "000");
            map.put("look",(i+1) + "000");
            map.put("comment",(i+2) + "000");

            list.add(map);
        }
        return list;
    }



}

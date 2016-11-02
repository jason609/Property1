package com.ctrl.android.property.staff.ui.forum;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.ForumDao;
import com.ctrl.android.property.staff.entity.ForumNoteComment;
import com.ctrl.android.property.staff.entity.ForumNoteDetail;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.ui.adapter.ForumDetailAdapter;
import com.ctrl.android.property.staff.ui.widget.ImageZoomActivity;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛 帖子内容 页面
 * Created by Eric on 2015/10/26
 */
public class ForumDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.forum_title)//帖子标题
    TextView forum_title;
    @InjectView(R.id.forum_favor_num)//赞数量
    TextView forum_favor_num;
    @InjectView(R.id.forum_look_num)//看数量
    TextView forum_look_num;
    @InjectView(R.id.forum_comment_num)//回帖数量
    TextView forum_comment_num;
    @InjectView(R.id.forum_writer)//帖子作者
    TextView forum_writer;
    @InjectView(R.id.forum_time)//帖子发布时间
    TextView forum_time;
    @InjectView(R.id.forum_content)//帖子内容
    TextView forum_content;
    @InjectView(R.id.forum_img)
    ImageView forum_img;
    @InjectView(R.id.forum_like_btn)//赞
    ImageView forum_like_btn;
    @InjectView(R.id.forum_comment_text)//评论内容
    EditText forum_comment_text;
    @InjectView(R.id.forum_comment_submit)//帖子评论按钮
    TextView forum_comment_submit;

    @InjectView(R.id.listView)//
    ListView listView;

    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position = 0;

    /**
     * 图片列表
     * */
    private List<Img> listImg = new ArrayList<>();

    private String TITLE = "内部交流公告";

    private String memberId = AppHolder.getInstance().getStaffInfo().getStaffId();//用户id 具体问题具体分析
    private String forumPostId;

    private ForumDetailAdapter forumDetailAdapter;
    private ForumDao forumDao;
    private ForumNoteDetail forumNoteDetail;
    private List<ForumNoteComment> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_detail_activity);
        ButterKnife.inject(this);
        /**首次进入该页不让弹出软键盘*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }

    private void init(){

        forum_like_btn.setOnClickListener(this);
        forum_comment_submit.setOnClickListener(this);

        //TITLE = getIntent().getStringExtra("title");
        forumPostId = getIntent().getStringExtra("forumPostId");

        forumDao = new ForumDao(this);
        showProgress(true);
        forumDao.requestNoteDetail(forumPostId, memberId);

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(2 == requestCode){
            //MessageUtils.showShortToast(this,"请求成功");
            forumNoteDetail = forumDao.getForumNoteDetail();
            listComment = forumDao.getListForumNoteComment();
            listImg = forumDao.getListImg();

            forum_title.setText(S.getStr(forumNoteDetail.getTitle()));
            forum_favor_num.setText(S.getStr(forumNoteDetail.getPraiseNum()));
            forum_look_num.setText(S.getStr(forumNoteDetail.getReadNum()));
            forum_comment_num.setText(S.getStr(forumNoteDetail.getReplyNum()));
            forum_writer.setText(S.getStr(forumNoteDetail.getMemberName()));
            forum_time.setText(D.getDateStrFromStamp(Constant.YYYY_MM_DD_HH_MM, forumNoteDetail.getCreateTime()));
            forum_content.setText(S.getStr(forumNoteDetail.getContent()));

            if(listImg == null || listImg.size() < 1){
                forum_img.setVisibility(View.GONE);
//                Arad.imageLoader.load("aa")
//                        .placeholder(R.drawable.default_image)
//                        .into(forum_img);
            } else {
                Arad.imageLoader.load(listImg.get(0).getZipImg() == null || (listImg.get(0).getZipImg()).equals("") ? "aa" : listImg.get(0).getZipImg())
                        .placeholder(R.drawable.default_image)
                        .into(forum_img);

                forum_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagelist = new ArrayList<String>();
                        imagelist.add(listImg.get(0).getOriginalImg());
                        Intent intent = new Intent(ForumDetailActivity.this, ImageZoomActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("imageList", imagelist);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });


            }

            forumDetailAdapter = new ForumDetailAdapter(this);
            forumDetailAdapter.setList(listComment);
            listView.setAdapter(forumDetailAdapter);
            //listView.setDivider(null);
            //listView.setDividerHeight(20);
        }

        if(3 == requestCode){
            forum_comment_text.setText("");
            MessageUtils.showShortToast(this, "回复成功");
            init();
        }

        if(6 == requestCode){
            MessageUtils.showShortToast(this,"点赞成功");
            init();
        }

        if(7 == requestCode){
            MessageUtils.showShortToast(this,"取消点赞");
            init();
        }

    }

    @Override
    public void onClick(View v) {
        if(v == forum_like_btn){
            //MessageUtils.showShortToast(this,"赞");
            //是否赞过（0：没有 1：赞过）
            if(forumNoteDetail.getIsPraise() == 0){
                forumDao.requestLikeNote(forumNoteDetail.getForumPostId(),AppHolder.getInstance().getStaffInfo().getStaffId());
            } else {
                forumDao.requestCancelLikeNote(forumNoteDetail.getForumPostId(), AppHolder.getInstance().getStaffInfo().getStaffId());
            }
        }

        if(v == forum_comment_submit){

            String postId = forumPostId;
            String pid = "";
            //String memberId = "";
            String replyContent = forum_comment_text.getText().toString();
            String receiverId = "";

            if(replyContent == null || replyContent.equals("")){
                MessageUtils.showShortToast(this,"请输入评论内容");
            } else {
                forumDao.requestCommentNote(postId, pid, memberId, replyContent, receiverId);
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
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }

    private boolean checkInput(){
        if(S.isNull(forum_comment_text.getText().toString())){
            MessageUtils.showShortToast(this,"请输入评论内容");
            return false;
        }
        return true;
    }


}

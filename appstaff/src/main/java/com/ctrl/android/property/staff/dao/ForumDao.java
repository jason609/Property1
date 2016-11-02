package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.ForumCategory;
import com.ctrl.android.property.staff.entity.ForumNote;
import com.ctrl.android.property.staff.entity.ForumNoteComment;
import com.ctrl.android.property.staff.entity.ForumNoteDetail;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 论坛 dao
 * Created by Eric on 2015/10/28.
 */
public class ForumDao extends IDao {

    /**
     * 所有板块名列表
     * */
    private List<ForumCategory> listForumCategory = new ArrayList<>();

    /**
     * 所有帖子列表
     * */
    private List<ForumNote> listForumNote = new ArrayList<>();

    /**
     * 帖子详细内容
     * */
    private ForumNoteDetail forumNoteDetail = new ForumNoteDetail();

    /**
     * 回复帖子列表
     * */
    private List<ForumNoteComment> listForumNoteComment = new ArrayList<>();

    /**
     * 图片列表
     * */
    private List<Img> listImg = new ArrayList<>();

    public ForumDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取所有板块m
     * @param communityId 社区id
     * */
    public void requestForumAreaList(String communityId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.forumCategory.queryForumCategoryList");//方法名称

        map.put("communityId",communityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取所有帖子列表m
     * @param categoryId 社区版块id
     * @param memberId 发帖人id（会员id）
     * @param handleStatus 帖子状态（0：未结帖、1：已结帖）
     * @param verifyStatus 审核状态（0：待审核、1：审核通过、2：审核未通过）
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestForumNoteList(String categoryId,String memberId,String handleStatus,
                                     String verifyStatus,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.forumPost.queryForumPostList");//方法名称

        map.put("categoryId", categoryId);
        map.put("memberId", memberId);
        map.put("handleStatus", handleStatus);
        map.put("verifyStatus", verifyStatus);
        map.put("currentPage", currentPage);
        map.put("rowCountPerPage", rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL,mapToRP(map),1);
    }

    /**
     * 获取帖子详细m
     * @param forumPostId 论坛帖子id
     * @param memberId 当前用户id
     * */
    public void requestNoteDetail(String forumPostId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.forumPost.queryForumPostDetl");//方法名称

        map.put("forumPostId",forumPostId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 回复帖子m
     * @param postId 帖子id
     * @param pid 父帖子回复id
     * @param memberId 回复者id
     * @param replyContent 回复内容
     * @param receiverId 被回复者id
     * */
    public void requestCommentNote(String postId,String pid,String memberId,
                                   String replyContent,String receiverId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.postReply.addPostReply");//方法名称

        map.put("postId",postId);
        map.put("pid",pid);
        map.put("memberId",memberId);
        map.put("replyContent",replyContent);
        map.put("receiverId",receiverId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 发表帖子m
     * @param categoryId 论坛版块id
     * @param memberId 用户id
     * @param title 帖子标题
     * @param content 帖子内容
     * @param picUrlStr1 论坛图片urlString串1（原图、缩略图，url间用逗号分开
     * @param picUrlStr2 论坛图片urlString串2（原图、缩略图，url间用逗号分开
     * @param picUrlStr3 论坛图片urlString串3（原图、缩略图，url间用逗号分开
     * */
    public void requestReleaseNote(String categoryId,String memberId,String title, String content,
                                   String picUrlStr1,String picUrlStr2, String picUrlStr3){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.forumPost.addForumPost");//方法名称

        map.put("categoryId",categoryId);
        map.put("memberId",memberId);
        map.put("title",title);
        map.put("content",content);
        map.put("picUrlStr1",picUrlStr1);
        map.put("picUrlStr2",picUrlStr2);
        map.put("picUrlStr3",picUrlStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }


    /**
     * 发布活动
     * @param communityId 社区id
     * @param memberId 会员id
     * @param title 活动标题
     * @param startTime 开始时间(yyyy-MM-dd HH:mm:ss)
     * @param endTime 结束时间(yyyy-MM-dd HH:mm:ss)
     * @param location 活动地点
     * @param content 活动内容
     * @param actionPicUrlStr1 活动图片1（原图Url + “,”+ 缩略图Url）
     * @param actionPicUrlStr2 活动图片2（原图Url + “,”+ 缩略图Url）
     * @param actionPicUrlStr3 活动图片3（原图Url + “,”+ 缩略图Url）
     * */
    public void requestStartAct(String communityId,String memberId,String title
                    ,String startTime,String endTime,String location,String content,
                    String actionPicUrlStr1,String actionPicUrlStr2,String actionPicUrlStr3){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.action.addAction");//方法名称

        map.put("communityId",communityId);
        map.put("memberId",memberId);
        map.put("title",title);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("location",location);
        map.put("content",content);
        map.put("actionPicUrlStr1",actionPicUrlStr1);
        map.put("actionPicUrlStr2",actionPicUrlStr2);
        map.put("actionPicUrlStr3",actionPicUrlStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 5);
    }

    /**
     * 点赞帖子
     * @param postId 论坛版块id
     * @param memberId 用户id
     * */
    public void requestLikeNote(String postId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.PostPraise.addPostPraise");//方法名称

        map.put("postId",postId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 6);
    }

    /**
     * 取消点赞帖子
     * @param postId 论坛版块id
     * @param memberId 用户id
     * */
    public void requestCancelLikeNote(String postId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.PostPraise.delPostPraise");//方法名称

        map.put("postId",postId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 7);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(板块列表): " + result);
            listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(帖子列表): " + result);
            List<ForumNote> list = new ArrayList<>();
            list = JsonUtil.node2pojoList(result.findValue("forumPostList"), ForumNote.class);
            listForumNote.addAll(list);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(帖子内容): " + result);
            forumNoteDetail = JsonUtil.node2pojo(result.findValue("queryPostInfo"), ForumNoteDetail.class);
            listImg = JsonUtil.node2pojoList(result.findValue("postPictureList"), Img.class);
            listForumNoteComment = JsonUtil.node2pojoList(result.findValue("replyList"), ForumNoteComment.class);

        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(回帖): " + result);
            //listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

        if(requestCode == 4){
            Log.d("demo","dao中结果集(发帖): " + result);
            //listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

        if(requestCode == 5){
            Log.d("demo","dao中结果集(发布活动): " + result);
            //listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

        if(requestCode == 6){
            Log.d("demo","dao中结果集(点赞帖子): " + result);
            listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

        if(requestCode == 7){
            Log.d("demo","dao中结果集(取消点赞): " + result);
            listForumCategory = JsonUtil.node2pojoList(result.findValue("forumCategoryList"), ForumCategory.class);
        }

    }

    public List<ForumCategory> getListForumCategory() {
        return listForumCategory;
    }

    public List<ForumNote> getListForumNote() {
        return listForumNote;
    }

    public ForumNoteDetail getForumNoteDetail() {
        return forumNoteDetail;
    }

    public List<ForumNoteComment> getListForumNoteComment() {
        return listForumNoteComment;
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

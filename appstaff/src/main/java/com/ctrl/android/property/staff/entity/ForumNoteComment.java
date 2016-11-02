package com.ctrl.android.property.staff.entity;

/**
 * 帖子评论
 * Created by Eric on 2015/10/29.
 */
public class ForumNoteComment {

    private String postReplyId;//回帖id
    private String memberId;//回复者id
    private String replyContent;//回复内容
    private String createTime;//回复时间
    private String receiverId;//被回复者id
    private String memberName;//用户名称
    private String receiverName;//被回复用户名称

    public String getPostReplyId() {
        return postReplyId;
    }

    public void setPostReplyId(String postReplyId) {
        this.postReplyId = postReplyId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}

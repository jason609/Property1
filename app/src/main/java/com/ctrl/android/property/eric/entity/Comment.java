package com.ctrl.android.property.eric.entity;

/**
 * 评价
 * Created by Eric on 2015/11/20.
 */
public class Comment {

    private int level;//评价级别（1：极差、2：差评、3：中评、4：好评、5：极好）
    private String content;//评价内容
    private String createTime;//评价时间
    private String replyContent;//商家回复内容
    private String deliveryDuration;//配送时常
    private String mobile;//买家手机号

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(String deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

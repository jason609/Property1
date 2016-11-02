package com.ctrl.android.property.eric.entity;

/**
 * 调查 实体
 * Created by Eric on 2015/11/4.
 */
public class Survey {

    private String id;//问卷调查/投票id
    private String title;//问卷调查/投票标题
    private String createTime;//创建时间
    private int hasParticipate;//是否参与（0：未参与、1：已参与）
    private String communityName;//地点（社区名称）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getHasParticipate() {
        return hasParticipate;
    }

    public void setHasParticipate(int hasParticipate) {
        this.hasParticipate = hasParticipate;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}

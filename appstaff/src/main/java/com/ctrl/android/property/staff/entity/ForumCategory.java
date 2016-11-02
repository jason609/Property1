package com.ctrl.android.property.staff.entity;

/**
 * 论坛板块
 * Created by Eric on 2015/10/28.
 */
public class ForumCategory {

    private String forumCategoryId;//论坛版块（分类）id
    private String communityId;//社区id
    private String name;//版块（分类）名称
    private String introduction;//版块（分类）介绍
    private String imgUrl = "aa";//版块（分类）图片url
    private String categoryType;//版块（分类）类型（0：业主论坛、1：物业员工论坛）
    private String managerId;//板块（分类）管理员id
    private String remark;//备注
    private int count;//本版块（分类）帖子数量

    public String getForumCategoryId() {
        return forumCategoryId;
    }

    public void setForumCategoryId(String forumCategoryId) {
        this.forumCategoryId = forumCategoryId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package com.ctrl.android.property.staff.entity;

/**
 * Created by jason on 2015/12/2.
 *
 * 巡更巡查点实体
 */
public class Point {
    private  String patrolRoutePointId;//巡更路线巡查点id
    private  String communityId;//社区id
    private  String routeId;//路线id
    private  String pointId;//巡查点id
    private  int sortNum;//序号
    private  String pointName;//巡查点名称
    private  String createTime;//巡查点创建时间
    private  String name;//巡查点名称（和上边重复）
    private  String keyMessage;//巡查点信息
    private  String handleState;//处理状态（0：未巡查、1：已巡查）

    public String getHandleState() {
        return handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    public String getPatrolRoutePointId() {
        return patrolRoutePointId;
    }

    public void setPatrolRoutePointId(String patrolRoutePointId) {
        this.patrolRoutePointId = patrolRoutePointId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}

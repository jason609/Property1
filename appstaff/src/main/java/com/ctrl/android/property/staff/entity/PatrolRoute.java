package com.ctrl.android.property.staff.entity;

/**
 * Created by jason on 2015/12/2.
 *
 * 巡更巡查实体
 */
public class PatrolRoute {
    private  String patrolRouteStaffId;//巡更巡查id
    private  String routeId;//巡更路线id
    private  String staffId;//巡更巡查者id  员工id
    private  String status;//巡更巡查状态
    private  String createTime;//开始巡查时间
    private  String finishTime;//完成巡查时间
    private  String routeName;//路线名称
    private  String staffName;//员工姓名
    private  String routeStatus;//路线性质 0：固定  1：灵活
    private  int pointNum;//巡更点个数
    private  int pointNumDo;//已完成巡更点个数

    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    public String getPatrolRouteStaffId() {
        return patrolRouteStaffId;
    }

    public void setPatrolRouteStaffId(String patrolRouteStaffId) {
        this.patrolRouteStaffId = patrolRouteStaffId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public int getPointNumDo() {
        return pointNumDo;
    }

    public void setPointNumDo(int pointNumDo) {
        this.pointNumDo = pointNumDo;
    }
}

package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.PatrolRoute;
import com.ctrl.android.property.staff.entity.Point;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡更巡查 dao
 * Created by jason on 2015/11/2
 */
public class PatrolDao extends IDao {

    /**
     * 巡更巡查列表
     * */
    private List<PatrolRoute> routeList = new ArrayList<>();
    private List<Point> pointList = new ArrayList<>();
    private PatrolRoute patrolRoute;
    private String nextPoint;

    public PatrolDao(INetResult activity) {
        super(activity);
    }


    /**
     * 获取巡更巡查列表
     * @param staffId 员工id（维修人员id）
     * @param patrolType 巡查查询状态（a：未巡查 b:已巡查），不传则查询全部
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestPatrolList(String staffId,String patrolType,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.patrolRouteStaff.queryPatrolRouteStaffList");//方法名称

        map.put("staffId",staffId);
        map.put("patrolType",patrolType);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取巡更巡查详情
     * @param patrolRouteStaffId 巡更巡查id
     * @param routeStatus 路线性质 0：固定 1：灵活
     * @param patrolRouteStaffId 员工id  (查灵活路线详情时用)
     * */
    public void requestPatrolDetail(String patrolRouteStaffId,String routeStatus,String staffId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.patrolRouteStaff.queryPatrolRouteStaffDetl");//方法名称

        map.put("patrolRouteStaffId",patrolRouteStaffId);
        map.put("routeStatus",routeStatus);
        map.put("staffId",staffId);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }
    /**
     * 新增员工巡更路线巡更点信息
     * @param communityId 社区id
     * @param patrolRouteStaffId 员工巡更路线id
     * @param staffId 员工id
     * @param routeId 路线id
     * @param pointId 巡更点id
     * @param message 备注
     * @param picUrlStr 巡更点图片urlString串（先原图、后缩略图，中间以逗号分隔）
     * */
    public void requestPatrolRoutePointAdd(String communityId,
                                          String patrolRouteStaffId,
                                          String staffId,
                                          String routeId,
                                          String pointId,
                                          String message,
                                          String picUrlStr
                                           ){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.patrolPointStaff.addPatrolPointStaffInfo");//方法名称

        map.put("communityId",communityId);
        map.put("patrolRouteStaffId",patrolRouteStaffId);
        map.put("staffId",staffId);
        map.put("routeId",routeId);
        map.put("pointId",pointId);
        map.put("message",message);
        map.put("picUrlStr",picUrlStr);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }




    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(访问列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            List<PatrolRoute> list= new ArrayList<>();
            list = JsonUtil.node2pojoList(result.findValue("patrolRouteList"), PatrolRoute.class);
           if(null!=list) {
               routeList.addAll(list);
           }
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(访问详情): " + result);
            patrolRoute = JsonUtil.node2pojo(result.findValue("patrolRouteStaffInfo"), PatrolRoute.class);
            nextPoint = result.findValue("nextPatrolRoutePointId").asText();
            List<Point>list=new ArrayList<>();
            list=JsonUtil.node2pojoList(result.findValue("pointList"), Point.class);
            pointList.addAll(list);
        }
    }

    public List<PatrolRoute> getPatrolRouteList() {
        return routeList;
    }
    public List<Point> getPointList() {
        return pointList;
    }
    public PatrolRoute getPatrolRoute(){return patrolRoute;}
    public String getNextPoint(){return nextPoint;}



}

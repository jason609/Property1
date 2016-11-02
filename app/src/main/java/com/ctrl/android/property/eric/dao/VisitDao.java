package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.Visit;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 到访 dao
 * Created by Eric on 2015/11/2
 */
public class VisitDao extends IDao {

    /**
     * 访问列表
     * */
    private List<Visit> listVisit = new ArrayList<>();

    private Visit visitDetail;

    private List<Img> listImg = new ArrayList<>();

    public VisitDao(INetResult activity){
        super(activity);
    }

    /**
     * 到访列表
     * @param proprietorId 业主id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestVisitList(String proprietorId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityVisit.queryCommunityVisitList");//方法名称

        map.put("proprietorId",proprietorId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 到访信息
     * @param communityVisitId 社区到访id
     * */
    public void requestVisitDetail(String communityVisitId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityVisit.queryCommunityVisitInfo");//方法名称

        map.put("communityVisitId",communityVisitId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 添加到访信息
     * @param communityId 社区id
     * @param proprietorId 业主id
     * @param addressId 业主住址id
     * @param visitorName 到访人姓名
     * @param arriveTime 到访时间
     * @param peopleNum 到访人数（大于等于0）
     * @param numberPlates 车牌号码
     * @param residenceTime 预计停留时间（注：单位已输入为准）
     * */
    public void requestAddVisit(String communityId,String proprietorId,
                                String addressId,String visitorName,
                                String arriveTime,String peopleNum,
                                String numberPlates,String residenceTime){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityVisit.addCommunityVisit");//方法名称

        map.put("communityId",communityId);
        map.put("proprietorId",proprietorId);
        map.put("addressId",addressId);
        map.put("visitorName",visitorName);
        map.put("arriveTime",arriveTime);
        map.put("peopleNum",peopleNum);
        map.put("numberPlates",numberPlates);
        map.put("residenceTime",residenceTime);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 处理到访信息
     * @param communityVisitId 社区到访id
     * @param handleStatus 处理状态（1：同意到访、2：拒绝到访、3：我不在家）
     * */
    public void requestHandleVisit(String communityVisitId,String handleStatus){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityVisit.handleCommunityVisit");//方法名称

        map.put("communityVisitId",communityVisitId);
        map.put("handleStatus",handleStatus);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(访问列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            List<Visit> list= new ArrayList<>();
            list = JsonUtil.node2pojoList(result.findValue("communityVisitList"), Visit.class);
            listVisit.addAll(list);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(访问详情): " + result);
            listImg = JsonUtil.node2pojoList(result.findValue("picList"), Img.class);
            visitDetail = JsonUtil.node2pojo(result.findValue("visitInfo"), Visit.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(添加访问): " + result);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(处理访问): " + result);
        }



    }

    public List<Visit> getListVisit() {
        return listVisit;
    }

    public Visit getVisitDetail() {
        return visitDetail;
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

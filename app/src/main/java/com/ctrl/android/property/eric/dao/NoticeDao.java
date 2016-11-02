package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.Notice;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知公告
 * Created by Administrator on 2015/10/28.
 */
public class NoticeDao extends IDao {
    private List<Notice> noticeList= new ArrayList<>();
    private Notice notice;

    private List<Img> listImg = new ArrayList<>();

    public List<Notice> getNoticeList(){
        return noticeList;
    }
    public Notice getNotice(){
        return notice;
    }
    public NoticeDao(INetResult activity) {
        super(activity);
    }

    /**
     * 获取物业通知公告列表
     * @param communityId 社区ID
     * @param activityId 业主或者员工id
     */
    public void requestNoticeList(String communityId,String activityId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyNotice.queryPropertyNoticeList");
        map.put("communityId", communityId);
        map.put("activityId", activityId);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取物业通知公告详情
     * @param propertyNoticeId 通知公告ID
     * @param activityId 业主id
     * */
    public void requestNotice(String propertyNoticeId,String activityId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyNotice.queryPropertyNoticeInfo");

        map.put("propertyNoticeId", propertyNoticeId);
        map.put("activityId", activityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);

        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 更改公告状态为已读
     * @param propertyNoticeId 通知公告ID
     * @param activityId 业主id
     */
    public void requestNoticeModify(String propertyNoticeId,String activityId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberNoticeRelation.updatePropertyNoticeStatus");

        map.put("propertyNoticeId", propertyNoticeId);
        map.put("activityId", activityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo", "dao中结果集(公告列表): " + result);
            List<Notice> data = JsonUtil.node2pojoList(result.findValue("propertyNoticeList"), Notice.class);
            noticeList.addAll(data);
        }
        if(requestCode == 1){
            Log.d("demo", "dao中结果集(公告详细): " + result);
            notice = JsonUtil.node2pojo(result.findValue("propertyNoticeInfo"), Notice.class);
            listImg = JsonUtil.node2pojoList(result.findValue("propertyNoticeImage"), Img.class);
        }
        if(requestCode == 2){
            Log.d("demo", "dao中结果集(签收公告): " + result);
            //notice = JsonUtil.node2pojo(result.findValue("propertyNoticeInfo"), Notice.class);
        }
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

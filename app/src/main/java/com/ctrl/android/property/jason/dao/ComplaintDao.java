package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.Complaint;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.Img;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投诉
 * Created by Administrator on 2015/11/2.
 */
public class ComplaintDao extends IDao{
    private List<Complaint> complaintList = new ArrayList<>();
    private Complaint complaint;
    private List<GoodPic> complaintPicList = new ArrayList<>();
    private List<Img> complaintResultPicList = new ArrayList<>();

    public List<Complaint> getComplaintList() {
        return complaintList;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public List<GoodPic> getComplaintPicList() {
        return complaintPicList;
    }

    public List<Img> getComplaintResultPicList() {
        return complaintResultPicList;
    }

    public ComplaintDao(INetResult activity) {
        super(activity);
    }

    /**
     * 获取投诉信息列表
     * @param proprietorId 业主ID
     * @param handleStatus 处理状态（0：处理中、1：已处理、2：已结束）
     * @param currentPage
     */
    public void requestComplaintList(String proprietorId,String handleStatus,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.complaint.list");
        params.put("proprietorId", proprietorId);
        params.put("handleStatus", handleStatus);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "20");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 获取投诉信息详情
     * @param id 投诉id
     */
    public void requestComplaint(String id) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.complaint.get");
        params.put("id", id);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 业主发起投诉
     * @param proprietorId 业主ID
     * @param communityId 社区ID
     * @param addressId 社区住址ID
     * @param complaintKindId 投诉类型
     * @param content 投诉详情
     * @param complaintPicUrl1 投诉图片1
     * @param complaintPicUrl2 投诉图片2
     * @param complaintPicUrl3 投诉图片3
     */
    public void requestComplaintAdd(String proprietorId,String communityId,String addressId,String complaintKindId,String content,String complaintPicUrl1,String complaintPicUrl2,String complaintPicUrl3) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.complaint.complaint");
        params.put("proprietorId", proprietorId);
        params.put("communityId", communityId);
        params.put("addressId", addressId);
        params.put("complaintKindId", complaintKindId);
        params.put("content", content);
        params.put("complaintPicUrl1", complaintPicUrl1);
        params.put("complaintPicUrl2", complaintPicUrl2);
        params.put("complaintPicUrl3", complaintPicUrl3);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),2);
    }

    /**
     * 业主对投诉信息进行评价
     * @param id 投诉id
     * @param evaluateLevel 评价等级（0：非常满意、1：基本满意、2：不满意）
     * @param evaluateContent 评价内容
     */
    public void requestComplaintEvaluate(String id,String evaluateLevel,String evaluateContent) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.complaint.evaluate");
        params.put("id", id);
        params.put("evaluateLevel", evaluateLevel);
        params.put("evaluateContent", evaluateContent);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),3);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            List<Complaint> data = JsonUtil.node2pojoList(result.findValue("complaintList"), Complaint.class);
            complaintList.addAll(data);
        }
        if(requestCode == 1){
            complaint = JsonUtil.node2pojo(result.findValue("complaintInfo"), Complaint.class);
            List<GoodPic> complaintPics = JsonUtil.node2pojoList(result.findValue("complaintPicList"), GoodPic.class);
            if(null!=complaintPics)
            complaintPicList.addAll(complaintPics);
            List<Img> complaintResultPics = JsonUtil.node2pojoList(result.findValue("complaintResultPicList"), Img.class);
            if(null != complaintResultPics)
            complaintResultPicList.addAll(complaintResultPics);

        }
    }
}

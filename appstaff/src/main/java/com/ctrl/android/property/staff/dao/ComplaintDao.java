package com.ctrl.android.property.staff.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Complaint;
import com.ctrl.android.property.staff.entity.ComplaintInfo;
import com.ctrl.android.property.staff.entity.GoodPic;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报修
 * Created by Administrator on 2015/11/2.
 */
public class ComplaintDao extends IDao{
    private List<Complaint> mComplaintsList = new ArrayList<>();
    private List<GoodPic> mGoodPicList = new ArrayList<>();
    private List<GoodPic> mGoodPicResultList = new ArrayList<>();
    private ComplaintInfo mComplaintInfo;
    private List<Complaint> assignList=new ArrayList<>();

    public List<Complaint> getAssignList() {
        return assignList;
    }

    public List<Complaint> getComplaintsList() {
        return mComplaintsList;
    }

    public ComplaintInfo getComplaint() {
        return mComplaintInfo;
    }

    public List<GoodPic> getGoodPicList() {
        return mGoodPicList;
    }

    public List<GoodPic> getGoodPicResultList() {
        return mGoodPicResultList;
    }

    public ComplaintDao(INetResult activity) {
        super(activity);
    }
    /**
     * 获取投诉信息列表
     * @param staffId 员工ID
     * @param handleStatus 处理状态（0：处理中、1：已处理、2：已结束）
     * @param currentPage
     */
    public void requestComplaintList(String staffId,String handleStatus,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.complaintList");
        params.put("staffId", staffId);
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
    public void requestComplait(String id) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.get");
        params.put("id", id);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 物业投诉接受、拒单、退单
     * @param complaintId 投诉ID
     * @param staffId 维修人员ID
     * @param acceptState 任务接收状态  1.已接收  2.已拒收
     * @param reasonKindId 拒单原因类型ID
     * @param reasonContent 拒单原因
     */
    public void requestComplaintAssignType(String complaintId,String staffId,String acceptState,String reasonKindId,String reasonContent) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.updateAssignStatus");
        params.put("complaintId", complaintId);
        params.put("staffId", staffId);
        params.put("acceptState", acceptState);
        params.put("reasonKindId", reasonKindId);
        params.put("reasonContent", reasonContent);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }

    /**
     * 投诉下达任务接口
     * @param staffId 报修id
     * @param complaintId 物业处理结果
     */
    public void requestAssignComplaint(String staffId,String complaintId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.assignComplaint");
        params.put("staffId", staffId);
        params.put("complaintId", complaintId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 3);
    }

    /**
     * 获取投诉待派工/已派工列表
     * @param assignStatus 任务分配状态（0：待分配、1：已分配）
     * @param communityId 社区id
     * @param currentPage 当前页数
     * @param rowCountPerPage 每页条数
     */
    public void requestQueryAssignList(String assignStatus,String communityId,String currentPage,String rowCountPerPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.queryAssignList");
        params.put("assignStatus", assignStatus);
        params.put("communityId", communityId);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", rowCountPerPage);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),4);
    }

    /**
     * 填写物业处理意见(投诉)
     * @param complaintId 报修id
     * @param result 物业处理结果
     * @param resultPicUrlStr1 物业处理结果图片string串1
     * @param resultPicUrlStr2 物业处理结果图片string串1
     * @param resultPicUrlStr3 物业处理结果图片string串1
     */
    public void requestComplaintFillResult(String complaintId,String result,String resultPicUrlStr1,String resultPicUrlStr2,String resultPicUrlStr3) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.complaint.fillResult");
        params.put("complaintId", complaintId);
        params.put("result", result);
        params.put("resultPicUrlStr1", resultPicUrlStr1);
        params.put("resultPicUrlStr2", resultPicUrlStr2);
        params.put("resultPicUrlStr3", resultPicUrlStr3);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 5);
    }



    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            List<Complaint> data = JsonUtil.node2pojoList(result.findValue("complaintList"), Complaint.class);
            mComplaintsList.addAll(data);
        }
        if(requestCode == 1){
            mComplaintInfo = JsonUtil.node2pojo(result.findValue("complaintInfo"), ComplaintInfo.class);
            List<GoodPic> repairPics = JsonUtil.node2pojoList(result.findValue("complaintPicList"), GoodPic.class);
           if(null!=repairPics)
            mGoodPicList.addAll(repairPics);
            List<GoodPic> repairResultPics = JsonUtil.node2pojoList(result.findValue("complaintResultPicList"), GoodPic.class);
            if(null!=repairResultPics) {
                mGoodPicResultList.addAll(repairResultPics);
            }
        }
        if(requestCode==4){
            assignList=JsonUtil.node2pojoList(result.findValue("assignList"),Complaint.class);
        }
    }
}

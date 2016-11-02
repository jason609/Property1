package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.Repair;
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
public class RepairDao extends IDao{
    private List<Repair> repairList = new ArrayList<>();
    private Repair repair;
    private List<GoodPic> repairPicList = new ArrayList<>();
    private List<GoodPic> repairResultPicList = new ArrayList<>();

    public List<Repair> getRepairList() {
        return repairList;
    }

    public Repair getRepair() {
        return repair;
    }

    public List<GoodPic> getRepairPicList() {
        return repairPicList;
    }

    public List<GoodPic> getRepairResultPicList() {
        return repairResultPicList;
    }

    public RepairDao(INetResult activity) {
        super(activity);
    }
    /**
     * 获取报修信息列表
     * @param proprietorId 业主ID
     * @param handleStatus 处理状态（0：处理中、1：已处理、2：已结束）
     * @param currentPage
     */
    public void requestRepairList(String proprietorId,String handleStatus,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.repairDemand.list");
        params.put("proprietorId", proprietorId);
        params.put("handleStatus", handleStatus);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "20");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 获取报修信息详情
     * @param repairDemandId 投诉id
     */
    public void requestRepair(String repairDemandId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.repairDemand.get");
        params.put("repairDemandId", repairDemandId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 业主发起报修
     * @param proprietorId 业主ID
     * @param communityId 社区ID
     * @param addressId 社区住址ID
     * @param repairKindId 报修类型ID
     * @param content 报修详情
     */
    public void requestRepairAdd(String proprietorId,String communityId,String addressId,String repairKindId,String content,String repairDemandPicUrl1,String repairDemandPicUrl2,String repairDemandPicUrl3) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.repairDemand.requestRepair");
        params.put("proprietorId", proprietorId);
        params.put("communityId", communityId);
        params.put("addressId", addressId);
        params.put("repairKindId", repairKindId);
        params.put("content", content);
        params.put("repairDemandPicUrl1", repairDemandPicUrl1);
        params.put("repairDemandPicUrl2", repairDemandPicUrl2);
        params.put("repairDemandPicUrl3", repairDemandPicUrl3);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }

    /**
     * 业主对报修信息进行评价
     * @param repairDemandId 报修id
     * @param evaluateLevel 评价等级（0：非常满意、1：基本满意、2：不满意）
     * @param evaluateContent 评价内容
     */
    public void requestRepairEvaluate(String repairDemandId,String evaluateLevel,String evaluateContent) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.repairDemand.evaluate");
        params.put("repairDemandId", repairDemandId);
        params.put("evaluateLevel", evaluateLevel);
        params.put("evaluateContent", evaluateContent);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 3);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            List<Repair> data = JsonUtil.node2pojoList(result.findValue("repairList"), Repair.class);
            repairList.addAll(data);
        }
        if(requestCode == 1){
            repair = JsonUtil.node2pojo(result.findValue("repairDemandInfo"), Repair.class);
            List<GoodPic> repairPics = JsonUtil.node2pojoList(result.findValue("repairDemandPicList"), GoodPic.class);
           if(null!=repairPics)
            repairPicList.addAll(repairPics);
            List<GoodPic> repairResultPics = JsonUtil.node2pojoList(result.findValue("repairDemandResultPicList"), GoodPic.class);
            if(null!=repairResultPics)
            repairResultPicList.addAll(repairResultPics);
        }
    }
}

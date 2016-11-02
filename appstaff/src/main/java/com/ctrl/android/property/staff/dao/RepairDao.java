package com.ctrl.android.property.staff.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.GoodPic;
import com.ctrl.android.property.staff.entity.Repair;
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
public class RepairDao extends IDao{
    private List<Repair> repairList = new ArrayList<>();
    private Repair repair;
    private List<GoodPic> repairPicList = new ArrayList<>();
    private List<GoodPic> repairResultPicList = new ArrayList<>();
    private List<Repair> assignList=new ArrayList<>();

    public List<Repair> getRepairList() {
        return repairList;
    }

    public List<Repair> getAssignList() {
        return assignList;
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
     * @param staffId 员工ID
     * @param handleStatus 处理状态（0：处理中、1：已处理、2：已结束）
     * @param currentPage
     */
    public void requestRepairList(String staffId,String handleStatus,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.repairDemand.queryRepairDemandList");
        params.put("staffId", staffId);
        params.put("handleStatus", handleStatus);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "20");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 获取报修信息详情
     * @param repairDemandId 报修id
     */
    public void requestRepair(String repairDemandId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.repairDemand.queryRepairDemandDetl");
        params.put("repairDemandId", repairDemandId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 物业报修接受、拒单、退单
     * @param repairDemandId 报修ID
     * @param staffId 维修人员ID
     * @param acceptState 任务接收状态  1.已接收  2.已拒收
     * @param reasonKindId 拒单原因类型ID
     * @param reasonContent 拒单原因
     */
    public void requestRepairAssignType(String repairDemandId,String staffId,String acceptState,String reasonKindId,String reasonContent) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.repairDemand.updateAssignStatus");
        params.put("repairDemandId", repairDemandId);
        params.put("staffId", staffId);
        params.put("acceptState", acceptState);
        params.put("reasonKindId", reasonKindId);
        params.put("reasonContent", reasonContent);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }

    /**
     * 填写物业处理意见
     * @param repairDemandId 报修id
     * @param result 物业处理结果
     * @param resultPicUrlStr1 物业处理结果图片string串1
     * @param resultPicUrlStr2 物业处理结果图片string串1
     * @param resultPicUrlStr3 物业处理结果图片string串1
     */
    public void requestRepairFillResult(String repairDemandId,String result,String resultPicUrlStr1,String resultPicUrlStr2,String resultPicUrlStr3) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.repairDemand.fillResult");
        params.put("repairDemandId", repairDemandId);
        params.put("result", result);
        params.put("resultPicUrlStr1", resultPicUrlStr1);
        params.put("resultPicUrlStr2", resultPicUrlStr2);
        params.put("resultPicUrlStr3", resultPicUrlStr3);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 3);
    }


    /**
     * 投诉下达任务接口
     * @param staffId 报修id
     * @param repairDemandId 物业处理结果
     */
    public void requestAssignComplaint(String staffId,String repairDemandId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.stf.repairDemand.assignRepair");
        params.put("staffId", staffId);
        params.put("repairDemandId", repairDemandId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 4);
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
        params.put("method","pm.stf.repairDemand.queryAssignList");
        params.put("assignStatus", assignStatus);
        params.put("communityId", communityId);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", rowCountPerPage);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),5);
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
            if(null!=repairResultPics) {
                repairResultPicList.addAll(repairResultPics);
            }
        }
        if(requestCode==5){
            assignList=JsonUtil.node2pojoList(result.findValue("assignList"), Repair.class);
        }

    }
}

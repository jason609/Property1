package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Device;
import com.ctrl.android.property.staff.entity.DeviceCate;
import com.ctrl.android.property.staff.entity.DeviceDetail;
import com.ctrl.android.property.staff.entity.DeviceRecord;
import com.ctrl.android.property.staff.entity.DeviceRecordDetail;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备 dao
 * Created by Eric on 2015/10/28.
 */
public class DeviceDao extends IDao {

    /**
     * 设备分类列表
     * */
    private List<DeviceCate> listDeviceCate = new ArrayList<>();

    /**
     * 设备列表
     * */
    private List<Device> listDevice = new ArrayList<>();

    /**
     * 设备详细
     * */
    private DeviceDetail deviceDetail;

    /**
     * 设备养护记录
     * */
    private List<DeviceRecord> listDeviceRecord = new ArrayList<>();

    private DeviceRecordDetail deviceRecordDetail;

    private List<Img> listImg;

    public DeviceDao(INetResult activity){
        super(activity);
    }

    /**
     * 设备分类列表
     * @param communityId 社区id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestDeviceCategory(String communityId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.equipmentCategory.cateList");//方法名称

        map.put("communityId",communityId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 设备分类列表
     * @param categoryId 设备分类id
     * @param communityId 社区id
     * @param name 设备名称关键字
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestDeviceList(String categoryId,String communityId,String name
                                    ,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.equipment.list");//方法名称

        map.put("categoryId",categoryId);
        map.put("communityId",communityId);
        map.put("name",name);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 养护设备详细
     * @param id 设备id
     * */
    public void requestDeviceDetail(String id){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.equipment.contextList");//方法名称

        map.put("id",id);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 设备养护记录列表
     * @param equipmentId 设备分类id
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestDeviceRecordList(String equipmentId,String startTime,String endTime
            ,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.maintainhistory.timeList");//方法名称

        map.put("equipmentId",equipmentId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 增加一条 设备养护记录
     * @param stateKindId 设备状态类别id
     * @param content 养护详情
     * @param equipmentId 设备id
     * @param staffId 养护人id
     * @param historyPicStr1 设备养护记录图片Url1（原图Url + “,”+ 缩略图Url）
     * @param historyPicStr2 设备养护记录图片Url2（原图Url + “,”+ 缩略图Url）
     * @param historyPicStr3 设备养护记录图片Url3（原图Url + “,”+ 缩略图Url）
     * */
    public void requestAddDeviceRecord(String stateKindId,String content
            ,String equipmentId,String staffId
            ,String historyPicStr1,String historyPicStr2,String historyPicStr3){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.maintainhistory.insert");//方法名称

        map.put("stateKindId",stateKindId);
        map.put("content",content);
        map.put("equipmentId",equipmentId);
        map.put("staffId",staffId);
        map.put("historyPicStr1",historyPicStr1);
        map.put("historyPicStr2",historyPicStr2);
        map.put("historyPicStr3",historyPicStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }

    /**
    * 设备记录详细
    * @param id 设备养护id
    * */
    public void requestDeviceRecordDetail(String id){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.maintainhistory.get");//方法名称

        map.put("id",id);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 5);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 0){
            Log.d("demo","dao中结果集(设备分类): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            listDeviceCate = JsonUtil.node2pojoList(result.findValue("selEquipmentCateList"), DeviceCate.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(设备列表): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            listDevice = JsonUtil.node2pojoList(result.findValue("selEquipmentList"), Device.class);
        }

        if(requestCode == 2){
          Log.d("demo","dao中结果集(设备详细): " + result);
            deviceDetail = JsonUtil.node2pojo(result.findValue("selEquipment"), DeviceDetail.class);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(设备养护列表): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            List<DeviceRecord> list = JsonUtil.node2pojoList(result.findValue("selTimeList"), DeviceRecord.class);
            //listDeviceRecord = JsonUtil.node2pojoList(result.findValue("selTimeList"), DeviceRecord.class);
            listDeviceRecord.addAll(list);
        }

        if(requestCode == 4){
            Log.d("demo","dao中结果集(增加设备记录): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //listDeviceCate = JsonUtil.node2pojoList(result.findValue("selEquipmentCateList"), DeviceCate.class);
        }

        if(requestCode == 5){
            Log.d("demo","dao中结果集(设备记录详细): " + result);
            deviceRecordDetail = JsonUtil.node2pojo(result.findValue("maintainHistoryContent"), DeviceRecordDetail.class);
            listImg = JsonUtil.node2pojoList(result.findValue("maintainHistoryPicList"), Img.class);
        }

    }

    public List<DeviceCate> getListDeviceCate() {
        return listDeviceCate;
    }

    public List<Device> getListDevice() {
        return listDevice;
    }

    public DeviceDetail getDeviceDetail() {
        return deviceDetail;
    }

    public List<DeviceRecord> getListDeviceRecord() {
        return listDeviceRecord;
    }

    public DeviceRecordDetail getDeviceRecordDetail() {
        return deviceRecordDetail;
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

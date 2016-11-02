package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Express;
import com.ctrl.android.property.staff.entity.ExpressRecive;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递
 * Created by Administrator on 2015/10/28.
 */
public class ExpressDao extends IDao{

    /**
     * 快递详细
     * */
    private ExpressRecive expressRecive;

    /**
     * 快递列表
     * */
    private List<Express> expressList= new ArrayList<>();

    /**
     * 图片列表
     * */
    private List<Img> listImg = new ArrayList<>();

    public ExpressDao(INetResult activity) {
        super(activity);
    }

    /**
     * 获取业主快递信息列表
     * @param communityId 社区id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     */
    public void requestExpressList(String communityId,String currentPage,String rowCountPerPage) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.express.list");//方法名称

        map.put("communityId", communityId);
        map.put("currentPage", currentPage);
        map.put("rowCountPerPage", rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取快递信息详情
     * @param expressId 快递id
     */
    /*public void requestExpressDetail(String expressId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.express.get");//方法名称

        map.put("expressId", expressId);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }*/

    /**
     * 扫码获取快递信息详情
     * @param id 快递id
     */
    public void requestQrCodeExpressDetail(String id) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.express.selectCode");//方法名称

        map.put("id", id);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }
    /**
     * 快递签收
     * @param expressId 快递id
     */
    public void requestSignExpress(String expressId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.express.update");//方法名称

        map.put("expressId", expressId);
        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 增加快递
     * @param adminId 操作者id
     * @param communityId 社区id
     * @param recipientName 收件人姓名
     * @param mobile 收件人电话
     * @param building 楼号
     * @param unit 单元号
     * @param room 房间号
     * @param logisticsKindId 快递公司分类id
     * @param logisticsNum 快递单号
     * @param expressPicStr1 快递图片Url1（原图Url + “,”+ 缩略图Url）
     * @param expressPicStr2 快递图片Url2（原图Url + “,”+ 缩略图Url）
     * @param expressPicStr3 快递图片Url3（原图Url + “,”+ 缩略图Url）
     */
    public void requestExpressAdd(String adminId,
                                  String communityId,
                                  String recipientName,
                                  String mobile,
                                  String building,
                                  String unit,
                                  String room,
                                  String logisticsKindId,
                                  String logisticsNum,
                                  String expressPicStr1,
                                  String expressPicStr2,
                                  String expressPicStr3) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.express.insert");//方法名称

        map.put("adminId", adminId);
        map.put("communityId", communityId);
        map.put("recipientName", recipientName);
        map.put("mobile", mobile);
        map.put("building", building);
        map.put("unit", unit);
        map.put("room", room);
        map.put("logisticsKindId", logisticsKindId);
        map.put("logisticsNum", logisticsNum);
        map.put("expressPicStr1", expressPicStr1);
        map.put("expressPicStr2", expressPicStr2);
        map.put("expressPicStr3", expressPicStr3);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo", "dao中结果集(快递列表): " + result);
            List<Express> list = JsonUtil.node2pojoList(result.findValue("selExpressList"), Express.class);
            expressList.addAll(list);
        }
      /*  if(requestCode == 1){
            Log.d("demo","dao中结果集(快递详情): " + result);
            expressRecive = JsonUtil.node2pojo(result.findValue("expressInfo"), ExpressRecive.class);
            listImg2 = JsonUtil.node2pojoList(result.findValue("expressPicList"), Img.class);
        }*/
        if(requestCode == 2){
            Log.d("demo","dao中结果集(快递详情): " + result);
            expressRecive = JsonUtil.node2pojo(result.findValue("selCodeList"), ExpressRecive.class);
            listImg = JsonUtil.node2pojoList(result.findValue("expressPicList"), Img.class);
        }
    }


    public List<Express> getExpressList(){
        return expressList;
    }

    public ExpressRecive getExpressRecive(){
        return expressRecive;
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

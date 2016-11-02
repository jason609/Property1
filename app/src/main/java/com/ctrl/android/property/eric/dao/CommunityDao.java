package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区 dao
 * Created by Eric on 2015/10/28.
 */
public class CommunityDao extends IDao {

    /**
     * 小区列表
     * */
    private List<Community> listCommunity = new ArrayList<>();

    /**
     * 房屋列表
     * */
    private List<String> listBuilding = new ArrayList<>();

    /**
     * 单元列表
     * */
    private List<String> listUnit = new ArrayList<>();

    /**
     * 房号列表
     * */
    private List<String> listRoom = new ArrayList<>();

    public CommunityDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取小区列表
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param keyword 社区名称关键字（用户模糊获取）
     * */
    public void requestCommunityList(String provinceName,String cityName,String areaName,String keyword){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.community.list");//方法名称

        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("keyword",keyword);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取社区楼号 列表
     * @param communityId 社区ID
     * */
    public void requestBuildingList(String communityId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityAddress.list");//方法名称

        map.put("communityId",communityId);
        map.put("building","");
        map.put("unit","");
        map.put("room","");

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 获取社区单元号 列表
     * @param communityId 社区ID
     * @param building 楼号
     * */
    public void requestUnitList(String communityId,String building){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityAddress.list");//方法名称

        map.put("communityId",communityId);
        map.put("building",building);
        map.put("unit","");
        map.put("room","");

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 获取社区房号 列表
     * @param communityId 社区ID
     * @param building 楼号
     * @param unit 单元号
     * */
    public void requestRoomList(String communityId,String building,String unit){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.communityAddress.list");//方法名称

        map.put("communityId",communityId);
        map.put("building",building);
        map.put("unit",unit);
        map.put("room","");

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 会员进行业主认证
     * @param communityId 社区ID
     * @param memberId 会员ID
     * @param name 业主姓名
     * @param mobile 业主手机号码
     * @param building 楼号
     * @param unit 单元号
     * @param room 房号
     * */
    public void requestProprietyVerify(String communityId,String memberId,String name
                                        ,String mobile,String building,
                                        String unit,String room){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.proprietor.verify");//方法名称

        map.put("communityId",communityId);
        map.put("memberId",memberId);
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("building",building);
        map.put("unit",unit);
        map.put("room",room);


//        Log.d("demo", "communityId: " + communityId);
//        Log.d("demo","memberId: " + memberId);
//        Log.d("demo","name: " + name);
//        Log.d("demo","mobile: " + mobile);
//        Log.d("demo","building: " + building);
//        Log.d("demo","unit: " + unit);
//        Log.d("demo","room: " + room);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(小区列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listCommunity = JsonUtil.node2pojoList(result.findValue("communityList"), Community.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(楼号列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            //listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            //listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(单元列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            //listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(房号列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            //listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }

        if(requestCode == 4){
            Log.d("demo","dao中结果集(业主认证): " + result);
        }



    }


    public List<Community> getListCommunity() {
        return listCommunity;
    }

    public List<String> getListBuilding() {
        return listBuilding;
    }

    public List<String> getListUnit() {
        return listUnit;
    }

    public List<String> getListRoom() {
        return listRoom;
    }
}

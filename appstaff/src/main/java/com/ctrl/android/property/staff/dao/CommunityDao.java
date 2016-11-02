package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.http.AopUtils;
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
        postRequest(Constant.RAW_URL, mapToRP(map), 10);
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
        postRequest(Constant.RAW_URL, mapToRP(map),11);
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
        postRequest(Constant.RAW_URL, mapToRP(map), 12);
    }



    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 10){
            Log.d("demo","dao中结果集(楼号列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            //listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            //listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }

        if(requestCode == 11){
            Log.d("demo","dao中结果集(单元列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            //listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }

        if(requestCode == 12){
            Log.d("demo","dao中结果集(房号列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listBuilding = JsonUtil.node2pojoList(result.findValue("buildList"), String.class);
            //listUnit = JsonUtil.node2pojoList(result.findValue("unitList"), String.class);
            listRoom = JsonUtil.node2pojoList(result.findValue("roomList"), String.class);
        }




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

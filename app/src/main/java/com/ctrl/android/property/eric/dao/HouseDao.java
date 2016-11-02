package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房屋 dao
 * Created by Eric on 2015/11/03.
 */
public class HouseDao extends IDao {

    /**
     * 房屋列表
     * */
    private List<House> listHouse = new ArrayList<>();

    public HouseDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取房屋列表
     * @param memberId 会员id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestHouseList(String memberId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberBind.list");//方法名称

        map.put("memberId",memberId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 设置默认房屋
     * @param memberId 会员id
     * @param housesBindId 当前页码
     * */
    public void requestSetDefaultHouse(String memberId,String housesBindId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberBind.setDefault");//方法名称

        map.put("memberId",memberId);
        map.put("housesBindId",housesBindId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(房屋列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listHouse = JsonUtil.node2pojoList(result.findValue("housesList"), House.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(默认房屋): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listHouse = JsonUtil.node2pojoList(result.findValue("housesList"), House.class);
        }



    }

    public List<House> getListHouse() {
        return listHouse;
    }
}

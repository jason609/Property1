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
 * 测试 dao
 * Created by Eric on 2015/6/16.
 */
public class MainDao extends IDao {

    /**
     * 小区列表
     * */
    private List<Community> listCommunity = new ArrayList<>();

    public MainDao(INetResult activity){
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




    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(小区列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listCommunity = JsonUtil.node2pojoList(result.findValue("communityList"), Community.class);
        }



    }

    public List<Community> getListCommunity() {
        return listCommunity;
    }


}

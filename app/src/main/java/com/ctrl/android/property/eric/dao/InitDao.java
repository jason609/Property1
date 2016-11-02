package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化 dao
 * Created by Eric on 2015/10/28.
 */
public class InitDao extends IDao {

    public InitDao(INetResult activity){
        super(activity);
    }

    /**
     * 初始化
     * @param memberId 会员id
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param longitude 当前位置经度
     * @param latitude 当前位置纬度
     * */
    public void requestInit(String memberId,String provinceName,String cityName,String areaName,
                            String longitude,String latitude){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.init");//方法名称

        map.put("memberId",memberId);
        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("longitude",longitude);
        map.put("latitude",latitude);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 109);
    }

    /**
     * 初始化
     * @param memberId 会员id
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param longitude 当前位置经度
     * @param latitude 当前位置纬度
     * */
    public void requestInit2(String memberId,String provinceName,String cityName,String areaName,
                            String longitude,String latitude){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.init");//方法名称

        map.put("memberId",memberId);
        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("longitude",longitude);
        map.put("latitude",latitude);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 110);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 109){
            Log.d("demo","dao中结果集(初始化): " + result);
            Community community = JsonUtil.node2pojo(result.findValue("communityInfo"), Community.class);
            AppHolder.getInstance().setCommunity(community);
        }

        if(requestCode == 110){
            Log.d("demo","dao中结果集(初始化): " + result);
            Community community = JsonUtil.node2pojo(result.findValue("communityInfo"), Community.class);
            AppHolder.getInstance().setCommunity(community);
        }




    }



}

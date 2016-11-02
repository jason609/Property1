package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 地图 dao
 * Created by Eric on 2015/10/30.
 */
public class MapDao extends IDao {

    public MapDao(INetResult activity){
        super(activity);
    }

    /**
     * 根据经纬度 获取地址信息
     * @param latitude 纬度
     * @param longitude 经度
     * */
    public void requestAddress(double latitude,double longitude){
        Map<String,String> map = new HashMap<String,String>();

        map.put("ak","E4805d16520de693a3fe707cdc962045");
        map.put("callback", "renderReverse");
        map.put("location", String.valueOf(latitude)+","+String.valueOf(longitude));
        map.put("output", "json");
        map.put("pois", "1");

        String raw_url = "http://api.map.baidu.com/geocoder/v2/?";

        postRequest(raw_url, mapToRP(map), 0);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 0){
            Log.d("demo","dao中结果集(地址信息): " + result);
            //forumNoteDetail = JsonUtil.node2pojo(result.findValue("queryPostInfo"), ForumNoteDetail.class);
            //listAct = JsonUtil.node2pojoList(result.findValue("actionList"), Act.class);
        }

    }

}

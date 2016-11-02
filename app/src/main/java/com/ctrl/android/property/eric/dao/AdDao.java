package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Ad;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告 dao
 * Created by Eric on 2015/10/28.
 */
public class AdDao extends IDao {

    public int code = 1000;

    /**
     * 广告列表
     * */
    private List<Ad> listAd = new ArrayList<>();

    public AdDao(INetResult activity){
        super(activity);
    }

    /**
     * 广告列表
     * @param location 广告位编号（广告位编号请和Web开发人员商定）
     * */
    public void requestAddList(String location){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.advertisement.list");//方法名称
        //Log.d("demo","SSSSSSSSSSSSSS");

        map.put("location",location);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        code = 103;

        postRequest(Constant.RAW_URL, mapToRP(map), 103);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 103){
            Log.d("demo","dao中结果集(广告列表): " + result);
            listAd = JsonUtil.node2pojoList(result.findValue("advertisementList"), Ad.class);
        }

    }

    public List<Ad> getListAd() {
        return listAd;
    }
}

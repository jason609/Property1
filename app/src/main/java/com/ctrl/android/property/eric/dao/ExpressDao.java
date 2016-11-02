package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Express;
import com.ctrl.android.property.eric.entity.ExpressRecive;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.http.AopUtils;
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
     * @param proprietorId 业主id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     */
    public void requestExpressList(String proprietorId,String currentPage,String rowCountPerPage) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.express.list");//方法名称

        map.put("proprietorId", proprietorId);
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
    public void requestExpressDetail(String expressId) {
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
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo", "dao中结果集(快递列表): " + result);
            List<Express> list = JsonUtil.node2pojoList(result.findValue("expressList"), Express.class);
            expressList.addAll(list);
        }
        if(requestCode == 1){
            Log.d("demo","dao中结果集(快递详情): " + result);
            expressRecive = JsonUtil.node2pojo(result.findValue("expressInfo"), ExpressRecive.class);
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

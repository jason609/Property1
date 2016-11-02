package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片 dao
 * Created by Eric on 2015/10/30
 */
public class ImgDao extends IDao {

    private Img2 img;

    public ImgDao(INetResult activity){
        super(activity);
    }

    /**
     * 上传图片 接口
     * @param activityId 处理对象ID(可选)
     * @param imgData 图片数据（Base64编码后的字符串）(必选)
     *   报修	        FIX
     *   报修处理结果	    FIX_RST
     *   投诉	        CPT
     *   投诉处理结果	    CPT_RST
     *   二手物品交易	    USD_GD
     *   房屋交易     	HS_TS
     *   指派任务反馈	    TSK_FB
     *   新增指派任务反馈	NEW_TSK_FB
     *   设备养护记录	    MT
     *   巡更结果	        PT_RST
     *   快递图片	        EX
     *   活动图片        	AC
     *   论坛配图        POST
     * @param typeKey 图片类别KEY(必选)
     * @param optMode 操作区分（0：添加、1:编辑）(必选)
     * */
    public void requestUploadImg(String activityId,String imgData,String typeKey,String optMode){
        Log.d("demo","上传图片 dao 1 ");
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPicture.upload");//方法名称

        map.put("activityId",activityId);
        map.put("imgData",imgData);
        map.put("typeKey",typeKey);
        map.put("optMode",optMode);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 101);
    }

    /**
     * 删除图片 接口
     * @param imgId 图片ID
     * */
    public void requestDelImg(String imgId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPicture.delete");//方法名称

        map.put("imgId",imgId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 102);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 101){
            Log.d("demo","dao中结果集(上传图片): " + result);
            img = JsonUtil.node2pojo(result.findValue("data"), Img2.class);
        }

        if(requestCode == 102){
            Log.d("demo","dao中结果集(删除图片): " + result);
            //img = JsonUtil.node2pojo(result.findValue("data"), Img2.class);
        }


    }

    public Img2 getImg() {
        return img;
    }
}

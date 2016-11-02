package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片
 * Created by Administrator on 2015/9/23.
 */
public class ImgDao extends IDao{

    private GoodPic goodPic=new GoodPic();
    public ImgDao(INetResult activity) {
        super(activity);
    }

    public GoodPic getGoodPic(){
        return  goodPic;
    }
    /**
     *上传图片接口
     * @param activityId 处理对象ID
     * @param imgData 图片数据（Base64编码后的字符串）
     * @param typeKey 图片类别KEY
     * @param optMode 操作区分（0：添加、1:编辑）
     */
    public void requestData(String activityId,String imgData,String typeKey,String optMode){
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.propertyPicture.upload");
        params.put("activityId", activityId);
        params.put("imgData", imgData);
        params.put("typeKey", typeKey);
        params.put("optMode", optMode);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     *删除图片接口
     * @param imgId
     */
    public void requestDeleteImg(String imgId){
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.propertyPicture.delete");
        params.put("imgId", imgId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            if(null!=result.findValue("imgId")) {
                goodPic.setId(result.findValue("imgId").asText());
            }
            if(null!=result.findValue("imgUrl")) {
                goodPic.setOriginalImg(result.findValue("imgUrl").asText());
            }
            if(null!=result.findValue("zipImgUrl")) {
                goodPic.setZipImgUrl(result.findValue("zipImgUrl").asText());
            }
        }
    }
}

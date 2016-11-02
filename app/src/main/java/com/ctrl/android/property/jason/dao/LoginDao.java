package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员相关接口
 * Created by Administrator on 2015/10/28.
 */
public class LoginDao extends IDao{

    private String proprietorId;

    public String getProprietorId(){
        return proprietorId;
    }

    public LoginDao(INetResult activity) {
        super(activity);
    }
    /**
     * 会员注册
     * @param userName
     * @param password
     */
    public void registe(String userName, String password) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.members.registe");
        params.put("userName", userName);
        params.put("password", password);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 会员进行业主认证
     * @param communityId  社区ID
     * @param memberId  会员ID
     * @param name  业主姓名
     * @param mobile  业主手机号码
     * @param building  楼号
     * @param unit  单元号
     * @param room  房号
     */
    public void requestVerify(String communityId, String memberId,String name,String mobile,String building,String unit,String room) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.proprietor.verify");
        params.put("communityId", communityId);
        params.put("memberId", memberId);
        params.put("name", name);
        params.put("mobile", mobile);
        params.put("building", building);
        params.put("unit", unit);
        params.put("room", room);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }
    /**
     * 获取业主认证信息
     * @param communityId  社区ID
     * @param memberId  会员ID
     */
    public void requestVerifyGet(String communityId, String memberId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.proprietor.get");
        params.put("communityId", communityId);
        params.put("memberId", memberId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 2){
            proprietorId = result.findValue("proprietorId").asText();
        }
    }
}

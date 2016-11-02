package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.MemberInfo;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息 dao
 * Created by Eric on 2015/10/28.
 */
public class MemberDao extends IDao {

    private MemberInfo memberInfo;

    private String imgUrl;

    public MemberDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取用户信息
     * @param memberId 用户ID
     * @param communityId 社区id
     * */
    public void requestMemberInfo(String memberId,String communityId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.members.get");//方法名称

        map.put("memberId",memberId);
        map.put("communityId",communityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 120);
    }

    /**
     * 修改用户信息
     * @param memberId 用户ID
     * @param oldMobile 旧手机号码
     * @param mobile （新）手机号码
     * @param nickName 昵称（用户名）
     * */
    public void requestModifyMemberInfo(String memberId,String oldMobile
                                    ,String mobile,String nickName){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.members.modify");//方法名称

        map.put("memberId",memberId);
        map.put("oldMobile",oldMobile);
        map.put("mobile",mobile);
        map.put("nickName",nickName);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 修改用户信息
     * @param memberId 用户ID
     * @param headImg 头像数据（（Base64编码后的字符串））
     * */
    public void requestModifyMemberIcon(String memberId,String headImg){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.members.modifyHeadImg");//方法名称

        map.put("memberId",memberId);
        map.put("headImg",headImg);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 120){
            Log.d("demo","dao中结果集(用户信息): " + result);
            memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            AppHolder.getInstance().setMemberInfo(memberInfo);
        }

        if(requestCode == 1){
            Log.d("demo", "dao中结果集(修改用户): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(修改头像): " + result);
            imgUrl = result.findValue("imgUrl").asText();
            AppHolder.getInstance().getMemberInfo().setImgUrl(imgUrl);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
        }


    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

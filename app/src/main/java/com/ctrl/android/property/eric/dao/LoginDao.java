package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.ApkInfo;
import com.ctrl.android.property.eric.entity.MemberInfo;
import com.ctrl.android.property.eric.entity.ReceiveAddress;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录 dao
 * Created by Eric on 2015/10/28.
 */
public class LoginDao extends IDao {

    /**
     * 用户信息
     * */
    private MemberInfo memberInfo;

    /**
     * 收货地址列表
     * */
    private List<ReceiveAddress> listReceiveAddress = new ArrayList<>();
    private ApkInfo apkInfo;

    public LoginDao(INetResult activity){
        super(activity);
    }

    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     * */
    public void requestLogin(String userName,String password){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.members.login");//方法名称

        map.put("userName",userName);
        map.put("password",password);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 登录 修改密码
     * @param memberId 会员ID
     * @param userName 用户名
     * @param oldPassword 原登录密码
     * @param password 登录密码
     * @param obtainType 修改类型（0：登录后修改、1：忘记密码时修改）
     * */
    public void requestChangePassword(String memberId,String userName,String oldPassword,
                                      String password,String obtainType){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.members.modifyPassword");//方法名称

        map.put("memberId",memberId);
        map.put("userName",userName);
        map.put("oldPassword",oldPassword);
        map.put("password",password);
        map.put("obtainType",obtainType);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 获取apk版本信息
     * @param clientType 会员ID
     * @param osType 用户名
     * @param currentPage 原登录密码
     * @param rowCountPerPage 登录密码
     * */
    public void updateApkVersions(String clientType,String osType,String currentPage,
                                  String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.apkVersions.updateApkVersions");//方法名称

        map.put("clientType",clientType);
        map.put("osType",osType);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map),2);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(登录返回): " + result);
            memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(修改密码): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(版本信息): " + result);
            apkInfo = JsonUtil.node2pojo(result.findValue("apkVersions"), ApkInfo.class);
            // listCommunity = JsonUtil.node2pojoList(result.findValue("communityList"), Community.class);
        }



    }


    public MemberInfo getMemberInfo() {
        return memberInfo;
    }
    public ApkInfo getApkInfo() {
        return apkInfo;
    }
    public List<ReceiveAddress> getListReceiveAddress() {
        return listReceiveAddress;
    }

}

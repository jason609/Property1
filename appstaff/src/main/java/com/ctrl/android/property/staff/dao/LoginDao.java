package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.ApkInfo;
import com.ctrl.android.property.staff.entity.StaffInfo;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录 dao
 * Created by Eric on 2015/10/28.
 */
public class LoginDao extends IDao {

    /**
     * 员工信息
     * */
    private StaffInfo staffInfo;
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
        map.put(Constant.METHOD,"pm.stf.staff.login");//方法名称

        map.put("userName",userName);
        map.put("password",password);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 修改密码
     * @param staffId 会员ID
     * @param oldPassword 原登录密码
     * @param password 登录密码
     * */
    public void requestChangePassword(String staffId,String oldPassword,String password){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.staff.modifyPassword");//方法名称

        map.put("staffId",staffId);
        map.put("oldPassword",oldPassword);
        map.put("password",password);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 退出登录
     * @param staffId 用户id
     * */
    public void requestLogout(String staffId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.staff.logout");//方法名称

        map.put("staffId",staffId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
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
        postRequest("http://60.208.78.252:8082/pm/api?", mapToRP(map),3);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(员工信息): " + result);
            staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            AppHolder.getInstance().setStaffInfo(staffInfo);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(修改密码): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //AppHolder.getInstance().setStaffInfo(staffInfo);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(退出登录): " + result);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //AppHolder.getInstance().setStaffInfo(staffInfo);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }
        if(requestCode == 3){
            Log.d("demo","dao中结果集(版本信息): " + result);
            apkInfo = JsonUtil.node2pojo(result.findValue("apkVersions"), ApkInfo.class);
            // listCommunity = JsonUtil.node2pojoList(result.findValue("communityList"), Community.class);
        }



    }

    public ApkInfo getApkInfo() {
        return apkInfo;
    }

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }
}

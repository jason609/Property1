package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.MemberAddress;
import com.ctrl.android.property.jason.entity.MemberInfo;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员
 * Created by Administrator on 2015/10/29.
 */
public class MemberDao extends IDao {
    private MemberInfo memberInfo;
    private List<MemberAddress> memberAddressList = new ArrayList<>();

    public MemberInfo getMemberInfo(){
        return memberInfo;
    }
    public List<MemberAddress> getMemberAddressList(){
        return memberAddressList;
    }
    public MemberDao(INetResult activity) {
        super(activity);
    }

    /**
     * 获取会员基本信息
     * @param memberId 用户ID
     */
    public void requestMemberInfo(String memberId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.members.get");
        params.put("memberId", memberId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 获取会员收货地址列表
     * @param memberId 会员ID
     * @param currentPage 当前页码
     */
    public void requestMemberAddressList(String memberId,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.receiveAddress.list");
        params.put("memberId", memberId);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "20");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 添加会员收货地址
     * @param memberId 会员ID
     * @param longitude 经度
     * @param latitude 纬度
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param streetName 街道
     * @param customize 详细地址
     * @param  zip    邮政编码
     * @param mobile 收货人手机号码
     * @param receiveName 收货人姓名
     */
    public void requestMemberAddressAdd(String memberId,String longitude,String latitude,String provinceName,String cityName,String areaName,String streetName,String customize,String zip,String mobile,String receiveName) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.receiveAddress.add");
        params.put("memberId", memberId);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("provinceName", provinceName);
        params.put("cityName", cityName);
        params.put("areaName", areaName);
        params.put("streetName",streetName);
        params.put("customize", customize);
        params.put("zip",zip);
        params.put("mobile", mobile);
        params.put("receiveName", receiveName);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }

    /**
     * 设置会员默认收货地址
     * @param memberId 会员ID
     * @param receiveAddressId 收货地址ID
     */
    public void requestMemberAddressDefault(String memberId,String receiveAddressId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.receiveAddress.setdefault");
        params.put("memberId", memberId);
        params.put("receiveAddressId", receiveAddressId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 3);
    }

    public void requestMemberAddressDelete(String receiveAddressId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.receiveAddress.delete");
        params.put("receiveAddressId", receiveAddressId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),4);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
        }
        if(requestCode == 1){
            List<MemberAddress> data = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), MemberAddress.class);
               memberAddressList.addAll(data);

        }
        if(requestCode == 2){

        }
    }
}

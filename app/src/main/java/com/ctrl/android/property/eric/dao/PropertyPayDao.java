package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.PropertyHisPay;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化 dao
 * Created by Eric on 2015/10/28.
 */
public class PropertyPayDao extends IDao {

    private List<PropertyPay> listPropertyPay = new ArrayList<>();

    private double debt;

    private String uniteId;

    private List<PropertyHisPay> listPropertyHisPay = new ArrayList<>();

    public PropertyPayDao(INetResult activity){
        super(activity);
    }

    /**
     * 物业缴费列表
     * @param proprietorId 业主id
     * @param addressId 地址id
     * @param status 缴费状态（0：等待缴费、1：已缴费）
     * @param currentPageStr 当前页码
     * @param rowCountPerPageStr 每页条数
     * */
    public void requestPropertyPayList(String proprietorId,String addressId,String status,
                            String currentPageStr,String rowCountPerPageStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPayment.queryPropertyPaymentList");//方法名称

        map.put("proprietorId",proprietorId);
        map.put("addressId",addressId);
        map.put("status",status);
        map.put("currentPageStr",currentPageStr);
        map.put("rowCountPerPageStr",rowCountPerPageStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 计算物业账单总额
     * @param propertyPaymentIdStr 物业账单id串
     * */
    public void requestPropertyPayAmount(String propertyPaymentIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPayment.calcPropertyPaymentPrice");//方法名称

        map.put("propertyPaymentIdStr",propertyPaymentIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 物业账单  支付前校验
     * @param propertyPaymentIdStr 物业账单id串
     * @param memberId 会员id
     * */
    public void requestBeforePropertyPay(String propertyPaymentIdStr,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPayment.checkBeforePay");//方法名称

        map.put("propertyPaymentIdStr",propertyPaymentIdStr);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 查询物业账单记录列表
     * @param proprietorId 业主id
     * @param addressId 地址id
     * @param year 年份（形式2014）
     * @param month 月份（形式01）
     * @param currentPageStr 当前页码
     * @param rowCountPerPageStr 每页条数
     * */
    public void requestPayHistory(String proprietorId,String addressId,String year
                                ,String month,String currentPageStr,String rowCountPerPageStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.propertyPaymentRecord.queryPropertyPaymentRecordList");//方法名称

        map.put("proprietorId",proprietorId);
        map.put("addressId",addressId);
        map.put("year",year);
        map.put("month",month);
        map.put("currentPageStr",currentPageStr);
        map.put("rowCountPerPageStr",rowCountPerPageStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(缴费列表): " + result);
            listPropertyPay = JsonUtil.node2pojoList(result.findValue("paymentList"), PropertyPay.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(物业总价): " + result);
            debt = result.findValue("debt").asDouble();
            //listPropertyPay = JsonUtil.node2pojoList(result.findValue("paymentList"), PropertyPay.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(支付前校验): " + result);
            uniteId = result.findValue("uniteId").asText();
            //listPropertyPay = JsonUtil.node2pojoList(result.findValue("paymentList"), PropertyPay.class);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(缴费历史): " + result);
            listPropertyHisPay = JsonUtil.node2pojoList(result.findValue("recordList"), PropertyHisPay.class);
            //uniteId = result.findValue("uniteId").asText();
            //listPropertyPay = JsonUtil.node2pojoList(result.findValue("paymentList"), PropertyPay.class);
        }


    }

    public List<PropertyPay> getListPropertyPay() {
        return listPropertyPay;
    }

    public double getDebt() {
        return debt;
    }

    public String getUniteId() {
        return uniteId;
    }

    public List<PropertyHisPay> getListPropertyHisPay() {
        return listPropertyHisPay;
    }
}

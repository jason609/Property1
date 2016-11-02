package com.ctrl.android.property.base;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.ctrl.android.property.eric.entity.Address;
import com.ctrl.android.property.eric.entity.ApkInfo;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.entity.MemberInfo;
import com.ctrl.android.property.eric.entity.OrderAddress;
import com.ctrl.android.property.eric.entity.PayType;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.eric.entity.Proprietor;
import com.ctrl.android.property.eric.entity.ReceiveAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全局控制类
 * 用于存储所有 全局控制变量
 * Created by Eric on 2015-09-14.
 */
public class AppHolder {

    /** 私有化的构造方法，保证外部的类不能通过构造器来实例化*/
    private AppHolder() {
    }

    /**内部类，用于实现lzay机制*/
    private static class SingletonHolder {
        /**单例变量*/
        private static AppHolder instance = new AppHolder();
    }

    /**
     * 获取单例对象实例
     */
    public static AppHolder getInstance() {
        return SingletonHolder.instance;
    }



    /**下面是 app全局变量控制类**/

    /**定位信息*/
    private BDLocation bdLocation;
    /**商城定位信息*/
    private BDLocation bdLocationMall;

    private Address address = new Address();

    /**用户信息*/
    private MemberInfo memberInfo = new MemberInfo();

    /**收货地址列表*/
    private List<ReceiveAddress> listReceiveAddress = new ArrayList<>();

    /**当前小区*/
    private Community community = new Community();

    /**当前的业主*/
    private Proprietor proprietor = new Proprietor();

    /**当前的房屋*/
    private House house = new House();

    /**答案列表*/
    private List<Map<String,String>> listAnswer= new ArrayList<>();

    /**支付方式*/
    private PayType payType = new PayType();

    /**订单默认地址*/
    private OrderAddress orderAddress = new OrderAddress();

    /**游客标识 0:正常用户; 1:游客*/
    private int visiterFlg = 0;

    /**物业缴费支付 条目*/
    private List<PropertyPay> listPropertyPay = new ArrayList<>();

    /**版本信息实体*/
    private ApkInfo versionInfo = new ApkInfo();






    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public List<ReceiveAddress> getListReceiveAddress() {
        return listReceiveAddress;
    }

    public void setListReceiveAddress(List<ReceiveAddress> listReceiveAddress) {
        this.listReceiveAddress = listReceiveAddress;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public House getHouse() {
        Log.d("demo","house: " + house.getId());
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Proprietor getProprietor() {
        Log.d("demo","proprietor: " + proprietor.getProprietorId());
        return proprietor;
    }

    public void setProprietor(Proprietor proprietor) {
        this.proprietor = proprietor;
    }

    public List<Map<String, String>> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Map<String, String>> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getVisiterFlg() {
        return visiterFlg;
    }

    public void setVisiterFlg(int visiterFlg) {
        this.visiterFlg = visiterFlg;
    }

    public List<PropertyPay> getListPropertyPay() {
        return listPropertyPay;
    }

    public void setListPropertyPay(List<PropertyPay> listPropertyPay) {
        this.listPropertyPay = listPropertyPay;
    }

    public BDLocation getBdLocationMall() {
        return bdLocationMall;
    }

    public void setBdLocationMall(BDLocation bdLocationMall) {
        this.bdLocationMall = bdLocationMall;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ApkInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(ApkInfo versionInfo) {
        this.versionInfo = versionInfo;
    }
}

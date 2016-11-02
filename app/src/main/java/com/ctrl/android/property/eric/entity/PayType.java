package com.ctrl.android.property.eric.entity;

/**
 * 支付方式
 * Created by Eric on 2015/11/17.
 */
public class PayType {

    /**
     * 支付方式Id
     * 0: 支付宝支付
     * 1: 微信支付
     * */
    private int id;

    /**
     * 支付方式名称
     * */
    private String name;

    public PayType(){

    }

    public PayType(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

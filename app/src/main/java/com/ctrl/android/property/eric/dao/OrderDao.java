package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Order;
import com.ctrl.android.property.eric.entity.OrderAddress;
import com.ctrl.android.property.eric.entity.OrderDetail;
import com.ctrl.android.property.eric.entity.OrderDetailItem;
import com.ctrl.android.property.eric.entity.OrderPro;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单 dao
 * Created by Eric on 2015/10/28.
 */
public class OrderDao extends IDao {

    /**
     * 商品数量
     * */
    private int productSum;

    /**
     * 商品总金额
     * */
    private double totalPrice;

    /**
     * 商品列表
     * */
    private List<OrderPro> listOrderPro = new ArrayList<>();

    /**
     * 订单 地址
     * */
    private OrderAddress orderAddress = new OrderAddress();

    /**
     * 订单列表
     * */
    private List<Order> listOrder = new ArrayList<>();

    /**
     * 订单详细
     * */
    private OrderDetail orderDetail = new OrderDetail();

    /**
     * 订单详细 条目
     * */
    private List<OrderDetailItem> listOrderDetailItem = new ArrayList<>();

    /**
     * 是否已经分单（0：未分单 1：已分单）
     * */
    private int separateStatus;

    /**
     * 订单id的String串
     * */
    private String orderIdStr;

    /**
     * 商家名称
     * */
    private String companyName;

    public OrderDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取填充 订单信息
     * @param memberId 会员ID
     * @param cartIdStr 购物车ID串（多个ID“,”分隔存放）
     * */
    public void requestFillOrder(String memberId,String cartIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.fillorderform");//方法名称

        map.put("memberId",memberId);
        map.put("cartIdStr",cartIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 生成订单
     * @param memberId 会员ID
     * @param settleType 结算类型（0：购物车结算、1：商品直接结算）
     * @param shoppingCartIdStr 购物车id的String串（购物车结算必须）
     * @param productId 商品id（商品直接结算必须）
     * @param productNum 商品数量（商品直接结算必须）
     * @param companyId 商家id（商品直接结算必须）
     * @param addressId 收货地址id
     * @param sourceType 订单来源（0：Web、1：Android、2：IOS、3：电话下单、4：其他）
     * @param payMode 支付方式（1：在线支付、2：货到付款）
     * */
    public void requestCreateOrder(String memberId,String settleType,String shoppingCartIdStr
                                ,String productId,String productNum,String companyId
                                ,String addressId,String sourceType,String payMode){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.generateOrder");//方法名称

        map.put("memberId",memberId);
        map.put("settleType",settleType);
        map.put("shoppingCartIdStr",shoppingCartIdStr);
        map.put("productId",productId);
        map.put("productNum",productNum);
        map.put("companyId",companyId);
        map.put("addressId",addressId);
        map.put("sourceType",sourceType);
        map.put("payMode",payMode);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 获取填充 订单信息
     * @param memberId 会员ID
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestOrderList(String memberId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.list");//方法名称

        map.put("memberId",memberId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 获取订单详细信息
     * @param orderId 订单ID
     * */
    public void requestOrderDetail(String orderId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.get");//方法名称

        map.put("orderId",orderId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 取消订单
     * @param orderId 订单ID
     * */
    public void requestCancelOrder(String orderId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.cancel");//方法名称

        map.put("orderId",orderId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }

    /**
     * 支付前校验
     * @param memberId 员工ID
     * @param orderIdStr 订单ID
     * */
    public void requestBeforePayOrder(String memberId,String orderIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.checkBeforePay");//方法名称

        map.put("memberId",memberId);
        map.put("orderIdStr",orderIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 5);
    }

    /**
     * 提交订单
     * @param companyId 员工ID
     * @param orderId 订单ID
     * @param memberId 订单ID
     * @param level 订单ID
     * @param content 订单ID
     * */
    public void requestCommentOrder(String companyId,String orderId,String memberId
                                    ,String level,String content){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.orderEvaluation.evaluate");//方法名称

        map.put("companyId",companyId);
        map.put("orderId",orderId);
        map.put("memberId",memberId);
        map.put("level",level);
        map.put("content",content);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 6);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(填充订单): " + result);
            productSum = result.findValue("productSum").asInt();
            totalPrice = result.findValue("totalPrice").asDouble();
            orderAddress = JsonUtil.node2pojo(result.findValue("addressInfo"), OrderAddress.class);
            listOrderPro = JsonUtil.node2pojoList(result.findValue("productList"), OrderPro.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(生成订单): " + result);
            separateStatus = result.findValue("separateStatus").asInt();
            totalPrice = result.findValue("totalPrice").asDouble();
            orderIdStr = result.findValue("orderIdStr").asText();
            companyName = result.findValue("companyName").asText();
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(订单列表): " + result);
            List<Order> list = JsonUtil.node2pojoList(result.findValue("orderList"), Order.class);
            listOrder.addAll(list);
        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(订单详细): " + result);
            orderDetail = JsonUtil.node2pojo(result.findValue("data"), OrderDetail.class);
            listOrderDetailItem = JsonUtil.node2pojoList(result.findValue("orderItemList"), OrderDetailItem.class);
        }

        if(requestCode == 4){
            Log.d("demo","dao中结果集(取消订单): " + result);
            //orderDetail = JsonUtil.node2pojo(result.findValue("data"), OrderDetail.class);
            //listOrderDetailItem = JsonUtil.node2pojoList(result.findValue("orderItemList"), OrderDetailItem.class);
        }

        if(requestCode == 5){
            Log.d("demo","dao中结果集(支付前校验): " + result);
            //orderDetail = JsonUtil.node2pojo(result.findValue("data"), OrderDetail.class);
            //listOrderDetailItem = JsonUtil.node2pojoList(result.findValue("orderItemList"), OrderDetailItem.class);
        }

        if(requestCode == 6){
            Log.d("demo","dao中结果集(评价订单): " + result);
            //orderDetail = JsonUtil.node2pojo(result.findValue("data"), OrderDetail.class);
            //listOrderDetailItem = JsonUtil.node2pojoList(result.findValue("orderItemList"), OrderDetailItem.class);
        }




    }

    public int getProductSum() {
        return productSum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderPro> getListOrderPro() {
        return listOrderPro;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public List<OrderDetailItem> getListOrderDetailItem() {
        return listOrderDetailItem;
    }

    public int getSeparateStatus() {
        return separateStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOrderIdStr() {
        return orderIdStr;
    }
}

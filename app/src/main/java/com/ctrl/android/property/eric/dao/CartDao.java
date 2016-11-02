package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.CartPro;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车 dao
 * Created by Eric on 2015/10/28.
 */
public class CartDao extends IDao {

    /**
     * 购物车内商品列表
     * */
    private List<CartPro> listCartPro = new ArrayList<>();

    /**
     * 选中的总金额
     * */
    private String amount;

    public CartDao(INetResult activity){
        super(activity);
    }

    /**
     * 加入购物车接口
     * @param productId 商品id
     * @param companyId 商家id
     * @param memberId 当前用户id
     * @param productNum 商品购买数量（如果为0时默认为1）
     * */
    public void requestAddToCart(String productId,String companyId,
                                 String memberId,String productNum){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.addShoppingCart");//方法名称

        map.put("productId",productId);
        map.put("companyId",companyId);
        map.put("memberId",memberId);
        map.put("productNum",productNum);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 110);
    }

    /**
     * 1.74、查询我的购物车列表
     * @param memberId 会员id
     * */
    public void requestCartProList(String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.queryShoppingCartList");//方法名称

        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 111);
    }

    /**
     * 1.80、计算购物车选定商品价格
     * @param shoppingCartIdStr 购物车id的String串
     * */
    public void requestCartProAmount(String shoppingCartIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.calcShoppingCartPrice");//方法名称

        map.put("shoppingCartIdStr",shoppingCartIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 112);
    }

    /**
     * 1.66、删除购物车商品
     * @param shoppingCartIdStr 购物车id的String串
     * */
    public void requestCartProDel(String shoppingCartIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.delShoppingCart");//方法名称

        map.put("shoppingCartIdStr",shoppingCartIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 113);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 110){
            Log.d("demo","dao中结果集(加入购物车): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listCartPro = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }

        if(requestCode == 111){
            Log.d("demo","dao中结果集(购物车列表): " + result);
            listCartPro = JsonUtil.node2pojoList(result.findValue("shoppingCartList"), CartPro.class);
        }

        if(requestCode == 112){
            Log.d("demo","dao中结果集(购物车总价): " + result);
            String str = result.findValue("totalPrice").asText();
            amount = N.toPriceFormate(str);
            //listCartPro = JsonUtil.node2pojoList(result.findValue("shoppingCartList"), CartPro.class);
        }

        if(requestCode == 113){
            Log.d("demo","dao中结果集(删除商品): " + result);
            //String str = result.findValue("totalPrice").asText();
            //amount = N.toPriceFormate(str);
            //listCartPro = JsonUtil.node2pojoList(result.findValue("shoppingCartList"), CartPro.class);
        }



    }

    public List<CartPro> getListCartPro() {
        return listCartPro;
    }

    public String getAmount() {
        return amount;
    }
}

package com.ctrl.android.property.jason.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.Product;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 * Created by Administrator on 2015/11/2.
 */
public class ProductDao extends IDao{
    private List<Product> productList = new ArrayList<>();
    private Product product;

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProduct() {
        return product;
    }

    public ProductDao(INetResult activity) {
        super(activity);
    }
    /**
     * 根据商品关键字搜索店内商品
     * @param productName 商品名称
     * @param companyId 商家id
     * @param currentPage 当前页码
     */
    public void requestKeyProduct(String companyId ,String productName,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.product.queryProductByKey");
        params.put("companyId", companyId);
        params.put("productName", productName);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "20");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 0);
    }

    /**
     * 查询商品详情信息
     * @param productId 商品id
     * @param memberId 当前用户id
     */
    public void requestProduct(String productId,String memberId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.product.queryProductInfo");
        params.put("productId", productId);
        params.put("memberId", memberId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            List<Product> products = JsonUtil.node2pojoList(result.findValue("productList"), Product.class);
            productList.addAll(products);
        }
        if(requestCode == 1){
            product = JsonUtil.node2pojo(result.findValue("queryProduct"), Product.class);
        }
    }
}

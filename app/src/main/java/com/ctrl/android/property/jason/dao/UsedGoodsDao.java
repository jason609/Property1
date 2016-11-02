package com.ctrl.android.property.jason.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.UsedGoodInfo;
import com.ctrl.android.property.jason.entity.UsedGoods;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二手交易
 * Created by Administrator on 2015/10/28.
 */
public class UsedGoodsDao extends IDao {
    private UsedGoodInfo mUsedGoodInfo;
    private List<GoodPic> mGoodPicList=new ArrayList<>();
    private List<UsedGoods> mUsedGoodsList=new ArrayList<>();

    public UsedGoodInfo getUsedGoodInfo(){
        return mUsedGoodInfo;
    }
    public List<GoodPic> getGoodPicList(){
        return mGoodPicList;
    }
    public List<UsedGoods> getUsedGoodsList(){
        return mUsedGoodsList;
    }
    public UsedGoodsDao(INetResult activity) {
        super(activity);
    }

    /**
     * 发布二手交易信息
     * @param communityId 社区id
     * @param memberId 会员id
     * @param proprietorId 业主id
     * @param title 标题
     * @param content 描述
     * @param contactName 联系人姓名
     * @param contactMobile 联系人电话
     * @param sellingPrice 转让价/期望价格
     * @param goodKindId 物品分类id
     * @param originalPrice 原价
     * @param usedGoodPicStr1 二手物品图片1
     * @param usedGoodPicStr2 二手物品图片2
     * @param usedGoodPicStr3 二手物品图片3
     * @param transactionType 交易类型（0：出卖、1：求购）
     * @param visibleType 可见类型（0：对外公开、1：仅社区内可见）
     */
    public void requestGoodsAdd(String communityId, String memberId,String proprietorId,String title, String content,String contactName,String contactMobile,String sellingPrice,String goodKindId,String originalPrice,String usedGoodPicStr1,String usedGoodPicStr2,String usedGoodPicStr3,String transactionType,String visibleType) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.usedGoods.add");
        params.put("communityId", communityId);
        params.put("memberId", memberId);
        params.put("proprietorId", proprietorId);
        params.put("title", title);
        params.put("content", content);
        params.put("contactName", contactName);
        params.put("contactMobile", contactMobile);
        params.put("sellingPrice", sellingPrice);
        params.put("goodKindId", goodKindId);
        params.put("originalPrice", originalPrice);
        params.put("usedGoodPicStr1", usedGoodPicStr1);
        params.put("usedGoodPicStr2", usedGoodPicStr2);
        params.put("usedGoodPicStr3", usedGoodPicStr3);
        params.put("transactionType", transactionType);
        params.put("visibleType", visibleType);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),99);
    }

    /**
     * 获取二手交易详情
     * @param usedGoodId 二手物品id
     */
    public void requestGoodsGet(String usedGoodId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.usedGoods.get");
        params.put("usedGoodId", usedGoodId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 1);
    }

    /**
     * 获取二手交易列表
     * @param transactionType 交易类型（0：转让、1：求购）
     * @param goodKindId 二手物品分类id
     * @param proprietorId  业主id
     * @param currentPage 当前页码
     */
    public void requestGoodsList(String transactionType,String goodKindId,String proprietorId,String currentPage) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.usedGoods.list");
        params.put("transactionType", transactionType);
        params.put("goodKindId", goodKindId);
        params.put("proprietorId", proprietorId);
        params.put("currentPage", currentPage);
        params.put("rowCountPerPage", "10");
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 2);
    }

    /**
     * 修改二手交易信息
     * @param usedGoodId 二手物品id
     * @param visibleType 可见类型（0：对外公开、1：仅社区内可见）
     * @param title 标题
     * @param content 描述
     * @param contactName 联系人姓名
     * @param contactMobile 联系人电话
     * @param sellingPrice 转让价/期望价格
     * @param goodKindId 物品分类id
     * @param originalPrice 原价
     */
    public void requestGoodsModify(String usedGoodId, String visibleType,String title, String content,String goodKindId,String originalPrice,String sellingPrice,String contactName,String contactMobile) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.usedGoods.modify");
        params.put("title", title);
        params.put("content", content);
        params.put("contactName", contactName);
        params.put("contactMobile", contactMobile);
        params.put("sellingPrice", sellingPrice);
        params.put("goodKindId", goodKindId);
        params.put("originalPrice", originalPrice);
        params.put("visibleType", visibleType);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 3);
    }

    /**
     * 删除二手交易信息
     * @param usedGoodId 二手物品id
     */
    public void requestGoodsDelete(String usedGoodId) {
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.ppt.usedGoods.delete");
        params.put("usedGoodId", usedGoodId);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params),4);
    }
    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if (requestCode == 1){
            Log.d("demo", "dao中结果集(发布二手信息): " + result);
            mUsedGoodInfo = JsonUtil.node2pojo(result.findValue("usedGoodInfo"), UsedGoodInfo.class);
            List<GoodPic> goodPics = JsonUtil.node2pojoList(result.findValue("usedGoodPicList"), GoodPic.class);
            if(null!=goodPics) {
                mGoodPicList.addAll(goodPics);
            }
        }
        if (requestCode == 2){
            Log.d("demo","dao中结果集(获取二手列表): " + result);
            List<UsedGoods> usedGoodses= JsonUtil.node2pojoList(result.findValue("usedGoodList"), UsedGoods.class);
            mUsedGoodsList.addAll(usedGoodses);
        }
    }
}

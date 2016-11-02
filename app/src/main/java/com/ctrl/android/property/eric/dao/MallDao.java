package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Comment;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.entity.Pro;
import com.ctrl.android.property.eric.entity.ProCategary;
import com.ctrl.android.property.eric.entity.ProDetail;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城 dao
 * Created by Eric on 2015/10/28.
 */
public class MallDao extends IDao {

    public int code = 1000;

    /**
     * 周围商家
     * */
    private List<MallShop> listShopArround = new ArrayList<>();

    /**
     * 商家列表
     * */
    private List<MallShop> listShop = new ArrayList<>();

    /**
     * 商家详细
     * */
    private MallShop mallShopDetail = new MallShop();

    /**
     * 信用评分
     * */
    private float sellersCredit = 0;

    /**
     * 商品列表
     * */
    private List<Pro> listPro = new ArrayList<>();

    /**
     * 商品分类列表
     * */
    private List<ProCategary> listProCategary = new ArrayList<>();

    /**
     * 商品列表 (根据分类查询)
     * */
    private List<Pro> listProByCategary = new ArrayList<>();

    /**
     * 是否分类 查找
     * */
    private boolean ifCategary = false;

    /**
     * 商品详细
     * */
    private ProDetail proDetail = new ProDetail();

    /**
     * 图片列表
     * */
    private List<Img> listProImg = new ArrayList<>();

    /**
     * 详情图片列表
     * */
    private List<Img> listProDetailImg = new ArrayList<>();

    /**
     * 商家列表
     * */
    private List<MallShop> listShopBySort = new ArrayList<>();

    /**
     *
     * */
    private int flg;

    /**
     * 商品列表
     * */
    private List<Pro> listProByKeyword = new ArrayList<>();

    /**
     * 评论列表
     * */
    private List<Comment> listComment = new ArrayList<>();

    /**
     * 购物车内 商品数
     * */
    private int proNum;

    /**
     * 是否重新实例化标识
     * 0: 需要实例化
     * 1:不需要实例化
     * */
    private int flg2 = 0;

    /**
     * 评价数
     * */
    private int evaluate = 0;

    /**
     * 订单数
     * */
    private int orders = 0;

    public MallDao(INetResult activity){
        super(activity);
    }

    /**
     * 周围商家列表
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param longitude 当前位置经度
     * @param latitude 当前位置纬度
     * @param currentPage 当前页
     * @param rowCountPerPage 每页条数
     * */
    public void requestShopArroundList(String provinceName,String cityName,String areaName,
                             String longitude,String latitude,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.companys.homeList");//方法名称

        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("longitude",longitude);
        map.put("latitude",latitude);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        if((Integer.parseInt(currentPage) == 1)){
            code = 0;
        } else {
            code = 1000;
        }

        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 商家列表
     * @param keyword 关键字
     * @param provinceName 所在省（名称） 注：无需加后缀“省”
     * @param cityName 所在市（名称） 注：无需加后缀“市”
     * @param areaName 所在区（名称） 注：无需加后缀“区”
     * @param longitude 当前位置经度
     * @param latitude 当前位置纬度
     * @param currentPage 当前页
     * @param rowCountPerPage 每页条数
     * */
    public void requestShopListByKeyword(String keyword,String longitude,String latitude,String provinceName,String cityName,String areaName,
                                       String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.companys.selectProductOrCompanyByKey");//方法名称

        map.put("keyword",keyword);
        map.put("longitude",longitude);
        map.put("latitude",latitude);
        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 商家详细
     * @param companyId 关键字
     * @param memberId 所在省（名称） 注：无需加后缀“省”
     * */
    public void requestShopDetail(String companyId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.companys.get");//方法名称

        map.put("companyId",companyId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 收藏商铺接口
     * @param companyId 商家id
     * @param memberId 所在省（名称） 注：无需加后缀“省”
     * @param collectingType 收藏或取消收藏(0:收藏, 1:取消收藏)
     * */
    public void requestFavorShop(String companyId,String memberId,String collectingType){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.collect.companyCollect");//方法名称

        map.put("companyId",companyId);
        map.put("memberId",memberId);
        map.put("collectingType",collectingType);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }

    /**
     * 1.45、根据商品关键字搜索店内商品
     * @param companyId 商家id
     * @param productName 商品名称
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestProList(String companyId,String productName,String currentPage,String rowCountPerPage){

        ifCategary = false;

        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.product.queryProductByKey");//方法名称

        map.put("companyId",companyId);
        map.put("productName",productName);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 4);
    }

    /**
     * 1.67、查询商家店内的商品分类
     * @param companyId 商家id
     * */
    public void requestProCategary(String companyId){

        ifCategary = true;

        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.productCategory.selectCategory");//方法名称

        map.put("companyId",companyId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 5);
    }

    /**
     * 1.62、根据分类查询商品
     * @param companyId 商家id
     * */
    public void requestProListByCategary(int flg1,String categoryId,String companyId
                                        ,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.product.list");//方法名称

        map.put("categoryId",categoryId);
        map.put("companyId",companyId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        flg2 = flg1;

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 6);
    }

    /**
     * 1.58、查询商品详情信息
     * @param productId 商品id
     * @param memberId 会员id
     * */
    public void requestProDetail(String productId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.product.queryProductInfo");//方法名称

        map.put("productId",productId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 7);
    }

    /**
     * 收藏商品接口
     * @param productId 商品id
     * @param memberId 所在省（名称） 注：无需加后缀“省”
     * @param collectingType 收藏或取消收藏(0:收藏, 1:取消收藏)
     * */
    public void requestFavorPro(String productId,String memberId,String collectingType){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.collect.productCollect");//方法名称

        map.put("productId",productId);
        map.put("memberId",memberId);
        map.put("collectingType",collectingType);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign", sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 8);
    }

    private boolean flgSta;
    /**
     * 1.68、对商家列表按指定方式排序(根据评价排序待定)
     * @param flg 是否处于刷新
     * @param sortType 排序类型(0:默认分类, 1:按销量 2:按评价)
     * @param companyKindId 分类id
     * @param keyword 关键字
     * @param longitude 经度
     * @param latitude 纬度
     * @param provinceName 省份名称
     * @param cityName 市名称
     * @param areaName 区名称
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestShopListBySort(boolean flg,String sortType,String companyKindId,String keyword,
                                      String longitude,String latitude,
                                      String provinceName,String cityName,String areaName,
                                      String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.companys.selectCompanysBySortType");//方法名称

        flgSta = flg;

        map.put("sortType",sortType);
        map.put("companyKindId",companyKindId);
        map.put("keyword",keyword);
        map.put("longitude",longitude);
        map.put("latitude",latitude);
        map.put("provinceName",provinceName);
        map.put("cityName",cityName);
        map.put("areaName",areaName);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        Log.d("demo", "map: " + map);
        postRequest(Constant.RAW_URL, mapToRP(map), 9);
    }

    /**
     * 1.45、根据商品关键字搜索店内商品
     * @param companyId 商家id
     * @param productName 商品名称
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestProList2(String companyId,String productName,String currentPage,String rowCountPerPage){

        ifCategary = false;

        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.product.queryProductByKey");//方法名称

        map.put("companyId",companyId);
        map.put("productName",productName);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 10);
    }

    /**
     * 评论列表
     * @param companyId 商家id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestCommentList(String companyId,String currentPage,String rowCountPerPage){

        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.orderEvaluation.list");//方法名称

        map.put("companyId",companyId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign", sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 11);
    }

    /**
     * 查询我的购物车中的商品数量
     * @param memberId 会员id
     * */
    public void requestCartProNum(String memberId){

        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.queryShoppingCartCount");//方法名称

        map.put("memberId", memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 12);
    }

    /**
     * 支付前校验
     * @param memberId 会员id
     * @param orderIdStr 订单ID串（多个ID用","分隔存放）
     * */
    public void requestBeforePay(String memberId,String orderIdStr){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.order.checkBeforePay");//方法名称

        map.put("memberId",memberId);
        map.put("orderIdStr",orderIdStr);
        Log.d("demo", "orderIdStr_dao: " + orderIdStr);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 13);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(商城列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            List<MallShop> list = JsonUtil.node2pojoList(result.findValue("companyList"), MallShop.class);
            listShopArround.addAll(list);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(搜索商家): " + result);
            List<MallShop> list = JsonUtil.node2pojoList(result.findValue("companysList"), MallShop.class);
            listShop.addAll(list);
        }

        if(requestCode == 2){
            Log.d("demo", "dao中结果集(商家详细): " + result);
            mallShopDetail = JsonUtil.node2pojo(result.findValue("companyInfo"), MallShop.class);
            evaluate = result.findValue("evaluate").asInt();
            orders = result.findValue("orders").asInt();
            //sellersCredit = Float.parseFloat(result.findValue("sellersCredit").asText());
        }

        if(requestCode == 3){
            Log.d("demo", "dao中结果集(收藏商家): " + result);
        }

        if(requestCode == 4){
            Log.d("demo", "dao中结果集(店内商品): " + result);
            List<Pro> list = JsonUtil.node2pojoList(result.findValue("productList"), Pro.class);
            listPro.addAll(list);
        }

        if(requestCode == 5){
            Log.d("demo", "dao中结果集(商品分类): " + result);
            listProCategary = JsonUtil.node2pojoList(result.findValue("productCateList"), ProCategary.class);
        }

        if(requestCode == 6){
            Log.d("demo", "dao中结果集(分类查询): " + result);
            List<Pro> list = JsonUtil.node2pojoList(result.findValue("productList"), Pro.class);

            if(flg2 == 0){
                listProByCategary = new ArrayList<>();
            }

            listProByCategary.addAll(list);
        }

        if(requestCode == 7){
            Log.d("demo", "dao中结果集(商品详细): " + result);
            proDetail = JsonUtil.node2pojo(result.findValue("queryProduct"), ProDetail.class);
            listProImg = JsonUtil.node2pojoList(result.findValue("picList"), Img.class);
            listProDetailImg = JsonUtil.node2pojoList(result.findValue("dtlPicList"), Img.class);
        }

        if(requestCode == 8){
            Log.d("demo", "dao中结果集(收藏商品): " + result);
        }

        if(requestCode == 9){
            Log.d("demo", "dao中结果集(商家排序): " + result);

//            if(flgSta == true){
                List<MallShop> list = JsonUtil.node2pojoList(result.findValue("companysList"), MallShop.class);
                listShopBySort.addAll(list);
//            } else {
//                List<MallShop> list = new ArrayList<>();
//                listShopBySort.addAll(list);
//            }

            //if(flg == 0){

            //} else {
                //listShopBySort = JsonUtil.node2pojoList(result.findValue("companysList"), MallShop.class);
            //}

        }

        if(requestCode == 10){
            Log.d("demo", "dao中结果集(查询商品): " + result);
            List<Pro> list = JsonUtil.node2pojoList(result.findValue("productList"), Pro.class);
            listProByKeyword.addAll(list);
        }

        if(requestCode == 11){
            Log.d("demo", "dao中结果集(评价列表): " + result);
            List<Comment> list = JsonUtil.node2pojoList(result.findValue("orderEvaluationList"), Comment.class);
            listComment.addAll(list);
        }

        if(requestCode == 12){
            Log.d("demo", "dao中结果集(购物车数量): " + result);
            proNum = result.findValue("totalAmount").asInt();
        }

        if(requestCode == 13){
            Log.d("demo", "dao中结果集(支付前校验): " + result);
        }


    }

    public List<MallShop> getListShopArround() {
        return listShopArround;
    }

    public List<MallShop> getListShop() {
        return listShop;
    }

    public MallShop getMallShopDetail() {
        return mallShopDetail;
    }

    public float getSellersCredit() {
        return sellersCredit;
    }

    public List<Pro> getListPro() {
        return listPro;
    }

    public List<ProCategary> getListProCategary() {
        return listProCategary;
    }

    public List<Pro> getListProByCategary() {
        return listProByCategary;
    }

    public boolean isIfCategary() {
        return ifCategary;
    }

    public ProDetail getProDetail() {
        return proDetail;
    }

    public List<Img> getListProImg() {
        return listProImg;
    }

    public List<MallShop> getListShopBySort() {
        return listShopBySort;
    }

    public List<Pro> getListProByKeyword() {
        return listProByKeyword;
    }

    public List<Comment> getListComment() {
        return listComment;
    }

    public int getProNum() {
        return proNum;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public int getOrders() {
        return orders;
    }

    public List<Img> getListProDetailImg() {
        return listProDetailImg;
    }

    public boolean isFlgSta() {
        return flgSta;
    }
}

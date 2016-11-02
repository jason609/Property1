package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.ShopCategory;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 周围商家 dao
 * Created by Eric on 2015/10/28.
 */
public class ShopArroundDao extends IDao {

    /**
     * 商家分类列表
     * */
    private List<ShopCategory> listShopCate = new ArrayList<>();

    public ShopArroundDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取周围商家
     * @param communityId 社区id
     * */
    public void requestShopList(String communityId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.aroundCompany.selectAroundCompany");//方法名称

        map.put("communityId",communityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(周围商家): " + result);
            //listShopCate
            listShopCate = JsonUtil.node2pojo(result.findValue("rtncategoryList"), new TypeReference<List<ShopCategory>>(){});
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }




    }

    public List<ShopCategory> getListShopCate() {
        return listShopCate;
    }
}

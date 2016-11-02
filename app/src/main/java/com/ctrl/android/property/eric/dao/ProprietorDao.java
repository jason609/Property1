package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.entity.Proprietor;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录 dao
 * Created by Eric on 2015/10/28.
 */
public class ProprietorDao extends IDao {

    /**
     * 业主
     * */
    private Proprietor proprietor;

    /**
     * 房屋列表
     * */
    private List<House> listHouse = new ArrayList<>();

    /**
     * 默认房屋
     * */
    private House house = new House();

    public ProprietorDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取房屋列表
     * @param memberId 会员id
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestHouseList(String memberId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberBind.list");//方法名称

        map.put("memberId",memberId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 98);
    }


    /**
     * 获取业主信息
     * @param communityId 社区id
     * @param memberId 会员id
     * */
    public void requestProprietorInfo(String communityId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.proprietor.get");//方法名称

        map.put("communityId",communityId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 99);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 98){
            Log.d("demo","dao中结果集(房屋列表信息): " + result);
            listHouse = JsonUtil.node2pojoList(result.findValue("housesList"), House.class);
            if(listHouse != null){
                AppHolder.getInstance().setHouse(listHouse.get(0));
            }

            //proprietor = JsonUtil.node2pojo(result.findValue("data"), Proprietor.class);
            //AppHolder.getInstance().setProprietor(proprietor);
        }
        if(requestCode == 99){
            Log.d("demo","dao中结果集(业主信息): " + result);
            //proprietor = JsonUtil.node2pojo(result.findValue("data"), Proprietor.class);
            //AppHolder.getInstance().setProprietor(proprietor);
            house = JsonUtil.node2pojo(result.findValue("housesInfo"), House.class);
            AppHolder.getInstance().getProprietor().setProprietorId(house.getProprietorId());
            AppHolder.getInstance().setHouse(house);
        }



    }

    public Proprietor getProprietor() {
        return proprietor;
    }
}

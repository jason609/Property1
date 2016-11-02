package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.ContactGroup;
import com.ctrl.android.property.staff.entity.Contactor;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人列表 dao
 * Created by Eric on 2015/10/28.
 */
public class ContactDao extends IDao {

    private List<ContactGroup> listContactGroup;

    private List<Contactor> listContactor;

    public ContactDao(INetResult activity){
        super(activity);
    }

    /**
     * 通讯录员工分组列表
     * @param communityId 社区id
     * */
    public void requestContactGroupList(String communityId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.contactsGroup.list");//方法名称

        map.put("communityId",communityId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 联系人列表
     * @param communityId 社区id
     * @param groupId 社区id
     * @param keyword 社区id
     * */
    public void requestContactorList(String communityId,String groupId,String keyword){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.staff.list");//方法名称

        map.put("communityId",communityId);
        map.put("groupId",groupId);
        map.put("keyword",keyword);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(联系人分组): " + result);
            listContactGroup = JsonUtil.node2pojoList(result.findValue("contactsGroupList"), ContactGroup.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(联系人列表): " + result);
            listContactor = JsonUtil.node2pojoList(result.findValue("staffList"), Contactor.class);
        }



    }

    public List<ContactGroup> getListContactGroup() {
        return listContactGroup;
    }

    public List<Contactor> getListContactor() {
        return listContactor;
    }
}

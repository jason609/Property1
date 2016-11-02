package com.ctrl.android.property.staff.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 头像 dao
 * Created by Eric on 2015/10/28.
 */
public class IconDao extends IDao {

    private String imgUrl;

    public IconDao(INetResult activity){
        super(activity);
    }

    /**
     * 上传头像
     * @param staffId 员工ID
     * @param headImg 头像数据（Base64编码后的字符串）
     * */
    public void requestUploadIcon(String staffId,String headImg){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.stf.staff.modifyHeadImg");//方法名称

        map.put("staffId",staffId);
        map.put("headImg",headImg);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 90);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 90){
            Log.d("demo","dao中结果集(上传头像): " + result);
            imgUrl = result.findValue("imgUrl").asText();
            AppHolder.getInstance().getStaffInfo().setImgUrl(imgUrl);
            //staffInfo = JsonUtil.node2pojo(result.findValue("staffInfo"), StaffInfo.class);
            //listReceiveAddress = JsonUtil.node2pojoList(result.findValue("receiveAddressList"), ReceiveAddress.class);
        }



    }

}

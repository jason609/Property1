package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信 验证码 dao
 * Created by Eric on 2015/10/28.
 */
public class CodeDao extends IDao {

    private String code;

    public CodeDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取验证码
     * @param mobile 手机号
     * */
    public void requestSMSCode(String mobile){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.messageRecord.getAuthCode");//方法名称

        map.put("mobile",mobile);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(验证码): " + result);
            code = result.findValue("authCode").asText();
        }



    }

    public String getCode() {
        return code;
    }
}

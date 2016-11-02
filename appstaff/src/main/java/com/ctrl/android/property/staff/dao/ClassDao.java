package com.ctrl.android.property.staff.dao;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Kind;
import com.ctrl.android.property.staff.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类列表
 * Created by Administrator on 2015/10/8.
 */
public class ClassDao extends IDao {

    private List<Kind> mData;
    public ClassDao(INetResult activity) {
        super(activity);
        mData = new ArrayList<>();
    }
    public List<Kind> getData() {
        return mData;
    }

    /**
     *获取分类列表接口
     * @param kindKey 类别KEY
     */
    public void requestData(String kindKey){
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        params.put(Constant.SECRET,Constant.SECRET_VALUE);
        params.put(Constant.VERSION,Constant.VERSION_VALUE);
        params.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        params.put(Constant.TYPE, Constant.TYPE_VALUE);
        params.put("method","pm.common.kind.list");
        params.put("kindKey",kindKey);
        String sign = AopUtils.sign(params, Constant.SECRET_VALUE);
        params.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(params), 999);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 999){
            List<Kind> data = JsonUtil.node2pojoList(result.findValue("kindList"), Kind.class);
            mData.addAll(data);
        }
    }
}

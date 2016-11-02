package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Act;
import com.ctrl.android.property.eric.entity.ActDetail;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.Participant;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动 dao
 * Created by Eric on 2015/10/30.
 */
public class ActDao extends IDao {

    /**
     * 社区活动列表
     * */
    private List<Act> listAct = new ArrayList<>();

    /**
     * 活动详细
     * */
    private ActDetail actDetail = new ActDetail();

    /**
     * 参与者列表
     * */
    private List<Participant> listParticipant = new ArrayList<>();

    /**
     * 图片列表
     * */
    private List<Img> listImg = new ArrayList<>();

    public ActDao(INetResult activity){
        super(activity);
    }

    /**
     * 获取社区活动列表
     * @param communityId 社区id
     * @param memberId 会员id
     * @param proprietorId 业主id
     * @param obtainType 活动类型(0:社区活动,1:我参与的,2:我发起的)
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestActList(String communityId,String memberId,String proprietorId,String obtainType,
                               String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.action.queryActionList");//方法名称

        map.put("communityId",communityId);
        map.put("memberId",memberId);
        map.put("proprietorId",proprietorId);
        map.put("obtainType",obtainType);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 获取社区活动详细
     * @param actionId 活动ID
     * @param memberId 会员id
     * */
    public void requestActDetail(String actionId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.action.queryActionDetail");//方法名称

        map.put("actionId",actionId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 参与社区活动
     * @param actionId 活动ID
     * @param memberId 会员id
     * */
    public void requestTakePartInAct(String actionId,String memberId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.action.joinAction");//方法名称

        map.put("actionId",actionId);
        map.put("memberId",memberId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

        if(requestCode == 0){
            Log.d("demo","dao中结果集(活动列表): " + result);
            //forumNoteDetail = JsonUtil.node2pojo(result.findValue("queryPostInfo"), ForumNoteDetail.class);
            listAct = JsonUtil.node2pojoList(result.findValue("actionList"), Act.class);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(活动详细): " + result);
            actDetail = JsonUtil.node2pojo(result.findValue("actionInfo"), ActDetail.class);
            if(result.findValue("participantList").size() > 0){
                listParticipant = JsonUtil.node2pojoList(result.findValue("participantList"), Participant.class);
            }
            listImg = JsonUtil.node2pojoList(result.findValue("actionPictureList"), Img.class);
            //forumNoteDetail = JsonUtil.nod2pojo(result.findValue("queryPostInfo"), ForumNoteDetail.class);
            //listAct = JsonUtil.node2pojoList(result.findValue("actionList"), Act.class);
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(参加活动): " + result);
        }

    }

    public List<Act> getListAct() {
        return listAct;
    }

    public ActDetail getActDetail() {
        return actDetail;
    }

    public List<Participant> getListParticipant() {
        return listParticipant;
    }

    public List<Img> getListImg() {
        return listImg;
    }
}

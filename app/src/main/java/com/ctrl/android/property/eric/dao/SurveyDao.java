package com.ctrl.android.property.eric.dao;

import android.util.Log;

import com.beanu.arad.http.IDao;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Survey;
import com.ctrl.android.property.eric.entity.SurveyDetail;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调查 dao
 * Created by Eric on 2015/10/28.
 */
public class SurveyDao extends IDao {

    /**
     * 调查列表
     * */
    private List<Survey> listSurvey = new ArrayList<>();

    /**
     * 调查详细
     * */
    private List<SurveyDetail> listSurveyDetail = new ArrayList<>();

    /**
     * 选项A 数量
     * */
    private String choice_num_a;
    /**
     * 选项A 名称
     * */
    private String choice_title_a;
    /**
     * 选项B 数量
     * */
    private String choice_num_b;

    /**
     * 选项B 名称
     * */
    private String choice_title_b;
    /**
     * 选项C 数量
     * */
    private String choice_num_c;
    /**
     * 选项C 名称
     * */
    private String choice_title_c;

    /**
     * 建议
     * */
    private String advice;

    public SurveyDao(INetResult activity){
        super(activity);
    }

    /**
     * 调查列表
     * @param communityId 社区ID
     * @param questionnaireType 问卷类型（0：调查问卷、1：投票）
     * @param proprietorId 业主ID
     * @param currentPage 当前页码
     * @param rowCountPerPage 每页条数
     * */
    public void requestSurveyList(String communityId,String questionnaireType,
                                  String proprietorId,String currentPage,String rowCountPerPage){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.questionnaire.list");//方法名称

        map.put("communityId",communityId);
        map.put("questionnaireType",questionnaireType);
        map.put("proprietorId",proprietorId);
        map.put("currentPage",currentPage);
        map.put("rowCountPerPage",rowCountPerPage);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 0);
    }

    /**
     * 调查列表
     * @param questionnaireId 调查问卷或者投票ID
     * @param proprietorId 业主ID
     * */
    public void requestSurveyDetail(String questionnaireId,String proprietorId){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.questionnaire.get");//方法名称

        map.put("questionnaireId",questionnaireId);
        map.put("proprietorId",proprietorId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 1);
    }

    /**
     * 提交调查
     * @param communityId 社区ID
     * @param questionnaireId 调查问卷或者投票ID
     * @param questionnaireType 问卷类型（0：调查问卷、1：投票）
     * @param proprietorId 业主ID
     * @param answerJson 回答内容JSON串（注：格式参照实例）
     * */
    public void requestSubmitSurvey(String communityId,String questionnaireId,String questionnaireType,
                                    String proprietorId,String answerJson){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.questionnaireMessageProprietor.reply");//方法名称

        map.put("communityId",communityId);
        map.put("questionnaireId",questionnaireId);
        map.put("questionnaireType",questionnaireType);
        map.put("proprietorId",proprietorId);
        map.put("answerJson",answerJson);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 2);
    }

    /**
     * 提交投票
     * @param communityId 社区ID
     * @param questionnaireId 调查问卷或者投票ID
     * @param questionnaireType 问卷类型（0：调查问卷、1：投票）
     * @param proprietorId 业主ID
     * @param answerJson 回答内容JSON串（注：格式参照实例）
     * */
    public void requestSubmitVote(String communityId,String questionnaireId,String questionnaireType,
                                    String proprietorId,String answerJson){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.questionnaireMessageProprietor.reply");//方法名称

        map.put("communityId",communityId);
        map.put("questionnaireId",questionnaireId);
        map.put("questionnaireType",questionnaireType);
        map.put("proprietorId",proprietorId);
        map.put("answerJson",answerJson);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);
        postRequest(Constant.RAW_URL, mapToRP(map), 3);
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
        if(requestCode == 0){
            Log.d("demo","dao中结果集(调查列表): " + result);
            //memberInfo = JsonUtil.node2pojo(result.findValue("memberInfo"), MemberInfo.class);
            List<Survey> list = new ArrayList<>();
            list = JsonUtil.node2pojoList(result.findValue("questionnaireList"), Survey.class);
            listSurvey.addAll(list);
        }

        if(requestCode == 1){
            Log.d("demo","dao中结果集(调查详细): " + result);
            listSurveyDetail = JsonUtil.node2pojoList(result.findValue("questionnaireMessageList"), SurveyDetail.class);
            advice = result.findValue("advice").asText();
        }

        if(requestCode == 2){
            Log.d("demo","dao中结果集(提交调查): " + result);
            //listSurveyDetail = JsonUtil.node2pojoList(result.findValue("questionnaireMessageList"), SurveyDetail.class);
            //choice_num_a = result.findValue("optionASum").asText();
            //choice_title_a = result.findValue("optionA").asText();
            //choice_num_b = result.findValue("optionBSum").asText();
            //choice_title_b = result.findValue("optionB").asText();
            //choice_num_c = result.findValue("optionCSum").asText();
            //choice_title_c = result.findValue("optionC").asText();

        }

        if(requestCode == 3){
            Log.d("demo","dao中结果集(提交投票): " + result);
            //listSurveyDetail = JsonUtil.node2pojoList(result.findValue("questionnaireMessageList"), SurveyDetail.class);
            choice_num_a = result.findValue("optionASum").asText();
            if(S.isNull(choice_num_a)){
                choice_num_a = "0";
            }
            choice_title_a = result.findValue("optionA").asText();
            choice_num_b = result.findValue("optionBSum").asText();
            if(S.isNull(choice_num_b)){
                choice_num_b = "0";
            }
            choice_title_b = result.findValue("optionB").asText();
            choice_num_c = result.findValue("optionCSum").asText();
            if(S.isNull(choice_num_c)){
                choice_num_c = "0";
            }
            choice_title_c = result.findValue("optionC").asText();

        }




    }

    public List<Survey> getListSurvey() {
        return listSurvey;
    }

    public String getAdvice() {
        return advice;
    }

    public List<SurveyDetail> getListSurveyDetail() {
        return listSurveyDetail;
    }

    public String getChoice_num_a() {
        return choice_num_a;
    }

    public String getChoice_title_a() {
        return choice_title_a;
    }

    public String getChoice_num_b() {
        return choice_num_b;
    }

    public String getChoice_title_b() {
        return choice_title_b;
    }

    public String getChoice_num_c() {
        return choice_num_c;
    }

    public String getChoice_title_c() {
        return choice_title_c;
    }

}

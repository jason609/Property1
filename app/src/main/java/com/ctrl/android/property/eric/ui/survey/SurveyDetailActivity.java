package com.ctrl.android.property.eric.ui.survey;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.SurveyDao;
import com.ctrl.android.property.eric.entity.SurveyDetail;
import com.ctrl.android.property.eric.ui.adapter.SurveyDetailAdapter;
import com.ctrl.android.property.eric.ui.widget.AlertDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 调查详情 activity
 * Created by Eric on 2015/11/04
 */
public class SurveyDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.scroll_view)
    ScrollView scroll_view;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.survey_advice)//意见建议
    TextView survey_advice;
    @InjectView(R.id.survey_advice_content)//意见建议内容
    EditText survey_advice_content;

    @InjectView(R.id.submit_layout)
    LinearLayout submit_layout;
    @InjectView(R.id.submit_btn)//提交按钮
    TextView submit_btn;

    private List<Map<String,String>> listMap= new ArrayList<>();

    private String TITLE = StrConstant.COMMUNITY_SURVEY_TITLE;

    private SurveyDao surveyDao;
    private SurveyDetailAdapter surveyDetailAdapter;
    private String questionnaireId;
    private String partInFlg;//是否参与（0：未参与、1：已参与）

    private List<SurveyDetail> listSurveyDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.survey_detail_activity);
        ButterKnife.inject(this);

        /**首次进入该页不让弹出软键盘*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        scroll_view.smoothScrollTo(0, 20);//设置scrollview的起始位置在顶部
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init() {

        submit_btn.setOnClickListener(this);

        questionnaireId = getIntent().getStringExtra("id");
        partInFlg = getIntent().getStringExtra("partInFlg");

        Log.d("demo","partInFlg: " + partInFlg);

        String proprietorId = AppHolder.getInstance().getProprietor().getProprietorId();

        surveyDao = new SurveyDao(this);
        showProgress(true);
        surveyDao.requestSurveyDetail(questionnaireId, proprietorId);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(1 == requestCode){

            survey_advice.setVisibility(View.VISIBLE);
            survey_advice_content.setVisibility(View.VISIBLE);
            submit_layout.setVisibility(View.VISIBLE);

            listSurveyDetail = surveyDao.getListSurveyDetail();
            survey_advice.setText((listSurveyDetail.size() + 1) + ". 意见或建议");

            surveyDetailAdapter = new SurveyDetailAdapter(this);
            surveyDetailAdapter.setPartInFlg(partInFlg);
            surveyDetailAdapter.setList(listSurveyDetail);
            surveyDetailAdapter.setPartInFlg(partInFlg);
            listView.setAdapter(surveyDetailAdapter);

            if(partInFlg.equals("0")){
                //
            } else {
                survey_advice_content.setEnabled(false);
                submit_btn.setEnabled(false);
                submit_btn.setClickable(false);
                submit_btn.setBackgroundResource(R.drawable.gray_bg_shap);
            }

            //listMap = getAnsList(listSurveyDetail);
            //AppHolder.getInstance().setListAnswer(getAnsList(listSurveyDetail));

        }

        if(2 == requestCode){

            showAlertDialog();

            //MessageUtils.showShortToast(this,"提交成功");
            //listView.setEnabled(false);
            //survey_advice_content.setEnabled(false);
            //alert_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == submit_btn){

            Log.d("demo", "调查结果: " + S.getJsonStr(getAnsList(listSurveyDetail)));
            String communityId = AppHolder.getInstance().getCommunity().getId();
            //String questionnaireId =
            //问卷类型（0：调查问卷、1：投票）
            String questionnaireType = "0";
            String proprietorId = AppHolder.getInstance().getProprietor().getProprietorId();
            String answerJson = S.getJsonStr(getAnsList(listSurveyDetail));
            showProgress(true);
            surveyDao.requestSubmitSurvey(communityId,questionnaireId,questionnaireType,proprietorId,answerJson);
        }

    }


    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
                onBackPressed();
            }
        });
        return true;
    }

    /**
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    /**
     * 根据问题生成答案列表
     * */
    private List<Map<String,String>> getAnsList(List<SurveyDetail> listSurveyDetail){
        List<Map<String,String>> list= new ArrayList<>();
        for(int i = 0 ; i < listSurveyDetail.size() ; i ++){

            //0：未选中、1：选中
            String stra;
            if(listSurveyDetail.get(i).getAnswerA() == 1){
                stra = "1";
            } else {
                stra = "";
            }

            String strb;
            if(listSurveyDetail.get(i).getAnswerB() == 1){
                strb = "2";
            } else {
                strb = "";
            }

            String strc;
            if(listSurveyDetail.get(i).getAnswerC() == 1){
                strc = "3";
            } else {
                strc = "";
            }

            Map<String,String> map = new HashMap<>();
            map.put(Constant.MESSAGEID,listSurveyDetail.get(i).getId());//问题编号
            map.put(Constant.OPTIONNUM,(stra + strb + strc));//答案
            list.add(map);
        }
        return list;
    }

    /**
     * 弹出自定义弹出框
     * */
    public void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancleFlg(false);
        builder.setMessage("感谢您的参与! ");
        builder.setReturnButton("返回",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }

}

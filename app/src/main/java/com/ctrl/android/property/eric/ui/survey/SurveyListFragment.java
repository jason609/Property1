package com.ctrl.android.property.eric.ui.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarFragment;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.SurveyDao;
import com.ctrl.android.property.eric.entity.Survey;
import com.ctrl.android.property.eric.ui.adapter.SurveyListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区调查(列表) fragment
 * Created by Eric on 2015/9/29.
 */
public class SurveyListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)
    PullToRefreshListView pull_to_refresh_listView;

    private ListView mListView;

    private SurveyListAdapter surveyListAdapter;
    private SurveyDao surveyDao;
    private String surveyType;//调查列表的类型

    private String proprietorId = "1";
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    //private int rowCountPerPage = 1;

    private List<Survey> listSurvey;

    public static SurveyListFragment newInstance(String surveyType){
        SurveyListFragment fragment = new SurveyListFragment();
        fragment.surveyType = surveyType;
        return fragment;
    }

    public static SurveyListFragment newInstance(String surveyType,String proprietorId){
        SurveyListFragment fragment = new SurveyListFragment();
        fragment.surveyType = surveyType;
        fragment.proprietorId = proprietorId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.survey_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        surveyDao = new SurveyDao(this);
        String communityId = AppHolder.getInstance().getCommunity().getId();
        //问卷类型（0：调查问卷、1：投票）
        String questionnaireType = "";
        if(surveyType.equals(Constant.COMMINITY_SURVEY)){
            questionnaireType = "0";
        } else if(surveyType.equals(Constant.COMMINITY_VOTE)){
            questionnaireType = "1";
        }
        showProgress(true);
        surveyDao.requestSurveyList(communityId,questionnaireType,proprietorId,String.valueOf(currentPage),String.valueOf(rowCountPerPage));

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        pull_to_refresh_listView.onRefreshComplete();

        if(0 == requestCode){

            listSurvey = surveyDao.getListSurvey();

            mListView = pull_to_refresh_listView.getRefreshableView();
            surveyListAdapter = new SurveyListAdapter(getActivity());

            surveyListAdapter.setList(listSurvey);
            mListView.setAdapter(surveyListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);
            //注册上下拉定义事件
            pull_to_refresh_listView.setMode(PullToRefreshBase.Mode.BOTH);
            pull_to_refresh_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    surveyDao.getListSurvey().clear();
                    String communityId = AppHolder.getInstance().getCommunity().getId();
                    //问卷类型（0：调查问卷、1：投票）
                    String questionnaireType = "";
                    if(surveyType.equals(Constant.COMMINITY_SURVEY)){
                        questionnaireType = "0";
                    } else if(surveyType.equals(Constant.COMMINITY_VOTE)){
                        questionnaireType = "1";
                    }
                    surveyDao.requestSurveyList(communityId, questionnaireType, proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    //String memberId = AppHolder.getInstance().getUserInfo().getId();
                    //String reqType = orderType;
                    //showProgress(true);
                    //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    String communityId = AppHolder.getInstance().getCommunity().getId();
                    //问卷类型（0：调查问卷、1：投票）
                    String questionnaireType = "";
                    if(surveyType.equals(Constant.COMMINITY_SURVEY)){
                        questionnaireType = "0";
                    } else if(surveyType.equals(Constant.COMMINITY_VOTE)){
                        questionnaireType = "1";
                    }
                    surveyDao.requestSurveyList(communityId, questionnaireType, proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(getActivity(), "点击了: " + listSurvey.get(position - 1).getTitle());
                    if(surveyType.equals(Constant.COMMINITY_SURVEY)){

                        if(listSurvey.get(position - 1).getHasParticipate() == 1){
                            MessageUtils.showShortToast(getActivity(),"已参与该社区调查");
                            Intent intent = new Intent(getActivity(), SurveyDetailActivity.class);
                            intent.putExtra("id",listSurvey.get(position - 1).getId());
                            //是否参与（0：未参与、1：已参与）
                            intent.putExtra("partInFlg","" + listSurvey.get(position - 1).getHasParticipate());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        } else {
                            Intent intent = new Intent(getActivity(), SurveyDetailActivity.class);
                            intent.putExtra("id",listSurvey.get(position - 1).getId());
                            //是否参与（0：未参与、1：已参与）
                            intent.putExtra("partInFlg","" + listSurvey.get(position - 1).getHasParticipate());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        }

                    } else if(surveyType.equals(Constant.COMMINITY_VOTE)){

                        if(listSurvey.get(position - 1).getHasParticipate() == 1){
                            MessageUtils.showShortToast(getActivity(),"已参与该投票");
                            Intent intent = new Intent(getActivity(), VoteDetailActivity.class);
                            intent.putExtra("id",listSurvey.get(position - 1).getId());
                            //是否参与（0：未参与、1：已参与）
                            intent.putExtra("partInFlg","" + listSurvey.get(position - 1).getHasParticipate());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        } else {
                            Intent intent = new Intent(getActivity(), VoteDetailActivity.class);
                            intent.putExtra("id",listSurvey.get(position - 1).getId());
                            //是否参与（0：未参与、1：已参与）
                            intent.putExtra("partInFlg","" + listSurvey.get(position - 1).getHasParticipate());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        }
                    }

                }
            });
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        pull_to_refresh_listView.onRefreshComplete();
        MessageUtils.showShortToast(getActivity(),errorMessage);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name",i + ": " + str + "缴费");
            map.put("amount",i + ".26" + i);
            map.put("status","" + (i%2));

            list.add(map);
        }
        return list;
    }
}

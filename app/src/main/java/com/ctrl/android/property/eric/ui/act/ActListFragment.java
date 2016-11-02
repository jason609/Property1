package com.ctrl.android.property.eric.ui.act;

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
import com.ctrl.android.property.eric.dao.ActDao;
import com.ctrl.android.property.eric.entity.Act;
import com.ctrl.android.property.eric.ui.adapter.ActivityListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商铺评价列表的 fragment
 * Created by Eric on 2015/9/29.
 */
public class ActListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private ActivityListAdapter activityListAdapter;
    private String actListType;//活动列表的类型

    private ActDao actDao;
    private List<Act> listAct;
    private int currentPage = 1;//当前页
    private int rowCountPerPage = Constant.ROW_COUNT_PER_PAGE;//每页数据条数
    //private int rowCountPerPage = 1;//每页数据条数

    private String communityId = AppHolder.getInstance().getCommunity().getId();
    private String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
    private String obtainType = "";//活动类型(0:社区活动,1:我参与的,2:我发起的)

    public static ActListFragment newInstance(String actListType){
        ActListFragment fragment = new ActListFragment();
        fragment.actListType = actListType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.act_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        actDao = new ActDao(this);

        if(actListType == Constant.ACT_ALL){
            //listMap = getListMap("全部活动");
            obtainType = "0";
        } else if(actListType == Constant.ACT_I_TAKE_IN){
            //listMap = getListMap("我参与的");
            obtainType = "1";
        } else if(actListType == Constant.ACT_I_START_UP){
            //listMap = getListMap("我发起的");
            obtainType = "2";
        }
        showProgress(true);
        actDao.requestActList(communityId, memberId, AppHolder.getInstance().getProprietor().getProprietorId(),obtainType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(0 == requestCode){
            //MessageUtils.showShortToast(this,"获取成功");

            listAct = actDao.getListAct();

            mListView = mPullToRefreshListView.getRefreshableView();
            activityListAdapter = new ActivityListAdapter(getActivity());
            activityListAdapter.setList(listAct);
            mListView.setAdapter(activityListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);
            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    actDao.getListAct().clear();
                    actDao.requestActList(communityId, memberId, AppHolder.getInstance().getProprietor().getProprietorId(), obtainType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    actDao.requestActList(communityId, memberId, AppHolder.getInstance().getProprietor().getProprietorId(), obtainType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(getActivity(), "点击了: " + listAct.get(position - 1).getTitle());
                    Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                    intent.putExtra("actionId",listAct.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(getActivity());
                }
            });
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        //super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();
        if(errorNo.equals("002")){
            //
        } else {
            MessageUtils.showShortToast(getActivity(),errorMessage);
        }

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("pic","aa");
            map.put("status","" + (i%2));
            map.put("name",str + ": 活动标题" + i );
            map.put("time","2015.10.26-2015.11." + (i+1));



            list.add(map);
        }
        return list;
    }
}

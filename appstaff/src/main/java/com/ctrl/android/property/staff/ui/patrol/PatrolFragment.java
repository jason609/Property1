package com.ctrl.android.property.staff.ui.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.dao.PatrolDao;
import com.ctrl.android.property.staff.ui.adapter.PatrolAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 巡更巡查fragment
 * Created by Administrator on 2015/11/10.
 */
public class PatrolFragment extends ToolBarFragment {
    @InjectView(R.id.pull_refresh_grid)
    PullToRefreshGridView mPullRefreshGridView;
    private GridView mGridView;
    private PatrolAdapter mAdapter;
    private String mType;
    private int rowCountPerPage=10;
    private int totalCountPerPage;
    private int mPage = 1;
    private int bol = 1;
    private PatrolDao pdao;

    public static PatrolFragment newInstance(String type) {
        PatrolFragment fragment = new PatrolFragment();
        fragment.mType = type;
        return fragment;
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == 0) {
            mPullRefreshGridView.onRefreshComplete();
            showProgress(false);
            bol = 0;
            mAdapter = new PatrolAdapter(getActivity(), this,mType);
            mGridView = mPullRefreshGridView.getRefreshableView();
            mAdapter.setData(pdao.getPatrolRouteList());
            mGridView.setAdapter(mAdapter);
            mGridView.setSelection(totalCountPerPage);
            mGridView.setNumColumns(1);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), PatrolDetail.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(getActivity());
                }
            });
            mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

                @Override
                public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                    mPage = 1;
                    pdao.getPatrolRouteList().clear();
                    pdao.requestPatrolList(AppHolder.getInstance().getStaffInfo().getStaffId(), mType, String.valueOf(mPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage=0;

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                    mPage += 1;
                    pdao.requestPatrolList(AppHolder.getInstance().getStaffInfo().getStaffId(), mType, String.valueOf(mPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage+=rowCountPerPage;

                }
            });
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), PatrolLineActivity.class);
                    intent.putExtra("patrolRouteStaffId", pdao.getPatrolRouteList().get(position).getPatrolRouteStaffId());
                    intent.putExtra("routeStatus", pdao.getPatrolRouteList().get(position).getRouteStatus());
                    startActivityForResult(intent, 200);
                    AnimUtil.intentSlidIn(getActivity());
                }
            });
//        mAdapter.setData(dao.getSendOrders());


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==201){
            PatrolActivity activity = (PatrolActivity) getActivity();
            activity.getAdapter().reLoad();
          //  activity.getmViewpager().setCurrentItem(1);
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        mPullRefreshGridView.onRefreshComplete();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = 1;
        pdao=new PatrolDao(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&bol==1) {
            showProgress(true);
            totalCountPerPage=0;
             pdao.requestPatrolList(AppHolder.getInstance().getStaffInfo().getStaffId(),mType, String.valueOf(mPage),String.valueOf(rowCountPerPage));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.j_fragment_patrol, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



}

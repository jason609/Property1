package com.ctrl.android.property.eric.ui.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.VisitDao;
import com.ctrl.android.property.eric.entity.Visit;
import com.ctrl.android.property.eric.ui.adapter.MyVisitAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的到访
 * Created by Administrator on 2015/10/26.
 * Modify by Eric on 2015/11/2.
 */
public class MyVisitActivity extends AppToolBarActivity {
    @InjectView(R.id.lv_express)
    PullToRefreshListView visitListView;

    private ListView mListView;
    private MyVisitAdapter adapter;

    private VisitDao visitDao;

    private String proprietorId = AppHolder.getInstance().getProprietor().getProprietorId();
    private int currentPage = 1;
    //private int rowCountPerPage = Constant.PAGE_CAPACITY;
    private int rowCountPerPage = 5;
    private List<Visit> listVisit;

    private String TITLE = StrConstant.MY_VISIT_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_visit_activity);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {

        visitDao = new VisitDao(this);
        showProgress(true);
        visitDao.requestVisitList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));



    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        visitListView.onRefreshComplete();

        if(0 == requestCode){
            listVisit = visitDao.getListVisit();

            visitListView.setMode(PullToRefreshBase.Mode.BOTH);
            mListView = visitListView.getRefreshableView();
            adapter = new MyVisitAdapter(this);
            adapter.setList(listVisit);
            mListView.setAdapter(adapter);
            visitListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    visitDao.getListVisit().clear();
                    visitDao.requestVisitList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    visitDao.requestVisitList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //到访类型（0：预约到访、1：突发到访）
                    if(listVisit.get(position-1).getType() == 0){
                        Intent intent = new Intent(MyVisitActivity.this,MyVisitDetail.class);
                        intent.putExtra("communityVisitId",listVisit.get(position-1).getCommunityVisitId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(MyVisitActivity.this);
                    } else {
                        Intent intent = new Intent(MyVisitActivity.this,BurstVisitActivity.class);
                        intent.putExtra("communityVisitId",listVisit.get(position-1).getCommunityVisitId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(MyVisitActivity.this);
                    }



                }
            });

        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        visitListView.onRefreshComplete();
    }

    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(R.string.add);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVisitActivity.this, VisitAddActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MyVisitActivity.this);
            }
        });
        return true;
    }


}

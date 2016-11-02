package com.ctrl.android.property.staff.ui.visit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.VisitDao;
import com.ctrl.android.property.staff.entity.Visit;
import com.ctrl.android.property.staff.ui.adapter.MyVisitAdapter;
import com.ctrl.android.property.staff.ui.qrcode.QrCodeActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 预约到访  activity
* */

public class VisitOrderActivity extends AppToolBarActivity {

    @InjectView(R.id.lv_visit_proruption)
    PullToRefreshListView lv_visit_proruption;

    private ListView mListView;
    private MyVisitAdapter adapter;
    private VisitDao visitDao;
    private String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
    private int currentPage = 1;
    private int rowCountPerPage = 10;
    private int totalCountPerPage = 0;
    private List<Visit> listVisit;
    private String visitType;
    private MyBroadCastReceiver bcr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_order);
        ButterKnife.inject(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //init();
    }

    private void init() {

        visitDao = new VisitDao(this);
        showProgress(true);
        visitType=getIntent().getStringExtra("visitType");
        visitDao.requestVisitList(communityId, visitType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
        bcr=new MyBroadCastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("visit_list_update");
        registerReceiver(bcr,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bcr);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        lv_visit_proruption.onRefreshComplete();

        if(0 == requestCode){
            listVisit = visitDao.getListVisit();
            lv_visit_proruption.setMode(PullToRefreshBase.Mode.BOTH);
            mListView = lv_visit_proruption.getRefreshableView();
            adapter = new MyVisitAdapter(this,visitType);
            adapter.setList(listVisit);
            mListView.setAdapter(adapter);
            mListView.setSelection(totalCountPerPage);
            lv_visit_proruption.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    visitDao.getListVisit().clear();
                    visitDao.requestVisitList(communityId, visitType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage=0;
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    visitDao.requestVisitList(communityId, visitType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage+=rowCountPerPage;
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(VisitOrderActivity.this,VisitOrderHandleDetailActivity.class);
                    intent.putExtra("communityVisitId",listVisit.get(position-1).getCommunityVisitId());
                    intent.addFlags(999);
                    startActivityForResult(intent, 1000);
                    AnimUtil.intentSlidIn(VisitOrderActivity.this);
                }
            });

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(resultCode==1001){
                    currentPage=1;
                    visitDao.getListVisit().clear();
                    visitDao.requestVisitList(communityId, visitType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage=0;
                }

        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        lv_visit_proruption.onRefreshComplete();
    }

    @Override
    public String setupToolBarTitle() {
        return "预约到访";
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
        mRightText.setText("扫码");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VisitOrderActivity.this, QrCodeActivity.class);
                intent.addFlags(888);
                startActivity(intent);
                AnimUtil.intentSlidIn(VisitOrderActivity.this);
            }
        });
        return true;
    }


    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("visit_list_update")) {
                currentPage = 1;
                visitDao.getListVisit().clear();
                visitDao.requestVisitList(communityId, visitType, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                totalCountPerPage = 0;
            }
        }
    }

    /*    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("添加");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitOrderActivity.this, VisitProruptionReleaseActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(VisitOrderActivity.this);
            }
        });
        return true;
    }*/
}

package com.ctrl.android.property.staff.ui.express;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ExpressDao;
import com.ctrl.android.property.staff.entity.Express;
import com.ctrl.android.property.staff.ui.qrcode.QrCodeActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
/*
* 快递列表 activity
* */

public class ExpressListActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.pull_refresh_express)//下拉刷新
    PullToRefreshListView pull_refresh_express;
    @InjectView(R.id.tv_toolbar_login)//扫码
    TextView tv_toolbar_login;
    @InjectView(R.id.tv_toolbar_register)//添加
    TextView tv_toolbar_register;
    @InjectView(R.id.login_regest_layout)//左上角布局
    LinearLayout login_regest_layout;
    @InjectView(R.id.view01)
    View view01;


    private ListView mListView;
    private ExpressAdapter adapter;

    private String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
    private int currentPage = 1;
    //private int rowCountPerPage = Constant.PAGE_CAPACITY;
    private int rowCountPerPage = 10;
    private int totalCountPerPage = 0;
    private List<Express> listExpress;
    private ExpressDao edao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_express_list);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        login_regest_layout.setVisibility(View.VISIBLE);
        view01.setVisibility(View.GONE);
        tv_toolbar_login.setText("扫码");
        tv_toolbar_register.setText("添加");
        tv_toolbar_login.setOnClickListener(this);
        tv_toolbar_register.setOnClickListener(this);
        edao=new ExpressDao(this);
        edao.requestExpressList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        pull_refresh_express.onRefreshComplete();

        if(0 == requestCode){
            listExpress = edao.getExpressList();

            pull_refresh_express.setMode(PullToRefreshBase.Mode.BOTH);
            mListView = pull_refresh_express.getRefreshableView();
            adapter = new ExpressAdapter(this);
            adapter.setList(listExpress);
            mListView.setAdapter(adapter);
            mListView.setSelection(totalCountPerPage);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);
            mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            pull_refresh_express.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    edao.getExpressList().clear();
                    //showProgress(true);
                    edao.requestExpressList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                   totalCountPerPage=0;
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    //showProgress(true);
                    edao.requestExpressList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage+=rowCountPerPage;
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(ExpressActivity.this,getList().get(position - 1).get("type"));
                    Intent intent = new Intent(ExpressListActivity.this, ExpressDetailActivity.class);
                    intent.putExtra("expressId", listExpress.get(position - 1).getId());
                    intent.addFlags(1025);
                    startActivityForResult(intent, 1000);
                    AnimUtil.intentSlidIn(ExpressListActivity.this);
                   // finish();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(1001==resultCode){
                    currentPage = 1;
                    edao.getExpressList().clear();
                    //showProgress(true);
                    edao.requestExpressList(communityId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    totalCountPerPage=0;
                }
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        pull_refresh_express.onRefreshComplete();
    }

    @Override
    public String setupToolBarTitle() {
        return "领取快递";
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
    public void onClick(View v) {

        if(v==tv_toolbar_login){
            Intent intent=new Intent(ExpressListActivity.this, QrCodeActivity.class);
            intent.addFlags(5001);
            startActivity(intent);
            AnimUtil.intentSlidIn(ExpressListActivity.this);
           // finish();

        }
        if(v==tv_toolbar_register){
            Intent intent=new Intent(ExpressListActivity.this,ExpressHandleActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(ExpressListActivity.this);
            //finish();
        }
    }
}

package com.ctrl.android.property.eric.ui.express;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.ExpressDao;
import com.ctrl.android.property.eric.entity.Express;
import com.ctrl.android.property.eric.ui.adapter.ExpressAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 快递列表
 * Created by Administrator on 2015/10/23.
 */
public class ExpressActivity extends AppToolBarActivity {
    @InjectView(R.id.lv_express)
    PullToRefreshListView expressListView;

    private String TITLE = StrConstant.EXPRESS_LIST_TITLE;

    private ListView mListView;
    private ExpressAdapter adapter;
    private ExpressDao expressDao;

    private String proprietorId = AppHolder.getInstance().getProprietor().getProprietorId();
    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;
    //private int rowCountPerPage = 1;
    private List<Express> listExpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init() {

        expressDao = new ExpressDao(this);
        showProgress(true);
        expressDao.requestExpressList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        expressListView.onRefreshComplete();

        if(0 == requestCode){
            listExpress = expressDao.getExpressList();

            expressListView.setMode(PullToRefreshBase.Mode.BOTH);
            mListView = expressListView.getRefreshableView();
            adapter = new ExpressAdapter(this);
            adapter.setList(listExpress);
            mListView.setAdapter(adapter);
            expressListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    expressDao.getExpressList().clear();
                    //showProgress(true);
                    expressDao.requestExpressList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    //showProgress(true);
                    expressDao.requestExpressList(proprietorId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(ExpressActivity.this,getList().get(position - 1).get("type"));
                    Intent intent = new Intent(ExpressActivity.this, ExpressDetailActivity.class);
                    intent.putExtra("expressId",listExpress.get(position-1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(ExpressActivity.this);
                }
            });
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        expressListView.onRefreshComplete();
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

}

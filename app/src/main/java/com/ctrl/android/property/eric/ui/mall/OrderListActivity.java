package com.ctrl.android.property.eric.ui.mall;

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
import com.ctrl.android.property.eric.dao.OrderDao;
import com.ctrl.android.property.eric.entity.Order;
import com.ctrl.android.property.eric.ui.adapter.OrderListAdapter;
import com.ctrl.android.property.eric.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单列表
 * Created by Eric on 2015/10/18
 */
public class OrderListActivity extends AppToolBarActivity {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private int currentPage = 1;
    private int rowCountPerPage = Constant.PAGE_CAPACITY;

    private ListView mListView;
    private OrderDao orderDao;
    private List<Order> listOrder;
    private OrderListAdapter orderListAdapter;

    private String TITLE = StrConstant.ORDER_LIST_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_activity);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        orderDao = new OrderDao(this);
        showProgress(true);
        orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(), String.valueOf(currentPage), String.valueOf(rowCountPerPage));
        //orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(),String.valueOf(currentPage),String.valueOf(rowCountPerPage));
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();

        if(2 == requestCode){
            listOrder = orderDao.getListOrder();

            mListView = mPullToRefreshListView.getRefreshableView();
            orderListAdapter = new OrderListAdapter(this);
            orderListAdapter.setList(listOrder);
            mListView.setAdapter(orderListAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight(20);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    orderDao.getListOrder().clear();
                    orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(), String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    //orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(),String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(), String.valueOf(currentPage), String.valueOf(rowCountPerPage));
                    //orderDao.requestOrderList(AppHolder.getInstance().getMemberInfo().getMemberId(),String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("companyId",listOrder.get(position - 1).getCompanyId());
                    intent.putExtra("orderId",listOrder.get(position - 1).getId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(OrderListActivity.this);

                }
            });
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        mPullToRefreshListView.onRefreshComplete();
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

//    @Override
//    public boolean setupToolBarRightText(TextView mRightText) {
//        mRightText.setText(R.string.add);
//        mRightText.setTextColor(getResources().getColor(R.color.text_white));
//        mRightText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OrderListActivity.this, VisitAddActivity.class);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(OrderListActivity.this);
//            }
//        });
//        return true;
//    }


}

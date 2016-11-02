package com.ctrl.android.property.eric.ui.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarFragment;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.ui.adapter.MallCommentListAdapter;
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
public class MallShopCommentListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private MallCommentListAdapter mallCommentListAdapter;
    private String proListType;//商品列表的类型

    private List<Map<String,String>> listMap;
    private int mPage = 1;//当前页
    private int rowCountPerPage = Constant.ROW_COUNT_PER_PAGE;//每页数据条数

    public static MallShopCommentListFragment newInstance(String proListType){
        MallShopCommentListFragment fragment = new MallShopCommentListFragment();
        fragment.proListType = proListType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mall_pro_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mListView = mPullToRefreshListView.getRefreshableView();
        mallCommentListAdapter = new MallCommentListAdapter(getActivity());
        if(proListType == Constant.MALL_COMMENT_LIST_ALL){
            listMap = getListMap("全部");
        } else if(proListType == Constant.MALL_COMMENT_LIST_GOOD){
            listMap = getListMap("好评");
        } else if(proListType == Constant.MALL_COMMENT_LIST_NORMAL){
            listMap = getListMap("中评");
        } else if(proListType == Constant.MALL_COMMENT_LIST_BAD){
            listMap = getListMap("差评");
        }
        //mallCommentListAdapter.setList(listMap);
        mListView.setAdapter(mallCommentListAdapter);
        mListView.setDivider(null);
        mListView.setDividerHeight(20);
        //注册上下拉定义事件
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //mPage = 1;
                //dao.getOrders().clear();
                //String memberId = AppHolder.getInstance().getUserInfo().getId();
                //String reqType = orderType;
                //showProgress(true);
                //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //String memberId = AppHolder.getInstance().getUserInfo().getId();
                //String reqType = orderType;
                //showProgress(true);
                //mPage = mPage + 1;
                //dao.requestOrderList(memberId,mPage,rowCountPerPage,reqType);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showShortToast(getActivity(), "点击了: " + listMap.get(position - 1).get("content"));
                //Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                //intent.putExtra("orderId",dao.getOrders().get(position - 1).getOrderId());
                //startActivity(intent);
                //AnimUtil.intentSlidIn(getActivity());
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
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
            map.put("rate","" + (i*0.5));
            map.put("time",i + "0");
            map.put("tel","1578****078" + i);
            map.put("content",str + " : 味道还不错, 送餐速度也挺快, 还有饮料.送货速度很快, 质量也不错 " + i );
            map.put("date","2015-09-29 10:3" + i);

            list.add(map);
        }
        return list;
    }
}

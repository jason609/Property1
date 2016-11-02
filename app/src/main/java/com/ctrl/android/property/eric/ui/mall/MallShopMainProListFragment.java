package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarFragment;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.ui.adapter.MallShopProListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商铺主页商品列表的 fragment
 * Created by Eric on 2015/9/29.
 */
public class MallShopMainProListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private MallShopProListAdapter mallShopProListAdapter;
    private String proListType;//商品列表的类型

    private List<Map<String,String>> listMap;
    private int mPage = 1;//当前页
    private int rowCountPerPage = Constant.ROW_COUNT_PER_PAGE;//每页数据条数

    public static MallShopMainProListFragment newInstance(String proListType){
        MallShopMainProListFragment fragment = new MallShopMainProListFragment();
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
        mallShopProListAdapter = new MallShopProListAdapter(getActivity());
        if(proListType == Constant.MALL_PRO_LIST_ALL){
            listMap = getListMap("全部");
        } else if(proListType == Constant.MALL_PRO_LIST_NEW){
            listMap = getListMap("最新");
        } else if(proListType == Constant.MALL_PRO_LIST_SALES){
            listMap = getListMap("销量");
        } else if(proListType == Constant.MALL_PRO_LIST_BEST_SELLER){
            listMap = getListMap("热销");
        }
        //mallShopProListAdapter.setList(listMap);
        mListView.setAdapter(mallShopProListAdapter);
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
                //MessageUtils.showShortToast(getActivity(), "点击了: " + listMap.get(position - 1).get("name"));
                Intent intent = new Intent(getActivity(), MallShopProDetailActivity.class);
                //intent.putExtra("orderId",dao.getOrders().get(position - 1).getOrderId());
                startActivity(intent);
                AnimUtil.intentSlidIn(getActivity());
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
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name", str + "商品名称商品名称商品名称商品名称商品名称商品名称" + i);
            map.put("sales", i + "00");
            map.put("price",i + ".00");

            list.add(map);
        }
        return list;
    }
}

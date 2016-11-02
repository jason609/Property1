package com.ctrl.android.property.jason.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.ui.adapter.HouseTradingAdapter;
import com.ctrl.android.property.jason.util.StrConstant;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋交易 fragment
 * Created by jason on 2015/10/12.
 */
public class HouseTradingRentFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.pull_to_refresh_listView007)
    PullToRefreshListView pull_to_refresh_listView007;//可刷新的列表
    private ListView mListView;
    private HouseTradingAdapter houseTradingAdapter;
    private String tradingType; //交易类型
    private List<Map<String,String>> listMap;


    public static HouseTradingRentFragment newInstance(String type){
        HouseTradingRentFragment fragment=new HouseTradingRentFragment();
        fragment.tradingType=type;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_house_trading_list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);

        mListView=pull_to_refresh_listView007.getRefreshableView();
        houseTradingAdapter=new HouseTradingAdapter(getActivity());
        if(tradingType == StrConstant.HOUSE_TRADING_RENT){
            listMap = getListMap("出租");
        } else if(tradingType == StrConstant.HOUSE_TRADING_SELL){
            listMap = getListMap("出售");
        }
        houseTradingAdapter.setList(listMap);
        mListView.setAdapter(houseTradingAdapter);

    }





    @Override
    public void onClick(View v) {

    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(String str){
        listMap = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("type", str + "中润世纪广场学区房" + i);
            map.put("location", i + "[历下区]-经十路");
            map.put("housedetail",i + "100m  三室一厅");
            map.put("price",i+"$25.00");
            listMap.add(map);
        }
        return listMap;
    }
}

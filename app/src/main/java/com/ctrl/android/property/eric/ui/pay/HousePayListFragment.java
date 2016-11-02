package com.ctrl.android.property.eric.ui.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarFragment;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.ui.adapter.HousePayListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费(列表) fragment
 * Created by Eric on 2015/9/29.
 */
public class HousePayListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    private HousePayListAdapter housePayListAdapter;
    private String payListType;//支付列表的类型

    private List<Map<String,String>> listMap;
    private int mPage = 1;//当前页
    private int rowCountPerPage = Constant.ROW_COUNT_PER_PAGE;//每页数据条数

    public static HousePayListFragment newInstance(String payListType){
        HousePayListFragment fragment = new HousePayListFragment();
        fragment.payListType = payListType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.house_pay_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        //mListView = mPullToRefreshListView.getRefreshableView();
        housePayListAdapter = new HousePayListAdapter(getActivity());
        if(payListType == Constant.PAY_LIST_MONTH_ONE){
            listMap = getListMap("3月");
        } else if(payListType == Constant.PAY_LIST_MONTH_TWO){
            listMap = getListMap("2月");
        } else if(payListType == Constant.PAY_LIST_MONTH_THREE){
            listMap = getListMap("1月");
        }
        housePayListAdapter.setList(listMap);
        listView.setAdapter(housePayListAdapter);
        //listView.setDivider(null);
        //listView.setDividerHeight(20);

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
        for(int i = 0 ; i < 5 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name",i + ": " + str + "缴费");
            map.put("amount",i + ".26" + i);
            map.put("status","" + (i%2));

            list.add(map);
        }
        return list;
    }
}

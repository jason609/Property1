package com.ctrl.android.property.staff.ui.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.dao.ComplaintDao;
import com.ctrl.android.property.staff.entity.Repair;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jason on 2015/10/26.
 */
public class ComplaintFragment extends ToolBarFragment{
    @InjectView(R.id.pull_to_refresh_listView_repairs)
    PullToRefreshListView pull_to_refresh_listView_repairs;//可刷新的列表
    private ListView mListView;
    private ComplaintAdapter myRepairsAdapter;
    private String progressState; //处理状态
    private List<Repair> listMap;//
    private ComplaintDao dao;
    private int currentpage=1;
    private View rootView;
    private int bol=1;


    public static ComplaintFragment newInstance(String state){
         ComplaintFragment fragment=new ComplaintFragment();
        fragment.progressState=state;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.j_fragment_my_repairs,container,false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&bol==1) {
            Log.i("Tag","Tag123131");
            //相当于Fragment的onResume
            dao=new ComplaintDao(this);
            dao.requestComplaintList(AppHolder.getInstance().getStaffInfo().getStaffId(), progressState, "");

        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
       // dao=new RepairDao(this);
       // dao.requestRepairList("1", progressState, "");
        pull_to_refresh_listView_repairs.setMode(PullToRefreshBase.Mode.BOTH);
        pull_to_refresh_listView_repairs.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage = 1;
                dao.getComplaintsList().clear();
                dao.requestComplaintList(AppHolder.getInstance().getStaffInfo().getStaffId(), progressState, currentpage + "");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage += 1;
                dao.requestComplaintList(AppHolder.getInstance().getStaffInfo().getStaffId(), progressState, currentpage + "");
            }
        });


    }


    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        pull_to_refresh_listView_repairs.onRefreshComplete();
        if(errorNo.equals("002")){
            //
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        pull_to_refresh_listView_repairs.onRefreshComplete();
        bol=0;
        if(requestCode==0){
            mListView=pull_to_refresh_listView_repairs.getRefreshableView();
            myRepairsAdapter=new ComplaintAdapter(getActivity(),progressState);
            myRepairsAdapter.setList(dao.getComplaintsList());
            mListView.setAdapter(myRepairsAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (progressState.equals("0")&&dao.getComplaintsList().size()>0) {
                        Intent intent = new Intent(getActivity(), ComplaintPretreatmentActivity.class);
                        intent.putExtra("progressState",progressState);
                        intent.putExtra("repairDemandId", dao.getComplaintsList().get(position-1).getId());
                        startActivityForResult(intent, 666);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("1")&&dao.getComplaintsList().size()>0) {
                        Intent intent = new Intent(getActivity(), ComplaintPretreatmentActivity.class);
                        intent.putExtra("progressState",progressState);
                        intent.putExtra("repairDemandId", dao.getComplaintsList().get(position - 1).getId());
                        startActivityForResult(intent, 331);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("2")&&dao.getComplaintsList().size()>0) {
                        Intent intent = new Intent(getActivity(), ComplaintPretreatmentActivity.class);
                        intent.putExtra("progressState",progressState);
                        intent.putExtra("repairDemandId", dao.getComplaintsList().get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("3")&&dao.getComplaintsList().size()>0) {
                        Intent intent = new Intent(getActivity(), ComplaintAftertreatmentActivity.class);
                        intent.putExtra("repairDemandId", dao.getComplaintsList().get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());

                    }
                   /* if (progressState.equals("4")) {

                        //退单详情跳转
                    }*/
                }
            });




        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==666&&resultCode==667){
            ComplaintActivity activity=(ComplaintActivity)getActivity();
            activity.getAdapter().reLoad();
        }
        if(requestCode==331&&resultCode==333){
            ComplaintActivity activity=(ComplaintActivity)getActivity();
            activity.getAdapter().reLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pull_to_refresh_listView_repairs.onRefreshComplete();
    }
    /**
     * 测试 获取数据的方法
     * */
/*    private List<Map<String,String>> getListMap(String str){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("number","投诉编号：123132" +i);
            map.put("type","投诉类型：" +"水暖维修" );
            map.put("time","投诉时间：2015/10/19 ");

            list.add(map);
        }
        return list;
    }*/

}

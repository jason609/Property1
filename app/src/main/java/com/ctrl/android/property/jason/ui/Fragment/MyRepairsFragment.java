package com.ctrl.android.property.jason.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.jason.dao.RepairDao;
import com.ctrl.android.property.jason.entity.Repair;
import com.ctrl.android.property.jason.ui.adapter.MyRepairsAdapter;
import com.ctrl.android.property.jason.ui.repairs.MyRepairsActivity;
import com.ctrl.android.property.jason.ui.repairs.MyRepairsAftertreatmentActivity;
import com.ctrl.android.property.jason.ui.repairs.MyRepairsPretreatmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jason on 2015/10/26.
 */
public class MyRepairsFragment extends ToolBarFragment{
    @InjectView(R.id.pull_to_refresh_listView_repairs)
    PullToRefreshListView pull_to_refresh_listView_repairs;//可刷新的列表
    private ListView mListView;
    private MyRepairsAdapter myRepairsAdapter;
    private String progressState; //处理状态
    private List<Repair> listMap;
    private RepairDao dao;
    private int currentpage=1;
    private int currentTotalItem=1;
    private int rowCountPerPage=20;

    public static MyRepairsFragment newInstance(String state){
        MyRepairsFragment fragment=new MyRepairsFragment();
        fragment.progressState=state;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.i("TAG","TAG"+7777);
        return inflater.inflate(R.layout.fragment_my_repairs,container,false);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            dao=new RepairDao(this);
            if(AppHolder.getInstance().getProprietor().getProprietorId()==null ||AppHolder.getInstance().getProprietor().getProprietorId().equals("")) {
            //
            }else{
                dao.requestRepairList(AppHolder.getInstance().getProprietor().getProprietorId(), progressState, String.valueOf(currentpage));
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
      /*  dao=new RepairDao(this);
        dao.requestRepairList("1", progressState, "");*/
        pull_to_refresh_listView_repairs.setMode(PullToRefreshBase.Mode.BOTH);

        pull_to_refresh_listView_repairs.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage = 1;
                dao.getRepairList().clear();
                dao.requestRepairList(AppHolder.getInstance().getProprietor().getProprietorId(), progressState, String.valueOf(currentpage));

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage += 1;
                dao.requestRepairList(AppHolder.getInstance().getProprietor().getProprietorId(), progressState, String.valueOf(currentpage));
                currentTotalItem += rowCountPerPage;
            }
        });

    }




    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        try{
        if(requestCode==0) {
            mListView = pull_to_refresh_listView_repairs.getRefreshableView();
            myRepairsAdapter = new MyRepairsAdapter(getActivity(), progressState);
            myRepairsAdapter.setList(dao.getRepairList());
            mListView.setAdapter(myRepairsAdapter);
            mListView.setSelection(currentTotalItem);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (progressState.equals("0")) {
                        Intent intent = new Intent(getActivity(), MyRepairsPretreatmentActivity.class);
                        intent.putExtra("progressState", progressState);
                        intent.putExtra("repairDemandId", dao.getRepairList().get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("1")) {
                        Intent intent = new Intent(getActivity(), MyRepairsPretreatmentActivity.class);
                        intent.putExtra("progressState", progressState);
                        intent.putExtra("repairDemandId", dao.getRepairList().get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("2")) {
                        Intent intent = new Intent(getActivity(), MyRepairsPretreatmentActivity.class);
                        intent.putExtra("progressState", progressState);
                        intent.putExtra("repairDemandId", dao.getRepairList().get(position - 1).getId());
                        startActivityForResult(intent, 2000);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                    if (progressState.equals("3")) {
                        Intent intent = new Intent(getActivity(), MyRepairsAftertreatmentActivity.class);
                        intent.putExtra("repairDemandId", dao.getRepairList().get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());
                    }
                }
            });



            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
         pull_to_refresh_listView_repairs.onRefreshComplete();
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
       /* if(errorNo.equals("002")){
            //Log.d("demo", errorNo + "##" + progressState);
        }*/
        pull_to_refresh_listView_repairs.onRefreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pull_to_refresh_listView_repairs.onRefreshComplete();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2000:
                if(2001==resultCode){
                    MyRepairsActivity a = (MyRepairsActivity) getActivity();
                   a.getAdapter().reLoad();
                }
        }

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

   /* class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }*/

}

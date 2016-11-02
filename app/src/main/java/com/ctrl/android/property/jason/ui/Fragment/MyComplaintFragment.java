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
import com.ctrl.android.property.jason.dao.ComplaintDao;
import com.ctrl.android.property.jason.entity.Complaint;
import com.ctrl.android.property.jason.ui.adapter.MyComplaintAdapter;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintActivity;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintAftertreatmentActivity;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintPretreatmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋出租 fragment
 * Created by jason on 2015/10/12.
 */
public class MyComplaintFragment extends ToolBarFragment implements View.OnClickListener {
    @InjectView(R.id.pull_to_refresh_listView001)
    PullToRefreshListView pull_to_refresh_listView001;//可刷新的列表
    private ListView mListView;
    private MyComplaintAdapter myComplaintAdapter;
    private String progressState; //处理状态 0：处理中、1：已处理、2：已结束
    private List<Complaint> listMap;
    private String proprietorId;
    private ComplaintDao mdao;
    private int currentpage;
    private int currentTotalItem=1;
    private int rowCountPerPage=20;


    public static MyComplaintFragment newInstance(String state){
        MyComplaintFragment fragment=new MyComplaintFragment();
        fragment.progressState=state;
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            mdao= new ComplaintDao(this);
            mdao.requestComplaintList(AppHolder.getInstance().getProprietor().getProprietorId(),progressState,"");

        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_complaint,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        pull_to_refresh_listView001.setMode(PullToRefreshBase.Mode.BOTH);
        pull_to_refresh_listView001.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage = 1;
                mdao.getComplaintList().clear();
                mdao.requestComplaintList(AppHolder.getInstance().getProprietor().getProprietorId(), progressState, currentpage + "");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpage += 1;
                mdao.requestComplaintList(AppHolder.getInstance().getProprietor().getProprietorId(), progressState, currentpage + "");
                currentTotalItem+=rowCountPerPage;

            }
        });

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

       if(requestCode==0){
          // MessageUtils.showShortToast(getActivity(),"连接成功");
           listMap=mdao.getComplaintList();
           mListView=pull_to_refresh_listView001.getRefreshableView();
           myComplaintAdapter=new MyComplaintAdapter(getActivity(),progressState);
         /*  if(progressState == StrConstant.MY_COMPLAINT_PROGRESSING){

               listMap = getListMap("处理中");
           }else if(progressState==StrConstant.MY_COMPLAINT_PROGRESSED){
               listMap=getListMap("已处理");

           } else if(progressState==StrConstant.MY_COMPLAINT_END) {
               listMap = getListMap("已结束");
           }*/
           myComplaintAdapter.setList(listMap);
           mListView.setAdapter(myComplaintAdapter);
           mListView.setSelection(currentTotalItem);
           mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   if (progressState.equals("0")) {
                       Intent intent = new Intent(getActivity(), MyComplaintPretreatmentActivity.class);
                       intent.putExtra("complaintId", listMap.get(position - 1).getId());
                       intent.putExtra("progressState",progressState);
                       startActivity(intent);
                       AnimUtil.intentSlidIn(getActivity());

                   }

                   if (progressState.equals("1")) {
                       Intent intent = new Intent(getActivity(), MyComplaintPretreatmentActivity.class);
                       intent.putExtra("complaintId", listMap.get(position - 1).getId());
                       intent.putExtra("progressState",progressState);
                       startActivityForResult(intent, 2500);
                       AnimUtil.intentSlidIn(getActivity());
                   }
                   if (progressState.equals("2")) {
                       Intent intent = new Intent(getActivity(), MyComplaintPretreatmentActivity.class);
                       intent.putExtra("complaintId", listMap.get(position - 1).getId());
                       intent.putExtra("progressState",progressState);
                       startActivity(intent);
                       AnimUtil.intentSlidIn(getActivity());
                   }
                   if (progressState.equals("3")) {
                       Intent intent = new Intent(getActivity(), MyComplaintAftertreatmentActivity.class);
                       intent.putExtra("complaintId", listMap.get(position - 1).getId());
                       startActivity(intent);
                       AnimUtil.intentSlidIn(getActivity());
                   }


               }
           });


        pull_to_refresh_listView001.onRefreshComplete();
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2500:
                if(2501==resultCode){
                    MyComplaintActivity a = (MyComplaintActivity) getActivity();
                    a.getAdapter().reLoad();
                }

        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
       /* if(errorNo.equals("002")){
            //Log.d("demo", errorNo + "##" + progressState);
        }*/
        pull_to_refresh_listView001.onRefreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pull_to_refresh_listView001.onRefreshComplete();
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

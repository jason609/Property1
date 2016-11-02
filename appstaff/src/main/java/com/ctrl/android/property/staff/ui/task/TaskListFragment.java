package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarFragment;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.TaskDao;
import com.ctrl.android.property.staff.entity.Task;
import com.ctrl.android.property.staff.ui.adapter.TaskListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 任务列表) fragment
 * Created by Eric on 2015/9/29.
 */
public class TaskListFragment extends AppToolBarFragment implements View.OnClickListener {

    @InjectView(R.id.pull_to_refresh_listView)//可刷新的列表
    PullToRefreshListView mPullToRefreshListView;

    private ListView mListView;

    private TaskListAdapter taskListAdapter;

    private TaskDao taskDao;
    private List<Task> listTask;

    private String type;//类型

    private List<Map<String,String>> listMap;
    private int currentPage = 1;//当前页
    private int rowCountPerPage = Constant.ROW_COUNT_PER_PAGE;//每页数据条数

    public static TaskListFragment newInstance(String type){
        TaskListFragment fragment = new TaskListFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        init();

    }

    private void init(){
        taskDao = new TaskDao(this);
        //showProgress(true);

        String taskType2 = "";//（0：执行中、1：已完成）
        if(type.equals(Constant.TASK_DEAL)){
            taskType2 = "0";
        } else if(type.equals(Constant.TASK_DONE)){
            taskType2 = "1";
        }
        String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
        String staffId = AppHolder.getInstance().getStaffInfo().getStaffId();
        showProgress(true);
        taskDao.requestTaskList(taskType2, communityId, staffId, String.valueOf(currentPage), String.valueOf(rowCountPerPage));

//        mListView = mPullToRefreshListView.getRefreshableView();
//        taskListAdapter = new TaskListAdapter(getActivity());
//        if(type == Constant.TASK_DEAL){
//            listMap = getListMap("待处理");
//        } else if(type == Constant.TASK_DONE){
//            listMap = getListMap("已完成");
//        }
//        taskListAdapter.setList(listMap);
//        listView.setAdapter(taskListAdapter);
        //listView.setDivider(null);
        //listView.setDividerHeight(20);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        mPullToRefreshListView.onRefreshComplete();
        showProgress(false);

        if(0 == requestCode){

            listTask = taskDao.getListTask();

            mListView = mPullToRefreshListView.getRefreshableView();
            taskListAdapter = new TaskListAdapter(getActivity());
            taskListAdapter.setFlg(type);
            taskListAdapter.setList(listTask);
            mListView.setAdapter(taskListAdapter);

            //注册上下拉定义事件
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                //下拉刷新
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = 1;
                    taskDao.getListTask().clear();
                    String taskType = "";//（0：执行中、1：已完成）
                    if(type.equals(Constant.TASK_DEAL)){
                        taskType = "0";
                    } else if(type.equals(Constant.TASK_DONE)){
                        taskType = "1";
                    }
                    String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
                    String staffId = AppHolder.getInstance().getStaffInfo().getStaffId();
                    taskDao.requestTaskList(taskType,communityId,staffId,String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                }

                //上拉加载
                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    currentPage = currentPage + 1;
                    String taskType2 = "";//（0：执行中、1：已完成）
                    if(type.equals(Constant.TASK_DEAL)){
                        taskType2 = "0";
                    } else if(type.equals(Constant.TASK_DONE)){
                        taskType2 = "1";
                    }
                    String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
                    String staffId = AppHolder.getInstance().getStaffInfo().getStaffId();
                    taskDao.requestTaskList(taskType2,communityId,staffId,String.valueOf(currentPage),String.valueOf(rowCountPerPage));
                }
            });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(getActivity(),listTask.get(position).getTaskName());
                    //职位级别（0：总管、1：部门负责人、2：普通员工）
                    if(AppHolder.getInstance().getStaffInfo().getGrade() == 2){

                        Intent intent = new Intent(getActivity(), TaskDetailActivity2.class);
                        intent.putExtra("taskId",listTask.get(position - 1).getId());
                        startActivity(intent);
                        AnimUtil.intentSlidIn(getActivity());

                    } else {

                        if((listTask.get(position - 1).getStaffId().
                                equals(AppHolder.getInstance().getStaffInfo().getStaffId()))
                                || (listTask.get(position - 1).getNewStaffId().
                                equals(AppHolder.getInstance().getStaffInfo().getStaffId()))){
                            Intent intent = new Intent(getActivity(), TaskDetailActivity2.class);
                            intent.putExtra("taskId",listTask.get(position - 1).getId());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        } else {
                            Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                            intent.putExtra("taskId",listTask.get(position - 1).getId());
                            startActivity(intent);
                            AnimUtil.intentSlidIn(getActivity());
                        }

                    }

                }
            });

        }

        if(3 == requestCode){
            //mListView = mPullToRefreshListView.getRefreshableView();
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        //super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
        mPullToRefreshListView.onRefreshComplete();
        if(errorNo.equals("002")){
            //
        } else {
            MessageUtils.showShortToast(getActivity(), errorMessage);
        }
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

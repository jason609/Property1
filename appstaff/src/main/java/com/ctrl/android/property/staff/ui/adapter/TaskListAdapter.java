package com.ctrl.android.property.staff.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.entity.Task;
import com.ctrl.android.property.staff.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费列表 adapter
 * Created by Eric on 2015/7/2.
 */
public class TaskListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Task> list;

    private String flg = "";

    public TaskListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Task> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.task_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Task task = list.get(position);

        holder.task_name.setText(S.getStr(task.getTaskName()));
        holder.task_from.setText(S.getStr(task.getName()));
        //holder.task_status.setText("" + (task.getTaskStatus() == 0 ? "执行中" : "已完成"));

        //Log.d("demo","flg: " + flg);

        if(flg.equals("task_deal")){
            holder.task_status.setText("执行中");
        } else if(flg.equals("task_done")){
            holder.task_status.setText("已完成");
        }

        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.task_name)//任务名称
        TextView task_name;
        @InjectView(R.id.task_from)//任务来自
        TextView task_from;
        @InjectView(R.id.task_status)//任务状态
        TextView task_status;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

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
 * 缴费记录的 adapter
 * Created by Eric on 2015/10/20
 */
public class TaskAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Task> list;

    public TaskAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Task> list) {
        this.list = list;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.device_history_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Task task = list.get(position);

        holder.device_time.setText(S.getStr(task.getTaskName()));
        //Log.d("demo",">>>>>> : " + S.getStr(task.getTaskName()));
        holder.device_man.setText(S.getStr(""));
        holder.device_detail.setText(S.getStr(task.getNewTaskStatus() == 0 ? "进行中" : "已完成"));

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.device_time)//养护时间
        TextView device_time;
        @InjectView(R.id.device_man)//养护人
        TextView device_man;
        @InjectView(R.id.device_detail)//详细
        TextView device_detail;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

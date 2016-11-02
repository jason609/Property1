package com.ctrl.android.property.staff.ui.complaint;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.entity.Complaint;
import com.ctrl.android.property.staff.util.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/10/26.
 */
public class ComplaintAdapter extends BaseAdapter{
    private List<Complaint> listMap;
    private Activity mActivity;
    private String state;
    private Complaint repair;


    public ComplaintAdapter(Activity mActivity, String state){
        this.mActivity=mActivity;
        this.state=state;
    }
    public void setList(List<Complaint> list) {
        this.listMap = list;
        notifyDataSetChanged();
    }




     @Override
    public int getCount() {
        return listMap==null?0:listMap.size();
    }

    @Override
    public Object getItem(int position) {
        return listMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(mActivity).inflate(R.layout.j_fragment_my_repairs_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        repair=listMap.get(position);
        holder.tv_repairs_number.setText("投诉编号："+repair.getComplaintNum());
        holder.tv_repairs_type.setText("投诉类型: "+repair.getComplaintKindName());
        if(null!=repair.getCreateTime()) {
            holder.tv_repairs_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(repair.getCreateTime())));
        }
        if(null!=repair.getAssignTime()) {
            holder.tv_repairs_appoint_time.setText("派工时间：" + TimeUtil.date(Long.parseLong(repair.getAssignTime())));
        }
        if(state.equals("0")){
            holder.tv_my_repairs_status.setText("待处理");
            holder.tv_my_repairs_status.setBackgroundResource(R.drawable.tv_shape_red);
        }
        if(state.equals("1")){
            holder.tv_my_repairs_status.setText("处理中");
            holder.tv_my_repairs_status.setBackgroundResource(R.drawable.repair_processing);
        }
        if(state.equals("2")){
            holder.tv_my_repairs_status.setText("已处理");
            holder.tv_my_repairs_status.setBackgroundResource(R.drawable.repair_processed);

        }
        if(state.equals("3")){
            holder.tv_my_repairs_status.setText("已结束");
            holder.tv_my_repairs_status.setBackgroundResource(R.drawable.repair_procend);

        }
        if(state.equals("4")){
            holder.tv_my_repairs_status.setText("已退单");
            holder.tv_my_repairs_status.setBackgroundResource(R.drawable.repair_evaluation);

        }


        return convertView;
    }

    static class ViewHolder{
        @InjectView(R.id.tv_repairs_number)
        TextView tv_repairs_number;
        @InjectView(R.id.tv_repairs_type)
        TextView  tv_repairs_type;
        @InjectView(R.id.tv_repairs_time)
        TextView tv_repairs_time;
        @InjectView(R.id.tv_my_repairs_status)
        TextView tv_my_repairs_status;
        @InjectView(R.id.tv_repairs_appoint_time)
        TextView tv_repairs_appoint_time;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

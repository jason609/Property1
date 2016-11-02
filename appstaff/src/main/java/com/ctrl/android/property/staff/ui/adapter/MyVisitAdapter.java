package com.ctrl.android.property.staff.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.entity.Visit;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的到访adapter
 * Created by Administrator on 2015/10/26.
 */
public class MyVisitAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Visit> list;
    private String visitType=null;

    public MyVisitAdapter(Activity activity,String visitType) {
        mActivity = activity;
        this.visitType=visitType;

    }
    public void setList(List<Visit> list) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity)
                    .inflate(R.layout.j_my_visit_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Visit visit = list.get(position);

        //0：预约到访、1：突发到访
       /* if(visit.getType() == 0){
            holder.visit_flg.setImageResource(R.drawable.appointment_flg_icon);
        } else {
            holder.visit_flg.setImageResource(R.drawable.burst_appointment_flg_icon);
            //holder.visit_flg.setVisibility(View.GONE);
        }*/

        holder.visit_num.setText("到访编号: " + S.getStr(visit.getVisitNum()));
        holder.visit_time.setText(D.getDateStrFromStamp(Constant.YYYY_MM_DD, visit.getCreateTime() == null ? "0000000" : visit.getArriveTime()));
        holder.visit_name.setText(D.getDateStrFromStamp(Constant.HH_MM, visit.getCreateTime() == null ? "0000000" : visit.getArriveTime()));
           if(visitType.equals("0")){
               if (visit.getHandleStatus() == 0) {
                   holder.visit_status.setText("未到访");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_none);
               } else if (visit.getHandleStatus() == 1) {
                   holder.visit_status.setText("已到访");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_refused);
               }

           }else {
               //处理状态（0：待处理、1：同意到访、2：拒绝到访、3：业主不在家）
               if (visit.getHandleStatus() == 0) {
                   holder.visit_status.setText("待处理");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_notice);
               } else if (visit.getHandleStatus() == 1) {
                   holder.visit_status.setText("同意到访");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_agreed);
               } else if (visit.getHandleStatus() == 2) {
                   holder.visit_status.setText("拒绝到访");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_refused);
               } else if (visit.getHandleStatus() == 3) {
                   holder.visit_status.setText("业主不在家");
                   holder.visit_status.setBackgroundResource(R.drawable.visit_none);
               }
           }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.visit_num)//到访编号
        TextView visit_num;
        @InjectView(R.id.visit_time)//到访时间
        TextView visit_time;
        @InjectView(R.id.visit_name)//到访人
        TextView visit_name;
        @InjectView(R.id.visit_status)//访问状态
        TextView visit_status;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

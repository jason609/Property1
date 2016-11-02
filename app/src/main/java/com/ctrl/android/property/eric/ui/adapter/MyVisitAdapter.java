package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Visit;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;

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

    public MyVisitAdapter(Activity activity) {
        mActivity = activity;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity)
                    .inflate(R.layout.my_visit_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Visit visit = list.get(position);

        //0：预约到访、1：突发到访
        if(visit.getType() == 0){
            holder.visit_flg.setImageResource(R.drawable.appointment_flg_icon);
        } else {
            holder.visit_flg.setImageResource(R.drawable.burst_appointment_flg_icon);
            //holder.visit_flg.setVisibility(View.GONE);
        }

        holder.visit_num.setText("到访编号: " + S.getStr(visit.getVisitNum()));
        holder.visit_time.setText(D.getDateStrFromStamp(Constant.YYYY_MM_DD,visit.getArriveTime() == null ? "0000000" : visit.getArriveTime()));
        holder.visit_name.setText(S.getStr(visit.getVisitorName()));

        //0：已预约 1：已结束 2：待处理 3：其他
        if(visit.getReturnStatus() == 0){
            holder.visit_status.setText("已预约");
            holder.visit_status.setBackgroundResource(R.drawable.orange_bg_shap);
        } else if(visit.getReturnStatus() == 1){
            holder.visit_status.setText("已结束");
            holder.visit_status.setBackgroundResource(R.drawable.gray_bg_shap);
        } else if(visit.getReturnStatus() == 2){
            holder.visit_status.setText("待处理");
            holder.visit_status.setBackgroundResource(R.drawable.green_bg_shap);
        } else if(visit.getReturnStatus() == 3){
            holder.visit_status.setText("其他");
            holder.visit_status.setBackgroundResource(R.color.text_black);
            holder.visit_status.setBackgroundResource(R.drawable.gray_bg_shap);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.visit_flg)
        ImageView visit_flg;
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

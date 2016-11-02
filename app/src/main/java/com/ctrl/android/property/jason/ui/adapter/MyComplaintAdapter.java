package com.ctrl.android.property.jason.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.entity.Complaint;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商品详细页 viewpager adapter
 * Created by jason on 2015/10/14
 */
public class MyComplaintAdapter extends BaseAdapter {
    private List<Complaint> listMap;
    private Activity mActivity;
    private String status;
    public MyComplaintAdapter(Activity mActivity,String status){
        this.mActivity=mActivity;
        this.status=status;
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
            convertView= LayoutInflater.from(mActivity).inflate(R.layout.fragment_my_complaint_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        Complaint mComplaint = listMap.get(position);
        holder.tv_complaint_number.setText("投诉编号："+mComplaint.getComplaintNum());
        holder.tv_complaint_type.setText("投诉类型："+mComplaint.getComplaintKindName());
        holder.tv_complaint_room.setText("投诉房间："+mComplaint.getCommunityName()+" "+mComplaint.getBuilding()+"-"+mComplaint.getUnit()+"-"+mComplaint.getRoom());
        if(mComplaint.getCreateTime()!=null) {
            holder.tv_complaint_time.setText("投诉时间：" + TimeUtil.date(Long.parseLong(mComplaint.getCreateTime())));
        }
        if(status.equals("0")){
            holder.tv_complaint_status.setText("待处理");
            holder.tv_complaint_status.setBackgroundResource(R.drawable.tv_shape_green);
        } else if(status.equals("1")){
            holder.tv_complaint_status.setText("处理中");
            holder.tv_complaint_status.setBackgroundResource(R.drawable.tv_shape_yellow);
        }else if(status.equals("2")){
            holder.tv_complaint_status.setText("已处理");
            holder.tv_complaint_status.setBackgroundResource(R.drawable.tv_shape_green);
        }else if(status.equals("3")){
            holder.tv_complaint_status.setText("已结束");
            holder.tv_complaint_status.setBackgroundResource(R.drawable.tv_shape_gray);
        }

        return convertView;
    }

    static class ViewHolder{
      @InjectView(R.id.tv_complaint_number)
        TextView  tv_complaint_number;
      @InjectView(R.id.tv_complaint_type)
        TextView  tv_complaint_type;
      @InjectView(R.id.tv_complaint_time)
        TextView  tv_complaint_time;
        @InjectView(R.id.tv_complaint_status)
                TextView tv_complaint_status;
        @InjectView(R.id.tv_complaint_room)
                TextView tv_complaint_room;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


    }
}

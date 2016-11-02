package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Act;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class FamilyActListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Act> list;

    public FamilyActListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Act> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.family_act_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Act act = list.get(position);

        holder.notice_title.setText(S.getStr(act.getTitle()));
        holder.notice_time.setText(S.getStr(act.getStartTime()));

        //参与状态(0:未参与,1:参与)
        if(act.getParticipateStatus().equals("0")){
            holder.notice_status_flg.setText(R.string.have_sign);
            holder.notice_status_flg.setBackgroundResource(R.drawable.notice_status_sign_y);
        } else if(act.getParticipateStatus().equals("1")){
            holder.notice_status_flg.setText(R.string.havnt_sign);
            holder.notice_status_flg.setBackgroundResource(R.drawable.notice_status_sign_n);
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.notice_title)//公告标题
        TextView notice_title;
        @InjectView(R.id.notice_time)//公告发布时间
        TextView notice_time;
        @InjectView(R.id.notice_status_flg)//公告签收情况
        TextView notice_status_flg;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

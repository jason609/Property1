package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Participant;
import com.ctrl.android.property.eric.ui.act.ActDetailActivity;
import com.ctrl.android.property.eric.ui.widget.RoundImageView;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 参加活动成员 列表
 * Created by Administrator on 2015/7/20.
 */
public class ActGridListAdapter extends BaseAdapter {
    private ActDetailActivity mActivity;
    private List<Participant> list;

    public void setList(List<Participant> list) {
        this.list = list;
    }

    public ActGridListAdapter(ActDetailActivity activity){
        this.mActivity = activity;
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

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.act_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Participant p = list.get(position);

        if(p == null){
            //
        } else {
            Arad.imageLoader.load(S.isNull(p.getImgUrl()) ? "aa" : p.getImgUrl())
                    .placeholder(R.drawable.touxiang2x)
                    .into(holder.act_icon);
            holder.act_name.setText(S.getStr(p.getNickName()));
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.act_icon)//用户头像
        RoundImageView act_icon;
        @InjectView(R.id.act_name)//用户名
        TextView act_name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Item;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class ItemAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Item> list;

    public ItemAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Item> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Item item = list.get(position);

        holder.name.setText(S.getStr(item.getName()));

        if(item.isCheck()){
            holder.check.setChecked(true);
            holder.check.setVisibility(View.VISIBLE);
            holder.name.setTextColor(mActivity.getResources().getColor(R.color.text_green));
        } else {
            holder.name.setTextColor(mActivity.getResources().getColor(R.color.text_black));
            holder.check.setVisibility(View.GONE);
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.name)//名称
        TextView name;
        @InjectView(R.id.check)//
        CheckBox check;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

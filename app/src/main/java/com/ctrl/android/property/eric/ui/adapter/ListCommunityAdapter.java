package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Community;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 只有一个textview列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class ListCommunityAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Community> list;

    public ListCommunityAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Community> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Community item = list.get(position);

        holder.item_name.setText(S.getStr(item.getCommunityName()));

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.item_name)
        TextView item_name;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

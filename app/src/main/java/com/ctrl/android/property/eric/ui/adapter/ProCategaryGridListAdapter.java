package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.ProCategary;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商品分类 列表
 * Created by Eric on 2015/11/12.
 */
public class ProCategaryGridListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<ProCategary> list;

    public void setList(List<ProCategary> list) {
        this.list = list;
    }

    public ProCategaryGridListAdapter(Activity activity){
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.pro_categray_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final ProCategary p = list.get(position);

        holder.pro_categary_name.setText(S.getStr(p.getName()));

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.pro_categary_name)//分类名称
        TextView pro_categary_name;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

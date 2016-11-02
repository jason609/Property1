package com.ctrl.android.property.staff.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.entity.Contactor;
import com.ctrl.android.property.staff.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通讯录 adapter
 * Created by Eric on 2015/10/22
 */
public class ContractAdapter2 extends BaseAdapter{

    private Activity mActivity;
    private List<Contactor> list;

    public ContractAdapter2(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Contactor> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.contact_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Contactor contactor = list.get(position);

        holder.device_name2.setText(S.getStr(contactor.getName()));


        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.device_name2)//设备名称
        TextView device_name2;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

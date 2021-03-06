package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Address;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 只有一个textview列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class ListAddressAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Address> list;

    public ListAddressAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Address> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.list_item3,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Address address = list.get(position);

        holder.item_name.setText(S.getStr(address.getName()));
        holder.item_address.setText(S.getStr(address.getAddress()));
        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.item_name)
        TextView item_name;
        @InjectView(R.id.item_address)
        TextView item_address;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

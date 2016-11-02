package com.ctrl.android.property.jason.ui.mall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.entity.MemberAddress;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jsaon on 2015/10/14.
 */
public  class ChooseShippingAddressAdapter extends BaseAdapter{
    private Context context;
    private List<MemberAddress> list;
   // private int temp=-1;

    public ChooseShippingAddressAdapter(Context context){
        this.context=context;
    }
    public void setList(List<MemberAddress> list) {
        this.list = list;
        notifyDataSetChanged();
    }
   /* public void setSelection(int position){
        temp=position;
    }
*/
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
         ViewHolder holder=null;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.choose_shipping_adress_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final MemberAddress adress = list.get(position);
        holder.tv_name.setText(adress.getReceiveName());
        holder.tv_telephone.setText(adress.getMobile());
        holder.tv_shippingadress.setText(adress.getProvinceName()+adress.getCityName()+adress.getAddress());
        if(adress.getIsDefault().equals("1")) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
            holder.tv_default.setVisibility(View.VISIBLE);
        }
        else{holder.checkBox.setVisibility(View.GONE);
        holder.tv_default.setVisibility(View.GONE);}
        return convertView;
    }

    static class ViewHolder{
        @InjectView(R.id.tv_name)//收货人姓名
                TextView tv_name;
        @InjectView(R.id.tv_telephone)//电话号码
                TextView tv_telephone;
        @InjectView(R.id.tv_shippingadress)//收货地址
                TextView tv_shippingadress;
        @InjectView(R.id.tv_default)//设置默认
                TextView tv_default;
        @InjectView(R.id.checkbox)
        CheckBox checkBox;//单选按钮
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


    }



}


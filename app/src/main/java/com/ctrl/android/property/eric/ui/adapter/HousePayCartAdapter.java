package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费 购物车 adapter
 * Created by Eric on 2015/10/20
 */
public class HousePayCartAdapter extends BaseAdapter{

    private Activity mActivity;
    //private HousePayCartAdapter2 adapter2;
    private List<PropertyPay> listPay;

    public HousePayCartAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<PropertyPay> listPay) {
        this.listPay = listPay;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listPay == null ? 0 : listPay.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_pay_cart_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final PropertyPay pay = listPay.get(position);

        holder.checkbox.setChecked(true);
        holder.checkbox.setEnabled(false);
        holder.name.setText(S.getPropertyPayTypeStr(pay.getPayType()));
        holder.price.setText(N.toPriceFormate(pay.getDebt()));

        //adapter2 = new HousePayCartAdapter2(mActivity);
        //adapter2.setList(pay.getListMap());
        //holder.listView.setAdapter(adapter2);


        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.house_checkbox)
        CheckBox checkbox;
        @InjectView(R.id.house_community)//名称
        TextView name;
        @InjectView(R.id.house_address)//金额
        TextView price;
        //@InjectView(R.id.listView)
        //ListView listView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

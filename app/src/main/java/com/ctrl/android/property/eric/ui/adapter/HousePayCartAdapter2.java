package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.ui.pay.HousePayCartActivity;
import com.ctrl.android.property.eric.util.N;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费 购物车 adapter
 * Created by Eric on 2015/10/20
 */
public class HousePayCartAdapter2 extends BaseAdapter{

    private HousePayCartActivity mActivity;
    private List<Map<String,String>> listMap;

    public HousePayCartAdapter2(HousePayCartActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Map<String,String>> listMap) {
        this.listMap = listMap;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listMap == null ? 0 : listMap.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_pay_cart_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Map<String,String> map = listMap.get(position);

        holder.item_checkbox.setChecked(true);
        holder.item_name.setText(map.get("name"));
        holder.item_amount.setText(N.toPriceFormate(map.get("amount")));

      return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.item_checkbox)
        CheckBox item_checkbox;
        @InjectView(R.id.item_name)//名称
        TextView item_name;
        @InjectView(R.id.item_amount)//金额
        TextView item_amount;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

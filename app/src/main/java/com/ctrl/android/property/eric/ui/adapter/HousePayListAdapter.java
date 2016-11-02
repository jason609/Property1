package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费列表 adapter
 * Created by Eric on 2015/7/2.
 */
public class HousePayListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Map<String,String>> list;

    public HousePayListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Map<String,String>> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_pay_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Map<String,String> map = list.get(position);

        holder.house_pay_name.setText(S.getStr(map.get("name")));
        holder.house_pay_amount.setText(N.toPriceFormate(map.get("amount")));

        if(S.getStr(map.get("status")).equals("0")){
            holder.house_pay_status.setText("已缴费");
            holder.house_pay_status.setBackgroundResource(R.drawable.orange_bg_shap);
        } else {
            holder.house_pay_status.setText("未缴费");
            holder.house_pay_status.setBackgroundResource(R.drawable.gray_bg_shap);
        }




        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.house_pay_name)//缴费名称
        TextView house_pay_name;
        @InjectView(R.id.house_pay_amount)//缴费金额
        TextView house_pay_amount;
        @InjectView(R.id.house_pay_status)//缴费状态
        TextView house_pay_status;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

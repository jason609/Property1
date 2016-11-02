package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.PropertyHisPay;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 缴费记录的 adapter
 * Created by Eric on 2015/10/20
 */
public class HousePayHistoryAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<PropertyHisPay> list;

    public HousePayHistoryAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<PropertyHisPay> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_pay_history_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final PropertyHisPay pay_item = list.get(position);

        holder.house_pay_history_time.setText(D.getDateStrFromStamp("MM月dd日",pay_item.getPayTime()));
        holder.house_pay_history_item.setText(S.getPropertyPayTypeStr(pay_item.getPayType()));
        holder.house_pay_history_amount.setText(N.toPriceFormate(pay_item.getDebt()));

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.house_pay_history_time)//缴费时间
        TextView house_pay_history_time;
        @InjectView(R.id.house_pay_history_item)//缴费项目
        TextView house_pay_history_item;
        @InjectView(R.id.house_pay_history_amount)//缴费金额
        TextView house_pay_history_amount;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

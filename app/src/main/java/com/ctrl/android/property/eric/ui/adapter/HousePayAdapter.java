package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.PropertyPay;
import com.ctrl.android.property.eric.ui.pay.HousePayActivity;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 物业缴费 adapter
 * Created by Eric on 2015/10/20
 */
public class HousePayAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<PropertyPay> list;

    public HousePayAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<PropertyPay> list) {
        this.list = list;
        //notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_pay_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final PropertyPay pay_item = list.get(position);

        //费用名称
        holder.pay_title.setText(S.getPropertyPayTypeStr(pay_item.getPayType()));
        //费用总额
        holder.pay_amount.setText(N.toPriceFormate(pay_item.getReceivableMoney()));

        if(pay_item.getPayFlg() == 1){
            holder.pay_add_btn.setBackgroundResource(R.drawable.gray_bg_shap);
            holder.pay_add_btn.setClickable(false);
            holder.pay_add_btn.setEnabled(false);
            holder.pay_add_btn.setText("已加入");
        } else {
            holder.pay_add_btn.setBackgroundResource(R.drawable.orange_bg_shap);
            holder.pay_add_btn.setClickable(true);
            holder.pay_add_btn.setEnabled(true);
            holder.pay_add_btn.setText("加入缴费");
        }

        //加入交费
        holder.pay_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HousePayActivity)mActivity).calAmount(position);
            }
        });
        //收费标准
        holder.pay_standard.setText("收费标准: " + S.getStr(pay_item.getPayStandard()));
        //应缴日期
        holder.pay_time.setText("应收日期: " + D.getDateStrFromStamp("yyyy.MM.dd", pay_item.getReceivableTime()));
        //计费开始时间
        holder.pay_time_start.setText(D.getDateStrFromStamp("yyyy.MM.dd", pay_item.getApplyStartTime()));
        //计费结束时间
        holder.pay_time_end.setText(D.getDateStrFromStamp("yyyy.MM.dd", pay_item.getApplyEndTime()));
        //交费金额
        holder.pay_cost.setText(N.toPriceFormate(pay_item.getCharge()));
        //滞纳金
        holder.pay_cost_delay.setText(N.toPriceFormate(pay_item.getPenalty()));
        //应收金额
        holder.pay_cost_will.setText(N.toPriceFormate(pay_item.getReceivableMoney()));
        //已收金额
        holder.pay_cost_already.setText(N.toPriceFormate(pay_item.getReceivedMoney()));
        //欠款
        holder.pay_own_amount.setText(N.toPriceFormate(pay_item.getDebt()));
        //欠款月数
        holder.pay_own_month.setText(S.getStr(pay_item.getDebtMonth()));

        holder.top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getFlg() == 1){
                    list.get(position).setFlg(2);
                } else {
                    list.get(position).setFlg(1);
                }

                notifyDataSetChanged();
            }
        });

        if(list.get(position).getFlg() == 1){
            holder.other_layout.setVisibility(View.GONE);
        } else {
            holder.other_layout.setVisibility(View.VISIBLE);
        }

        //holder.easy_shop_arround_name.setText((position + 1) + "." + S.getStr(shop.getShopName()));
        //holder.easy_shop_arround_address.setText(S.getStr(shop.getShopAddress()));

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.pay_title)//费用名称
        TextView pay_title;
        @InjectView(R.id.pay_amount)//费用总额
        TextView pay_amount;
        @InjectView(R.id.pay_add_btn)//加入交费
        TextView pay_add_btn;
        @InjectView(R.id.pay_standard)//收费标准
        TextView pay_standard;
        @InjectView(R.id.pay_time)//应缴日期
        TextView pay_time;
        @InjectView(R.id.pay_time_start)//计费开始时间
        TextView pay_time_start;
        @InjectView(R.id.pay_time_end)//计费结束时间
        TextView pay_time_end;
        @InjectView(R.id.pay_cost)//交费金额
        TextView pay_cost;
        @InjectView(R.id.pay_cost_delay)//滞纳金
        TextView pay_cost_delay;
        @InjectView(R.id.pay_cost_will)//应收金额
        TextView pay_cost_will;
        @InjectView(R.id.pay_cost_already)//已收金额
        TextView pay_cost_already;
        @InjectView(R.id.pay_own_amount)//欠款
        TextView pay_own_amount;
        @InjectView(R.id.pay_own_month)//欠款月数
        TextView pay_own_month;

        @InjectView(R.id.top_layout)
        LinearLayout top_layout;
        @InjectView(R.id.other_layout)
        LinearLayout other_layout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.OrderDetailItem;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单详细 商品列表的 adapter
 * Created by Eric on 2015/7/2.
 */
public class OrderDetailItemAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<OrderDetailItem> list;

    public OrderDetailItemAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<OrderDetailItem> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.order_detail_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final OrderDetailItem orderItem = list.get(position);

        //Log.d("demo", "地址: " + orderPrgetImgUrl());
        Arad.imageLoader.load(S.getStr(S.isNull(orderItem.getZipImg()) ? "aa" : orderItem.getZipImg()))
                .placeholder(R.drawable.default_image)
                .into(holder.order_pro_image);

        holder.order_pro_name.setText(S.getStr(orderItem.getProductName()));
        holder.order_pro_price.setText("￥ " + N.toPriceFormate(orderItem.getSellingPrice()));
        holder.order_pro_num.setText("× " + S.getStr(orderItem.getNums()));
        holder.order_pro_amount.setText("总价: ￥ " + N.toPriceFormate(orderItem.getAmount()));
//        holder.order_pro_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(mActivity,"取消订单");
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.order_pro_image)//商品图片
        ImageView order_pro_image;
        @InjectView(R.id.order_pro_name)//商品名称
        TextView order_pro_name;
        @InjectView(R.id.order_pro_price)//商品价格
        TextView order_pro_price;
        @InjectView(R.id.order_pro_num)//商品数量
        TextView order_pro_num;
        @InjectView(R.id.order_pro_amount)//商品总价
        TextView order_pro_amount;
        //@InjectView(R.id.order_pro_cancel)//订单取消
        //TextView order_pro_cancel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.OrderPro;
import com.ctrl.android.property.eric.ui.mall.MallShopOrderActivity;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单 商品列表的 adapter
 * Created by Eric on 2015/7/2.
 */
public class MallShopOrderProListAdapter extends BaseAdapter{

    private MallShopOrderActivity mActivity;
    private List<OrderPro> listOrderPro;

    public MallShopOrderProListAdapter(MallShopOrderActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setListCartPro(List<OrderPro> listOrderPro) {
        this.listOrderPro = listOrderPro;
        //notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listOrderPro == null ? 0 : listOrderPro.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.mall_order_pro_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final OrderPro orderPro = listOrderPro.get(position);

        //Log.d("demo", "地址: " + orderPrgetImgUrl());
        Arad.imageLoader.load(S.getStr(S.isNull(orderPro.getZipImg()) ? "aa" : orderPro.getZipImg()))
                .placeholder(R.drawable.default_image)
                .into(holder.order_pro_image);

        holder.order_pro_name.setText(S.getStr(orderPro.getProductName()));
        holder.order_pro_info.setText(S.getStr(""));
        holder.order_pro_price.setText("￥ " + N.toPriceFormate(orderPro.getSellingPrice()));
        holder.order_pro_num.setText("× " + S.getStr(orderPro.getProductNum()));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.order_pro_image)//商品图片
        ImageView order_pro_image;
        @InjectView(R.id.order_pro_name)//商品名称
        TextView order_pro_name;
        @InjectView(R.id.order_pro_info)//商品简介
        TextView order_pro_info;
        @InjectView(R.id.order_pro_price)//商品价格
        TextView order_pro_price;
        @InjectView(R.id.order_pro_num)//商品数量
        TextView order_pro_num;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

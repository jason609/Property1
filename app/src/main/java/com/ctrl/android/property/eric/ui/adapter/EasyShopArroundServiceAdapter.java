package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.ShopService;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公交出行中 公交站列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class EasyShopArroundServiceAdapter extends BaseAdapter{

    private Activity mActivity;
    private EasyShopArroundServiceAdapter2 adapter2;
    private List<ShopService> listShop;

    public EasyShopArroundServiceAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<ShopService> listShop) {
        this.listShop = listShop;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listShop == null ? 0 : listShop.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.easy_shop_arround_service_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final ShopService shop = listShop.get(position);

        Arad.imageLoader
                .load("aa")
                .placeholder(R.drawable.easy_shop_around_service_default_icon)
                .into(holder.shop_service_img);
        holder.shop_service_name.setText(S.getStr(shop.getCategory()));

        adapter2 = new EasyShopArroundServiceAdapter2(mActivity);
        adapter2.setList(shop.getListMap());
        holder.listView_shop_service.setAdapter(adapter2);

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.shop_service_img)//商家图像
        ImageView shop_service_img;
        @InjectView(R.id.shop_service_name)//商家名称
        TextView shop_service_name;
        @InjectView(R.id.listView_shop_service)
        ListView listView_shop_service;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

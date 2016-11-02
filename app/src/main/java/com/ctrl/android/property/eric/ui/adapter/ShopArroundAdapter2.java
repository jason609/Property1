package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Shop;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公交出行中 公交站列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class ShopArroundAdapter2 extends BaseAdapter{

    private Activity mActivity;
    private List<Shop> listShop;

    public ShopArroundAdapter2(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Shop> listShop) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.easy_shop_arround_service_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Shop shop = listShop.get(position);


        holder.shop_service_name2.setText(S.getStr(shop.getCom()));
        holder.shop_service_tel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!S.isNull(shop.getMobile())){
                    AndroidUtil.dial(mActivity, shop.getMobile());
                } else {
                    MessageUtils.showShortToast(mActivity,"未获得电话号码");
                }

            }
        });
        return convertView;
    }

    static class ViewHolder {

        //@InjectView(R.id.shop_service_img)//商家图像
        //ImageView shop_service_img;
        @InjectView(R.id.shop_service_name2)//商家名称
        TextView shop_service_name2;
        @InjectView(R.id.shop_service_tel2)//
        ImageView shop_service_tel2;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

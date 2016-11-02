package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.MallShop;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商铺列表 adapter
 * Created by Eric on 2015/7/2.
 */
public class MallShopListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<MallShop> list;

    public MallShopListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<MallShop> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.mall_shop_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final MallShop shop = list.get(position);

        Arad.imageLoader
                .load(S.isNull(shop.getLogoUrl()) ? "aa" : shop.getLogoUrl())
                .placeholder(R.drawable.default_image)
                .into(holder.shop_pic);
        holder.shop_name.setText(S.getStr(shop.getCompanyName()));
        holder.shop_business_time.setText(StrConstant.BUSINESS_TIME_TITLE + shop.getBusinessStartTime() + "-" + shop.getBusinessEndTime());
        holder.shop_ratingBar.setRating(shop.getEvaluatLevel() / 2);
        //Log.d("demo","EvaluatLevel: " + shop.getEvaluatLevel());

        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.shop_pic)//商家图片
        ImageView shop_pic;
        @InjectView(R.id.shop_name)//商家名称
        TextView shop_name;
        @InjectView(R.id.shop_business_time)//营业时间
        TextView shop_business_time;
        @InjectView(R.id.shop_ratingBar)//卖家信用等级
        RatingBar shop_ratingBar;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

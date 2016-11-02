package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.ShopArround_Easy;
import com.ctrl.android.property.eric.ui.easy.EasyShopAroundListActivity;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 周边商家 adapter
 * Created by Eric on 2015/10/20
 */
public class EasyShopArroundListAdapter extends BaseAdapter{

    private EasyShopAroundListActivity mActivity;
    private List<ShopArround_Easy> listShopArround;

    public EasyShopArroundListAdapter(EasyShopAroundListActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<ShopArround_Easy> listShopArround) {
        this.listShopArround = listShopArround;
        //notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listShopArround == null ? 0 : listShopArround.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.easy_shop_arround_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final ShopArround_Easy shop = listShopArround.get(position);

        holder.easy_shop_arround_name.setText((position + 1) + "." + S.getStr(shop.getShopName()));
        holder.easy_shop_arround_address.setText(S.getStr(shop.getShopAddress()));
        holder.easy_shop_arround_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(mActivity,shop.getShopName() + shop.getShopPhoneNum());
                if(!S.isNull(shop.getShopPhoneNum())){
                    AndroidUtil.dial(mActivity, shop.getShopPhoneNum());
                } else {
                    MessageUtils.showShortToast(mActivity, StrConstant.HAVE_NO_TEL);
                }

            }
        });

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.easy_shop_arround_name)//商家名称
        TextView easy_shop_arround_name;
        @InjectView(R.id.easy_shop_arround_address)//商家地址
        TextView easy_shop_arround_address;
        @InjectView(R.id.easy_shop_arround_tel)//商家电话
        ImageView easy_shop_arround_tel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

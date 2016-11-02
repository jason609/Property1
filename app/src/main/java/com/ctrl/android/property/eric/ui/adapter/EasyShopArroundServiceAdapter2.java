package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AndroidUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.util.S;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公交出行中 公交站列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class EasyShopArroundServiceAdapter2 extends BaseAdapter{

    private Activity mActivity;
    private List<Map<String,String>> listMap;

    public EasyShopArroundServiceAdapter2(Activity mActivity){
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.easy_shop_arround_service_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Map<String,String> map = listMap.get(position);


        holder.shop_service_name2.setText(S.getStr(map.get("name")));
        holder.shop_service_tel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.dial(mActivity, map.get("tel"));
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

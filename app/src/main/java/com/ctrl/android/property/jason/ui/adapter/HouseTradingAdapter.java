package com.ctrl.android.property.jason.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jason on 2015/10/13.
 */
public class HouseTradingAdapter extends BaseAdapter{
    private List<Map<String,String>>listMap;
    private Activity mActivity;
    public HouseTradingAdapter(Activity activity) {
        this.mActivity=activity;
    }

    public void setList(List<Map<String,String>> list) {
        this.listMap = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listMap==null?0:listMap.size();
    }

    @Override
    public Object getItem(int position) {
        return listMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_house_trading_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Map<String,String>map=listMap.get(position);
        Arad.imageLoader
                .load("aa")
                .placeholder(R.drawable.pro_pic_01)
                .into(holder.img_house_trading_item);
        holder.tv01_house_trading_item.setText(map.get("type"));
        holder.tv02_house_trading_item.setText(map.get("location"));
        holder.tv03_house_trading_item.setText(map.get("housedetail"));
        holder.tv05_house_trading_item.setText(map.get("price"));
        return convertView;
    }


   static class ViewHolder{
       @InjectView(R.id.img_house_trading_item)//房屋图片
               ImageView img_house_trading_item;
       @InjectView(R.id.tv01_house_trading_item)//房屋类型
               TextView tv01_house_trading_item;
       @InjectView(R.id.tv02_house_trading_item)//所在位置
               TextView tv02_house_trading_item;
       @InjectView(R.id.tv03_house_trading_item)//房屋详情
               TextView tv03_house_trading_item;
       @InjectView(R.id.tv04_house_trading_item)//查看详情
               TextView tv04_house_trading_item;
       @InjectView(R.id.tv05_house_trading_item)//价格
               TextView tv05_house_trading_item;
       ViewHolder(View view) {
           ButterKnife.inject(this, view);
       }

    }
}

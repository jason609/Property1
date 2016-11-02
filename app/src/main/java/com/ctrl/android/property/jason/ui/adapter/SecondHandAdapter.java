package com.ctrl.android.property.jason.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.ui.customctrol.RoundImageView;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *二手交易详细页 fragment adapter
 * Created by jason on 2015/10/14
 */
public class SecondHandAdapter extends BaseAdapter {
    private List<Map<String,String>> listMap;
    private Activity mActivity;
    public SecondHandAdapter(Activity mActivity){
        this.mActivity=mActivity;
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
        if(convertView==null){
            convertView= LayoutInflater.from(mActivity).inflate(R.layout.fragment_second_hand_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        Map<String, String> map = listMap.get(position);
        holder.tv_second_hand_item_name.setText(map.get("name"));
        holder.tv_second_hand_item_location.setText(map.get("location"));
        holder.tv_second_hand_item_price.setText(map.get("price"));
        holder.tv_second_hand_item_time.setText(map.get("time"));
        return convertView;
    }

    static class ViewHolder{
      @InjectView(R.id.iv_photo)
        RoundImageView iv_photo;
      @InjectView(R.id.tv_second_hand_item_name)
        TextView  tv_second_hand_item_name;
      @InjectView(R.id.tv_second_hand_item_time)
        TextView  tv_second_hand_item_time;
      @InjectView(R.id.tv_second_hand_item_location)
        TextView  tv_second_hand_item_location;
      @InjectView(R.id.tv_second_hand_item_price)
        TextView tv_second_hand_item_price;
      @InjectView(R.id.iv01_second_hand)
      ImageView iv01_second_hand;
      @InjectView(R.id.iv02_second_hand)
      ImageView iv02_second_hand;
      @InjectView(R.id.iv03_second_hand)
      ImageView iv03_second_hand;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}

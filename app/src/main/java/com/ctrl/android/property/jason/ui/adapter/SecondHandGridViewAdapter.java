package com.ctrl.android.property.jason.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.entity.Kind;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *二手交易详细页 fragment adapter
 * Created by jason on 2015/10/14
 */
public class SecondHandGridViewAdapter extends BaseAdapter {
    private List<String> listMap;
    private Activity mActivity;
    private int clickTemp = -1;
    private Kind kind;
    //标识选择的Item

    public SecondHandGridViewAdapter(Activity mActivity){
        this.mActivity=mActivity;
    }
    public void setList(List<String> list) {
        this.listMap = list;
        notifyDataSetChanged();
    }

    public void setSeclection(int position) {
        clickTemp = position;
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
            convertView= LayoutInflater.from(mActivity).inflate(R.layout.gv_second_hand_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tv_gv_item.setText(listMap.get(position));
        if (clickTemp == position) {
            holder.tv_gv_item.setBackgroundResource(R.drawable.tv_border_black_shape);
            holder.tv_gv_item.setTextColor(Color.BLACK);
            holder.iv_second_hand_check.setVisibility(View.VISIBLE);

        } else {
            holder.tv_gv_item.setBackgroundResource(R.drawable.tv_border_gray_shape);
            holder.tv_gv_item.setTextColor(Color.GRAY);
            holder.iv_second_hand_check.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder{
      @InjectView(R.id.tv_gv_item)
        TextView  tv_gv_item;

      @InjectView(R.id.iv_second_hand_check)
      ImageView  iv_second_hand_check;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}

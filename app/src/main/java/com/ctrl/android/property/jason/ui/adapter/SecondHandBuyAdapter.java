package com.ctrl.android.property.jason.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.jason.entity.UsedGoods;
import com.ctrl.android.property.jason.ui.customctrol.RoundImageView;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *二手交易详细页 fragment adapter
 * Created by jason on 2015/10/14
 */
public class SecondHandBuyAdapter extends BaseAdapter {
    private List<UsedGoods> listMap;
    private Activity mActivity;
    public SecondHandBuyAdapter(Activity mActivity){
        this.mActivity=mActivity;
    }
    public void setList(List<UsedGoods> list) {
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
            convertView= LayoutInflater.from(mActivity).inflate(R.layout.fragment_second_hand_buy_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        UsedGoods usedGoods=listMap.get(position);
        holder.tv_second_hand_item_name.setText(usedGoods.getProprietorName());
        holder.tv_second_hand_item_location.setText(usedGoods.getCommunityName() + " " + usedGoods.getBuilding()+"-"+usedGoods.getUnit()+"-"+usedGoods.getRoom());
        DecimalFormat df = new DecimalFormat("#.00");
        holder.tv_second_hand_item_price.setText("￥" +df.format(usedGoods.getSellingPrice()));
        long currentTime = System.currentTimeMillis();
        Log.i("demo", "当前时间" + currentTime);
        Log.i("demo", "传人时间" +usedGoods.getCreateTime());
       // long time = currentTime - Long.parseLong(usedGoods.getCreateTime());
        holder.tv_second_hand_item_time.setText(TimeUtil.dateTime(usedGoods.getCreateTime()));
        holder.tv_second_hand_item_title.setText(usedGoods.getTitle());
        Arad.imageLoader.load(usedGoods.getImgUrl()==null||usedGoods.getImgUrl().equals("")
                ?"aa":usedGoods.getImgUrl()).placeholder(R.drawable.touxiang2x).into(holder.iv_photo_buy);

        return convertView;
    }

    static class ViewHolder{
      @InjectView(R.id.tv_second_hand_item_name)
        TextView  tv_second_hand_item_name;
      @InjectView(R.id.tv_second_hand_item_time)
        TextView  tv_second_hand_item_time;
      @InjectView(R.id.tv_second_hand_item_location)
        TextView  tv_second_hand_item_location;
      @InjectView(R.id.tv_second_hand_item_price)
        TextView tv_second_hand_item_price;
      @InjectView(R.id.tv_second_hand_item_title)
        TextView  tv_second_hand_item_title;
        @InjectView(R.id.iv_photo_buy)
        RoundImageView iv_photo_buy;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}

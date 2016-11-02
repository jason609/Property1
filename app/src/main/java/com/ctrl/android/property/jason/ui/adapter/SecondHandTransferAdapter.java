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
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.UsedGoods;
import com.ctrl.android.property.jason.ui.customctrol.RoundImageView;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *二手交易详细页 fragment adapter
 * Created by jason on 2015/10/14
 */
public class SecondHandTransferAdapter extends BaseAdapter {
    private List<UsedGoods> listMap;
    private Activity mActivity;
    private List<GoodPic>mGoodPicList = new ArrayList<>();
    private UsedGoods usedGoods;



    public SecondHandTransferAdapter(Activity mActivity){
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

       final  ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_second_hand_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        usedGoods = listMap.get(position);
        mGoodPicList=usedGoods.getUsedGoodPicSubList();
        holder.tv_second_hand_item_name.setText(usedGoods.getProprietorName());
        holder.tv_second_hand_item_location.setText(usedGoods.getCommunityName() + " " + usedGoods.getBuilding()+"-"+usedGoods.getUnit()+"-"+usedGoods.getRoom());
        DecimalFormat df = new DecimalFormat("#.00");
        holder.tv_second_hand_item_price.setText("￥" + df.format(usedGoods.getSellingPrice()));
        holder.tv_second_hand_item_time.setText(TimeUtil.dateTime(usedGoods.getCreateTime()));
        holder.tv_second_hand_item_title.setText(usedGoods.getTitle());

        if (mGoodPicList== null || mGoodPicList.size()< 1){
            holder.iv01_second_hand.setVisibility(View.GONE);
            holder.iv02_second_hand.setVisibility(View.GONE);
            holder.iv03_second_hand.setVisibility(View.GONE);
        }
           else if (mGoodPicList.size() == 1) {
                holder.iv01_second_hand.setVisibility(View.VISIBLE);
                Arad.imageLoader.load(mGoodPicList.get(0).getOriginalImg() == null || mGoodPicList.get(0).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv01_second_hand);
              //  Log.i("Tag","zipImgUrl01"+mGoodPicList.get(0).getZipImgUrl());
                holder.iv02_second_hand.setVisibility(View.INVISIBLE);
                holder.iv03_second_hand.setVisibility(View.INVISIBLE);
            } else if (mGoodPicList.size() == 2) {
             holder.iv01_second_hand.setVisibility(View.VISIBLE);
                holder.iv02_second_hand.setVisibility(View.VISIBLE);

           Arad.imageLoader.load(mGoodPicList.get(0).getOriginalImg() == null || mGoodPicList.get(0).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv01_second_hand);
            Arad.imageLoader.load(mGoodPicList.get(1).getOriginalImg() == null || mGoodPicList.get(1).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv02_second_hand);
           /* Log.i("Tag", "zipImgUrl01" + mGoodPicList.get(0).getZipImgUrl());
            Log.i("Tag", "zipImgUrl02" + mGoodPicList.get(1).getZipImgUrl());*/
            /*holder.iv03_second_hand.setVisibility(View.INVISIBLE);*/
            } else if (mGoodPicList.size() == 3) {
                holder.iv01_second_hand.setVisibility(View.VISIBLE);
                holder.iv02_second_hand.setVisibility(View.VISIBLE);
                holder.iv03_second_hand.setVisibility(View.VISIBLE);

           Arad.imageLoader.load(mGoodPicList.get(0).getOriginalImg() == null || mGoodPicList.get(0).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv01_second_hand);
                Arad.imageLoader.load(mGoodPicList.get(1).getOriginalImg() == null || mGoodPicList.get(1).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv02_second_hand);
                Arad.imageLoader.load(mGoodPicList.get(2).getOriginalImg() == null || mGoodPicList.get(2).getOriginalImg().equals("") ? "aa" : mGoodPicList.get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(holder.iv03_second_hand);
           /*   Log.i("Tag", "zipImgUrl01" + mGoodPicList.get(0).getZipImgUrl());
            Log.i("Tag", "zipImgUrl02" + mGoodPicList.get(1).getZipImgUrl());
            Log.i("Tag", "zipImgUrl03" + mGoodPicList.get(2).getZipImgUrl());*/
//
// else {
//                holder.ll_item_image.setVisibility(View.GONE);
//
//            }
        }
        Arad.imageLoader.load(usedGoods.getImgUrl()==null||usedGoods.getImgUrl().equals("")
        ?"aa":usedGoods.getImgUrl()).placeholder(R.drawable.touxiang2x).into(holder.iv_photo);
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
      @InjectView(R.id.iv01_second_hand)
      ImageView iv01_second_hand;
      @InjectView(R.id.iv02_second_hand)
      ImageView iv02_second_hand;
      @InjectView(R.id.iv03_second_hand)
      ImageView iv03_second_hand;
      @InjectView(R.id.tv_second_hand_item_title)
      TextView tv_second_hand_item_title;
      @InjectView(R.id.iv_photo)
        RoundImageView iv_photo;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}

package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class HouseListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<House> list;

    public HouseListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<House> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final House house = list.get(position);

//        holder.house_owner.setText(S.getStr(map.get("owner")));
        holder.house_name_and_time.setText(S.getStr(house.getCommunityName()) + "    " + (house.getBuilding() + "-" + house.getUnit() + "-" + house.getRoom()));
        //是否需要物业缴费（0：不需 1：需要）
        if(house.getIsPayment() == 0){
            holder.house_status_flg.setText(R.string.have_paied);
            holder.house_status_flg.setTextColor(mActivity.getResources().getColor(R.color.text_gray));
            holder.house_status_flg.setBackgroundResource(R.drawable.gray_border_shap2);
        } else if(house.getIsPayment() == 1){
            holder.house_status_flg.setText(R.string.havnt_paied);
            holder.house_status_flg.setTextColor(mActivity.getResources().getColor(R.color.dark_green));
            holder.house_status_flg.setBackgroundResource(R.drawable.green_border_shap);
        }

        return convertView;
    }


    static class ViewHolder {
//        @InjectView(R.id.house_owner)//房屋所有者
//        TextView house_owner;
        @InjectView(R.id.house_name_and_time)//小区名称和时间
        TextView house_name_and_time;
        @InjectView(R.id.house_status_flg)//缴费状态
        TextView house_status_flg;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

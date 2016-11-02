package com.ctrl.android.property.staff.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.entity.ForumCategory;
import com.ctrl.android.property.staff.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛板块 adapter
 * Created by Eric on 2015/10/13.
 */
public class ForumCategaryAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<ForumCategory> list;

    public ForumCategaryAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<ForumCategory> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.forum_area_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final ForumCategory category = list.get(position);

        Arad.imageLoader.load(S.getStr(category.getImgUrl()))
                .placeholder(R.drawable.default_image)
                .into(holder.area_img);

        holder.area_name.setText(S.getStr(category.getName()));
        holder.area_num.setText("" + category.getCount());

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.area_img)//板块图片
        ImageView area_img;
        @InjectView(R.id.area_name)//板块名称
        TextView area_name;
        @InjectView(R.id.area_num)//贴子数
        TextView area_num;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

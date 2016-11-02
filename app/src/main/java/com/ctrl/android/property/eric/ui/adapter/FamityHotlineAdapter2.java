package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.ui.hotline.FamilyHotlineActivity;
import com.ctrl.android.property.eric.util.S;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通讯录 adapter
 * Created by Eric on 2015/10/22
 */
public class FamityHotlineAdapter2 extends BaseAdapter{

    private FamilyHotlineActivity mActivity;
    private List<Map<String,String>> listMap;

    public FamityHotlineAdapter2(FamilyHotlineActivity mActivity){
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.family_hotline_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Map<String,String> map = listMap.get(position);

        Arad.imageLoader
                .load(map.get("img"))
                .placeholder(R.drawable.hotline_default_head_img)
                .into(holder.hotline2_head_img);

        holder.hotline2_name.setText(S.getStr(map.get("name")));
        holder.hotline2_position.setText(S.getStr(map.get("position")));
        holder.hotline2_duty.setText(S.getStr(map.get("duty")));
        holder.hotline2_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.dial(mActivity, map.get("tel"));
            }
        });

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.hotline2_head_img)//头像
        ImageView hotline2_head_img;
        @InjectView(R.id.hotline2_name)//姓名
        TextView hotline2_name;
        @InjectView(R.id.hotline2_position)//职位
        TextView hotline2_position;
        @InjectView(R.id.hotline2_duty)//职责
        TextView hotline2_duty;
        @InjectView(R.id.hotline2_tel)//电话
        ImageView hotline2_tel;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

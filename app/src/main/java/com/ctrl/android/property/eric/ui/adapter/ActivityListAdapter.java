package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Act;
import com.ctrl.android.property.eric.ui.act.ActDetailActivity;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 活动列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class ActivityListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Act> list;

    public ActivityListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Act> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.activity_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Act act = list.get(position);

        Arad.imageLoader.load(S.getStr(act.getZipImg()).equals("") ? "aa" : act.getZipImg())
                .placeholder(R.drawable.default_image)
                .into(holder.activity_img);

        //参与状态(0:未参与,1:参与)
        if(!S.isNull(act.getParticipateStatus())){
            if(act.getParticipateStatus().equals("1")){
                holder.activity_status.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.activity_take_part_in_already_icon));
            } else if((act.getParticipateStatus().equals("0"))){
                holder.activity_status.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.activitytake_part_in_not_icon));
            }
        } else {

        }


        holder.activity_name.setText(S.getStr(act.getTitle()));
        holder.activity_time.setText(D.getDateStrFromStamp("yyyy.MM.dd",act.getStartTime()) + " - " + D.getDateStrFromStamp("MM.dd",act.getEndTime()));

        holder.activity_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(mActivity,S.getStr(map.get("name")));
                Intent intent = new Intent(mActivity, ActDetailActivity.class);
                intent.putExtra("actionId",act.getId());
                mActivity.startActivity(intent);
                AnimUtil.intentSlidIn(mActivity);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.activity_img)//活动图片
        ImageView activity_img;
        @InjectView(R.id.activity_status)//活动状态
        ImageView activity_status;
        @InjectView(R.id.activity_name)//活动名称
        TextView activity_name;
        @InjectView(R.id.activity_time)//活动时间
        TextView activity_time;
        @InjectView(R.id.activity_detail_btn)//活动详细按钮
        TextView activity_detail_btn;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

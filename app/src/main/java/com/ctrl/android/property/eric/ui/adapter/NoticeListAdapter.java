package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Notice;
import com.ctrl.android.property.jason.util.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class NoticeListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Notice> list;
    private Notice notice;

    public NoticeListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Notice> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.notice_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

       // final Map<String,String> map = list.get(position);
        notice = list.get(position);
        holder.notice_title.setText(notice.getNoticeTitle());

        //公告级别（0：重要、1：一般）
        if(notice.getLevel().equals("0")){
            holder.notice_title.setTextColor(mActivity.getResources().getColor(R.color.text_red));
        }

        holder.notice_time.setText(TimeUtil.date(Long.parseLong(notice.getCreateTime())));
        if(notice.getStatus().equals("1")){
            holder.notice_status_flg.setText(R.string.have_sign);
            //holder.notice_status_flg.setBackgroundResource(R.drawable.notice_status_sign_y);
            holder.notice_status_flg.setBackgroundResource(R.drawable.gray_bg_shap);
        } else if(notice.getStatus().equals("0")){
            holder.notice_status_flg.setText(R.string.havnt_sign);
            //holder.notice_status_flg.setBackgroundResource(R.drawable.notice_status_sign_n);
            holder.notice_status_flg.setBackgroundResource(R.drawable.green_bg_shap);
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.notice_title)//公告标题
        TextView notice_title;
        @InjectView(R.id.notice_time)//公告发布时间
        TextView notice_time;
        @InjectView(R.id.notice_status_flg)//公告签收情况
        TextView notice_status_flg;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Comment;
import com.ctrl.android.property.eric.util.D;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 商品评论列表 adapter
 * Created by Eric on 2015/7/2.
 */
public class MallCommentListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Comment> list;

    public MallCommentListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Comment> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.mall_shop_comment_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Comment comment = list.get(position);

        holder.shop_comment_ratingBar.setRating((float)(comment.getLevel()/2));
        holder.shop_comment_send_time.setText(comment.getDeliveryDuration() + "分钟");
        holder.shop_comment_tel.setText(comment.getMobile());
        holder.shop_comment_content.setText(comment.getContent());
        holder.shop_comment_date.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm", comment.getCreateTime()));


        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.shop_comment_ratingBar)//卖家信用等级
        RatingBar shop_comment_ratingBar;
        @InjectView(R.id.shop_comment_send_time)//商家送货时间
        TextView shop_comment_send_time;
        @InjectView(R.id.shop_comment_tel)//电话
        TextView shop_comment_tel;
        @InjectView(R.id.shop_comment_content)//评论内容
        TextView shop_comment_content;
        @InjectView(R.id.shop_comment_date)//评论时间
        TextView shop_comment_date;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

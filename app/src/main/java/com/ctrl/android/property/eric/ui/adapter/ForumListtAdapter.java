package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.ForumNote;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛帖子列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class ForumListtAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<ForumNote> list;

    public ForumListtAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<ForumNote> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.forum_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final ForumNote note = list.get(position);

        holder.forum_title.setText(S.getStr(note.getTitle()));
        holder.forum_content.setText(S.getStr(note.getContent()));
        holder.forum_member.setText(S.getStr(note.getMemberName()));
        holder.forum_time.setText(D.getDateStrFromStamp(Constant.YYYY_MM_DD, S.getStr(note.getCreateTime())));
        holder.forum_favor_num.setText(S.getStr(String.valueOf(note.getPraiseNum())));
        holder.forum_look_num.setText(S.getStr(String.valueOf(note.getReadNum())));
        holder.forum_comment_num.setText(S.getStr(String.valueOf(note.getReplyNum())));

        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.forum_title)//帖子名称
        TextView forum_title;
        @InjectView(R.id.forum_content)//帖子内容
        TextView forum_content;
        @InjectView(R.id.forum_member)//帖子发布人
        TextView forum_member;
        @InjectView(R.id.forum_time)//帖子发布时间
        TextView forum_time;
        @InjectView(R.id.forum_favor_num)//帖子赞 数量
        TextView forum_favor_num;
        @InjectView(R.id.forum_look_num)//帖子浏览数量
        TextView forum_look_num;
        @InjectView(R.id.forum_comment_num)//帖子评论数量
        TextView forum_comment_num;



        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

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
import com.ctrl.android.property.eric.entity.Order;
import com.ctrl.android.property.eric.ui.mall.OrderCommentActivity;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class OrderListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Order> list;

    public OrderListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Order> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.order_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Order order = list.get(position);

        Arad.imageLoader
                .load(S.isNull(order.getLogoUrl()) ? "aa" : order.getLogoUrl())
                .placeholder(R.drawable.default_image)
                .into(holder.shop_pic);
        holder.shop_name.setText(S.getStr(order.getCompanyName()));
        holder.order_amount.setText("总价: ￥" + N.toPriceFormate(order.getTotalCost()));
        holder.order_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm:ss", order.getCreateTime()));
        holder.order_status.setText(S.getOrderStatus(order.getOrderStatus()));

        List<Integer> comment_flg = new ArrayList<>();
        comment_flg.add(6);
        comment_flg.add(7);
        comment_flg.add(8);
        comment_flg.add(9);
        comment_flg.add(10);
        comment_flg.add(11);
        comment_flg.add(12);
        comment_flg.add(13);
        if(comment_flg.contains(order.getOrderStatus()) && order.getEvaluationState() == 0){
            holder.order_comment_btn.setEnabled(true);
            holder.order_comment_btn.setClickable(true);
            holder.order_comment_btn.setBackgroundResource(R.drawable.green_bg_shap);
            holder.order_comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, OrderCommentActivity.class);
                    //intent.putExtra("title",TITLE);
                    //intent.putExtra("forumPostId",listNote.get(position-1).getForumPostId());
                    intent.putExtra("companyId",order.getCompanyId());
                    intent.putExtra("orderId",order.getId());
                    mActivity.startActivity(intent);
                    AnimUtil.intentSlidIn(mActivity);
                }
            });
        } else {
            holder.order_comment_btn.setEnabled(false);
            holder.order_comment_btn.setClickable(false);
            holder.order_comment_btn.setBackgroundResource(R.drawable.gray_bg_shap);
        }

        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.shop_pic)//商家图片
        ImageView shop_pic;

        @InjectView(R.id.shop_name)//商家名称
        TextView shop_name;
        @InjectView(R.id.order_amount)//总价
        TextView order_amount;
        @InjectView(R.id.order_time)//订单时间
        TextView order_time;
        @InjectView(R.id.order_status)//交易状态
        TextView order_status;
        @InjectView(R.id.order_comment_btn)//评价按钮
        TextView order_comment_btn;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }




}

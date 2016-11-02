package com.ctrl.android.property.staff.ui.express;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.entity.Express;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 快递列表adapter
 * Created by Administrator on 2015/10/23.
 */
public class ExpressAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Express> list;

    public ExpressAdapter(Activity activity) {
        mActivity = activity;
    }

    public void setList(List<Express> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity)
                    .inflate(R.layout.express_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Express express = list.get(position);

        holder.express_name.setText(S.getStr(express.getBuilding()+"-"+express.getUnit()+"-"+express.getRoom()+"   "+express.getRecipientName()));
        holder.express_time.setText(S.getStr(TimeUtil.date(Long.parseLong(express.getCreateTime()))));

        //领取状态（0：待领取、1：已领取）
        if(express.getStatus() == 0){
            holder.express_state.setText("未领取");
            holder.express_state.setBackgroundResource(R.drawable.orange_bg_shap);
        } else {
            holder.express_state.setText("已领取");
            holder.express_state.setBackgroundResource(R.drawable.gray_bg_shap);
        }

        //holder

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.express_name)//姓名
        TextView express_name;
        @InjectView(R.id.express_time)//快递时间
        TextView express_time;
        @InjectView(R.id.express_state)//快递状态
        TextView express_state;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

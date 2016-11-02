package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.Hotline;
import com.ctrl.android.property.eric.ui.hotline.FamilyHotlineActivity;
import com.ctrl.android.property.eric.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通讯录 adapter
 * Created by Eric on 2015/10/20
 */
public class FamilyHotlineAdapter extends BaseAdapter{

    private FamilyHotlineActivity mActivity;
    private FamityHotlineAdapter2 adapter2;
    private List<Hotline> listHotline;

    public FamilyHotlineAdapter(FamilyHotlineActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Hotline> listHotline) {
        this.listHotline = listHotline;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listHotline == null ? 0 : listHotline.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.family_hotline_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Hotline hotline = listHotline.get(position);

        holder.hotline_categary_name.setText(S.getStr(hotline.getCategory()) + "(" + hotline.getListMap().size() + ")");
        holder.hotline_categary_arrow.setChecked(false);

        adapter2 = new FamityHotlineAdapter2(mActivity);
        adapter2.setList(hotline.getListMap());
        holder.listView_hotline.setAdapter(adapter2);
        holder.listView_hotline.setVisibility(View.GONE);

        holder.hotline_categary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(mActivity, hotline.getCategory());
                if (holder.hotline_categary_arrow.isChecked()) {
                    //MessageUtils.showShortToast(mActivity, "页面不要拉伸");
                    holder.hotline_categary_arrow.setChecked(false);
                    holder.listView_hotline.setVisibility(View.GONE);
                } else {
                    //MessageUtils.showShortToast(mActivity, "页面要拉伸");
                    holder.hotline_categary_arrow.setChecked(true);
                    holder.listView_hotline.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.hotline_categary_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(mActivity, hotline.getCategory());
                if (holder.hotline_categary_arrow.isChecked()) {
                    //MessageUtils.showShortToast(mActivity, "页面要拉伸");
                    holder.hotline_categary_arrow.setChecked(true);
                    holder.listView_hotline.setVisibility(View.VISIBLE);
                } else {
                    //MessageUtils.showShortToast(mActivity, "页面不要拉伸");
                    holder.hotline_categary_arrow.setChecked(false);
                    holder.listView_hotline.setVisibility(View.GONE);

                }
            }
        });



        return convertView;
    }



    static class ViewHolder {

        @InjectView(R.id.hotline_categary_btn)//分类按钮
        RelativeLayout hotline_categary_btn;
        @InjectView(R.id.hotline_categary_name)//分类名称
        TextView hotline_categary_name;
        @InjectView(R.id.listView_hotline)//分类列表
        ListView listView_hotline;
        @InjectView(R.id.hotline_categary_arrow)//右侧箭头
        CheckBox hotline_categary_arrow;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

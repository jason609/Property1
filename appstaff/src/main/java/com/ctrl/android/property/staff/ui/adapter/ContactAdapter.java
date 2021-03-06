package com.ctrl.android.property.staff.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.entity.ContactGroup;
import com.ctrl.android.property.staff.util.S;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通讯录 adapter
 * Created by Eric on 2015/10/20
 */
public class ContactAdapter extends BaseAdapter{

    private Activity mActivity;
    private ContractAdapter2 adapter2;
    private List<ContactGroup> list;

    //private List<Device> listDevice;

    public ContactAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<ContactGroup> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.contact_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final ContactGroup group = list.get(position);

        holder.hotline_categary_name.setText(S.getStr(group.getGroupName()) + "(" + group.getStaffCount() + ")");
        holder.hotline_categary_arrow.setChecked(false);

        adapter2 = new ContractAdapter2(mActivity);

        //0:展开  1:不展开
        if(group.getFlg() == 0){
            adapter2.setList(group.getListContactor());
            holder.listView_2.setAdapter(adapter2);
            holder.hotline_categary_arrow.setChecked(true);
            holder.listView_2.setVisibility(View.VISIBLE);
        } else {
            holder.hotline_categary_arrow.setChecked(false);
            holder.listView_2.setVisibility(View.GONE);
        }

        holder.listView_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  MessageUtils.showShortToast(mActivity, group.getListContactor().get(position).getName());
                AppHolder.getInstance().setContactor(group.getListContactor().get(position));
                Intent intent=new Intent();
                intent.putExtra("name",group.getListContactor().get(position).getName());
                intent.putExtra("id",group.getListContactor().get(position).getId());
                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();
                //Intent intent = new Intent(mActivity, DeviceHistoryActivity.class);
                //intent.putExtra("id",deviceCate.getListDevice().get(position).getId());
                //mActivity.startActivity(intent);
                //AnimUtil.intentSlidIn(mActivity);
            }
        });

//        adapter2 = new DeviceAdapter2(mActivity);
//        //adapter2.setList(hotline.getListMap());
//        holder.listView_hotline.setAdapter(adapter2);
//        holder.listView_hotline.setVisibility(View.GONE);

//        holder.hotline_categary_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.hotline_categary_arrow.isChecked()) {
//                    holder.hotline_categary_arrow.setChecked(false);
//                    holder.listView_2.setVisibility(View.GONE);
//                } else {
//                    holder.hotline_categary_arrow.setChecked(true);
//                    holder.listView_2.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//        holder.hotline_categary_arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.hotline_categary_arrow.isChecked()) {
//                    //MessageUtils.showShortToast(mActivity, "页面不要拉伸");
//                    setDeviceList(holder,deviceCate);
//                    //holder.hotline_categary_arrow.setChecked(false);
//                    holder.listView_2.setVisibility(View.VISIBLE);
//                } else {
//                    //MessageUtils.showShortToast(mActivity, "页面要拉伸");
//
//                    //holder.hotline_categary_arrow.setChecked(true);
//                    holder.listView_2.setVisibility(View.GONE);
//
//                }
//            }
//        });



        return convertView;
    }


    static class ViewHolder {

        @InjectView(R.id.hotline_categary_btn)//分类按钮
        RelativeLayout hotline_categary_btn;
        @InjectView(R.id.hotline_categary_name)//分类名称
        TextView hotline_categary_name;
        @InjectView(R.id.listView_2)//分类列表
        ListView listView_2;
        @InjectView(R.id.hotline_categary_arrow)//右侧箭头
        CheckBox hotline_categary_arrow;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

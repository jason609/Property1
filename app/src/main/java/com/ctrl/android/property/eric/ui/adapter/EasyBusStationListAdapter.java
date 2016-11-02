package com.ctrl.android.property.eric.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.entity.BusStation;
import com.ctrl.android.property.eric.ui.easy.EasyBusActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公交出行中 公交站列表的 adapter
 * Created by Eric on 2015/10/20
 */
public class EasyBusStationListAdapter extends BaseAdapter{

    private EasyBusActivity mActivity;
    private List<BusStation> listBusStation;

    public EasyBusStationListAdapter(EasyBusActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<BusStation> listBusStation) {
        this.listBusStation = listBusStation;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listBusStation == null ? 0 : listBusStation.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.easy_bus_station_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final BusStation station = listBusStation.get(position);

        holder.station_name.setText((position + 1) + "." + station.getStationName());
        holder.station_distance.setText(station.getStationDistance() + "m");
        holder.station_address.setText(station.getStationAddress());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtils.showShortToast(mActivity, station.getStationName());
            }
        });

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.item)
        LinearLayout item;

        @InjectView(R.id.station_name)//车站名称
        TextView station_name;
        @InjectView(R.id.station_distance)//车站距离
        TextView station_distance;
        @InjectView(R.id.station_address)//车站线路
        TextView station_address;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

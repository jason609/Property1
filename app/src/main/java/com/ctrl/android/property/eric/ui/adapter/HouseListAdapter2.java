package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.ui.house.HouseListActivity2;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.http.AopUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋列表 adapter
 * Created by Eric on 2015/10/13.
 */
public class HouseListAdapter2 extends BaseAdapter{

    private Activity mActivity;
    private List<House> list;

    public HouseListAdapter2(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<House> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.house_list_item2,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final House house = list.get(position);

        holder.community_name.setText(S.getStr(house.getCommunityName()));
        holder.buildind_unit_room.setText(S.getStr(house.getBuilding()) + " 楼 " + S.getStr(house.getUnit()) + " 单元 " + S.getStr(house.getRoom()) + " 室");

        //是否默认住址（0：非默认、1：默认）
        if(house.getIsDefault() == 1){
            holder.default_checkbox.setChecked(true);
        } else {
            holder.default_checkbox.setChecked(false);
        }

        holder.default_checkbox.setClickable(false);
        holder.remove_house_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHouseBind(house, position);
            }
        });
        holder.remove_house_bind2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHouseBind(house, position);
            }
        });
//        holder.default_checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.default_checkbox.isChecked()) {
//                    setDefaultHouse(house,position);
//                } else {
//                    //
//                }
//            }
//        });

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.community_name)//小区名
        TextView community_name;
        @InjectView(R.id.buildind_unit_room)//楼号 单元号 房号
        TextView buildind_unit_room;
        @InjectView(R.id.default_checkbox)//默认选择
        CheckBox default_checkbox;
        @InjectView(R.id.remove_house_bind)//解除绑定
        ImageView remove_house_bind;
        @InjectView(R.id.remove_house_bind2)//解除绑定
        TextView remove_house_bind2;



        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 设为默认房屋
     * */
    private void setDefaultHouse(final House house,final int position){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberBind.setDefault");//方法名称

        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
        String housesBindId = house.getId();

        map.put("memberId",memberId);
        map.put("housesBindId",housesBindId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        Log.d("demo", "地址: " + AsyncHttpClient.getUrlWithQueryString(true, Constant.RAW_URL, mapToRP(map)));
        Arad.http.post(Constant.RAW_URL, mapToRP(map), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                ((HouseListActivity2)mActivity).showProgress(false);
                Log.d("demo", "responseBody: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        String code = node.findValue("code").asText();
                        Log.d("demo", "code: " + code);
                        for(int i = 0 ; i < list.size() ; i ++){
                            if(i == position){
                                list.get(i).setIsDefault(0);
                            } else {
                                list.get(i).setIsDefault(1);
                            }
                        }
                        notifyDataSetChanged();
                        //Log.d("demo","getCartIdStr: " + getCartIdStr(listCartPro));
                    }
                } catch (AradException e) {
                    ((HouseListActivity2)mActivity).showProgress(false);
                    MessageUtils.showShortToast(mActivity, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                ((HouseListActivity2)mActivity).showProgress(false);
                Log.d("demo", "XXXXXXX: 003");
                if (statusCode == 0){
                    MessageUtils.showLongToast(mActivity.getApplicationContext(), "网络异常");
                }
            }
        });


    }

    /**
     * 解除房屋绑定
     * */
    private void removeHouseBind(final House house,final int position){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.memberBind.removeBind");//方法名称

        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
        String housesBindId = house.getId();

        map.put("memberId",memberId);
        map.put("housesBindId",housesBindId);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        Log.d("demo", "地址: " + AsyncHttpClient.getUrlWithQueryString(true, Constant.RAW_URL, mapToRP(map)));
        Arad.http.post(Constant.RAW_URL, mapToRP(map), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                ((HouseListActivity2)mActivity).showProgress(false);
                Log.d("demo", "responseBody: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        String code = node.findValue("code").asText();
                        Log.d("demo", "code: " + code);
                        MessageUtils.showShortToast(mActivity,"删除成功");
                        list.remove(position);
                        //((HouseListActivity2) mActivity).requestHouseList();
                        notifyDataSetChanged();

                        if(list.size() > 0){
                            for(int i = 0 ; i < list.size() ; i ++){
                                if(list.get(i).getIsDefault() == 1){
                                    AppHolder.getInstance().setHouse(list.get(i));
                                    AppHolder.getInstance().getProprietor().setProprietorId(list.get(i).getProprietorId());
                                }
                            }
                        } else {
                            AppHolder.getInstance().setHouse(new House());
                            AppHolder.getInstance().getProprietor().setProprietorId("");
                        }

                        //Log.d("demo","getCartIdStr: " + getCartIdStr(listCartPro));
                    }
                } catch (AradException e) {
                    ((HouseListActivity2)mActivity).showProgress(false);
                    MessageUtils.showShortToast(mActivity, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                ((HouseListActivity2)mActivity).showProgress(false);
                ((HouseListActivity2)mActivity).requestHouseList();
                if (statusCode == 0){
                    MessageUtils.showLongToast(mActivity.getApplicationContext(), "网络异常");
                }
            }
        });


    }

    public RequestParams mapToRP(Map<String,String> map){
        RequestParams params = new RequestParams();
        Set entrySet = map.entrySet(); // key-value的set集合
        for(String key : map.keySet()){
            params.put(key,map.get(key));
        }
        return params;
    }



}

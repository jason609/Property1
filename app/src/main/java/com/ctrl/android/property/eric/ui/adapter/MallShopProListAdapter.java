package com.ctrl.android.property.eric.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.Pro;
import com.ctrl.android.property.eric.ui.mall.MallShopMainActivity;
import com.ctrl.android.property.eric.util.N;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
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
 * 商品列表 adapter
 * Created by Eric on 2015/7/2.
 */
public class MallShopProListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Pro> list;

    public MallShopProListAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void setList(List<Pro> list) {
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.mall_shop_pro_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Pro pro = list.get(position);

        Arad.imageLoader
                .load(S.isNull(pro.getZipImg()) ? "aa" : pro.getZipImg())
                .placeholder(R.drawable.pro_pic_01)
                .into(holder.pro_pic);
        holder.pro_name.setText(S.getStr(pro.getName()));
        holder.pro_month_sales.setText(StrConstant.PRO_MONTH_SALES + pro.getSalesVolume());
        holder.pro_price.setText(N.toPriceFormate(pro.getSellingPrice()));

        holder.pro_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(mActivity,"购物车");
                addToCart(pro);
            }
        });
        return convertView;
    }


    /**
     * 加入购物车
     * */
    private void addToCart(final Pro pro){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.addShoppingCart");//方法名称


        String productId = pro.getId();//
        String companyId = pro.getCompanyId();//
        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();//
        String productNum = "1";//

        map.put("productId", productId);
        map.put("companyId",companyId);
        map.put("memberId", memberId);
        map.put("productNum", productNum);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign", sign);

        Log.d("demo", "地址: " + AsyncHttpClient.getUrlWithQueryString(true, Constant.RAW_URL, mapToRP(map)));
        Arad.http.post(Constant.RAW_URL, mapToRP(map), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                ((MallShopMainActivity)mActivity).showProgress(false);
                Log.d("demo", "responseBody: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        String code = node.findValue("code").asText();
                        MessageUtils.showShortToast(mActivity,"成功加入购物车");
                        Log.d("demo", "code: " + code);
                        //Log.d("demo", "adapter中结果集(修改数量): " + node);
                        //listCartPro.get(position).setProductNum(num);
                        //notifyDataSetChanged();
                        //mActivity.numChanged();
                        //Log.d("demo","getCartIdStr: " + getCartIdStr(listCartPro));
                    }
                } catch (AradException e) {
                    ((MallShopMainActivity)mActivity).showProgress(false);
                    MessageUtils.showShortToast(mActivity,e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                ((MallShopMainActivity)mActivity).showProgress(false);
                Log.d("demo", "XXXXXXX: 003");
                if (statusCode == 0) {
                    MessageUtils.showLongToast(mActivity.getApplicationContext(), "网络异常");
                }

            }
        });


    }

    static class ViewHolder {

        @InjectView(R.id.pro_pic)//商品图片
        ImageView pro_pic;
        @InjectView(R.id.pro_name)//商品名称
        TextView pro_name;
        @InjectView(R.id.pro_month_sales)//月销量
        TextView pro_month_sales;
        @InjectView(R.id.pro_price)//价格
        TextView pro_price;
        @InjectView(R.id.pro_cart_btn)//购物车按钮
        ImageView pro_cart_btn;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
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

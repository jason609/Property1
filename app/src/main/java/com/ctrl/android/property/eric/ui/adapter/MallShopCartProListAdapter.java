package com.ctrl.android.property.eric.ui.adapter;

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
import com.ctrl.android.property.eric.entity.CartPro;
import com.ctrl.android.property.eric.ui.mall.MallShopCartActivity;
import com.ctrl.android.property.eric.util.N;
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
 * 购物车 商品列表的 adapter
 * Created by Eric on 2015/7/2.
 */
public class MallShopCartProListAdapter extends BaseAdapter{

    private MallShopCartActivity mActivity;
    private List<CartPro> listCartPro;

    public MallShopCartProListAdapter(MallShopCartActivity mActivity){
        this.mActivity = mActivity;
    }

    public void setListCartPro(List<CartPro> listCartPro) {
        this.listCartPro = listCartPro;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listCartPro == null ? 0 : listCartPro.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.mall_cart_pro_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final CartPro cartPro = listCartPro.get(position);

        Arad.imageLoader.load(S.isNull(cartPro.getOriginalImg()) ? "aa" : cartPro.getOriginalImg() )
                .placeholder(R.drawable.default_image)
                .into(holder.pro_image);
        holder.pro_name.setText(S.getStr(cartPro.getName()));
        holder.pro_price.setText("￥" + N.toPriceFormate(cartPro.getSellingPrice()));
        holder.pro_num.setText("" + cartPro.getProductNum());

        //判断单选按钮是否是选中状态
        if(cartPro.isCheck()){
            holder.pro_checkbox.setChecked(true);
        } else {
            holder.pro_checkbox.setChecked(false);
        }



        holder.mNumAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNum(cartPro,position);
            }
        });

        holder.mNumMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusNum(cartPro, position);
            }
        });

        holder.pro_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.pro_checkbox.isChecked()){
                    listCartPro.get(position).setCheck(true);
                } else {
                    listCartPro.get(position).setCheck(false);
                }
                notifyDataSetChanged();
                mActivity.checkChanged();
            }
        });

        holder.cart_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCartPro(cartPro,position);
            }
        });


//        holder.pro_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    //holder.mChildBox.setChecked(false);
//                    cartPro.setCheck(true);
//                } else {
//                    //holder.mChildBox.setChecked(true);
//                    cartPro.setCheck(false);
//                }
//                notifyDataSetChanged();
//                mActivity.checkChanged();
//            }
//        });
//
//        holder.mDelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delCartPro(cartPro,position);
//            }
//        });

//
//        holder.childNum.setText("×" + cartPro.getGoodsNum());

        return convertView;
    }

    /**
     * 增加数量
     * */
    private void addNum(CartPro cartPro,int position){
        int num = cartPro.getProductNum();
        mActivity.showProgress(true);
        changeNum(cartPro,position,(num + 1));
    }

    /**
     * 减少数量
     * */
    private void minusNum(CartPro cartPro,int position){
        int num = cartPro.getProductNum();
        if(num > 1){
            mActivity.showProgress(true);
            changeNum(cartPro,position,(num - 1));
        } else {
            //showDialog(cartPro, position);
        }

    }

    /**
     * 改变商品数量
     * */
    private void changeNum(final CartPro cartPro,final int position,final int num){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.updateShoppingCartGoodsNum");//方法名称

        String shoppingCartId = cartPro.getShoppingCartId();//
        String productId = cartPro.getProductId();//
        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();//
        String productNum = String.valueOf(num);//

        map.put("shoppingCartId",shoppingCartId);
        map.put("productId",productId);
        map.put("memberId",memberId);
        map.put("productNum",productNum);

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        //Log.d("demo","地址: " + AsyncHttpClient.getUrlWithQueryString(true, Constant.IMAGE_URL, mapToRP(map)));
        Arad.http.post(Constant.RAW_URL, mapToRP(map), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                mActivity.showProgress(false);
                Log.d("demo", "responseBody: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        String code = node.findValue("code").asText();
                        //Log.d("demo","code: " + code);
                        //Log.d("demo","adapter中结果集(修改数量): " + node);
                        listCartPro.get(position).setProductNum(num);
                        notifyDataSetChanged();
                        mActivity.numChanged();
                        //Log.d("demo","getCartIdStr: " + getCartIdStr(listCartPro));
                    }
                } catch (AradException e) {
                    mActivity.showProgress(false);
                    MessageUtils.showShortToast(mActivity,e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                mActivity.showProgress(false);
                Log.d("demo","XXXXXXX: 003");
                if (statusCode == 0){
                    MessageUtils.showLongToast(mActivity.getApplicationContext(), "网络异常");
                }
            }
        });


    }




    /**
     * 删除一件 商品
     * */
    private void delCartPro(CartPro cartPro,final int position){
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.APPKEY,Constant.APPKEY_VALUE);
        map.put(Constant.SECRET,Constant.SECRET_VALUE);
        map.put(Constant.VERSION,Constant.VERSION_VALUE);
        map.put(Constant.FORMAT,Constant.FORMAT_VALUE);
        map.put(Constant.TYPE, Constant.TYPE_VALUE);
        map.put(Constant.METHOD,"pm.ppt.shoppingCart.delShoppingCart");//方法名称

        map.put("shoppingCartIdStr",cartPro.getShoppingCartId());

        String sign = AopUtils.sign(map, Constant.SECRET_VALUE);
        map.put("sign",sign);

        Log.d("demo","地址: " + AsyncHttpClient.getUrlWithQueryString(true, Constant.RAW_URL, mapToRP(map)));
        Arad.http.post(Constant.RAW_URL, mapToRP(map), new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                mActivity.showProgress(false);
                Log.d("demo","responseBody: " + responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        Log.d("demo","adapter中结果集: " + node);
                        listCartPro.remove(position);
                        notifyDataSetChanged();
                        mActivity.numChanged();
                    }
                } catch (Exception e) {
                    mActivity.showProgress(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                mActivity.showProgress(false);
                if (statusCode == 0)
                    MessageUtils.showLongToast(mActivity.getApplicationContext(), "网络异常");
            }
        });
    }



//
//    private void showDialog(final CartPro cartPro,final int position){
//        final TextView text = new TextView(mActivity);
//        text.setTextSize(25f);
//        text.setPadding(20, 0, 20, 0);
//        text.setBackground(null);
//        text.setGravity(Gravity.CENTER);
//        text.setText("是否删除商品?");
//        text.setHintTextColor(Color.parseColor("#cbdfe6"));
//        //text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setTitle("提示");
//        //builder.setIcon.drawable.ic_launcher);
//        builder.setView(text);
//        //确认 输入支付密码后
//        builder.setPositiveButton("确定" ,  new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                delCartPro(cartPro,position);
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.show();
//    }

    static class ViewHolder {
        @InjectView(R.id.pro_checkbox)//选择按钮
        CheckBox pro_checkbox;
        @InjectView(R.id.pro_image)//商品图片
        ImageView pro_image;
        @InjectView(R.id.pro_name)//商品名称
        TextView pro_name;
        @InjectView(R.id.pro_price)//商品价格
        TextView pro_price;
        @InjectView(R.id.num_minus_btn)//减 按钮
        ImageView mNumMinusBtn;
        @InjectView(R.id.pro_num)//商品数量
        TextView pro_num;
        @InjectView(R.id.num_add_btn)//加 按钮
        ImageView mNumAddBtn;
        @InjectView(R.id.cart_del_btn)//删除按钮
        TextView cart_del_btn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    //private List<Integer> listInt;

    public RequestParams mapToRP(Map<String,String> map){
        RequestParams params = new RequestParams();
        Set entrySet = map.entrySet(); // key-value的set集合
        for(String key : map.keySet()){
            params.put(key,map.get(key));
        }
        return params;
    }

}

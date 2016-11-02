package com.ctrl.android.property.jason.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.MemberDao;
import com.ctrl.android.property.jason.entity.MemberAddress;
import com.ctrl.android.property.jason.util.StrConstant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 选择收货地址 activity
 * Created by Jason on 2015/10/10.
 * */
public class ChooseShippingAddressActivity extends AppToolBarActivity implements View.OnClickListener {

    @InjectView(R.id.choose_shipping_adress_listview)
    ListView choose_shipping_adress_listview;
    @InjectView(R.id.tv_choose_shipping_adress_add)
    TextView tv_choose_shipping_adress_add;

    private List<MemberAddress>listMap=null;
    private String TITLE= StrConstant.CHOOSE_SHIPPING_ADDRESS;
    private ChooseShippingAddressAdapter adapter;
    private MemberDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shipping_address);
        ButterKnife.inject(this);
        init();


    }

    private void init() {
        dao=new MemberDao(this);
        dao.requestMemberAddressList(AppHolder.getInstance().getMemberInfo().getMemberId(), "");
        tv_choose_shipping_adress_add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==tv_choose_shipping_adress_add){
            startActivity(new Intent(ChooseShippingAddressActivity.this,ModifyShippingAddressActivity.class));
            finish();
            AnimUtil.intentSlidIn(ChooseShippingAddressActivity.this);
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
           // MessageUtils.showShortToast(this, "连接成功");
            listMap=dao.getMemberAddressList();
                adapter = new ChooseShippingAddressAdapter(this);
                adapter.setList(listMap);
                choose_shipping_adress_listview.setAdapter(adapter);
                choose_shipping_adress_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*adapter.setSelection(position);
                    adapter.notifyDataSetChanged();*/
                        AppHolder.getInstance().getOrderAddress().setAddressId(listMap.get(position).getReceiveAddressId());
                        AppHolder.getInstance().getOrderAddress().setReceiveName(listMap.get(position).getReceiveName());
                        AppHolder.getInstance().getOrderAddress().setMobile(listMap.get(position).getMobile());
                        AppHolder.getInstance().getOrderAddress().setReceiveAddress(listMap.get(position).getAddress());
                        finish();
                    }
                });

        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        tv_choose_shipping_adress_add.setVisibility(View.VISIBLE);
        //MessageUtils.showShortToast(this, "连接失败");
     /*       rl_choose_shipping_adress=(RelativeLayout)findViewById(R.id.rl_choose_shipping_adress);
            TextView tvadd=new TextView(this);
        Drawable drawable=getResources().getDrawable(R.drawable.edit_add);
        drawable.setBounds( 0 ,  0 , drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvadd.setCompoundDrawables(null, drawable, null, null);
            tvadd.setCompoundDrawablePadding(10);
            tvadd.setText("您暂时还没有收货地址，点击添加吧！");
        // 添加到屏幕布局
        LinearLayout.LayoutParams layoutParams =  new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rl_choose_shipping_adress.addView(tvadd,layoutParams);*/


    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.green_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
                onBackPressed();
            }
        });
        return true;
    }

    /**
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(StrConstant.MANAGE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseShippingAddressActivity.this, ManagementShippingAdressActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(ChooseShippingAddressActivity.this);
                finish();
            }
        });
        return true;
    }

    /**
     * 测试 获取数据的方法
     * */
  /*  private List<Map<String,String>> getListMap(){
        listMap = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name","收货人：张胜男" +i);
            map.put("tel","155555555" + i);
            map.put("adress","收货地址：山东省济南市历下区神光花园1号楼202" + i);
            listMap.add(map);
        }
        return listMap;
    }
*/
  /*  class ChooseShippingAddressAdapter01 extends BaseAdapter {
        private ChooseShippingAddressActivity mActivity;
        private List<Map<String,String>> list;
        private int temp=-1;

        public ChooseShippingAddressAdapter01(ChooseShippingAddressActivity mActivity){
            this.mActivity=mActivity;
        }

        public void setList(List<Map<String,String>> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if(convertView == null){
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.choose_shipping_adress_item,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            *//*holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 当前点击的CB
                    boolean cu = !isSelected.get(position);
                    // 先将所有的置为FALSE
                    for (Integer p : isSelected.keySet()) {
                        isSelected.put(p, false);
                    }
                    // 再将当前选择CB的实际状态
                    isSelected.put(position, cu);
                    ChooseShippingAddressAdapter01.this.notifyDataSetChanged();
                    beSelectedData.clear();
                    if (cu) {
                        beSelectedData.add(listMap.get(position));
                    }
                }
            });*//*
           *//* holder.checkbox.setChecked(isSelected.get(position));
            if(holder.checkbox.isChecked()){
                holder.tv_default.setVisibility(View.VISIBLE);
            }else {
                holder.tv_default.setVisibility(View.GONE);
            }*//*

            holder.checkbox.setId(position);//对checkbox的id进行重新设置为当前的position
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){//实现checkbox的单选功能,同样适用于radiobutton
                        if(temp!=-1){
                            //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
                            CheckBox tempCheckBox=(CheckBox)mActivity.findViewById(temp);
                            if(tempCheckBox!=null)
                                tempCheckBox.setChecked(false);
                        }
                        temp=buttonView.getId();//保存当前选中的checkbox的id值
                    }
                }
            });
            if(position==temp){holder.checkbox.setChecked(true);}
            else {holder.checkbox.setChecked(false);}
            final Map<String,String> map = list.get(position);
            holder.tv_name.setText(map.get("name"));
            holder.tv_telephone.setText(map.get("tel"));
            holder.tv_shippingadress.setText(map.get("adress"));
            return convertView;
        }

      class ViewHolder{
            @InjectView(R.id.tv_name)//收货人姓名
                    TextView tv_name;
            @InjectView(R.id.tv_telephone)//电话号码
                    TextView tv_telephone;
            @InjectView(R.id.tv_shippingadress)//收货地址
                    TextView tv_shippingadress;
            @InjectView(R.id.tv_default)//默认
                    TextView tv_default;
            @InjectView(R.id.checkbox)//checkBox
                    CheckBox checkbox;
            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }



    }*/
    }




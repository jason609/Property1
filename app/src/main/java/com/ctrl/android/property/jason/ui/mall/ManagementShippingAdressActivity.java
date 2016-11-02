package com.ctrl.android.property.jason.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

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

public class ManagementShippingAdressActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.management_shipping_adress_btn)
    Button management_shipping_adress_btn;
    @InjectView(R.id.choose_shipping_adress_listview)
    ListView choose_shipping_adress_listview;
    private String TITLE= StrConstant.MANAGE_SHIPPING_ADDRESS;
    private List<MemberAddress>listMap;
    private MemberDao dao;
    private MemberAddress mMemberAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_shipping_adress);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        dao=new MemberDao(this);
        dao.requestMemberAddressList(AppHolder.getInstance().getMemberInfo().getMemberId(), "");
        management_shipping_adress_btn.setOnClickListener(this);


    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            listMap = dao.getMemberAddressList();
             mMemberAddress=new MemberAddress();
            final ManagementShippingAddressAdapter adapter = new ManagementShippingAddressAdapter(this);
            adapter.setList(listMap);
            choose_shipping_adress_listview.setAdapter(adapter);
            choose_shipping_adress_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*adapter.setSelection(position);
                    adapter.notifyDataSetChanged();*/
                    Intent intent=new Intent(ManagementShippingAdressActivity.this,ShippingAddressActivity.class);
                    mMemberAddress=listMap.get(position);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("mMemberAddress",mMemberAddress);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(ManagementShippingAdressActivity.this);
                    finish();
                }
            });


        }
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

    @Override
    public void onClick(View v) {
        if(v==management_shipping_adress_btn){
            startActivity(new Intent(ManagementShippingAdressActivity.this,ModifyShippingAddressActivity.class));
            AnimUtil.intentSlidIn(ManagementShippingAdressActivity.this);
            finish();
        }

    }

    /**
     * 测试 获取数据的方法
     * */
/*    private List<Map<String,String>> getListMap(){
        listMap = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name","收货人：张胜男" +i);
            map.put("tel","155555555" + i);
            map.put("adress","收货地址：山东省济南市历下区神光花园1号楼202" + i);
            listMap.add(map);
        }
        return listMap;
    }*/

}

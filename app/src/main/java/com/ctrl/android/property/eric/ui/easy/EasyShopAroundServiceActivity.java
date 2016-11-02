package com.ctrl.android.property.eric.ui.easy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.entity.ShopService;
import com.ctrl.android.property.eric.ui.adapter.EasyShopArroundServiceAdapter;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 便民商家(服务) activity
 * Created by Eric on 2015/10/13.
 */
public class EasyShopAroundServiceActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//商家列表
    ListView listView;

    private EasyShopArroundServiceAdapter easyShopArroundServiceAdapter;

    private String TITLE = StrConstant.EASY_SHOP_ARROUND_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.easy_shop_arround_service_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        easyShopArroundServiceAdapter = new EasyShopArroundServiceAdapter(this);
        easyShopArroundServiceAdapter.setList(getList());
        listView.setAdapter(easyShopArroundServiceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageUtils.showShortToast(EasyShopAroundServiceActivity.this,getList().get(position).getCategory());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * header 左侧按钮
     * */
    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
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
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.white_cross_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(EasyShopAroundActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }

    private List<ShopService> getList(){
        List<ShopService> list = new ArrayList<>();

        for(int i = 0 ; i < 6 ; i ++){
            ShopService shop = new ShopService();
            shop.setCategory(i + "分类名称");
            shop.setImgUrl("aa");

            List<Map<String,String>> listmap = new ArrayList<>();
            for(int j = 0 ; j < 3 ; j ++){
                Map<String,String> map = new HashMap<>();
                map.put("name",j + "服务项目");
                map.put("tel",j + "0000000000");
                listmap.add(map);
            }

            shop.setListMap(listmap);
            list.add(shop);

        }

        return list;
    }

}

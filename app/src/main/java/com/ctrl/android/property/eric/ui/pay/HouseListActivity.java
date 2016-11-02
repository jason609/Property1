package com.ctrl.android.property.eric.ui.pay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.ctrl.android.property.eric.dao.HouseDao;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.ui.adapter.HouseListAdapter;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋列表 activity
 * Created by Eric on 2015/10/22
 */
public class HouseListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//列表
    ListView listView;

    private HouseDao houseDao;

    private List<House> listHouse = new ArrayList<>();

    private String TITLE = StrConstant.HOUSES_TITLE;
    private HouseListAdapter houseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.house_list_activity);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        houseDao = new HouseDao(this);
        showProgress(true);
        houseDao.requestHouseList(AppHolder.getInstance().getMemberInfo().getMemberId(), "", "");

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            listHouse = houseDao.getListHouse();

            houseListAdapter = new HouseListAdapter(this);
            houseListAdapter.setList(listHouse);
            listView.setAdapter(houseListAdapter);
            listView.setDivider(null);
            listView.setDividerHeight(20);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //MessageUtils.showShortToast(HouseListActivity.this, listHouse.get(position).getCommunityName());
                    Intent intent = new Intent(HouseListActivity.this, HousePayActivity.class);
                    intent.putExtra("communityName",listHouse.get(position).getCommunityName());
                    intent.putExtra("building_unit_room",(listHouse.get(position).getBuilding() + "-" + listHouse.get(position).getUnit() + "-" + listHouse.get(position).getRoom()));
                    intent.putExtra("proprietorId",listHouse.get(position).getProprietorId());
                    intent.putExtra("addressId",listHouse.get(position).getAddressId());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(HouseListActivity.this);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        //
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
     * header 右侧文本
     * */
    public boolean setupToolBarRightText(TextView mRightText) {

        mRightText.setText(StrConstant.ADD);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseListActivity.this, HouseConfirmActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(HouseListActivity.this);
            }
        });

        return true;
    }



}

package com.ctrl.android.property.eric.ui.house;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.HouseDao;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.ui.adapter.HouseListAdapter2;
import com.ctrl.android.property.eric.ui.pay.HouseConfirmActivity;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋列表 activity
 * Created by Eric on 2015/10/22
 */
public class HouseListActivity2 extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.listView)//可刷新的列表
    ListView listView;
    @InjectView(R.id.add_house_btn)
    TextView add_house_btn;


    private String TITLE = StrConstant.HOUSES_TITLE;

    private HouseListAdapter2 houseListAdapter;
    private HouseDao houseDao;
    private List<House> listHouse;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.house_list_activity2);
        ButterKnife.inject(this);
        //init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //now_community_name.setText(S.getStr(AppHolder.getInstance().getCommunity().getCommunityName()));
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        add_house_btn.setOnClickListener(this);

        houseListAdapter = new HouseListAdapter2(this);

        houseDao = new HouseDao(this);
        showProgress(true);
        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
        String currentPage = "";
        String rowCountPerPage = "";
        houseDao.requestHouseList(memberId, currentPage, rowCountPerPage);


    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){

            listHouse = houseDao.getListHouse();

            houseListAdapter.setList(listHouse);
            listView.setAdapter(houseListAdapter);
            listView.setDivider(null);
            listView.setDividerHeight(20);

            if(listHouse.size() > 0){
                for(int i = 0 ; i < listHouse.size() ; i ++){
                    if(listHouse.get(i).getIsDefault() == 1){
                        AppHolder.getInstance().setHouse(listHouse.get(i));
                        AppHolder.getInstance().getProprietor().setProprietorId(listHouse.get(i).getProprietorId());
                        AppHolder.getInstance().getCommunity().setCommunityName(listHouse.get(i).getCommunityName());
                        AppHolder.getInstance().getCommunity().setId(listHouse.get(i).getCommunityId());
                    }
                }
            } else {
                AppHolder.getInstance().setHouse(new House());
                AppHolder.getInstance().getProprietor().setProprietorId("");
            }



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showProgress(true);
                    index = position;
                    houseDao.requestSetDefaultHouse(AppHolder.getInstance().getMemberInfo().getMemberId(), listHouse.get(position).getId());
                }
            });
        }

        if(1 == requestCode){
            MessageUtils.showShortToast(this, "设置成功");
            requestHouseList();
        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        if(errorNo.equals("043")){
            MessageUtils.showShortToast(HouseListActivity2.this,"该房屋已经是默认房屋！");
        }else {
            super.onRequestFaild(errorNo, errorMessage);

        }


//        if(listHouse.size() > 0){
//            for(int i = 0 ; i < listHouse.size() ; i ++){
//                if(listHouse.get(i).getIsDefault() == 1){
//                    AppHolder.getInstance().setHouse(listHouse.get(i));
//                    AppHolder.getInstance().getProprietor().setProprietorId(listHouse.get(i).getProprietorId());
//                }
//            }
//        } else {
//            AppHolder.getInstance().setHouse(new House());
//            AppHolder.getInstance().getProprietor().setProprietorId("");
//        }

    }

    public void requestHouseList(){
        showProgress(true);
        String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
        String currentPage = "";
        String rowCountPerPage = "";
        houseDao.requestHouseList(memberId, currentPage, rowCountPerPage);
        //MessageUtils.showShortToast(HouseListActivity2.this, listHouse.get(position).getCommunityName());
        //AppHolder.getInstance().setHouse(listHouse.get(position));
        //AppHolder.getInstance().getProprietor().setProprietorId(listHouse.get(position).getProprietorId());
        //finish();
    }

    @Override
    public void onClick(View v) {
        if(v == add_house_btn){
            Intent intent = new Intent(HouseListActivity2.this, HouseConfirmActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(HouseListActivity2.this);
        }
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
//    public boolean setupToolBarRightText(TextView mRightText) {
//
//        mRightText.setText(StrConstant.ADD);
//        mRightText.setTextColor(getResources().getColor(R.color.text_white));
//        mRightText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HouseListActivity2.this, HouseConfirmActivity.class);
//                startActivity(intent);
//                AnimUtil.intentSlidIn(HouseListActivity2.this);
//            }
//        });
//
//        return true;
//    }



}

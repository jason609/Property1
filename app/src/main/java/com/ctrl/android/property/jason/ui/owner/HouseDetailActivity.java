package com.ctrl.android.property.jason.ui.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.util.ViewUtil;
import com.ctrl.android.property.jason.ui.adapter.HouseDetailPagerAdapter;
import com.ctrl.android.property.jason.ui.complaint.MyComplaintActivity;
import com.ctrl.android.property.jason.util.StrConstant;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HouseDetailActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.viewpager_house_detail)
    ViewPager viewpager_house_detail;
    @InjectView(R.id.indicator_house_detail)
    CirclePageIndicator indicator_house_detai;
    private List<String>listPics=new ArrayList<>();
    private HouseDetailPagerAdapter adapter;
    private String TITLE= StrConstant.HOUSE_DETAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        listPics.add("a");
        listPics.add("b");
        adapter=new HouseDetailPagerAdapter(getSupportFragmentManager(),listPics);
        viewpager_house_detail.setAdapter(adapter);
        viewpager_house_detail.setOffscreenPageLimit(listPics.size() == 0 ? 1 : listPics.size() + 2);
        viewpager_house_detail.setCurrentItem(0);
        //为viewpager配置indicator
        ViewUtil.settingIndicator(indicator_house_detai,viewpager_house_detail);
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
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.white_check_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
                Intent intent=new Intent(HouseDetailActivity.this, MyComplaintActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidOut(HouseDetailActivity.this);

            }
        });
        return true;
    }

}

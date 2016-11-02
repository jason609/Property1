package com.ctrl.android.property.jason.ui.owner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.ui.Fragment.BuyReleaseFragment;
import com.ctrl.android.property.jason.ui.adapter.ViewPagerAdapter;
import com.ctrl.android.property.jason.util.StrConstant;

/**
 * 房屋发布 Activity
 */

public class BuyReleaseActivity extends AppToolBarActivity implements View.OnClickListener {
    TextView btn_house_release_rent;
    TextView btn_house_release_sell;
    ViewPager viewPager;
    SparseArray<Fragment>fragmets=new SparseArray<>();


    private String TITLE= StrConstant.BUY_RELEASE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_release);
        init();
    }

    private void init() {
        btn_house_release_rent=(TextView)findViewById(R.id.btn_house_release_rent);
        btn_house_release_sell=(TextView)findViewById(R.id.btn_house_release_sell);
        btn_house_release_rent.setOnClickListener(this);
        btn_house_release_sell.setOnClickListener(this);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        BuyReleaseFragment fragment01= BuyReleaseFragment.newInstance();
        BuyReleaseFragment fragment02= BuyReleaseFragment.newInstance();
        fragmets.put(0,fragment01);
        fragmets.put(1,fragment02);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmets));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             switch (position){
                 case 0:
                     btn_house_release_rent.setBackgroundResource(R.drawable.button_left_shap_checked);
                     btn_house_release_rent.setTextColor(Color.WHITE);
                     btn_house_release_sell.setBackgroundResource(R.drawable.button_right_shap);
                     btn_house_release_sell.setTextColor(Color.GRAY);
                     break;
                 case 1:
                     btn_house_release_sell.setBackgroundResource(R.drawable.button_right_shap_checked);
                     btn_house_release_sell.setTextColor(Color.WHITE);
                     btn_house_release_rent.setBackgroundResource(R.drawable.button_left_shap);
                     btn_house_release_rent.setTextColor(Color.GRAY);
                     break;

             }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       }



    @Override
    public void onClick(View v) {
        if(v==btn_house_release_rent){
            btn_house_release_rent.setBackgroundResource(R.drawable.button_left_shap_checked);
            btn_house_release_rent.setTextColor(Color.WHITE);
            btn_house_release_sell.setBackgroundResource(R.drawable.button_right_shap);
            btn_house_release_sell.setTextColor(Color.GRAY);
            viewPager.setCurrentItem(0);

        }
        if(v==btn_house_release_sell){
           btn_house_release_sell.setBackgroundResource(R.drawable.button_right_shap_checked);
            btn_house_release_sell.setTextColor(Color.WHITE);
            btn_house_release_rent.setBackgroundResource(R.drawable.button_left_shap);
            btn_house_release_rent.setTextColor(Color.GRAY);
            viewPager.setCurrentItem(1);
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
     * header 右侧按钮
     * */
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.white_check_icon);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(MallShopMainActivity.this, "MORE");
                Intent intent=new Intent(BuyReleaseActivity.this,HouseDetailActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }
}

package com.ctrl.android.property.staff.ui.task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.ui.adapter.JasonViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*报修 activity*/

public class ComplaintAppointActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.viewpager_repairs)
    ViewPager viewpager_repairs;
    @InjectView(R.id.btn_appiont_end) //待处理
            TextView btn_my_repairs_progressing;
    @InjectView(R.id.btn_appiont_pending)//待处理
            TextView  btn_my_repairs_pending;

    List<Fragment>fragments=new ArrayList<>();
    private JasonViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_complaint_appoint);
        ButterKnife.inject(this);
        init();
    }


    private void init() {
        btn_my_repairs_pending.setOnClickListener(this);
        btn_my_repairs_progressing.setOnClickListener(this);
        ComplaintAppointFragment fragment01 = ComplaintAppointFragment.newInstance("0");
        ComplaintAppointFragment fragment04 = ComplaintAppointFragment.newInstance("1");

        fragments.add(fragment01);
        fragments.add(fragment04);
        viewPagerAdapter = new JasonViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPagerAdapter.setOnReloadListener(new JasonViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragments = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add( ComplaintAppointFragment.newInstance("0"));
                list.add(ComplaintAppointFragment.newInstance("1"));
                viewPagerAdapter.setPagerItems(list);
            }
        });
        viewpager_repairs.setAdapter(viewPagerAdapter);
        viewpager_repairs.setOffscreenPageLimit(2);
        viewpager_repairs.setCurrentItem(0);
        viewpager_repairs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap_checked);
                        btn_my_repairs_pending.setTextColor(Color.WHITE);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_progressing.setTextColor(Color.GRAY);
                        break;
                    case 1:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_repairs_pending.setTextColor(Color.GRAY);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_right_shap_checked);
                        btn_my_repairs_progressing.setTextColor(Color.WHITE);
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // unregisterReceiver(mbc);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_my_repairs_pending){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap_checked);
            btn_my_repairs_pending.setTextColor(Color.WHITE);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_progressing.setTextColor(Color.GRAY);
            viewpager_repairs.setCurrentItem(0);

        }
        if(v==btn_my_repairs_progressing){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_repairs_pending.setTextColor(Color.GRAY);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_right_shap_checked);
            btn_my_repairs_progressing.setTextColor(Color.WHITE);
            viewpager_repairs.setCurrentItem(1);
        }


    }

    /**
     * 获取适配器
     * @return
     */
    public JasonViewPagerAdapter getAdapter()
    {
        return viewPagerAdapter;
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
        return "投诉派工";
    }





}

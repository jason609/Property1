package com.ctrl.android.property.staff.ui.repair;

import android.content.Intent;
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
import com.ctrl.android.property.staff.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*报修 activity*/

public class RepairsActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.viewpager_repairs)
    ViewPager viewpager_repairs;
    @InjectView(R.id.btn_my_repairs_progressing) //待处理
    TextView btn_my_repairs_progressing;
    @InjectView(R.id.btn_my_repairs_progressed)//处理中
    TextView btn_my_repairs_progressed;
    @InjectView(R.id.btn_my_repairs_end)//已结束
    TextView btn_my_repairs_end;
    @InjectView(R.id.btn_my_repairs_pending)//待处理
    TextView  btn_my_repairs_pending;
   /* @InjectView(R.id.btn_my_repairs_chargeback)//已退单
    TextView  btn_my_repairs_chargeback;*/

   // SparseArray<Fragment> fragmets=null;
    List<Fragment>fragments=new ArrayList<>();
    private JasonViewPagerAdapter viewPagerAdapter;
   // private MyBroadCastReceiver mbc;
    private Intent onHomeIntent;
    //  private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_repairs);
        ButterKnife.inject(this);
        init();
    }


    private void init() {
        /*mbc=new MyBroadCastReceiver();
        IntentFilter filter=new IntentFilter("repair_update");
        registerReceiver(mbc,filter);*/
        btn_my_repairs_pending.setOnClickListener(this);
        btn_my_repairs_progressing.setOnClickListener(this);
        btn_my_repairs_progressed.setOnClickListener(this);
        btn_my_repairs_end.setOnClickListener(this);
        //btn_my_repairs_chargeback.setOnClickListener(this);
        RepairsFragment fragment01 = RepairsFragment.newInstance(StrConstant.REPAIRS_PENDING);
        RepairsFragment fragment02 = RepairsFragment.newInstance(StrConstant.REPAIRS_PROGRESSING);
        RepairsFragment fragment03 = RepairsFragment.newInstance(StrConstant.REPAIRS_PROGRESSED);
        RepairsFragment fragment04 = RepairsFragment.newInstance(StrConstant.REPAIRS_END);
       // RepairsFragment fragment05 = RepairsFragment.newInstance(StrConstant.CHARGE_BACK);
        /*fragmets=new SparseArray<>();
        fragmets.put(0, fragment01);
        fragmets.put(1, fragment02);
        fragmets.put(2, fragment03);
        fragmets.put(3, fragment04);*/

        fragments.add(fragment01);
        fragments.add(fragment02);
        fragments.add(fragment03);
        fragments.add(fragment04);
       // fragmets.put(4, fragment05);
        viewPagerAdapter = new JasonViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPagerAdapter.setOnReloadListener(new JasonViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragments = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(RepairsFragment.newInstance(StrConstant.REPAIRS_PENDING));
                list.add(RepairsFragment.newInstance(StrConstant.REPAIRS_PROGRESSING));
                list.add(RepairsFragment.newInstance(StrConstant.REPAIRS_PROGRESSED));
                list.add(RepairsFragment.newInstance(StrConstant.REPAIRS_END));
                viewPagerAdapter.setPagerItems(list);
            }
        });
        viewpager_repairs.setAdapter(viewPagerAdapter);
        viewpager_repairs.setOffscreenPageLimit(4);
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
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressing.setTextColor(Color.GRAY);
                        btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressed.setTextColor(Color.GRAY);
                        btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_end.setTextColor(Color.GRAY);
                       /* btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
                        break;
                    case 1:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_repairs_pending.setTextColor(Color.GRAY);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap_checked);
                        btn_my_repairs_progressing.setTextColor(Color.WHITE);
                        btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressed.setTextColor(Color.GRAY);
                        btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_end.setTextColor(Color.GRAY);
                        /*btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
                        break;
                    case 2:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_repairs_pending.setTextColor(Color.GRAY);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressing.setTextColor(Color.GRAY);
                        btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap_checked);
                        btn_my_repairs_progressed.setTextColor(Color.WHITE);
                        btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_end.setTextColor(Color.GRAY);
                       /* btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
                        break;
                    case 3:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_repairs_pending.setTextColor(Color.GRAY);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressing.setTextColor(Color.GRAY);
                        btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressed.setTextColor(Color.GRAY);
                        btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap_checked);
                        btn_my_repairs_end.setTextColor(Color.WHITE);
                       /* btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
                        break;
                   /* case 4:
                        btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_repairs_pending.setTextColor(Color.GRAY);
                        btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressing.setTextColor(Color.GRAY);
                        btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_progressed.setTextColor(Color.GRAY);
                        btn_my_repairs_end.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_repairs_end.setTextColor(Color.GRAY);
                       *//* btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap_checked);
                        btn_my_repairs_chargeback.setTextColor(Color.WHITE);*//*
                        break;*/
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
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressing.setTextColor(Color.GRAY);
            btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressed.setTextColor(Color.GRAY);
            btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_end.setTextColor(Color.GRAY);
          /*  btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
            viewpager_repairs.setCurrentItem(0);

        }
        if(v==btn_my_repairs_progressing){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_repairs_pending.setTextColor(Color.GRAY);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap_checked);
            btn_my_repairs_progressing.setTextColor(Color.WHITE);
            btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressed.setTextColor(Color.GRAY);
            btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_end.setTextColor(Color.GRAY);
            /*btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
            viewpager_repairs.setCurrentItem(1);
        }
        if(v==btn_my_repairs_progressed){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_repairs_pending.setTextColor(Color.GRAY);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressing.setTextColor(Color.GRAY);
            btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap_checked);
            btn_my_repairs_progressed.setTextColor(Color.WHITE);
            btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_end.setTextColor(Color.GRAY);
            /*btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
            viewpager_repairs.setCurrentItem(2);
        }
        if(v==btn_my_repairs_end){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_repairs_pending.setTextColor(Color.GRAY);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressing.setTextColor(Color.GRAY);
            btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressed.setTextColor(Color.GRAY);
            btn_my_repairs_end.setBackgroundResource(R.drawable.button_right_shap_checked);
            btn_my_repairs_end.setTextColor(Color.WHITE);
           /* btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_repairs_chargeback.setTextColor(Color.GRAY);*/
            viewpager_repairs.setCurrentItem(3);
        }
       /* if(v==btn_my_repairs_chargeback){
            btn_my_repairs_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_repairs_pending.setTextColor(Color.GRAY);
            btn_my_repairs_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressing.setTextColor(Color.GRAY);
            btn_my_repairs_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_progressed.setTextColor(Color.GRAY);
            btn_my_repairs_end.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_repairs_end.setTextColor(Color.GRAY);
            btn_my_repairs_chargeback.setBackgroundResource(R.drawable.button_right_shap_checked);
            btn_my_repairs_chargeback.setTextColor(Color.WHITE);
            viewpager_repairs.setCurrentItem(4);
        }*/


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
        return "报修管理";
    }

  /*  class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("repair_update")){
                Log.i("TAG", "TAG111");
               viewPagerAdapter.reLoad();
            }
        }
    }*/




}

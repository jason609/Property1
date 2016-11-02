package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 房屋缴费 activity
 * Created by Eric on 2015/10/22
 */
public class TaskListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.radio_group)//表头
    RadioGroup radio_group;
    @InjectView(R.id.radio_deal)//待处理
    RadioButton radio_deal;
    @InjectView(R.id.radio_done)//已处理
    RadioButton radio_done;

    @InjectView(R.id.toolbar_rightText)
    TextView toolbar_rightText;

    @InjectView(R.id.viewpager)//列表内容
    ViewPager viewpager;

    SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private String TITLE = "任务指派";

    private int flg = 0;//0:待处理  1: 已完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.task_list_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        fragments.put(0, TaskListFragment.newInstance(Constant.TASK_DEAL));
        fragments.put(1, TaskListFragment.newInstance(Constant.TASK_DONE));

        String str = getIntent().getStringExtra("flg");

//        if(!S.isNull(str)){
//            flg = Integer.parseInt(str);
//        }


//        /**设置默认选择为全部*/
//        if(flg == 0){
//            radio_deal.setChecked(true);
//        } else {
//            radio_done.setChecked(true);
//        }

        radio_deal.setChecked(true);

        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.setOnPageChangeListener(new FragmentOnPageChangeListener());
        //指定加载页数(可避免页面的重复加载)
        viewpager.setOffscreenPageLimit(6);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_deal:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.radio_done:
                        viewpager.setCurrentItem(1);
                        break;
                }
            }
        });

    }

    public class FragmentOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch(position){
                case 0:
                    radio_group.check(R.id.radio_deal);
                    break;
                case 1:
                    radio_group.check(R.id.radio_done);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
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

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {

        if(AppHolder.getInstance().getStaffInfo().getGrade() == 2){
            //
        } else {
            mRightText.setText("添加");
            mRightText.setTextColor(getResources().getColor(R.color.text_white));
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskListActivity.this, TaskAddActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(TaskListActivity.this);
                }
            });
        }


        return true;
    }

    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("owner", i + "业主名称");
            map.put("name", "中润世纪广场");
            map.put("time", "2015-10-22");
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }



}

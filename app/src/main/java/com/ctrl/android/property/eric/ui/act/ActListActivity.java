package com.ctrl.android.property.eric.ui.act;

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
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.ui.adapter.ViewPagerAdapter;
import com.ctrl.android.property.eric.ui.forum.ForumStartActActivity;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区活动列表 activity
 * Created by Eric on 2015/10/26
 */
public class ActListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.radio_group)//表头
    RadioGroup radio_group;
    @InjectView(R.id.radio_all_act)//全部活动
    RadioButton radio_all_act;
    @InjectView(R.id.radio_I_take_in)//我参与的
    RadioButton radio_I_take_in;
    @InjectView(R.id.radio_I_start_up)//我发起的
    RadioButton I_start_up;

    @InjectView(R.id.viewpager)//列表内容
    ViewPager viewpager;

    SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private String TITLE = StrConstant.COMMUNITY_ACTIVITY_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.act_list_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        fragments.put(0, ActListFragment.newInstance(Constant.ACT_ALL));
        fragments.put(1, ActListFragment.newInstance(Constant.ACT_I_TAKE_IN));
        fragments.put(2, ActListFragment.newInstance(Constant.ACT_I_START_UP));


        /**设置默认选择为全部*/
        radio_all_act.setChecked(true);

        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.setOnPageChangeListener(new FragmentOnPageChangeListener());
        //指定加载页数(可避免页面的重复加载)
        viewpager.setOffscreenPageLimit(6);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_all_act:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.radio_I_take_in:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.radio_I_start_up:
                        viewpager.setCurrentItem(2);
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
                    radio_group.check(R.id.radio_all_act);
                    break;
                case 1:
                    radio_group.check(R.id.radio_I_take_in);
                    break;
                case 2:
                    radio_group.check(R.id.radio_I_start_up);
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

    /**
     * header 右侧文本
     * */
    public boolean setupToolBarRightText(TextView mRightText) {

        mRightText.setText("发起活动");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActListActivity.this, ForumStartActActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(ActListActivity.this);
            }
        });

        return true;
    }
}

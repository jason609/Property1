package com.ctrl.android.property.staff.ui.patrol;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.ui.adapter.JasonViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/11/10.
 */
public class PatrolActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.radio_one)
    RadioButton mRadioOne;
    @InjectView(R.id.radio_two)
    RadioButton mRadioTwo;
    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;

    ArrayList<Fragment> fragments = new ArrayList<>();
    private JasonViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_patrol);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        PatrolFragment patrolFragment1 = PatrolFragment.newInstance("a");
        PatrolFragment patrolFragment2 = PatrolFragment.newInstance("b");
        fragments.add( patrolFragment1);
        fragments.add( patrolFragment2);
        adapter=new JasonViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);
        mViewpager.setOnPageChangeListener(new FragmentOnPageChangeListener());
        adapter.setOnReloadListener(new JasonViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragments = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(PatrolFragment.newInstance("a"));
                list.add(PatrolFragment.newInstance("b"));
                adapter.setPagerItems(list);
            }
        });
        mViewpager.setOffscreenPageLimit(2);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_one:
                        mRadioOne.setBackgroundResource(R.drawable.button_left_shap_checked);
                        mRadioTwo.setBackgroundResource(R.drawable.button_right_shap);
                        mRadioOne.setTextColor(Color.WHITE);
                        mRadioTwo.setTextColor(Color.GRAY);
                        mViewpager.setCurrentItem(0);
                        break;
                    case R.id.radio_two:
                        mRadioGroup.check(R.id.radio_two);
                        mRadioOne.setBackgroundResource(R.drawable.button_left_shap);
                        mRadioTwo.setBackgroundResource(R.drawable.button_right_shap_checked);
                        mRadioTwo.setTextColor(Color.WHITE);
                        mRadioOne.setTextColor(Color.GRAY);
                        mViewpager.setCurrentItem(1);
                        break;
                }
            }
        });
        mRadioOne.setText("未巡视");
        mRadioTwo.setText("已巡视");
        mRadioOne.setChecked(true);
    }

    @Override
    public void onClick(View v) {


    }

    public JasonViewPagerAdapter getAdapter(){
        return adapter;
    }

    public class FragmentOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int index) {
            switch (index) {
                case 0:
                    mRadioGroup.check(R.id.radio_one);
                    mRadioOne.setBackgroundResource(R.drawable.button_left_shap_checked);
                    mRadioTwo.setBackgroundResource(R.drawable.button_right_shap);
                    mRadioOne.setTextColor(Color.WHITE);
                    mRadioTwo.setTextColor(Color.GRAY);
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_two);
                    mRadioOne.setBackgroundResource(R.drawable.button_left_shap);
                    mRadioTwo.setBackgroundResource(R.drawable.button_right_shap_checked);
                    mRadioTwo.setTextColor(Color.WHITE);
                    mRadioOne.setTextColor(Color.GRAY);
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public String setupToolBarTitle() {
        return "巡视巡查";
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }
}

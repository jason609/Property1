package com.ctrl.android.property.eric.ui.survey;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.ui.adapter.ViewPagerAdapter;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 调查投票 activity
 * Created by Eric on 2015/11/04
 */
public class SurveyListActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.radio_group)//表头
    RadioGroup radio_group;
    @InjectView(R.id.radio_survey)//社区调查
    RadioButton radio_survey;
    @InjectView(R.id.radio_vote)//投票
    RadioButton radio_vote;

    @InjectView(R.id.viewpager)//列表内容
    ViewPager viewpager;

    SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private String TITLE = StrConstant.COMMUNITY_SURVEY_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.survey_list_activity);
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

        Log.d("demo","Proprietor : " + AppHolder.getInstance().getProprietor().getProprietorId());

        fragments.put(0, SurveyListFragment.newInstance(Constant.COMMINITY_SURVEY, AppHolder.getInstance().getProprietor().getProprietorId()));
        fragments.put(1, SurveyListFragment.newInstance(Constant.COMMINITY_VOTE,AppHolder.getInstance().getProprietor().getProprietorId()));

        /**设置默认选择为全部*/
        radio_survey.setChecked(true);

        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewpager.setOnPageChangeListener(new FragmentOnPageChangeListener());
        //指定加载页数(可避免页面的重复加载)
        viewpager.setOffscreenPageLimit(6);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_survey:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.radio_vote:
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
                    radio_group.check(R.id.radio_survey);
                    break;
                case 1:
                    radio_group.check(R.id.radio_vote);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

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

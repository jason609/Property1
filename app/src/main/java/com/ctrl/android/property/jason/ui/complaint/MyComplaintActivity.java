package com.ctrl.android.property.jason.ui.complaint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.ui.Fragment.MyComplaintFragment;
import com.ctrl.android.property.jason.ui.adapter.JasonViewPagerAdapter;
import com.ctrl.android.property.jason.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*我的投诉 activity*/

public class MyComplaintActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.viewpager_complaint)
    ViewPager viewpager_complaint;
    @InjectView(R.id.btn_my_complaint_progressing)
    TextView btn_my_complaint_progressing;
    @InjectView(R.id.btn_my_complaint_progressed)
    TextView btn_my_complaint_progressed;
    @InjectView(R.id.btn_my_complaint_end)
    TextView btn_my_complaint_end;
    @InjectView(R.id.btn_my_complaint_pending)
    TextView btn_my_complaint_pending;

    //SparseArray<Fragment> fragmets=new SparseArray<>();
    List<Fragment>fragments=new ArrayList<>();
    private JasonViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        btn_my_complaint_pending.setOnClickListener(this);
        btn_my_complaint_progressing.setOnClickListener(this);
        btn_my_complaint_progressed.setOnClickListener(this);
        btn_my_complaint_end.setOnClickListener(this);
        MyComplaintFragment fragment01= MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PENDING);
        MyComplaintFragment fragment02= MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PROGRESSING);
        MyComplaintFragment fragment03= MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PROGRESSED);
        MyComplaintFragment fragment04= MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_END);
        /*fragmets.put(0, fragment01);
        fragmets.put(1, fragment02);
        fragmets.put(2, fragment03);*/
        fragments.add(fragment01);
        fragments.add(fragment02);
        fragments.add(fragment03);
        fragments.add(fragment04);
        adapter=new JasonViewPagerAdapter(getSupportFragmentManager(), fragments);
        adapter.setOnReloadListener(new JasonViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragments = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PENDING));
                list.add(MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PROGRESSING));
                list.add(MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_PROGRESSED));
                list.add(MyComplaintFragment.newInstance(StrConstant.MY_COMPLAINT_END));
                adapter.setPagerItems(list);
            }
        });
        viewpager_complaint.setAdapter(adapter);

        viewpager_complaint.setCurrentItem(0);
        viewpager_complaint.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap_checked);
                        btn_my_complaint_pending.setTextColor(Color.WHITE);
                        btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressing.setTextColor(Color.GRAY);
                        btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressed.setTextColor(Color.GRAY);
                        btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_complaint_end.setTextColor(Color.GRAY);
                        break;
                    case 1:
                        btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_complaint_pending.setTextColor(Color.GRAY);
                        btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap_checked);
                        btn_my_complaint_progressing.setTextColor(Color.WHITE);
                        btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressed.setTextColor(Color.GRAY);
                        btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_complaint_end.setTextColor(Color.GRAY);
                        break;
                    case 2:
                        btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_complaint_pending.setTextColor(Color.GRAY);
                        btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressing.setTextColor(Color.GRAY);
                        btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap_checked);
                        btn_my_complaint_progressed.setTextColor(Color.WHITE);
                        btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
                        btn_my_complaint_end.setTextColor(Color.GRAY);
                        break;
                    case 3:
                        btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
                        btn_my_complaint_pending.setTextColor(Color.GRAY);
                        btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressing.setTextColor(Color.GRAY);
                        btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
                        btn_my_complaint_progressed.setTextColor(Color.GRAY);
                        btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap_checked);
                        btn_my_complaint_end.setTextColor(Color.WHITE);
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
        if(v==btn_my_complaint_pending){
            btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap_checked);
            btn_my_complaint_pending.setTextColor(Color.WHITE);
            btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressing.setTextColor(Color.GRAY);
            btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressed.setTextColor(Color.GRAY);
            btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_complaint_end.setTextColor(Color.GRAY);
            viewpager_complaint.setCurrentItem(0);
        }
        if(v==btn_my_complaint_progressing){
            btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_complaint_pending.setTextColor(Color.GRAY);
            btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap_checked);
            btn_my_complaint_progressing.setTextColor(Color.WHITE);
            btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressed.setTextColor(Color.GRAY);
            btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_complaint_end.setTextColor(Color.GRAY);
            viewpager_complaint.setCurrentItem(1);
        }
        if(v==btn_my_complaint_progressed){
            btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_complaint_pending.setTextColor(Color.GRAY);
            btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressing.setTextColor(Color.GRAY);
            btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap_checked);
            btn_my_complaint_progressed.setTextColor(Color.WHITE);
            btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap);
            btn_my_complaint_end.setTextColor(Color.GRAY);
            viewpager_complaint.setCurrentItem(2);
        }
        if(v==btn_my_complaint_end){
            btn_my_complaint_pending.setBackgroundResource(R.drawable.button_left_shap);
            btn_my_complaint_pending.setTextColor(Color.GRAY);
            btn_my_complaint_progressing.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressing.setTextColor(Color.GRAY);
            btn_my_complaint_progressed.setBackgroundResource(R.drawable.button_center_shap);
            btn_my_complaint_progressed.setTextColor(Color.GRAY);
            btn_my_complaint_end.setBackgroundResource(R.drawable.button_right_shap_checked);
            btn_my_complaint_end.setTextColor(Color.WHITE);
            viewpager_complaint.setCurrentItem(3);
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
        return getString(R.string.my_complain);
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("添加");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MyRepairsActivity.this,MyRepairsPretreatmentActivity.class);
                Intent intent=new Intent(MyComplaintActivity.this,MyComplaintAddActivity.class);
                startActivityForResult(intent, 4000);
                AnimUtil.intentSlidIn(MyComplaintActivity.this);


            }
        });
        return true;
    }

    /**
     * 获取适配器
     * @return
     */
    public JasonViewPagerAdapter getAdapter()
    {
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 4000 :
                if (4001 == resultCode) {
                    adapter.reLoad();
                //viewpager_complaint.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmets));
            }
            break;
        }
    }
}

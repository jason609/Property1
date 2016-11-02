package com.ctrl.android.property.jason.ui.repairs;

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
import com.ctrl.android.property.jason.ui.Fragment.MyRepairsFragment;
import com.ctrl.android.property.jason.ui.adapter.JasonViewPagerAdapter;
import com.ctrl.android.property.jason.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*我的报修 activity*/

public class MyRepairsActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.viewpager_repairs)
    ViewPager viewpager_repairs;
    @InjectView(R.id.btn_my_repairs_progressing)
    TextView btn_my_repairs_progressing;
    @InjectView(R.id.btn_my_repairs_progressed)
    TextView btn_my_repairs_progressed;
    @InjectView(R.id.btn_my_repairs_end)
    TextView btn_my_repairs_end;
    @InjectView(R.id.btn_my_repairs_pending)
    TextView  btn_my_repairs_pending;

    //SparseArray<Fragment> fragmets=new SparseArray<>();
    List<Fragment>fragments=new ArrayList<>();
    private JasonViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        btn_my_repairs_pending.setOnClickListener(this);
        btn_my_repairs_progressing.setOnClickListener(this);
        btn_my_repairs_progressed.setOnClickListener(this);
        btn_my_repairs_end.setOnClickListener(this);
        MyRepairsFragment fragment01 = MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PENDING);
        MyRepairsFragment fragment02 = MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PROGRESSING);
        MyRepairsFragment fragment03 = MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PROGRESSED);
        MyRepairsFragment fragment04 = MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_END);
       /* fragmets.put(0, fragment01);
        fragmets.put(1, fragment02);
        fragmets.put(2, fragment03);
        fragmets.put(3, fragment04);*/
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
                list.add(MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PENDING));
                list.add(MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PROGRESSING));
                list.add(MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_PROGRESSED));
                list.add( MyRepairsFragment.newInstance(StrConstant.MY_REPAIRS_END));
                adapter.setPagerItems(list);
            }
        });
        viewpager_repairs.setAdapter(adapter);
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
            viewpager_repairs.setCurrentItem(3);
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
        return getString(R.string.my_repairs);
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
                Intent intent=new Intent(MyRepairsActivity.this,MyRepairsAddActivity.class);
                startActivityForResult(intent, 3000);
                AnimUtil.intentSlidIn(MyRepairsActivity.this);


            }
        });
        return true;
    }
    public JasonViewPagerAdapter getAdapter(){
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(3000==requestCode && RESULT_OK==resultCode){
            adapter.reLoad();
        }

        }

}

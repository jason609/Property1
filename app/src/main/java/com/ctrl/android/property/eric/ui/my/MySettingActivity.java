package com.ctrl.android.property.eric.ui.my;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 我的设置 页面
 * Created by Eric on 2015/11/17.
 */
public class MySettingActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.set_name_change_btn)//用户名修改
    RelativeLayout set_name_change_btn;
    @InjectView(R.id.set_mobile_change_btn)//手机号修改
    RelativeLayout set_mobile_change_btn;
    @InjectView(R.id.set_password_change_btn)//密码修改
    RelativeLayout set_password_change_btn;
    @InjectView(R.id.logout_btn)//退出登录
    TextView logout_btn;

    private String TITLE = StrConstant.SETTING_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.my_setting_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        set_name_change_btn.setOnClickListener(this);
        set_mobile_change_btn.setOnClickListener(this);
        set_password_change_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
    }

    @Override
    public void onClick(View v) {

        if(v == set_name_change_btn){
            //MessageUtils.showShortToast(this,"用户名修改");
            Intent intent = new Intent(MySettingActivity.this, ChangeUsernameActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MySettingActivity.this);
        }

        if(v == set_mobile_change_btn){
            //MessageUtils.showShortToast(this,"手机号修改");
            Intent intent = new Intent(MySettingActivity.this, ChangeMobileActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MySettingActivity.this);
        }

        if(v == set_password_change_btn){
            //MessageUtils.showShortToast(this,"密码修改");
            Intent intent = new Intent(MySettingActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(MySettingActivity.this);
        }

        if(v == logout_btn){
            MyApplication.getInstance().exit();
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
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }

}

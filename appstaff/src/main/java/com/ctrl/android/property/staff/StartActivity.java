package com.ctrl.android.property.staff;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.base.AppToolBarActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 开始页面 activity
 * Created by Eric on 2015/11/23.
 * */
public class StartActivity extends AppToolBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    class splashhandler implements Runnable{

        public void run() {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(StartActivity.this);
            finish();
        }

    }



}

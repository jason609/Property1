package com.ctrl.android.property.staff.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.MainActivity;
/**
 * 当前项目 所有activity的基类
 * Created by Eric on 2015/9/15.
 */
public class AppToolBarActivity extends ToolBarActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*禁止横屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
    }

    /**
     * 跳到当前App的主页
     * 使用时再修改相关部分代码
     * */
    protected void toHomePage(){
        Intent intent = new Intent(AppToolBarActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        AnimUtil.intentSlidOut(AppToolBarActivity.this);
    }

}

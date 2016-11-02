package com.ctrl.android.property.eric.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;

/**
 * 关于页面的工具类
 * Created by Eric on 2015/10/13.
 */
public class UiUtil {

    /**
     * 点击某个textView按钮时会跳到某个activity中
     * @param textBtn 被点击的textView
     * @param fromActivity 当前的activity上下文语境
     * @param toActivityClazz 跳转到的activity
     * */
    public static void clickToActivity(final TextView textBtn,final Context fromActivity,final Class toActivityClazz){

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(fromActivity,textBtn.getText().toString());
                Intent intent = new Intent(fromActivity, toActivityClazz);
                fromActivity.startActivity(intent);
                AnimUtil.intentSlidIn((Activity)fromActivity);
            }
        });

    }

    /**
     * 点击某个textView按钮时会跳到某个activity中(带有一个参数)
     * @param textBtn 被点击的textView
     * @param arg 传递的参数
     * @param fromActivity 当前的activity上下文语境
     * @param toActivityClazz 跳转到的activity
     * */
    public static void clickToActivityWithArg(final TextView textBtn,final String arg, final Context fromActivity,final Class toActivityClazz){

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtils.showShortToast(fromActivity, textBtn.getText().toString());
                Intent intent = new Intent(fromActivity, toActivityClazz);
                intent.putExtra("arg",arg);
                fromActivity.startActivity(intent);
                AnimUtil.intentSlidIn((Activity)fromActivity);
            }
        });

    }

    /**
     * 点击某个textView按钮时会跳到某个activity中(带有一个参数)
     * @param layout 被点击的RelativeLayout
     * @param arg 传递的参数
     * @param fromActivity 当前的activity上下文语境
     * @param toActivityClazz 跳转到的activity
     * */
    public static void clickToActivityWithArg(final RelativeLayout layout,final String arg, final Context fromActivity,final Class toActivityClazz){

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fromActivity, toActivityClazz);
                intent.putExtra("arg",arg);
                fromActivity.startActivity(intent);
                AnimUtil.intentSlidIn((Activity)fromActivity);
            }
        });

    }

    /**
     * 点击某个LinearLayout按钮时会跳到某个activity中
     * @param layout 被点击的LinearLayout
     * @param fromActivity 当前的activity上下文语境
     * @param toActivityClazz 跳转到的activity
     * */
    public static void clickToActivity(final LinearLayout layout,final Context fromActivity,final Class toActivityClazz){

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(fromActivity, layout.getText().toString());
                Intent intent = new Intent(fromActivity, toActivityClazz);
                fromActivity.startActivity(intent);
                AnimUtil.intentSlidIn((Activity)fromActivity);
            }
        });

    }

    /**
     * 点击某个RelativeLayout按钮时会跳到某个activity中
     * @param layout 被点击的RelativeLayout
     * @param fromActivity 当前的activity上下文语境
     * @param toActivityClazz 跳转到的activity
     * */
    public static void clickToActivity(final RelativeLayout layout,final Context fromActivity,final Class toActivityClazz){

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(fromActivity, layout.getText().toString());
                Intent intent = new Intent(fromActivity, toActivityClazz);
                fromActivity.startActivity(intent);
                AnimUtil.intentSlidIn((Activity) fromActivity);
                //((Activity) fromActivity).finish();
            }
        });

    }

}

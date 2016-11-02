package com.ctrl.android.property.eric.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

/**
 * 与控件相关 的工具类
 * Created by Eric on 2015/9/25.
 */
public class ViewUtil {

    /**
     * 在一个listCheckBox, 有且只有一个CheckBox被选中
     * @param listCheckBox 全部的CheckBox
     * @param checkBox 选中的CheckBox
     * */
    public static void setOneChecked(List<CheckBox> listCheckBox, CheckBox checkBox){
        if(null != listCheckBox){
            if(null != checkBox){
                for(CheckBox c : listCheckBox){
                    if(null != c){
                        if(c == checkBox){
                            c.setChecked(true);
                        } else {
                            c.setChecked(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * 在一个listradio, 有且只有一个radio被选中
     * @param listRadio 全部的radio
     * @param radio 选中的radio
     * */
    public static void setOneChecked(List<RadioButton> listRadio, RadioButton radio){
        if(null != listRadio){
            if(null != radio){
                for(RadioButton c : listRadio){
                    if(null != c){
                        if(c == radio){
                            c.setChecked(true);
                        } else {
                            c.setChecked(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * 搜索框的 配置
     * @param search_del_btn 右侧的删除
     * @param search_text 中间的要搜索的文本
     * */
    public static void settingSearch(final ImageView search_del_btn,final TextView search_text){
        search_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text.setText("");
            }
        });
        search_text.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    search_del_btn.setVisibility(View.GONE);
                } else {
                    search_del_btn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 商品详细页面图片  指示器的配置
     * @param indicator 需要配置的指示器
     * @param viewpager 指示器需要配置在哪个viewpager中
     * */
    public static void settingIndicator(CirclePageIndicator indicator,ViewPager viewpager){
        indicator.setViewPager(viewpager);
        indicator.setRadius(15);
        indicator.setPageColor(0xffcccccc);
        indicator.setFillColor(0xff4ab9e7);
    }

    /**
     * 列表listview的布局配置
     * @param listView 需要配置的listview
     * */
    public static void settingListView(ListView listView){
        listView.setDivider(null);
        listView.setDividerHeight(20);
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context,View view){
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

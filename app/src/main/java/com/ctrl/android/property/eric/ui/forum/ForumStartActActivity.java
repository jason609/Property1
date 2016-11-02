package com.ctrl.android.property.eric.ui.forum;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.ui.widget.TimePicker;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.eric.util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛 发布活动 页面
 * Created by Eric on 2015/10/26.
 */
public class ForumStartActActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.activity_layout)//
    RelativeLayout activity_layout;

    @InjectView(R.id.act_title_text)//标题
    EditText act_title_text;
    @InjectView(R.id.act_start_time_text)//起始时间
    EditText act_start_time_text;
    @InjectView(R.id.act_end_time_text)//结束时间
    EditText act_end_time_text;
    @InjectView(R.id.act_address_text)//活动地点
    EditText act_address_text;

    private View mMenuView;
    private TimePicker timePicker;
    private Button superman_ok_btn;
    private Button superman_cancel_btn;

    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String time_str;

    private String TITLE = StrConstant.START_ACT_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_start_act_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        //act_title_text.setOnClickListener(this);
        act_start_time_text.setOnClickListener(this);
        act_end_time_text.setOnClickListener(this);
        //act_address_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == act_start_time_text){
            ViewUtil.hideInput(this,act_start_time_text);
            showTimePickerPop(act_start_time_text);
        }

        if(v == act_end_time_text){
            ViewUtil.hideInput(this,act_start_time_text);
            showTimePickerPop(act_end_time_text);
        }
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            MessageUtils.showShortToast(this, "请求成功");
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

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(R.string.next_step);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkInput()){
                    MessageUtils.showShortToast(ForumStartActActivity.this,"下一步");
                    Intent intent = new Intent(ForumStartActActivity.this, ForumStartActActivity2.class);
                    intent.putExtra("title",act_title_text.getText().toString());
                    intent.putExtra("startTime",act_start_time_text.getText().toString() + ":00");
                    intent.putExtra("endTime",act_end_time_text.getText().toString() + ":00");
                    intent.putExtra("location",act_address_text.getText().toString());
                    startActivity(intent);
                    AnimUtil.intentSlidIn(ForumStartActActivity.this);
                }

            }
        });
        return true;
    }


    private void showTimePickerPop(final EditText editText){
        //setRoomData();
        mMenuView = LayoutInflater.from(ForumStartActActivity.this).inflate(R.layout.choose_time_bottom_pop, null);
        timePicker = (TimePicker)mMenuView.findViewById(R.id.timePicker);
        superman_ok_btn = (Button)mMenuView.findViewById(R.id.superman_ok_btn);
        superman_cancel_btn = (Button)mMenuView.findViewById(R.id.superman_cancel_btn);
        final PopupWindow Pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        Pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        Pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        Pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        Pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        Pop.setBackgroundDrawable(dw);


        superman_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = timePicker.getYear();
                month = timePicker.getMonth();
                day = timePicker.getDay();
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                time_str = timePicker.getTime_string();
                Log.d("demo", "time_str: " + time_str);
                editText.setText(time_str);
                Pop.dismiss();
            }
        });

        superman_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        Pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        Pop.setFocusable(true);
        Pop.showAtLocation(activity_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private boolean checkInput(){

        if(act_title_text.getText().toString() == null || act_title_text.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"标题不可为空");
            return false;
        }

        if(act_start_time_text.getText().toString() == null || act_start_time_text.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"开始时间不可为空");
            return false;
        }

        if(act_end_time_text.getText().toString() == null || act_end_time_text.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"结束时间不可为空");
            return false;
        }

        if(act_address_text.getText().toString() == null || act_address_text.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"活动地点不可为空");
            return false;
        }

        return true;
    }

}

package com.ctrl.android.property.eric.ui.my;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.CodeDao;
import com.ctrl.android.property.eric.dao.LoginDao;
import com.ctrl.android.property.eric.ui.widget.AlertDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 找回密码 activity
 * Created by Eric on 2015/11/3
 */
public class FindPasswordActivity extends AppToolBarActivity implements View.OnClickListener{

//    @InjectView(R.id.username)//用户名
//    EditText username;
    @InjectView(R.id.mobile)//手机号
    EditText mobile;
    @InjectView(R.id.vertify_code)//验证码
    EditText vertify_code;
    @InjectView(R.id.get_vertify_code_btn)//获取验证码
    TextView get_vertify_code_btn;
    @InjectView(R.id.new_password)//新密码
    EditText new_password;
    @InjectView(R.id.comfirm_password)//密码确认
    EditText comfirm_password;

    @InjectView(R.id.submit_btn)//提交按钮
    Button submit_btn;

    private CodeDao codeDao;
    private LoginDao loginDao;

    private String TITLE = StrConstant.FIND_PASSWORD_TITLE;
    private String code;


    //定时周期执行制定的任务
    private ScheduledExecutorService scheduledExecutorService;
    private final int TIME_START = 0;
    private final int TIME_GOING = 1;
    private final int TIME_END = 2;
    private final int TIME_COUNT = 60;
    private int timeCount = TIME_COUNT;

    private String GET_CODE = "获取验证码";
    private String REGET_AFTER_SECONDS = "秒";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.find_password_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        get_vertify_code_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(requestCode == 0) {
            code = codeDao.getCode();
            MessageUtils.showShortToast(this, "成功发送验证码");
            //Log.d("demo","code: " + code);
        }

        if(requestCode == 1) {

            showAlertDialog();

            //code = codeDao.getCode();
//            MessageUtils.showShortToast(this, "修改成功");
//            username.setEnabled(false);
//            mobile.setEnabled(false);
//            vertify_code.setEnabled(false);
//            get_vertify_code_btn.setEnabled(false);
//            get_vertify_code_btn.setClickable(false);
//            get_vertify_code_btn.setTextColor(getResources().getColor(R.color.text_gray));
//            new_password.setEnabled(false);
//            comfirm_password.setEnabled(false);
//            submit_btn.setClickable(false);
//            submit_btn.setBackgroundResource(R.drawable.gray_bg_shap);
//            alert_layout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == get_vertify_code_btn){
            if(mobile.getText().toString() == null || mobile.getText().toString().equals("")){
                MessageUtils.showShortToast(this,"手机号不可为空");
            } else if(mobile.getText().toString().length() != 11){
                MessageUtils.showShortToast(this,"手机号格式不正确");
            } else {
                codeDao = new CodeDao(this);
                showProgress(true);
                codeDao.requestSMSCode(mobile.getText().toString());
                get_vertify_code_btn.setTextColor(getResources().getColor(R.color.text_gray));
                get_vertify_code_btn.setEnabled(false);

                mHandler.sendEmptyMessage(TIME_START);
            }
        }

        if(v == submit_btn){
            if(checkInput()){
                loginDao = new LoginDao(this);
                String memberId = "";
                String userName = mobile.getText().toString();
                //String userName = username.getText().toString();
                String oldPassword = "";
                String password = new_password.getText().toString();
                String obtainType = "1";
                showProgress(true);
                loginDao.requestChangePassword(memberId,userName,oldPassword,password,obtainType);
            }
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
        return TITLE;
    }

    /**
     * 检查输入内容格式
     * */
    private boolean checkInput(){

//        if(S.isNull(username.getText().toString())){
//            MessageUtils.showShortToast(this,"请输入用户名");
//            return false;
//        }

        if(S.isNull(mobile.getText().toString())){
            MessageUtils.showShortToast(this,"请输入手机号");
            return false;
        }

        if(S.isNull(vertify_code.getText().toString())){
            MessageUtils.showShortToast(this,"请输入验证码");
            return false;
        }

        if(!((vertify_code.getText().toString()).equals(code))){
            MessageUtils.showShortToast(this,"验证码错误");
            return false;
        }

        if(S.isNull(new_password.getText().toString())){
            MessageUtils.showShortToast(this,"请输入新密码");
            return false;
        }

        if(S.isNull(comfirm_password.getText().toString())){
            MessageUtils.showShortToast(this,"请第二次输入密码");
            return false;
        }

        if(!((new_password.getText().toString()).equals(comfirm_password.getText().toString()))){
            MessageUtils.showShortToast(this,"二次输入密码不一致");
            return false;
        }

        return true;
    }

    /**
     * 弹出自定义弹出框
     * */
    public void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancleFlg(false);
        builder.setMessage("恭喜您修改密码成功");
        builder.setReturnButton("返回",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //如果msg传值为0
            if(msg.what == TIME_START){
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();//线程调度
                scheduledExecutorService.scheduleAtFixedRate(new TimeTask(),
                        1,//初始化延迟
                        1,//两次开始执行最小间隔时间
                        TimeUnit.SECONDS);
                get_vertify_code_btn.setText(timeCount + REGET_AFTER_SECONDS);
            }
            //如果msg传值为1
            if(msg.what == TIME_GOING){
                get_vertify_code_btn.setText(timeCount + REGET_AFTER_SECONDS);
            }
            //如果msg传值为2
            if(msg.what == TIME_END){
                get_vertify_code_btn.setText(GET_CODE);
                get_vertify_code_btn.setEnabled(true);
                timeCount = TIME_COUNT;
                if(scheduledExecutorService != null &&
                        !scheduledExecutorService.isShutdown()){
                    scheduledExecutorService.shutdown();
                }
            }
        }
    };

    private class TimeTask implements Runnable{
        public void run(){
            timeCount --;
            if(timeCount > 0){
                mHandler.sendEmptyMessage(TIME_GOING);
            } else {
                mHandler.sendEmptyMessage(TIME_END);
            }
        }
    }

}

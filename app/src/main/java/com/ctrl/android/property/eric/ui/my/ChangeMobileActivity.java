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
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.CodeDao;
import com.ctrl.android.property.eric.dao.MemberDao;
import com.ctrl.android.property.eric.ui.widget.AlertDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 修改手机号 activity
 * Created by Eric on 2015/11/3
 */
public class ChangeMobileActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.old_mobile)//旧手机号
    EditText old_mobile;
    @InjectView(R.id.new_mobile)//新手机号
    EditText new_mobile;
    @InjectView(R.id.vertify_code)//验证码
    EditText vertify_code;
    @InjectView(R.id.get_vertify_code_btn)//获取验证码
    TextView get_vertify_code_btn;

    @InjectView(R.id.submit_btn)//提交按钮
    Button submit_btn;

    private CodeDao codeDao;
    private MemberDao memberDao;

    private String TITLE = StrConstant.MODIFY_MOBILE_TITLE;
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
        setContentView(R.layout.change_mobile_activity);
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
        }
    }


    @Override
    public void onClick(View v) {
        if(v == get_vertify_code_btn){
            if(new_mobile.getText().toString() == null || new_mobile.getText().toString().equals("")){
                MessageUtils.showShortToast(this,"新手机号不可为空");
            } else if(new_mobile.getText().toString().length() != 11){
                MessageUtils.showShortToast(this,"新手机号格式不正确");
            } else {
                codeDao = new CodeDao(this);
                showProgress(true);

                codeDao.requestSMSCode(new_mobile.getText().toString());
                get_vertify_code_btn.setTextColor(getResources().getColor(R.color.text_gray));
                get_vertify_code_btn.setEnabled(false);

                mHandler.sendEmptyMessage(TIME_START);
            }
        }

        if(v == submit_btn){
            if(checkInput()){

                if(code.equals(vertify_code.getText().toString())){
                    memberDao = new MemberDao(this);
                    String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                    String oldMobile = old_mobile.getText().toString();
                    String mobile = new_mobile.getText().toString();
                    String nickName = "";
                    showProgress(true);
                    memberDao.requestModifyMemberInfo(memberId,oldMobile,mobile,nickName);
                } else {
                    MessageUtils.showShortToast(this,"验证码输入错误");
                }


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

        if(S.isNull(old_mobile.getText().toString())){
            MessageUtils.showShortToast(this,"请输入旧手机号");
            return false;
        }

        if(old_mobile.getText().toString().length() != 11){
            MessageUtils.showShortToast(this,"旧手机号格式不正确");
            return false;
        }

        if(S.isNull(new_mobile.getText().toString())){
            MessageUtils.showShortToast(this,"请输入手机号");
            return false;
        }

        if(new_mobile.getText().toString().length() != 11){
            MessageUtils.showShortToast(this,"新手机号格式不正确");
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

        return true;
    }

    /**
     * 弹出自定义弹出框
     * */
    public void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancleFlg(false);
        builder.setMessage("恭喜您修改手机号成功");
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

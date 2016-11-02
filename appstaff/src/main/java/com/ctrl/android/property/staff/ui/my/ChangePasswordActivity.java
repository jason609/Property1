package com.ctrl.android.property.staff.ui.my;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.base.MyApplication;
import com.ctrl.android.property.staff.dao.LoginDao;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.ui.widget.AlertDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 修改密码 activity
 * Created by Eric on 2015/11/17
 */
public class ChangePasswordActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.old_password)//旧密码
    EditText old_password;
    @InjectView(R.id.new_password)//新密码
    EditText new_password;
    @InjectView(R.id.comfirm_password)//密码确认
    EditText comfirm_password;

    @InjectView(R.id.submit_btn)//提交按钮
    Button submit_btn;

    private LoginDao loginDao;

    private String TITLE = "修改密码";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.change_password_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        submit_btn.setOnClickListener(this);
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(requestCode == 1) {

            Arad.preferences.putString(Constant.PASSWORD, new_password.getText().toString());
            Arad.preferences.flush();

            showAlertDialog();
        }
    }


    @Override
    public void onClick(View v) {

        if(v == submit_btn){
            if(checkInput()){
                loginDao = new LoginDao(this);
                String memberId = AppHolder.getInstance().getStaffInfo().getStaffId();
                String oldPassword = old_password.getText().toString();
                String password = new_password.getText().toString();
                showProgress(true);
                loginDao.requestChangePassword(memberId,oldPassword,password);
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


        if(S.isNull(old_password.getText().toString())){
            MessageUtils.showShortToast(this,"请输入旧密码");
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
        builder.setReturnButton("返回", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }

}

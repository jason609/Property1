package com.ctrl.android.property.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.Constant;
import com.ctrl.android.property.staff.dao.LoginDao;
import com.ctrl.android.property.staff.entity.StaffInfo;
import com.ctrl.android.property.staff.util.S;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 登录 activity
 * Created by Eric on 2015/11/23.
 * */
public class LoginActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.username_text)//用户名
    EditText username_text;
    @InjectView(R.id.password_text)//密码
    EditText password_text;
    @InjectView(R.id.login_btn)//登录按钮
    TextView login_btn;

    private LoginDao loginDao;
    private StaffInfo staffInfo;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化方法
     * */
    private void init(){
        login_btn.setOnClickListener(this);

        String username = Arad.preferences.getString(Constant.USERNAME);
        String password = Arad.preferences.getString(Constant.PASSWORD);
        if(!S.isNull(username)){
            username_text.setText(username);
        }
        if(!S.isNull(password)){
            password_text.setText(password);
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(3==requestCode){
            AppHolder.getInstance().setVersionInfo(loginDao.getApkInfo());
        }
        if(0 == requestCode){
            loginDao.updateApkVersions("2","0","","");
            //MessageUtils.showShortToast(this,"登陆成功");
            Arad.preferences.putString(Constant.USERNAME, username);
            Arad.preferences.putString(Constant.PASSWORD, password);
            Arad.preferences.putString("grade",loginDao.getStaffInfo().getGrade()+"");
            Arad.preferences.flush();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(LoginActivity.this);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if(v == login_btn){
            if(checkInput()){
                username =  username_text.getText().toString();
                password =  password_text.getText().toString();
                loginDao = new LoginDao(this);
                showProgress(true);
                loginDao.requestLogin(username, password);
            }
        }
    }

    private boolean checkInput(){

        if(S.isNull(username_text.getText().toString())){
            MessageUtils.showShortToast(this, "用户名不可为空");
            return false;
        }

        if(S.isNull(password_text.getText().toString())){
            MessageUtils.showShortToast(this, "密码不可为空");
            return false;
        }

        return true;
    }


}

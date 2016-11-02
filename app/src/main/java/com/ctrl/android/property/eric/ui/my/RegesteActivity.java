package com.ctrl.android.property.eric.ui.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.RegisteDao;
import com.ctrl.android.property.eric.ui.widget.AlertDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 用户注册 activity
 * Created by Eric on 2015/10/28
 */
public class RegesteActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.username)//用户名
    EditText username;
    @InjectView(R.id.password)//密码
    EditText password;
    @InjectView(R.id.password_layout)
    RelativeLayout password_layout;
    @InjectView(R.id.checkbox)//checkbox
    CheckBox checkbox;
    @InjectView(R.id.registe_btn)//注册按钮
    Button registe_btn;

    private RegisteDao registeDao;
    private String TITLE = StrConstant.REGESTE_TITLE;

    private String username_text;
    private String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.registe_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        registe_btn.setOnClickListener(this);
        password_layout.setOnClickListener(this);
        registe_btn.setClickable(false);
        checkbox.setOnClickListener(this);

        registeDao = new RegisteDao(this);

        username.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    registe_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                    registe_btn.setClickable(false);
                } else {
                    if (!S.isNull(password.getText().toString())) {
                        registe_btn.setBackgroundResource(R.drawable.orange_bg_shap);
                        registe_btn.setClickable(true);
                    } else {
                        registe_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                        registe_btn.setClickable(false);
                    }
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    //checkbox.setChecked(false);
                    registe_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                    registe_btn.setClickable(false);
                } else {

                    //if (password.getText().toString().length() < 6) {
                    //    checkbox.setChecked(false);
                    //} else {
                    //    checkbox.setChecked(true);
                    //}

                    if (!S.isNull(username.getText().toString())) {
                        registe_btn.setBackgroundResource(R.drawable.orange_bg_shap);
                        registe_btn.setClickable(true);
                    } else {
                        registe_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                        registe_btn.setClickable(false);
                    }
                }
            }
        });

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(requestCode == 0) {
            showAlertDialog();
            //username.setEnabled(false);
            //password.setEnabled(false);
            //registe_btn.setBackgroundResource(R.drawable.gray_bg_shap);
            //registe_btn.setClickable(false);
            //alert_layout.setVisibility(View.VISIBLE);
            //MessageUtils.showShortToast(this,"注册成功");
        }
    }


    @Override
    public void onClick(View v) {
        if(v == password_layout){
            password.setFocusable(true);
            password.setFocusableInTouchMode(true);
            password.requestFocus();
            InputMethodManager imm = (InputMethodManager) password.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }

        if(v == registe_btn){
            if(checkInput()){
                username_text = username.getText().toString();
                password_text = password.getText().toString();
                showProgress(true);
                registeDao.requestRegiste(username_text,password_text);
                //alert_layout.setVisibility(View.VISIBLE);
            }
        }

        if(v == checkbox){
            if(checkbox.isChecked()){
                // 显示密码
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // 隐藏密码
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        if(S.isNull(username.getText().toString())){
            MessageUtils.showShortToast(this,"邮箱或手机号不可为空");
            return false;
        }
        if(S.isNull(password.getText().toString())){
            MessageUtils.showShortToast(this,"密码不可为空");
            return false;
        }

        if(S.getStr(password.getText().toString()).length() > 20 || S.getStr(password.getText().toString()).length() < 6){
            MessageUtils.showShortToast(this,"请设置6-20位登录密码");
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
        builder.setMessage("恭喜您注册成功");
        builder.setReturnButton("返回",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }

}

package com.ctrl.android.property;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.InitDao;
import com.ctrl.android.property.eric.dao.LoginDao;
import com.ctrl.android.property.eric.dao.ProprietorDao;
import com.ctrl.android.property.eric.ui.my.FindPasswordActivity;
import com.ctrl.android.property.eric.ui.my.RegesteActivity;
import com.ctrl.android.property.eric.ui.widget.CustomDialog;
import com.ctrl.android.property.eric.util.S;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 登录页面
 * Created by Eric on 2015/10/12.
 */
public class LoginActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.username_text)//用户名
    EditText username_text;
    @InjectView(R.id.password_text)//密码
    EditText password_text;
    @InjectView(R.id.login_btn)//登录按钮
    Button login_btn;
    @InjectView(R.id.forget_password_btn)//忘记密码按钮
    TextView forget_password_btn;
    @InjectView(R.id.regest_btn)//注册按钮
    TextView regest_btn;
    @InjectView(R.id.visiter_btn)//游客按钮
    TextView visiter_btn;

    private String username;
    private String password;

    private LoginDao loginDao;
    private InitDao initDao;
    private ProprietorDao proprietorDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        String username = Arad.preferences.getString(Constant.USERNAME);
        String password = Arad.preferences.getString(Constant.PASSWORD);
        if(!S.isNull(username)){
            username_text.setText(username);
        }
        if(!S.isNull(password)){
            password_text.setText(password);
        }
        login_btn.setOnClickListener(this);
        forget_password_btn.setOnClickListener(this);
        regest_btn.setOnClickListener(this);
        visiter_btn.setOnClickListener(this);
    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(2==requestCode){
            AppHolder.getInstance().setVersionInfo(loginDao.getApkInfo());
        }
        if(0 == requestCode){
            loginDao.updateApkVersions("1","0","","");
            //游客标识 0:正常用户; 1:游客
            AppHolder.getInstance().setVisiterFlg(0);

            AppHolder.getInstance().setMemberInfo(loginDao.getMemberInfo());
            AppHolder.getInstance().setListReceiveAddress(loginDao.getListReceiveAddress());

            Arad.preferences.putString(Constant.USERNAME, username);
            Arad.preferences.putString(Constant.PASSWORD, password);
            Arad.preferences.flush();

            String provinceName = AppHolder.getInstance().getBdLocation().getProvince();
            String cityName = AppHolder.getInstance().getBdLocation().getCity();
            String areaName = AppHolder.getInstance().getBdLocation().getDistrict();
            String longitude = String.valueOf(AppHolder.getInstance().getBdLocation().getLongitude());
            String latitude = String.valueOf(AppHolder.getInstance().getBdLocation().getLatitude());

            Log.d("demo", "经度Longitude: " + longitude);
            Log.d("demo", "纬度Latitude: " + latitude);
            Log.d("demo", "省: " + provinceName);
            Log.d("demo", "市: " + cityName);
            Log.d("demo", "区: " + areaName);

            initDao = new InitDao(this);
            showProgress(true);
            initDao.requestInit(AppHolder.getInstance().getMemberInfo().getMemberId(),provinceName, cityName, areaName, longitude, latitude);

            //proprietorDao = new ProprietorDao(this);
            //showProgress(true);
            //proprietorDao.requestProprietorInfo(AppHolder.getInstance().getCommunity().getId(), AppHolder.getInstance().getMemberInfo().getMemberId());

        }

        if(109 == requestCode){

            proprietorDao = new ProprietorDao(this);
            showProgress(true);
            proprietorDao.requestProprietorInfo(AppHolder.getInstance().getCommunity().getId(), AppHolder.getInstance().getMemberInfo().getMemberId());

            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //startActivity(intent);
            //AnimUtil.intentSlidIn(LoginActivity.this);
            //finish();
        }

        if(110 == requestCode){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(LoginActivity.this);

            finish();

            //proprietorDao = new ProprietorDao(this);
            //showProgress(true);
            //proprietorDao.requestProprietorInfo(AppHolder.getInstance().getCommunity().getId(), AppHolder.getInstance().getMemberInfo().getMemberId());

            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //startActivity(intent);
            //AnimUtil.intentSlidIn(LoginActivity.this);
            //finish();
        }

        if(99 == requestCode){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(LoginActivity.this);

            finish();

//            proprietorDao = new ProprietorDao(this);
//            showProgress(true);
//            String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
//            String currentPage = "";
//            String rowCountPerPage = "";
//            proprietorDao.requestHouseList(memberId, currentPage, rowCountPerPage);

        }

//        if(98 == requestCode){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            AnimUtil.intentSlidIn(LoginActivity.this);
//            finish();
//        }

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {

        showProgress(false);
        if(errorNo.equals("018")){
            MessageUtils.showShortToast(this,"用户名/密码错误");
        }else if(errorNo.equals("009")){

            //MessageUtils.showShortToast(LoginActivity.this, "未找到业主信息,请添加房屋");

            final CustomDialog.Builder builder = new CustomDialog.Builder(LoginActivity.this);
            builder.setTitle("提示");
            builder.setMessage("未找到业主信息,是否继续使用");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    AnimUtil.intentSlidIn(LoginActivity.this);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();


        }else { super.onRequestFaild(errorNo, errorMessage);}


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

        if(v == forget_password_btn){
            //MessageUtils.showShortToast(this, "忘记密码");
            Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(LoginActivity.this);
        }

        if(v == regest_btn){
            //MessageUtils.showShortToast(this,"注册");
            Intent intent = new Intent(LoginActivity.this, RegesteActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(LoginActivity.this);
        }

        if(v == visiter_btn){
            //MessageUtils.showShortToast(this,"游客");

            //游客标识 0:正常用户; 1:游客
            AppHolder.getInstance().setVisiterFlg(1);

            String provinceName = AppHolder.getInstance().getBdLocation().getProvince();
            String cityName = AppHolder.getInstance().getBdLocation().getCity();
            String areaName = AppHolder.getInstance().getBdLocation().getDistrict();
            String longitude = String.valueOf(AppHolder.getInstance().getBdLocation().getLongitude());
            String latitude = String.valueOf(AppHolder.getInstance().getBdLocation().getLatitude());

            Log.d("demo", "经度Longitude: " + longitude);
            Log.d("demo", "纬度Latitude: " + latitude);
            Log.d("demo", "省: " + provinceName);
            Log.d("demo", "市: " + cityName);
            Log.d("demo", "区: " + areaName);

            initDao = new InitDao(this);
            showProgress(true);
            initDao.requestInit2("", provinceName, cityName, areaName, longitude, latitude);
        }
    }

    private boolean checkInput(){

        if(S.isNull(username_text.getText().toString())){
            MessageUtils.showShortToast(this,"用户名不可为空");
            return false;
        }

        if(S.isNull(password_text.getText().toString())){
            MessageUtils.showShortToast(this,"密码不可为空");
            return false;
        }

        return true;
    }


}

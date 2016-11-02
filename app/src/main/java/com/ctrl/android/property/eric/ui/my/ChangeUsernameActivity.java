package com.ctrl.android.property.eric.ui.my;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.MyApplication;
import com.ctrl.android.property.eric.dao.MemberDao;
import com.ctrl.android.property.eric.ui.widget.AlertDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 修改用户昵称 activity
 * Created by Eric on 2015/11/3
 */
public class ChangeUsernameActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.username_text)//用户名
    EditText username_text;

    @InjectView(R.id.submit_btn)//提交按钮
    Button submit_btn;

    private MemberDao memberDao;

    private String TITLE = StrConstant.MODIFY_USERNAME_TITLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.change_username_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        submit_btn.setOnClickListener(this);
        username_text.setText(AppHolder.getInstance().getMemberInfo().getNickName());

    }

    /**
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(requestCode == 1) {
            showAlertDialog();
            AppHolder.getInstance().getMemberInfo().setNickName(username_text.getText().toString());
        }
    }


    @Override
    public void onClick(View v) {

        if(v == submit_btn){

            if(checkInput()){
                memberDao = new MemberDao(this);
                String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                String oldMobile = "";
                String mobile = "";
                String nickName = username_text.getText().toString();
                showProgress(true);
                memberDao.requestModifyMemberInfo(memberId,oldMobile,mobile,nickName);
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

        if(S.isNull(username_text.getText().toString())){
            MessageUtils.showShortToast(this,"请输入用户名");
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
        builder.setMessage("恭喜您修改用户名成功");
        builder.setReturnButton("返回",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }

}

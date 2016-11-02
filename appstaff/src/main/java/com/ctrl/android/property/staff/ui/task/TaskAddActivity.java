package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.TaskDao;
import com.ctrl.android.property.staff.ui.contact.ContactActivity;
import com.ctrl.android.property.staff.util.S;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 任务指派 activity
 * Created by Eric on 2015/10/22
 */
public class TaskAddActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.task_name_text)//
    EditText task_name_text;
    @InjectView(R.id.task_man_text)//
    EditText task_man_text;
    @InjectView(R.id.task_detail_text)//
    EditText task_detail_text;
    @InjectView(R.id.task_send_btn)//
    TextView task_send_btn;

    @InjectView(R.id.add_btn)//进入通讯录
    ImageView add_btn;

    private String TITLE = "任务指派";
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.task_add_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        task_send_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);

        taskDao = new TaskDao(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppHolder.getInstance().getContactor() == null){
            task_man_text.setText("");
        } else {
            task_man_text.setText(AppHolder.getInstance().getContactor().getName());
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(1 == requestCode){
            MessageUtils.showShortToast(this,"添加成功");
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if(v == task_send_btn){

            if(checkInput()){
                //MessageUtils.showShortToast(this,"下达");
                String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();
                String staffId = AppHolder.getInstance().getContactor().getId();
                String taskName = task_name_text.getText().toString();
                String content = task_detail_text.getText().toString();
                String adminId = AppHolder.getInstance().getStaffInfo().getStaffId();
                showProgress(true);
                taskDao.requestAddTask(communityId,staffId,taskName,content,adminId);
            }

        }

        if(v == add_btn){
            //MessageUtils.showShortToast(this,"添加");
            Intent intent = new Intent(TaskAddActivity.this, ContactActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(TaskAddActivity.this);
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

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtils.showShortToast(TaskAddActivity.this, "完成");
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

    private boolean checkInput(){

        if(S.isNull(task_name_text.getText().toString())){
            MessageUtils.showShortToast(this,"任务名称不可为空");
            return false;
        }

        if(S.isNull(task_man_text.getText().toString())){
            MessageUtils.showShortToast(this,"任务执行人不可为空");
            return false;
        }

        if(S.isNull(task_detail_text.getText().toString())){
            MessageUtils.showShortToast(this,"任务内容不可为空");
            return false;
        }

        return true;
    }



}

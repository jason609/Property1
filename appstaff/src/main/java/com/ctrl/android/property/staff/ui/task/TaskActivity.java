package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 任务指派 activity
 * Created by Eric on 2015/10/22
 */
public class TaskActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.tv_repair)
    TextView tv_repair;
    @InjectView(R.id.tv_complaint)
    TextView tv_complaint;
    @InjectView(R.id.tv_task)
    TextView tv_task;

    private static final String TITLE ="任务指派" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.task_main_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        tv_complaint.setOnClickListener(this);
        tv_repair.setOnClickListener(this);
        tv_task.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
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


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.tv_repair:
                intent = new Intent(TaskActivity.this, RepairAppointActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(TaskActivity.this);
                break;
            case R.id.tv_complaint:
                intent = new Intent(TaskActivity.this, ComplaintAppointActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(TaskActivity.this);
                break;
            case R.id.tv_task:
                intent = new Intent(TaskActivity.this, TaskListActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(TaskActivity.this);
                break;
        }

    }
}

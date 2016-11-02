package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.TaskDao;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.entity.TaskDetail;
import com.ctrl.android.property.staff.ui.contact.ContactActivity;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 任务指派 activity
 * Created by Eric on 2015/10/22
 */
public class TaskDetailActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.task_name)//任务名称
    TextView task_name;
    @InjectView(R.id.task_man)//下达人
    TextView task_man;
    @InjectView(R.id.task_time)//下达时间
    TextView task_time;
    @InjectView(R.id.task_status)//任务状态
    TextView task_status;
    @InjectView(R.id.task_detail_content1)//任务详细
    TextView task_detail_content1;

    //@InjectView(R.id.task_return_time)//反馈时间
    //TextView task_return_time;
    //@InjectView(R.id.task_detail_content2)//任务反馈详细
    //TextView task_detail_content2;

    @InjectView(R.id.basic_info_layout1)
    LinearLayout basic_info_layout1;
    //@InjectView(R.id.basic_info_layout2)
    //LinearLayout basic_info_layout2;
    @InjectView(R.id.basic_info_layout3)
    LinearLayout basic_info_layout3;

    @InjectView(R.id.task_man_text)//
    EditText task_man_text;
    @InjectView(R.id.add_btn)//进入通讯录
    ImageView add_btn;

//    @InjectView(R.id.img_01)
//    ImageView img_01;
//    @InjectView(R.id.img_02)
//    ImageView img_02;
//    @InjectView(R.id.img_03)
//    ImageView img_03;

    @InjectView(R.id.add_task_btn)//
    TextView add_task_btn;
    @InjectView(R.id.task_submit_btn)//
    TextView task_submit_btn;
    @InjectView(R.id.task_detail_text)
    EditText task_detail_text;

    @InjectView(R.id.basic_info_layout2)
    LinearLayout basic_info_layout2;//第一次反馈部分
    @InjectView(R.id.task_return_time)
    TextView task_return_time;//第一次反馈时间
    @InjectView(R.id.task_detail_content2)
    TextView task_detail_content2;//第一次任务反馈详细
    @InjectView(R.id.img_1)
    ImageView img_1;
    @InjectView(R.id.img_2)
    ImageView img_2;
    @InjectView(R.id.img_3)
    ImageView img_3;

    @InjectView(R.id.basic_info_layout4)
    LinearLayout basic_info_layout4;//第二次反馈部分
    @InjectView(R.id.task_return_time4)
    TextView task_return_time4;//第二次反馈时间
    @InjectView(R.id.task_detail_content4)
    TextView task_detail_content4;//第二次任务反馈详细
    @InjectView(R.id.img_001)
    ImageView img_001;
    @InjectView(R.id.img_002)
    ImageView img_002;
    @InjectView(R.id.img_003)
    ImageView img_003;

    @InjectView(R.id.basic_info_layout5)
    LinearLayout basic_info_layout5;//追加任务
    @InjectView(R.id.task_detail_content5)
    TextView task_detail_content5;//追加任务内容

    private String TITLE = "任务指派";
    private TaskDao taskDao;

    private String taskId = "";

    private TaskDetail taskDetail;

    private List<Img> listImgTask;
    private List<Img> listImgTaskAdd;

    //private List<Img> listImg;

    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.task_detail_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

//        img_01.setOnClickListener(this);
//        img_02.setOnClickListener(this);
//        img_03.setOnClickListener(this);
//
//        img_01.setVisibility(View.GONE);
//        img_02.setVisibility(View.GONE);
//        img_03.setVisibility(View.GONE);

        add_btn.setOnClickListener(this);
        add_task_btn.setOnClickListener(this);
        task_submit_btn.setOnClickListener(this);

        taskId = getIntent().getStringExtra("taskId");

        taskDao = new TaskDao(this);
        //String taskAssignmentId = "";
        String adminId = "";
        String newStaffId = "";
        String staffId = AppHolder.getInstance().getStaffInfo().getStaffId();
        showProgress(true);
        taskDao.requestTaskDetail(taskId, adminId, newStaffId, staffId);

        //task_send_btn.setOnClickListener(this);
        //taskDao = new TaskDao(this);
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

        if(2 == requestCode){
            taskDetail = taskDao.getTaskDetail();
            listImgTask = taskDao.getListImgTask();
            listImgTaskAdd = taskDao.getListImgTaskAdd();

            /**显示  任务基本信息部分*/
            task_name.setText(S.getStr(taskDetail.getTaskName()));
            task_man.setText(S.getStr(taskDetail.getName()));
            task_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm", taskDetail.getCreateTime()));
            task_status.setText(S.getStr(taskDetail.getTaskStatus() == 0 ? "执行中" : "已完成"));
            task_detail_content1.setText(S.getStr(taskDetail.getContent()));

            //task_return_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm",taskDetail.getFeedbackTime()));
            //task_detail_content2.setText(S.getStr(taskDetail.getTaskFeedback()));


            /**第一次 处理反馈部分*/
            if(!S.isNull(taskDetail.getFeedbackTime())){
                basic_info_layout2.setVisibility(View.VISIBLE);
                task_return_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm", taskDetail.getFeedbackTime()) + "  " + taskDetail.getStaffName());
                task_detail_content2.setText(S.getStr(taskDetail.getTaskFeedback()));

                if(listImgTask.size() == 1){
                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_1);
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.GONE);
                    img_3.setVisibility(View.GONE);
                }
                if(listImgTask.size() == 2){
                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_1);
                    Arad.imageLoader.load(listImgTask.get(1).getOriginalImg() == null || (listImgTask.get(1).getOriginalImg()).equals("") ? "aa" : listImgTask.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_2);
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.GONE);
                }

                if(listImgTask.size() >= 3){
                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_1);
                    Arad.imageLoader.load(listImgTask.get(1).getOriginalImg() == null || (listImgTask.get(1).getOriginalImg()).equals("") ? "aa" : listImgTask.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_2);
                    Arad.imageLoader.load(listImgTask.get(2).getOriginalImg() == null || (listImgTask.get(2).getOriginalImg()).equals("") ? "aa" : listImgTask.get(2).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_3);
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                }

            }

            /**追加部分*/
            if(!S.isNull(taskDetail.getNewTaskContent())){
                basic_info_layout5.setVisibility(View.VISIBLE);
                task_detail_content5.setText(taskDetail.getNewTaskContent());
            } else {
                basic_info_layout5.setVisibility(View.GONE);
            }


            /**第二次 处理反馈部分*/
            if(!S.isNull(taskDetail.getNewFeedbackTime())){
                task_return_time4.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm", taskDetail.getNewFeedbackTime()) + "   " + taskDetail.getNewStaffName());
                task_detail_content4.setText(S.getStr(taskDetail.getNewTaskFeedback()));

                if(listImgTaskAdd.size() == 1){
                    Arad.imageLoader.load(listImgTaskAdd.get(0).getOriginalImg() == null || (listImgTaskAdd.get(0).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_001);
                    img_001.setVisibility(View.VISIBLE);
                    img_002.setVisibility(View.GONE);
                    img_003.setVisibility(View.GONE);
                }
                if(listImgTaskAdd.size() == 2){
                    Arad.imageLoader.load(listImgTaskAdd.get(0).getOriginalImg() == null || (listImgTaskAdd.get(0).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_001);
                    Arad.imageLoader.load(listImgTaskAdd.get(1).getOriginalImg() == null || (listImgTaskAdd.get(1).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_002);
                    img_001.setVisibility(View.VISIBLE);
                    img_002.setVisibility(View.VISIBLE);
                    img_003.setVisibility(View.GONE);
                }

                if(listImgTaskAdd.size() >= 3){
                    Arad.imageLoader.load(listImgTaskAdd.get(0).getOriginalImg() == null || (listImgTaskAdd.get(0).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_001);
                    Arad.imageLoader.load(listImgTaskAdd.get(1).getOriginalImg() == null || (listImgTaskAdd.get(1).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_002);
                    Arad.imageLoader.load(listImgTaskAdd.get(2).getOriginalImg() == null || (listImgTaskAdd.get(2).getOriginalImg()).equals("") ? "aa" : listImgTaskAdd.get(2).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_003);
                    img_001.setVisibility(View.VISIBLE);
                    img_002.setVisibility(View.VISIBLE);
                    img_003.setVisibility(View.VISIBLE);
                }
                //basic_info_layout3.setVisibility(View.GONE);
                basic_info_layout4.setVisibility(View.VISIBLE);
            } else {
                basic_info_layout4.setVisibility(View.GONE);

//                /**是否 显示 添加反馈信息部分*/
//                if(S.isNull(taskDetail.getStaffId())){
//                    basic_info_layout3.setVisibility(View.GONE);
//                } else {
//                    if(S.isNull(taskDetail.getNewStaffId())){
//                        if((!S.isNull(taskDetail.getFeedbackTime()))){
//                            if(S.isNull(taskDetail.getNewTaskContent())){
//                                basic_info_layout3.setVisibility(View.GONE);
//                            } else {
//                                basic_info_layout3.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            basic_info_layout3.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        if((taskDetail.getStaffId()).equals(taskDetail.getNewStaffId())){
//                            if((!S.isNull(taskDetail.getFeedbackTime()))){
//                                if(S.isNull(taskDetail.getNewTaskContent())){
//                                    basic_info_layout3.setVisibility(View.GONE);
//                                } else {
//                                    basic_info_layout3.setVisibility(View.VISIBLE);
//                                }
//                            } else {
//                                basic_info_layout3.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            if((taskDetail.getNewStaffId()).equals(AppHolder.getInstance().getStaffInfo().getStaffId())){
//                                if((!S.isNull(taskDetail.getFeedbackTime()))){
//                                    if(S.isNull(taskDetail.getNewTaskContent())){
//                                        basic_info_layout3.setVisibility(View.GONE);
//                                    } else {
//                                        basic_info_layout3.setVisibility(View.VISIBLE);
//                                    }
//                                } else {
//                                    basic_info_layout3.setVisibility(View.VISIBLE);
//                                }
//                            } else {
//                                basic_info_layout3.setVisibility(View.GONE);
//                            }
//                        }
//                    }
//                }
            }


            //判断是否可以追加任务(0:可以,1:不可以)
            if(taskDetail.getStatus() == 0){
                if(!S.isNull(taskDetail.getFeedbackTime())){
                    add_task_btn.setEnabled(true);
                    add_task_btn.setBackgroundResource(R.drawable.orange_bg_shap);
                    //basic_info_layout2.setVisibility(View.VISIBLE);
                    basic_info_layout3.setVisibility(View.GONE);
                } else {
                    add_task_btn.setEnabled(false);
                    add_task_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                    //basic_info_layout2.setVisibility(View.GONE);
                    basic_info_layout3.setVisibility(View.GONE);
                }

            } else {
                add_task_btn.setEnabled(false);
                add_task_btn.setBackgroundResource(R.drawable.gray_bg_shap);
                //basic_info_layout2.setVisibility(View.GONE);
                basic_info_layout3.setVisibility(View.GONE);
            }

//            if(listImgTask != null && listImgTask.size() > 0){
//                if(listImgTask.size() == 1){
//                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_01);
//                    img_01.setVisibility(View.VISIBLE);
//                    img_02.setVisibility(View.GONE);
//                    img_03.setVisibility(View.GONE);
//                }
//                if(listImgTask.size() == 2){
//                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_01);
//                    Arad.imageLoader.load(listImgTask.get(1).getOriginalImg() == null || (listImgTask.get(1).getOriginalImg()).equals("") ? "aa" : listImgTask.get(1).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_02);
//                    img_01.setVisibility(View.VISIBLE);
//                    img_02.setVisibility(View.VISIBLE);
//                    img_03.setVisibility(View.GONE);
//                }
//
//                if(listImgTask.size() >= 3){
//                    Arad.imageLoader.load(listImgTask.get(0).getOriginalImg() == null || (listImgTask.get(0).getOriginalImg()).equals("") ? "aa" : listImgTask.get(0).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_01);
//                    Arad.imageLoader.load(listImgTask.get(1).getOriginalImg() == null || (listImgTask.get(1).getOriginalImg()).equals("") ? "aa" : listImgTask.get(1).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_02);
//                    Arad.imageLoader.load(listImgTask.get(2).getOriginalImg() == null || (listImgTask.get(2).getOriginalImg()).equals("") ? "aa" : listImgTask.get(2).getOriginalImg())
//                            .placeholder(R.drawable.default_image)
//                            .into(img_03);
//                    img_01.setVisibility(View.VISIBLE);
//                    img_02.setVisibility(View.VISIBLE);
//                    img_03.setVisibility(View.VISIBLE);
//                }
//            }

        }

        if(3 == requestCode){
            MessageUtils.showShortToast(this,"提交成功");
            task_detail_text.setEnabled(false);
            task_submit_btn.setEnabled(false);
            task_submit_btn.setClickable(false);
            task_submit_btn.setBackgroundResource(R.drawable.gray_bg_shap);

            add_task_btn.setEnabled(false);
            add_task_btn.setClickable(false);
            add_task_btn.setBackgroundResource(R.drawable.gray_bg_shap);

            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
            finish();

        }

    }

    @Override
    public void onClick(View v) {
        if(v == add_btn){
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == add_task_btn){
            basic_info_layout3.setVisibility(View.VISIBLE);
            add_task_btn.setEnabled(false);
            add_task_btn.setBackgroundResource(R.drawable.gray_bg_shap);
            add_task_btn.setVisibility(View.GONE);
        }

        if(v == task_submit_btn){

            if(!S.isNull(task_detail_text.getText().toString())){
                //String taskAssignmentId = "";
                String newTaskContent = task_detail_text.getText().toString();
                String newStaffId = AppHolder.getInstance().getContactor().getId();
                showProgress(true);
                taskDao.requestAddTask(taskId,newTaskContent,newStaffId);
            }
        }

//        if(v == img_01){
//            imagelist = new ArrayList<String>();
//            for(int i = 0 ; i < listImgTask.size() ; i ++){
//                imagelist.add(listImgTask.get(i).getOriginalImg());
//            }
//            position = 0;
//            Intent intent = new Intent(TaskDetailActivity.this, ImageZoomActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("imageList", imagelist);
//            bundle.putInt("position", position);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//
//        if(v == img_02){
//            imagelist = new ArrayList<String>();
//            for(int i = 0 ; i < listImgTask.size() ; i ++){
//                imagelist.add(listImgTask.get(i).getOriginalImg());
//            }
//            position = 1;
//            Intent intent = new Intent(TaskDetailActivity.this, ImageZoomActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("imageList", imagelist);
//            bundle.putInt("position", position);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//
//        if(v == img_03){
//            imagelist = new ArrayList<String>();
//            for(int i = 0 ; i < listImgTask.size() ; i ++){
//                imagelist.add(listImgTask.get(i).getOriginalImg());
//            }
//            position = 2;
//            Intent intent = new Intent(TaskDetailActivity.this, ImageZoomActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("imageList", imagelist);
//            bundle.putInt("position", position);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }

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
                MessageUtils.showShortToast(TaskDetailActivity.this, "完成");
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



}

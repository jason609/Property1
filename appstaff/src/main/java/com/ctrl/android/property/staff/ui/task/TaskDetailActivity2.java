package com.ctrl.android.property.staff.ui.task;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.dao.TaskDao;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.entity.TaskDetail;
import com.ctrl.android.property.staff.util.D;
import com.ctrl.android.property.staff.util.S;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 任务指派 activity
 * Created by Eric on 2015/10/22
 */
public class TaskDetailActivity2 extends AppToolBarActivity implements View.OnClickListener{

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

    @InjectView(R.id.basic_info_layout1)
    LinearLayout basic_info_layout1;//任务基本信息部分
    @InjectView(R.id.basic_info_layout3)
    LinearLayout basic_info_layout3;//添加完成任务 部分

    @InjectView(R.id.img_01)
    ImageView img_01;
    @InjectView(R.id.img_02)
    ImageView img_02;
    @InjectView(R.id.img_03)
    ImageView img_03;

    @InjectView(R.id.task_feedback_text)
    EditText task_feedback_text;

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

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private ImgDao imgDao;
    private int delFlg = 0;

    private List<ImageView> listImgView = new ArrayList<>();
    private List<Img2> listImg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.task_detail_activity2);
        /**首次进入该页不让弹出软键盘*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        //basic_info_layout1.setVisibility(View.GONE);
        basic_info_layout2.setVisibility(View.GONE);
        basic_info_layout4.setVisibility(View.GONE);
        basic_info_layout3.setVisibility(View.GONE);

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        listImgView.add(img_01);
        listImgView.add(img_02);
        listImgView.add(img_03);

        imgDao = new ImgDao(this);

        img_01.setVisibility(View.VISIBLE);
        img_02.setVisibility(View.GONE);
        img_03.setVisibility(View.GONE);

//        add_btn.setOnClickListener(this);
//        add_task_btn.setOnClickListener(this);
//        task_submit_btn.setOnClickListener(this);

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
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(1 == requestCode){
            MessageUtils.showShortToast(this, "添加成功");
            finish();
        }

        if(2 == requestCode){
            taskDetail = taskDao.getTaskDetail();
            listImgTask = taskDao.getListImgTask();
            listImgTaskAdd = taskDao.getListImgTaskAdd();

            /**显示  任务基本信息部分*/
            basic_info_layout1.setVisibility(View.VISIBLE);
            task_name.setText(S.getStr(taskDetail.getTaskName()));
            task_man.setText(S.getStr(taskDetail.getName()));
            task_time.setText(D.getDateStrFromStamp("yyyy-MM-dd HH:mm",taskDetail.getCreateTime()));
            task_status.setText(S.getStr(taskDetail.getTaskStatus() == 0 ? "执行中" : "已完成"));
            task_detail_content1.setText(S.getStr(taskDetail.getContent()));


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
                basic_info_layout3.setVisibility(View.GONE);
                basic_info_layout4.setVisibility(View.VISIBLE);
            } else {
                basic_info_layout4.setVisibility(View.GONE);

                /**是否 显示 添加反馈信息部分*/
                if(S.isNull(taskDetail.getStaffId())){
                    basic_info_layout3.setVisibility(View.GONE);
                } else {
                    if(S.isNull(taskDetail.getNewStaffId())){
                        if((!S.isNull(taskDetail.getFeedbackTime()))){
                            if(S.isNull(taskDetail.getNewTaskContent())){
                                basic_info_layout3.setVisibility(View.GONE);
                            } else {
                                basic_info_layout3.setVisibility(View.VISIBLE);
                            }
                        } else {
                            basic_info_layout3.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if((taskDetail.getStaffId()).equals(taskDetail.getNewStaffId())){
                            if((!S.isNull(taskDetail.getFeedbackTime()))){
                                if(S.isNull(taskDetail.getNewTaskContent())){
                                    basic_info_layout3.setVisibility(View.GONE);
                                } else {
                                    basic_info_layout3.setVisibility(View.VISIBLE);
                                }
                            } else {
                                basic_info_layout3.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if((taskDetail.getNewStaffId()).equals(AppHolder.getInstance().getStaffInfo().getStaffId())){
                                if((!S.isNull(taskDetail.getFeedbackTime()))){
                                    if(S.isNull(taskDetail.getNewTaskContent())){
                                        basic_info_layout3.setVisibility(View.GONE);
                                    } else {
                                        basic_info_layout3.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    basic_info_layout3.setVisibility(View.VISIBLE);
                                }
                            } else {
                                basic_info_layout3.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

//            /**是否 显示 添加反馈信息部分*/
//            if(S.isNull(taskDetail.getStaffId())){
//                basic_info_layout3.setVisibility(View.GONE);
//            } else {
//                if(S.isNull(taskDetail.getNewStaffId())){
//                    basic_info_layout3.setVisibility(View.VISIBLE);
//                } else {
//                    if((taskDetail.getStaffId()).equals(taskDetail.getNewStaffId())){
//                        basic_info_layout3.setVisibility(View.VISIBLE);
//                    } else {
//                        if((taskDetail.getNewStaffId()).equals(AppHolder.getInstance().getStaffInfo().getStaffId())){
//                            basic_info_layout3.setVisibility(View.VISIBLE);
//                        } else {
//                            basic_info_layout3.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            }

            //0：执行中、1：已完成
//            if(taskDetail.getTaskStatus() == 0){
//                if(taskDetail.getNewTaskStatus() == 0){
//                    basic_info_layout3.setVisibility(View.VISIBLE);
//                    basic_info_layout4.setVisibility(View.GONE);
//                } else if(taskDetail.getNewTaskStatus() == 1){
//                    basic_info_layout3.setVisibility(View.GONE);
//                    basic_info_layout4.setVisibility(View.VISIBLE);
//                } else {
//                    basic_info_layout3.setVisibility(View.VISIBLE);
//                    basic_info_layout4.setVisibility(View.GONE);
//                }
//            } else if(taskDetail.getTaskStatus() == 1){
//                if(taskDetail.getNewTaskStatus() == 0){
//                    basic_info_layout3.setVisibility(View.VISIBLE);
//                    basic_info_layout4.setVisibility(View.GONE);
//                } else if(taskDetail.getNewTaskStatus() == 1){
//                    basic_info_layout3.setVisibility(View.GONE);
//                    basic_info_layout4.setVisibility(View.VISIBLE);
//                } else {
//                    basic_info_layout3.setVisibility(View.GONE);
//                    basic_info_layout4.setVisibility(View.VISIBLE);
//                }
//            }

        }

        if(4 == requestCode){
            MessageUtils.showShortToast(this, "提交成功");
            task_feedback_text.setEnabled(false);

            img_01.setEnabled(false);
            img_02.setEnabled(false);
            img_03.setEnabled(false);

            Intent intent = new Intent(this, TaskListActivity.class);
            intent.putExtra("flg","1");
            startActivity(intent);
            AnimUtil.intentSlidIn(this);

            finish();

        }

        if(5 == requestCode){
            MessageUtils.showShortToast(this, "提交成功");
            task_feedback_text.setEnabled(false);

            img_01.setEnabled(false);
            img_02.setEnabled(false);
            img_03.setEnabled(false);

            Intent intent = new Intent(this, TaskListActivity.class);
            intent.putExtra("flg","1");
            startActivity(intent);
            AnimUtil.intentSlidIn(this);

            finish();

        }

        if(101 == requestCode){
            Img2 m = imgDao.getImg();
            listImg.add(m);

            //setImageView(listImgView,listImg);
            //setLongListener(listImgView,listImg);


            if(listImg != null && listImg.size() > 0){
                if(listImg.size() == 1){
                    Log.d("demo", "XX: " + listImg.get(0).getImgUrl());
                    Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(0));
                    listImgView.get(0).setVisibility(View.VISIBLE);
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.INVISIBLE);
                }

                if(listImg.size() == 2){
                    Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(1));
                    listImgView.get(0).setVisibility(View.VISIBLE);
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.VISIBLE);
                }

                if(listImg.size() == 3){
                    Arad.imageLoader.load(S.getStr(listImg.get(2).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(2));
                    listImgView.get(0).setVisibility(View.VISIBLE);
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.VISIBLE);
                }
            }

            img_01.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImg != null && listImg.size() > 0) {
                        if (listImg.size() >= 1) {

                            listImg.remove(0);

                            if (listImg.size() == 0) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 1) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 2) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                listImgView.get(2).setImageResource(R.drawable.green_add_img_icon);
                            }

                            if (listImg.size() == 3) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                Arad.imageLoader.load(S.getStr(listImg.get(3).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(3));
                            }
                        }
                    } else {
                        //
                    }
                    return true;
                }
            });

            img_02.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImg != null && listImg.size() > 0) {
                        if (listImg.size() >= 2) {

                            listImg.remove(1);

                            if (listImg.size() == 0) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 1) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 2) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                listImgView.get(2).setImageResource(R.drawable.green_add_img_icon);
                            }

                            if (listImg.size() == 3) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                Arad.imageLoader.load(S.getStr(listImg.get(3).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(3));
                            }
                        }
                    } else {
                        //
                    }
                    return true;
                }
            });

            img_03.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImg != null && listImg.size() > 0) {
                        if (listImg.size() >= 3) {

                            listImg.remove(2);

                            if (listImg.size() == 0) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 1) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if (listImg.size() == 2) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                listImgView.get(2).setImageResource(R.drawable.green_add_img_icon);
                            }

                            if (listImg.size() == 3) {
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.VISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));

                                Arad.imageLoader.load(S.getStr(listImg.get(1).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(1));

                                Arad.imageLoader.load(S.getStr(listImg.get(3).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(3));
                            }
                        }
                    } else {
                        //
                    }
                    return true;
                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        if(v == img_01){
            //MessageUtils.showShortToast(this,"01");
            if(listImg == null && listImg.size() < 0){
                //
            } else if(listImg.size() >= 1){
                //showDialog();
            } else {
                showDialog();
            }

        }

        if(v == img_02){
            //MessageUtils.showShortToast(this,"01");
            if(listImg == null && listImg.size() < 0){
                //
            } else if(listImg.size() >= 2){
                //showDialog();
            } else {
                showDialog();
            }
        }

        if(v == img_03){
            //MessageUtils.showShortToast(this,"01");
            if(listImg == null && listImg.size() < 0){
                //
            } else if(listImg.size() >= 3){
                //showDialog();
            } else {
                showDialog();
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

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!S.isNull(task_feedback_text.getText().toString())){

                    String taskFeedback = task_feedback_text.getText().toString();

                    String taskAssignmentPicStr1 = "";
                    String taskAssignmentPicStr2 = "";
                    String taskAssignmentPicStr3 = "";

                    if(listImg != null && listImg.size() == 1 ){
                        taskAssignmentPicStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                    } else {
                        taskAssignmentPicStr1 = "";
                    }

                    if(listImg != null && listImg.size() == 2 ){
                        taskAssignmentPicStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                    } else {
                        taskAssignmentPicStr2 = "";
                    }

                    if(listImg != null && listImg.size() == 3 ){
                        taskAssignmentPicStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
                    } else {
                        taskAssignmentPicStr3 = "";
                    }

                    if(S.isNull(taskDetail.getNewStaffId())){
                        taskDao.requestSubmitFeedback(taskId, taskFeedback, taskAssignmentPicStr1, taskAssignmentPicStr2, taskAssignmentPicStr3);
                    } else {
                        taskDao.requestSubmitAddFeedback(taskId, taskFeedback, taskAssignmentPicStr1, taskAssignmentPicStr2, taskAssignmentPicStr3);
                    }

                } else {
                    MessageUtils.showShortToast(TaskDetailActivity2.this,"内容不可为空");
                }


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
     * 显示选择对话框
     */
    private void showDialog() {
        Log.d("demo","进入拍照");
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cxh.jpg")));
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
//
            case CAMERA_REQUEST_CODE:

                getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");

                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    //getImageToView(data);
                    //Log.d("demo","取消上传");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getImageToView1(String path) {
        Bitmap bitmap ;
        try{
            bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);

            String photo = new String(encode);
            if (photo != null){
                Log.d("demo","上传方法2");
                /**调用后台方法  将图片上传**/
                String activityId = "";
                String imgData = photo;
                String typeKey = "MT";//
                String optMode = "0";//0：添加、1:编辑
                showProgress(true);
                imgDao.requestUploadImg(activityId,imgData,typeKey,optMode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 保存裁剪之后的图片数据
     * @param uri
     */
    private void getImageToView(Uri uri) {
        Log.d("demo", "上传方法");
        Bitmap bitmap ;
        //Bundle extras = data.getExtras();
        try{
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor =getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            bitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);

            String photo = new String(encode);
            Log.d("demo","photo : " + photo);
            //Drawable drawable = new BitmapDrawable(bitmap);
            if (photo != null){
                Log.d("demo","上传方法2");
                /**调用后台方法  将图片上传**/
                String activityId = "";
                String imgData = photo;
                String typeKey = "MT";//设备养护记录
                String optMode = "0";//0：添加、1:编辑
                showProgress(true);
                imgDao.requestUploadImg(activityId,imgData,typeKey,optMode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //if (extras != null) {
        //Bitmap bitmap = extras.getParcelable("data");
        //ByteArrayOutputStream out = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
//            try {
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        //    }
    }

    /**
     * 给imageView赋值
     * */
    private void setImageView(List<ImageView> listV, List<Img2> listI){

        /*将所有ImageView控件都设为  隐藏*/
        if(listV != null && listV.size() > 0){
            for(int i = 0 ; i < listV.size() ; i ++){
                if(listV.get(i) != null){
                    listV.get(i).setImageResource(R.drawable.green_add_img_icon);
                    listV.get(i).setVisibility(View.GONE);
                }
            }
        }

        /*将所有img  赋值给imageView控件*/
        if(listI != null && listI.size() > 0){
            for(int i = 0 ; i < listI.size() ; i ++){
                if(listI.size() == listV.size()){
                    if(listI.get(i) != null){
                        String url = S.isNull(listI.get(i).getImgUrl()) ? "aa" : listI.get(i).getImgUrl();
                        Arad.imageLoader.load(S.getStr(url))
                                .placeholder(R.drawable.default_image)
                                .into(listV.get(i));
                    }
                } else if(listI.size() < listV.size()){
                    for(int j = (listI.size() - 1) ; j < listV.size() ; j ++){
                        listV.get(j).setVisibility(View.INVISIBLE);
                        listV.get(listI.size() - 1).setVisibility(View.VISIBLE);
                        listV.get(listI.size() - 1).setImageResource(R.drawable.green_add_img_icon);
                    }
                }
            }
        }
    }



}

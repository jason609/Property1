package com.ctrl.android.property.staff.ui.my;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.base.MyApplication;
import com.ctrl.android.property.staff.dao.IconDao;
import com.ctrl.android.property.staff.ui.device.DeviceActivity;
import com.ctrl.android.property.staff.ui.express.ExpressListActivity;
import com.ctrl.android.property.staff.ui.forum.ForumActivity;
import com.ctrl.android.property.staff.ui.notice.NoticeListActivity;
import com.ctrl.android.property.staff.ui.patrol.PatrolLineActivity;
import com.ctrl.android.property.staff.ui.repair.RepairsActivity;
import com.ctrl.android.property.staff.ui.task.TaskListActivity;
import com.ctrl.android.property.staff.ui.visit.VisitHandleActivity;
import com.ctrl.android.property.staff.ui.widget.RoundImageView;
import com.ctrl.android.property.staff.util.S;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主页面 activity
 * Created by Eric on 2015/11/23.
 * */
public class MyActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.userIcon)//头像
    RoundImageView userIcon;

    @InjectView(R.id.welcome_text)
    TextView welcome_text;
    @InjectView(R.id.belong_to_community)
    TextView belong_to_community;

    @InjectView(R.id.my_setting_btn)//
    ImageView my_setting_btn;

    @InjectView(R.id.my_notice_btn)//我的通知
    RelativeLayout my_notice_btn;
    @InjectView(R.id.my_forum_btn)//我的论坛
    RelativeLayout my_forum_btn;
    @InjectView(R.id.my_repair_btn)//我的报修
    RelativeLayout my_repair_btn;
    @InjectView(R.id.my_visit_btn)//预约及到访
    RelativeLayout my_visit_btn;
    @InjectView(R.id.my_express_btn)//代收快递
    RelativeLayout my_express_btn;
    @InjectView(R.id.my_partrol_btn)//巡更巡查
    RelativeLayout my_partrol_btn;
    @InjectView(R.id.my_equipment_btn)//设备养护
    RelativeLayout my_equipment_btn;
    @InjectView(R.id.my_task_btn)//任务指派
    RelativeLayout my_task_btn;

    private IconDao iconDao;


    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private String[] items = new String[]{"本地图片", "拍照"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setContentView(R.layout.my_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化方法
     * */
    private void init(){

        userIcon.setOnClickListener(this);
        my_setting_btn.setOnClickListener(this);

        Arad.imageLoader.load(S.isNull(AppHolder.getInstance().getStaffInfo().getImgUrl()) ? "aa" : AppHolder.getInstance().getStaffInfo().getImgUrl())
                .placeholder(R.drawable.touxiang)
                .into(userIcon);

        my_notice_btn.setOnClickListener(this);
        my_forum_btn.setOnClickListener(this);
        my_repair_btn.setOnClickListener(this);
        my_visit_btn.setOnClickListener(this);
        my_express_btn.setOnClickListener(this);
        my_partrol_btn.setOnClickListener(this);
        my_equipment_btn.setOnClickListener(this);
        my_task_btn.setOnClickListener(this);

        welcome_text.setText("欢迎你   " + AppHolder.getInstance().getStaffInfo().getUserName());
        belong_to_community.setText("所属 " + AppHolder.getInstance().getStaffInfo().getCommunityName());

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(90 == requestCode){
            Arad.imageLoader.load(S.isNull(AppHolder.getInstance().getStaffInfo().getImgUrl()) ? "aa" : AppHolder.getInstance().getStaffInfo().getImgUrl())
                    .placeholder(R.drawable.touxiang)
                    .into(userIcon);
        }

    }

    @Override
    public void onClick(View v) {

        if(v == my_setting_btn){
            Intent intent = new Intent(this, MySettingActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_notice_btn){
            //MessageUtils.showShortToast(this,"我的通知");
            Intent intent = new Intent(this, NoticeListActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_forum_btn){
            //MessageUtils.showShortToast(this,"我的论坛");
            Intent intent = new Intent(this, ForumActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_repair_btn){
            //MessageUtils.showShortToast(this,"报修");
            Intent intent = new Intent(this, RepairsActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_visit_btn){
            //MessageUtils.showShortToast(this,"预约到访");
            Intent intent = new Intent(this, VisitHandleActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_express_btn){
            //MessageUtils.showShortToast(this,"代收快递");
            Intent intent = new Intent(this, ExpressListActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_partrol_btn){
            //MessageUtils.showShortToast(this,"巡更巡查");
            Intent intent = new Intent(this, PatrolLineActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_equipment_btn){
            //MessageUtils.showShortToast(this,"设备养护");
            Intent intent = new Intent(this, DeviceActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == my_task_btn){
            //MessageUtils.showShortToasis,"任务指派");
            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(this);
        }

        if(v == userIcon){
            showDialog();
        }

    }

    /**
     * header 左侧按钮
     * */
//    @Override
//    public boolean setupToolBarLeftButton(ImageView leftButton) {
//        leftButton.setImageResource(R.drawable.white_arrow_left_none);
//        leftButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
//                onBackPressed();
//            }
//        });
//        return true;
//    }

    /**
     * 页面标题
     * */
//    @Override
//    public String setupToolBarTitle() {
//        return TITLE;
//    }

    /**
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.white_cross_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MessageUtils.showShortToast(EasyActivity.this, "MORE");
//                //showProStyleListPop();
//            }
//        });
//        return true;
//    }

    /**
     * header 右侧文本
     * */
    public boolean setupToolBarRightText(TextView mRightText) {

        mRightText.setText("个人中心");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtils.showShortToast(MyActivity.this, "个人中心");
            }
        });

        return true;
    }


    /**
     * 显示选择对话框
     */
    private void showDialog() {

        new AlertDialog.Builder(MyActivity.this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case IMAGE_REQUEST_CODE:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case CAMERA_REQUEST_CODE:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if(data != null){
                    startPhotoZoom(data.getData());
                }
                break;
            case CAMERA_REQUEST_CODE:
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(MyActivity.this.getContentResolver(), bitmap, null, null));
                    startPhotoZoom(uri);
                }
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);

            //Drawable drawable = new BitmapDrawable(bitmap);
            //userIcon.setImageDrawable(drawable);
            if (photo != null){
                showProgress(true);
                iconDao = new IconDao(this);
                //memberDao.requestModifyMemberIcon(AppHolder.getInstance().getMemberInfo().getMemberId(),photo);
                iconDao.requestUploadIcon(AppHolder.getInstance().getStaffInfo().getStaffId(),photo);
            }
        }
    }


}

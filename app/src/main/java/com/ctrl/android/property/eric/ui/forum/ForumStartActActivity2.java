package com.ctrl.android.property.eric.ui.forum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Utils;
import com.ctrl.android.property.eric.dao.ForumDao;
import com.ctrl.android.property.eric.dao.ImgDao;
import com.ctrl.android.property.eric.entity.Img2;
import com.ctrl.android.property.eric.ui.widget.CustomDialog;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛 发布活动 页面2
 * Created by Eric on 2015/10/26.
 */
public class ForumStartActActivity2 extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.act_content)//活动内容
    EditText act_content;

    @InjectView(R.id.img_01)//上传图片1
    ImageView img_01;
    @InjectView(R.id.img_02)//上传图片2
    ImageView img_02;
    @InjectView(R.id.img_03)//上传图片3
    ImageView img_03;

    private List<ImageView> listImgView = new ArrayList<>();
    private List<Img2> listImg = new ArrayList<>();

    private String TITLE = StrConstant.START_ACT_TITLE;

    private String title;
    private String startTime;
    private String endTime;
    private String location;

    private String[] items = new String[]{"本地图片", "拍照"};
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private ForumDao forumDao;
    private ImgDao imgDao;
    private int delFlg = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_start_act_activity2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        forumDao = new ForumDao(this);
        imgDao = new ImgDao(this);

        title = getIntent().getStringExtra("title");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        location = getIntent().getStringExtra("location");

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        listImgView.add(img_01);
        listImgView.add(img_02);
        listImgView.add(img_03);
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
     * 请求数据成功后 调用此方法
     * */
    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(0 == requestCode){
            MessageUtils.showShortToast(this,"请求成功");
        }

        if(5 == requestCode){
            showDialog2();
            //MessageUtils.showShortToast(this,"发布成功");

        }

        if(101 == requestCode){
            Img2 m = imgDao.getImg();
            listImg.add(m);

            //setImageView(listImgView,listImg);
            //setLongListener(listImgView,listImg);


            if(listImg != null && listImg.size() > 0){
                if(listImg.size() == 1){
                    Log.d("demo","XX: " + listImg.get(0).getImgUrl());
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
                    if(listImg != null && listImg.size() > 0){
                        if(listImg.size() >= 1){

                            listImg.remove(0);

                            if(listImg.size() == 0){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 1){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 2){
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

                            if(listImg.size() == 3){
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
                    if(listImg != null && listImg.size() > 0){
                        if(listImg.size() >= 2){

                            listImg.remove(1);

                            if(listImg.size() == 0){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 1){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 2){
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

                            if(listImg.size() == 3){
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

                            if(listImg.size() == 0){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.INVISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);
                                listImgView.get(0).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 1){
                                listImgView.get(0).setVisibility(View.VISIBLE);
                                listImgView.get(1).setVisibility(View.VISIBLE);
                                listImgView.get(2).setVisibility(View.INVISIBLE);

                                Arad.imageLoader.load(S.getStr(listImg.get(0).getImgUrl()))
                                        .placeholder(R.drawable.default_image)
                                        .into(listImgView.get(0));
                                listImgView.get(1).setImageResource(R.drawable.green_add_img_icon);

                            }

                            if(listImg.size() == 2){
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

                            if(listImg.size() == 3){
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

        if(102 == requestCode){

            //setLongListener(listImgView,listImg);

            if(delFlg == 1){
                listImg.remove(0);
                for(int i = 0 ; i < listImg.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImg.get(i).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(i));
                }

                if(listImg.size() == 1){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.INVISIBLE);
                }

                if(listImg.size() == 2){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.VISIBLE);
                }

            }

            if(delFlg == 2){
                listImg.remove(1);
                for(int i = 0 ; i < listImg.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImg.get(i).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(i));
                }

                if(listImg.size() == 1){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.INVISIBLE);
                }

                if(listImg.size() == 2){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.VISIBLE);
                }

            }

            if(delFlg == 3){
                listImg.remove(2);
                for(int i = 0 ; i < listImg.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImg.get(i).getImgUrl()))
                            .placeholder(R.drawable.default_image)
                            .into(listImgView.get(i));
                }

                if(listImg.size() == 1){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.INVISIBLE);
                }

                if(listImg.size() == 2){
                    listImgView.get(1).setVisibility(View.VISIBLE);
                    listImgView.get(2).setVisibility(View.VISIBLE);
                }

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
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.toolbar_home);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toHomePage();
//            }
//        });
//        return true;
//    }

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(R.string.release);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    //MessageUtils.showShortToast(ForumStartActActivity2.this,"发布");
                    String communityId = AppHolder.getInstance().getCommunity().getId();
                    String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();
                    //String title
                    //String startTime,
                    //String endTime,
                    //String location,
                    String content = act_content.getText().toString();

                    String actionPicUrlStr1 = "";
                    String actionPicUrlStr2 = "";
                    String actionPicUrlStr3 = "";

                    if(listImg != null && listImg.size() == 1 ){
                        actionPicUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        actionPicUrlStr2 = "";
                        actionPicUrlStr3 = "";
                    } else if(listImg != null && listImg.size() == 2 ){
                        actionPicUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        actionPicUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                        actionPicUrlStr3 = "";
                    } else if(listImg != null && listImg.size() >= 3 ){
                        actionPicUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        actionPicUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                        actionPicUrlStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
                    } else {
                        actionPicUrlStr1 = "";
                        actionPicUrlStr2 = "";
                        actionPicUrlStr3 = "";
                    }

//                    else {
//                        actionPicUrlStr1 = "";
//                        actionPicUrlStr2 = "";
//                        actionPicUrlStr3 = "";
//                    }

//                    if(listImg != null && listImg.size() == 2 ){
//                        actionPicUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
//                        actionPicUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
//                        actionPicUrlStr3 = "";
//                    } else {
//                        actionPicUrlStr1 = "";
//                        actionPicUrlStr2 = "";
//                        actionPicUrlStr3 = "";
//                    }

//                    if(listImg != null && listImg.size() == 3 ){
//                        actionPicUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
//                        actionPicUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
//                        actionPicUrlStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
//                    } else {
//                        actionPicUrlStr1 = "";
//                        actionPicUrlStr2 = "";
//                        actionPicUrlStr3 = "";
//                    }

                    Log.d("demo","listImg: " + listImg.size());
                    Log.d("demo","actionPicUrlStr1: " + actionPicUrlStr1);
                    Log.d("demo","actionPicUrlStr2: " + actionPicUrlStr2);
                    Log.d("demo","actionPicUrlStr3: " + actionPicUrlStr3);

                    showProgress(true);
                    forumDao.requestStartAct(communityId,memberId, title
                                            , startTime, endTime, location, content,
                                             actionPicUrlStr1, actionPicUrlStr2, actionPicUrlStr3);
                }

            }
        });
        return true;
    }

    private boolean checkInput(){

        if(act_content.getText().toString() == null || act_content.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"内容不可为空");
            return false;
        }

        return true;
    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("请选择")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case IMAGE_REQUEST_CODE:

                                Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
                                intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);

                                break;
                            case CAMERA_REQUEST_CODE:

                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cxh.jpg")));
                                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (data != null){

                    Uri uri = data.getData();
                    String thePath = Utils.getInstance().getPath(this, uri);
                    getImageToView1(thePath);

                }
                break;
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
                String typeKey = "AC";//活动配图
                String optMode = "0";//0：添加、1:编辑
                showProgress(true);
                imgDao.requestUploadImg(activityId, imgData, typeKey, optMode);
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
            Cursor cursor =getContentResolver().query(uri, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
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

            Drawable drawable = new BitmapDrawable(bitmap);
            if (photo != null){
                Log.d("demo","上传方法2");
                /**调用后台方法  将图片上传**/
                String activityId = "";
                String imgData = photo;
                String typeKey = "AC";//活动配图
                String optMode = "0";//0：添加、1:编辑
                showProgress(true);
                imgDao.requestUploadImg(activityId,imgData,typeKey,optMode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 显示提示框
     */
    private void showDialog2() {

        CustomDialog.Builder builder = new CustomDialog.Builder(ForumStartActActivity2.this);
        builder.setTitle("提示");
        builder.setMessage("活动发布后需要审核, 请耐心等待");
        builder.hidenNegativeButton();
        builder.setCancelableArg(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                //MessageUtils.showShortToast(LoginActivity.this, "未找到业主信息,请添加房屋");

            }
        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });

        builder.create().show();

    }

}

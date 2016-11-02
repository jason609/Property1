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
import com.ctrl.android.property.eric.dao.ForumDao;
import com.ctrl.android.property.eric.dao.ImgDao;
import com.ctrl.android.property.eric.entity.Img2;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 论坛 发表帖子页面
 * Created by Eric on 2015/10/26.
 */
public class ForumStartNoteActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.note_title)//帖子标题
    EditText note_title;
    @InjectView(R.id.note_content)//帖子内容
    EditText note_content;

    @InjectView(R.id.img_01)//上传图片1
    ImageView img_01;
    @InjectView(R.id.img_02)//上传图片2
    ImageView img_02;
    @InjectView(R.id.img_03)//上传图片3
    ImageView img_03;

    private List<ImageView> listImgView = new ArrayList<>();
    private List<Img2> listImg = new ArrayList<>();

    private String TITLE = StrConstant.START_NOTE_TITLE;

    private String[] items = new String[]{"本地图片", "拍照"};
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private ForumDao forumDao;
    private ImgDao imgDao;
    private int delFlg = 0;

    private String categoryId;
    private String memberId = AppHolder.getInstance().getMemberInfo().getMemberId();//用户id 具体问题具体分析

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.forum_start_note_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        categoryId = getIntent().getStringExtra("categoryId");
        forumDao = new ForumDao(this);

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        listImgView.add(img_01);
        listImgView.add(img_02);
        listImgView.add(img_03);

        imgDao = new ImgDao(this);

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

        if(4 == requestCode){
            MessageUtils.showShortToast(this, "发布成功");
            finish();
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
                //MessageUtils.showShortToast(ForumStartNoteActivity.this,"发布");
                if(checkInput()){
                    //String categoryId =
                    //String memberId,
                    String title = note_title.getText().toString();
                    String content = note_content.getText().toString();

//                    String picUrlStr1 = "";
//                    if(listImg != null && listImg.size() == 1 ){
//                        picUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
//                    } else {
//                        picUrlStr1 = "";
//                    }
//
//                    String picUrlStr2 = "";
//                    if(listImg != null && listImg.size() == 2 ){
//                        picUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
//                    } else {
//                        picUrlStr2 = "";
//                    }
//
//                    String picUrlStr3 = "";
//                    if(listImg != null && listImg.size() == 3 ){
//                        picUrlStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
//                    } else {
//                        picUrlStr3 = "";
//                    }

                    String picUrlStr1 = "";
                    String picUrlStr2 = "";
                    String picUrlStr3 = "";

                    if(listImg != null && listImg.size() == 1 ){
                        picUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        picUrlStr2 = "";
                        picUrlStr3 = "";
                    } else if(listImg != null && listImg.size() == 2 ){
                        picUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        picUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                        picUrlStr3 = "";
                    } else if(listImg != null && listImg.size() >= 3 ){
                        picUrlStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                        picUrlStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                        picUrlStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
                    } else {
                        picUrlStr1 = "";
                        picUrlStr2 = "";
                        picUrlStr3 = "";
                    }

                    showProgress(true);
                    forumDao.requestReleaseNote(categoryId,memberId,title,content,picUrlStr1,picUrlStr2,picUrlStr3);
                }

            }
        });
        return true;
    }

    private boolean checkInput(){

        if(S.isNull(note_title.getText().toString())){
            MessageUtils.showShortToast(this,"请输入标题");
            return false;
        }

        if(S.isNull(note_content.getText().toString())){
            MessageUtils.showShortToast(this,"请输入内容");
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
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                                break;
                            case CAMERA_REQUEST_CODE:

                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
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
                    getImageToView(data.getData());
                    //startPhotoZoom(data.getData());
                    //Log.d("demo", "图片上传");
                }
                break;
            case CAMERA_REQUEST_CODE:
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                    //getImageToView(data);
                    getImageToView(uri);
                    //startPhotoZoom(uri);
                    //Log.d("demo","照片上传");
                }
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

    /**
     * 保存裁剪之后的图片数据
     * @param uri
     */
    private void getImageToView(Uri uri) {
        Log.d("demo","上传方法");
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

            Drawable drawable = new BitmapDrawable(bitmap);
            if (photo != null){
                //Log.d("demo","上传方法2");
                /**调用后台方法  将图片上传**/
                String activityId = "";
                String imgData = photo;
                String typeKey = "POST";//论坛配图
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

    /**
     * 设置长按 监听
     * */
    private void setLongListener(List<ImageView> listV, final List<Img2> listI){

        if(listV != null && listV.size() > 0){
            for(int i = 0 ; i < listV.size() ; i ++){
                if(listV.get(i) != null){
                    final int flg = i;
                    listV.get(i).setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            listI.remove(flg);
                            return true;
                        }
                    });
                }
            }
        }

    }

















    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
//    public void startPhotoZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//         //outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, RESULT_REQUEST_CODE);
//    }

    /**
     * 保存裁剪之后的图片数据
     * @param data
     */
//    private void getImageToView(Intent data) {
//        Log.d("demo","上传方法");
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap bitmap = extras.getParcelable("data");
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
//            try {
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            byte[] buffer = out.toByteArray();
//            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
//
//            String photo = new String(encode);
//
//            Drawable drawable = new BitmapDrawable(bitmap);
//            if (photo != null){
//                Log.d("demo","上传方法2");
//                /**调用后台方法  将图片上传**/
//                String activityId = "";
//                String imgData = photo;
//                String typeKey = "POST";//论坛配图
//                String optMode = "0";//0：添加、1:编辑
//                showProgress(true);
//                imgDao.requestUploadImg(activityId,imgData,typeKey,optMode);
//            }
//        } else {
//            Log.d("demo","上传 null ");
//        }
//    }

}

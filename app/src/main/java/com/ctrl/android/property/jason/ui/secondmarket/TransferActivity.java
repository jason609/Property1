package com.ctrl.android.property.jason.ui.secondmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Utils;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.jason.dao.ImgDao;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.Kind;
import com.ctrl.android.property.jason.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransferActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.et_baobei_title)
    EditText et_baobei_title;
    @InjectView(R.id.et_baobei_detail)
    EditText et_baobei_detail;
    @InjectView(R.id.et_baobei_transferprice)//转让价
    EditText et_baobei_transferprice;
    @InjectView(R.id.et_baobei_originalprice)
    EditText et_baobei_originalprice;
    @InjectView(R.id.et_contact)
    EditText et_contact;
    @InjectView(R.id.et_contact_telephone)
    EditText et_contact_telephone;
    @InjectView(R.id.iv01_second_hand_transfer)
    ImageView iv01_second_hand_transfer;
    @InjectView(R.id.iv02_second_hand_transfer)
    ImageView iv02_second_hand_transfer;
    @InjectView(R.id.iv03_second_hand_transfer)
    ImageView iv03_second_hand_transfer;
    @InjectView(R.id.rl_wang_transfer)
    RelativeLayout rl_wang_transfer;
    @InjectView(R.id.tv_secong_hand_transfer_type)
    TextView tv_secong_hand_transfer_type;

    private UsedGoodsDao dao;
    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private List<GoodPic> listImage=new ArrayList<>();
    private List<String> listImageId=new ArrayList<>();
    private List<String> listImageUrl=new ArrayList<>();
    private List<String> listZipImageUrl=new ArrayList<>();
    private String[] items = new String[]{"本地图片", "拍照"};

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int WANT_TRANSFER_TYPE_CODE = 3;
    private Kind kind;
    private int imageFlag=-1;
    private ImgDao idao;
    private String TRANSFER_ACTIVITYID="501";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.inject(this);
        // 隐藏输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();

    }

    private void init() {
        idao=new ImgDao(this);
        listBitmap=new ArrayList<>();
        listImgStr=new ArrayList<>();
        listImg=new ArrayList<>();
        listImg.add(iv01_second_hand_transfer);
        listImg.add(iv02_second_hand_transfer);
        listImg.add(iv03_second_hand_transfer);
        iv01_second_hand_transfer.setOnClickListener(this);
        iv02_second_hand_transfer.setOnClickListener(this);
        iv03_second_hand_transfer.setOnClickListener(this);
        rl_wang_transfer.setOnClickListener(this);
        dao=new UsedGoodsDao(this);
    /*    String title=et_baobei_title.getText().toString();
        String content=et_baobei_detail.getText().toString();
        String transferPrice=et_baobei_transferprice.getText().toString();
        String originalprice=et_baobei_originalprice.getText().toString();
        String contact=et_contact.getText().toString();
        String contactPhone=et_contact_telephone.getText().toString();*/

        iv01_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImageUrl.size() >= 1) {
                    imageFlag = 1;
                    showDelDialog(1);
                }
                return true;
            }
        });
        iv02_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImageUrl.size() >= 2) {
                    imageFlag = 2;
                    showDelDialog(2);
                }
                return true;
            }
        });
        iv03_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImageUrl.size() >= 3) {
                    imageFlag = 3;
                    showDelDialog(3);
                }
                return true;
            }
        });
    }

    private void showDelDialog(final int posititon) {
        new AlertDialog.Builder(this)
                .setTitle("确定删除吗？")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                idao.requestDeleteImg(listImageId.get(posititon-1));
                android.util.Log.d("demo", "imageId : " + listImage.get(posititon - 1).getId());

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
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==0){
            showProgress(false);
            MessageUtils.showShortToast(this, "图片上传成功");
            listImage.add(idao.getGoodPic());
            listImageUrl.add(idao.getGoodPic().getOriginalImg());
            listImageId.add(idao.getGoodPic().getId());
            listZipImageUrl.add(idao.getGoodPic().getZipImgUrl());
            setBitmapImg();

        }
        if(requestCode==1){
            MessageUtils.showShortToast(this,"图片删除成功");
            if(imageFlag==1){
                listImageId.remove(0);
                listZipImageUrl.remove(0);
                delImg(1);
            }
            if(imageFlag==2){
                listZipImageUrl.remove(1);
                listImageId.remove(1);
                delImg(2);
            }
            if(imageFlag==3){
                listZipImageUrl.remove(2);
                listImageId.remove(2);
                delImg(3);
            }

        }
        if(requestCode==99){
            MessageUtils.showShortToast(this, "信息发布成功");
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==rl_wang_transfer){
            Intent intent=new Intent(TransferActivity.this,ClassifyActivity.class);
            intent.addFlags(StrConstant.WANT_BUY_TYPE);
            startActivityForResult(intent,WANT_TRANSFER_TYPE_CODE);
            AnimUtil.intentSlidIn(TransferActivity.this);
        }
        if(v == iv01_second_hand_transfer){
            if(listImageUrl.size() >= 1){
                //
            } else {
                showDialog();
            }

            //setBitmapImg();
        }

        if(v == iv02_second_hand_transfer){
            if(listImageUrl.size() >= 2){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }

        if(v == iv03_second_hand_transfer){
            if(listImageUrl.size() >= 3){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }

    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("添加图片")
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
    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    if (data != null) {
                        Uri uri = data.getData();
                        String thePath = Utils.getInstance().getPath(this, uri);
                        getImageToView1(thePath);
                    }
                    break;
                case CAMERA_REQUEST_CODE:

                    getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");

                    break;
          /*  case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;*/
                case WANT_TRANSFER_TYPE_CODE:
                    if (resultCode == RESULT_OK) {
                        kind = (Kind) data.getSerializableExtra("kind");
                        tv_secong_hand_transfer_type.setText(kind.getKindName());
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e){
            e.printStackTrace();
        }
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
                Log.d("demo", "上传方法2");
                /**调用后台方法  将图片上传**/
                showProgress(true);
                idao.requestData(TRANSFER_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
                //imgDao.requestUploadImg(activityId, imgData, typeKey, optMode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param uri
     */
    private void getImageToView(Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);//压缩比例50%
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);
            Drawable drawable = new BitmapDrawable(bitmap);


            //userIcon.setImageDrawable(drawable);
            if (photo != null){
                // showProgress(true);
                //dao.requestModifyHeadImg(photo);
                // listImgStr.add(photo);
                idao.requestData(TRANSFER_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
              // listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }




      /*  Bundle extras = data.getExtras();
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

            Drawable drawable = new BitmapDrawable(bitmap);
            //userIcon.setImageDrawable(drawable);
            if (photo != null){
               // showProgress(true);
                //dao.requestModifyHeadImg(photo);
                listBitmap.add(bitmap);
                listImgStr.add(photo);

                idao.requestData("1", photo, StrConstant.SECOND_MARKET_IMAGE_APPKEY, "1");
                showProgress(true);
               // setBitmapImg();
            }
        }*/
    }

    private void setBitmapImg(){

        if(listImageUrl != null){
            if(listImageUrl.size() == 1){
                iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
                int w=iv01_second_hand_transfer.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv01_second_hand_transfer.setLayoutParams(params);

              /*  for(int i = 0 ; i < listBitmap.size() ; i ++){
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                     listZipImageUrl.add(listImage.get(i).getZipImgUrl());

                    *//*Arad.imageLoader.load(listImage.get(i).getZipImgUrl()==null ||listImage.get(i).equals("")?"aa":listImage.get(i).getZipImgUrl())
                            .placeholder(R.drawable.green_add_img_icon).into(listImg.get(i));*//*
                }*/

                for(int i = 0 ; i < listImageUrl.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImageUrl.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }

            }

            if(listImageUrl.size() == 2){
               iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);

                ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
                int w=iv01_second_hand_transfer.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv01_second_hand_transfer.setLayoutParams(params);
                iv02_second_hand_transfer.setLayoutParams(params);

            /*    for(int i = 0 ; i < listBitmap.size() ; i ++){
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                    listZipImageUrl.add(listImage.get(i).getZipImgUrl());

                   // Arad.imageLoader.load(listImage.get(i).getZipImgUrl()).into(listImg.get(i));

                }*/

                for(int i = 0 ; i < listImageUrl.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImageUrl.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }

            }

            if(listImageUrl.size() == 3){
                iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
                int w=iv01_second_hand_transfer.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv01_second_hand_transfer.setLayoutParams(params);
                iv02_second_hand_transfer.setLayoutParams(params);
                iv03_second_hand_transfer.setLayoutParams(params);

            /*    for(int i = 0 ; i < listBitmap.size() ; i ++){
                   //listImg.get(i).setImageBitmap(listBitmap.get(i));
                    listZipImageUrl.add(listImage.get(i).getZipImgUrl());
                  //  Arad.imageLoader.load(listImage.get(i).getZipImgUrl()).into(listImg.get(i));
                }*/
                for(int i = 0 ; i < listImageUrl.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImageUrl.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }
            }
        } else {
            iv01_second_hand_transfer.setVisibility(View.VISIBLE);
            iv01_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
            iv02_second_hand_transfer.setVisibility(View.INVISIBLE);
            iv03_second_hand_transfer.setVisibility(View.INVISIBLE);
        }
    }

    private void delImg(int imgFlg){
        if(listImageUrl != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listImageUrl.size() == 1){

                  // listBitmap.remove(0);
                    listImageUrl.remove(0);
                    //listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv01_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv02_second_hand_transfer.setVisibility(View.INVISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                    setBitmapImg();

                }

                if(listImageUrl.size() == 2){
                   // listBitmap.remove(0);
                    listImageUrl.remove(0);
                    //listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImageUrl.size() == 3){
                   // listBitmap.remove(0);
                    listImageUrl.remove(0);
                    //listImage.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
                }
            }

            /**长按 第二张图*/
            if(imgFlg == 2){
                if(listImageUrl.size() == 1){

//                    listBitmap.remove(0);
//                    listImgStr.remove(0);
//
//                    repair_pic1.setVisibility(View.VISIBLE);
//                    repair_pic1.setImageResource(R.drawable.repair_add_pic);
//                    repair_pic2.setVisibility(View.GONE);
//                    repair_pic3.setVisibility(View.GONE);
//
//                    setBitmapImg();

                }

                if(listImageUrl.size() == 2){
                  //listBitmap.remove(1);
                   // listImgStr.remove(1);
                    listImageUrl.remove(1);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImageUrl.size() == 3){
                  // listBitmap.remove(1);
                   // listImgStr.remove(1);
                    listImageUrl.remove(1);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
                }
            }


            /**长按 第三张图*/
            if(imgFlg == 3){
                if(listImageUrl.size() == 1){

//                    listBitmap.remove(0);
//                    listImgStr.remove(0);
//
//                    repair_pic1.setVisibility(View.VISIBLE);
//                    repair_pic1.setImageResource(R.drawable.repair_add_pic);
//                    repair_pic2.setVisibility(View.GONE);
//                    repair_pic3.setVisibility(View.GONE);
//
//                    setBitmapImg();

                }

                if(listImageUrl.size() == 2){
//                    listBitmap.remove(1);
//                    listImgStr.remove(1);
//
//                    repair_pic1.setVisibility(View.VISIBLE);
//                    repair_pic2.setVisibility(View.VISIBLE);
//                    repair_pic2.setImageResource(R.drawable.repair_add_pic);
//                    repair_pic3.setVisibility(View.GONE);
//
//                    setBitmapImg();
                }

                if(listImageUrl.size() == 3){
                  //  listBitmap.remove(2);
                   // listImgStr.remove(2);
                    listImageUrl.remove(2);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
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
        return "我要转";
    }


    /**
     * header 右侧按钮
     * */

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     startActivity(new Intent(TransferActivity.this,DetailToTransferActivity.class));
                AnimUtil.intentSlidIn(TransferActivity.this);*/
                if (TextUtils.isEmpty(et_baobei_title.getText().toString())) {
                    MessageUtils.showShortToast(TransferActivity.this, "标题为空");
                    return;
                }
                if (TextUtils.isEmpty(et_baobei_detail.getText().toString())) {
                    MessageUtils.showShortToast(TransferActivity.this, "描述为空");
                    return;
                }
                if (TextUtils.isEmpty(et_baobei_transferprice.getText().toString())) {
                    MessageUtils.showShortToast(TransferActivity.this, "价格为空");
                    return;
                }
                if (kind.getId() == null || kind.getId().equals("")) {
                    MessageUtils.showShortToast(TransferActivity.this, "请选择分类");
                    return;
                }
                if (TextUtils.isEmpty(et_contact.getText().toString())) {
                    MessageUtils.showShortToast(TransferActivity.this, "联系人为空");
                    return;
                }
                if (TextUtils.isEmpty(et_contact_telephone.getText().toString())) {
                    MessageUtils.showShortToast(TransferActivity.this, "联系电话为空");
                    return;
                }
                if (listImageUrl.size() == 1) {
                    dao.requestGoodsAdd(AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getMemberInfo().getMemberId(),
                            AppHolder.getInstance().getProprietor().getProprietorId(),
                            et_baobei_title.getText().toString(),
                            et_baobei_detail.getText().toString(),
                            et_contact.getText().toString(),
                            et_contact_telephone.getText().toString(),
                            et_baobei_transferprice.getText().toString(),
                            kind.getId(),
                            et_baobei_originalprice.getText().toString(),
                            listImageUrl.get(0) + "," +listZipImageUrl.get(0), "", "",
                            StrConstant.WANT_TRANSFER_TRANSACTION_TYPE,
                            StrConstant.VISIBLE_TYPE
                    );
                }
                if (listImageUrl.size() == 2) {
                    dao.requestGoodsAdd(AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getMemberInfo().getMemberId(),
                            AppHolder.getInstance().getProprietor().getProprietorId(),
                            et_baobei_title.getText().toString(),
                            et_baobei_detail.getText().toString(),
                            et_contact.getText().toString(),
                            et_contact_telephone.getText().toString(),
                            et_baobei_transferprice.getText().toString(),
                            kind.getId(),
                            et_baobei_originalprice.getText().toString(),
                            listImageUrl.get(0) + "," + listZipImageUrl.get(0),
                            listImageUrl.get(1) + "," + listZipImageUrl.get(1), "",
                            StrConstant.WANT_TRANSFER_TRANSACTION_TYPE,
                            StrConstant.VISIBLE_TYPE
                    );
                }
                if (listImageUrl.size() == 3) {
                    dao.requestGoodsAdd(AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getMemberInfo().getMemberId(),
                            AppHolder.getInstance().getProprietor().getProprietorId(),
                            et_baobei_title.getText().toString(),
                            et_baobei_detail.getText().toString(),
                            et_contact.getText().toString(),
                            et_contact_telephone.getText().toString(),
                            et_baobei_transferprice.getText().toString(),
                            kind.getId(),
                            et_baobei_originalprice.getText().toString(),
                            listImageUrl.get(0) + "," + listZipImageUrl.get(0),
                            listImageUrl.get(1) + "," + listZipImageUrl.get(1),
                            listImageUrl.get(2) + "," + listZipImageUrl.get(2),
                            StrConstant.WANT_TRANSFER_TRANSACTION_TYPE,
                            StrConstant.VISIBLE_TYPE
                    );
                }
                if (listZipImageUrl.size() == 0) {
                    dao.requestGoodsAdd(AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getMemberInfo().getMemberId(),
                            AppHolder.getInstance().getProprietor().getProprietorId(),
                            et_baobei_title.getText().toString(),
                            et_baobei_detail.getText().toString(),
                            et_contact.getText().toString(),
                            et_contact_telephone.getText().toString(),
                            et_baobei_transferprice.getText().toString(),
                            kind.getId(),
                            et_baobei_originalprice.getText().toString(),
                            "", "", "",
                            StrConstant.WANT_TRANSFER_TRANSACTION_TYPE,
                            StrConstant.VISIBLE_TYPE
                    );
                }

            }
        });
        return true;
    }
}

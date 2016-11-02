package com.ctrl.android.property.jason.ui.repairs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Utils;
import com.ctrl.android.property.eric.entity.House;
import com.ctrl.android.property.eric.ui.house.HouseListActivity2;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.jason.dao.ClassDao;
import com.ctrl.android.property.jason.dao.ImgDao;
import com.ctrl.android.property.jason.dao.RepairDao;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.Kind;
import com.ctrl.android.property.jason.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 我的报修  （添加）activity
* */

public class MyRepairsAddActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_change)
    TextView tv_change;
    @InjectView(R.id.tv_my_repairs_room)
    TextView tv_my_repairs_room;
    @InjectView(R.id.spinner_repairs_add)
    Spinner spinner_repairs_add;
    @InjectView(R.id.iv01_second_hand_transfer)
    ImageView iv01_second_hand_transfer;
    @InjectView(R.id.iv02_second_hand_transfer)
    ImageView iv02_second_hand_transfer;
    @InjectView(R.id.iv03_second_hand_transfer)
    ImageView iv03_second_hand_transfer;
    @InjectView(R.id.et_repairs_add)
    EditText et_repairs_add;

    List<Kind>list=new ArrayList<>();
    List<GoodPic>mGoodPicList=new ArrayList<>();
    List<String>spinnerlist=new ArrayList<>();
    List<String>mGoodPicId=new ArrayList<>();
    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private String[] items = new String[]{"本地图片", "拍照"};
    private int imageFlag=-1;

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int REPAIRS_ROOM_CODE = 3;
    private ClassDao cDao;
    private ImgDao iDao;
    private RepairDao rdao;
    private House house;
    private String kindId;
    private String REPAIRS_ADD_ACTIVITYID="500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repairs_add);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        iDao=new ImgDao(this);
        rdao=new RepairDao(this);
        listBitmap=new ArrayList<>();
        listImgStr=new ArrayList<>();
        listImg=new ArrayList<>();
        listImg.add(iv01_second_hand_transfer);
        listImg.add(iv02_second_hand_transfer);
        listImg.add(iv03_second_hand_transfer);
        iv01_second_hand_transfer.setOnClickListener(this);
        iv02_second_hand_transfer.setOnClickListener(this);
        iv03_second_hand_transfer.setOnClickListener(this);
        tv_change.setOnClickListener(this);

        cDao=new ClassDao(this);
        cDao.requestData(StrConstant.REPAIRS_TYPE_APPKEY);


        iv01_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImgStr.size() >= 1) {
                    imageFlag = 1;
                    showDelDialog(1);
                }
                return true;
            }
        });
        iv02_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImgStr.size() >= 2) {
                    imageFlag = 2;
                    showDelDialog(2);
                }
                return true;
            }
        });
        iv03_second_hand_transfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listImgStr.size() >= 3) {
                    imageFlag = 3;
                    showDelDialog(3);
                }
                return true;
            }
        });
        spinner_repairs_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kindId=list.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void showDelDialog(final int posititon) {
        new AlertDialog.Builder(this)
                .setTitle("确定删除吗？")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        iDao.requestDeleteImg(mGoodPicId.get(posititon-1));
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
    protected void onResume() {
        super.onResume();
        if(AppHolder.getInstance().getHouse().getId() != null){
            //my_community_name.setText(S.getStr(AppHolder.getInstance().getHouse().getCommunityName()));
            tv_my_repairs_room.setText(S.getStr(AppHolder.getInstance().getHouse().getCommunityName())
                    + " " +AppHolder.getInstance().getHouse().getBuilding()
                    + "楼" + AppHolder.getInstance().getHouse().getUnit()
                    + "单元" + AppHolder.getInstance().getHouse().getRoom() + "室");
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==0){
            showProgress(false);
            MessageUtils.showShortToast(this, "图片上传成功");
            listImgStr.add(iDao.getGoodPic().getOriginalImg());

            mGoodPicList.add(iDao.getGoodPic());
            mGoodPicId.add(iDao.getGoodPic().getId());
            setBitmapImg();
        }
        if(requestCode==1){
            MessageUtils.showShortToast(this, "图片删除成功");
            if(imageFlag==1){
                mGoodPicId.remove(0);
                mGoodPicList.remove(0);
                delImg(1);
            }
            if(imageFlag==2){
                mGoodPicId.remove(1);
                mGoodPicList.remove(1);
                delImg(2);
            }
            if (imageFlag==3){
                mGoodPicId.remove(2);
                mGoodPicList.remove(2);
                delImg(3);
            }

        }
        if(requestCode==2){
            MessageUtils.showShortToast(this, "报修发起成功");
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();
        }

        if(requestCode==4){
            list=cDao.getData();
            for(int i=0;i<list.size();i++){
                spinnerlist.add(list.get(i).getKindName());
            }
            tv_change.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerlist);
            spinner_repairs_add.setAdapter(adapter);
         }
    }

    @Override
    public void onClick(View v) {
        if(v==tv_change){
            Intent intent=new Intent(MyRepairsAddActivity.this, HouseListActivity2.class);
            intent.addFlags(StrConstant.COMPLAINT_ROOM_CHANGE);
            startActivityForResult(intent, REPAIRS_ROOM_CODE);
        }
        if(v == iv01_second_hand_transfer){
            if(listImgStr.size() >= 1){
                //
            } else {
                showDialog();
            }

            //setBitmapImg();
        }

        if(v == iv02_second_hand_transfer){
            if(listImgStr.size() >= 2){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }

        if(v == iv03_second_hand_transfer){
            if(listImgStr.size() >= 3){
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
                    if(resultCode==-1)
                    getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");

                    break;
         /*   case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                 break;*/
                case REPAIRS_ROOM_CODE:
                    if (resultCode == RESULT_OK) {
                        Bundle bundle = data.getBundleExtra("bundle");
                        house = (House) bundle.getSerializable("house");
                        tv_my_repairs_room.setText(house.getCommunityName() + house.getBuilding()+"楼" + house.getUnit() +"单元"+ house.getRoom()+"号房");
                    }

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
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

    private void getImageToView1(String path) {
        Bitmap bitmap ;
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(path,options);
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
                iDao.requestData(REPAIRS_ADD_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
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
                iDao.requestData(REPAIRS_ADD_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
                //listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        /*Bundle extras = data.getExtras();
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
               *//* listBitmap.add(bitmap);
                listImgStr.add(photo);

                setBitmapImg();*//*
                iDao=new ImgDao(this);
                iDao.requestData("1", photo,StrConstant.REPAIRS_TYPE_APPKEY,"1");
                showProgress(true);
                listBitmap.add(bitmap);

            }
        }*/
    }

    private void setBitmapImg(){

        //MessageUtils.showLongToast(this, "size: " + listImgStr.size());

        if(listImgStr != null){
            if(listImgStr.size() == 1){
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

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //listImg.get(i).setImageBitmap(listImgStr.get(i));
                }

            }

            if(listImgStr.size() == 2){
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

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                }
            }

            if(listImgStr.size() == 3){
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

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
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
        if(listImgStr != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listImgStr.size() == 1){

                    //listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv01_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv02_second_hand_transfer.setVisibility(View.INVISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);



                    setBitmapImg();

                }

                if(listImgStr.size() == 2){
                    //listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);


                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);

                    iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);


                    setBitmapImg();
                }
            }

            /**长按 第二张图*/
            if(imgFlg == 2){
                if(listImgStr.size() == 1){

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

                if(listImgStr.size() == 2){
                    //listBitmap.remove(1);
                    listImgStr.remove(1);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listBitmap.remove(1);
                    listImgStr.remove(1);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
                }
            }


            /**长按 第三张图*/
            if(imgFlg == 3){
                if(listImgStr.size() == 1){

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

                if(listImgStr.size() == 2){
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

                if(listImgStr.size() == 3){
                    //listBitmap.remove(2);
                    listImgStr.remove(2);

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
        return "报修详情";
    }

    /**
     *右侧文本
     */
    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(MyRepairsAddActivity.this, SecondHandActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidIn(MyRepairsAddActivity.this);*/
                if (kindId == null || kindId.equals("")) {
                    MessageUtils.showShortToast(MyRepairsAddActivity.this, "请选择类型");
                    return;
                }
                if (TextUtils.isEmpty(et_repairs_add.getText().toString())) {
                    MessageUtils.showShortToast(MyRepairsAddActivity.this, "报修内容为空");
                    return;
                }
                if (TextUtils.isEmpty(tv_my_repairs_room.getText().toString())) {
                    MessageUtils.showShortToast(MyRepairsAddActivity.this, "请选择房间");
                    return;
                }
                if (listImgStr.size() == 1) {
                    rdao.requestRepairAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kindId,
                            et_repairs_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            "",
                            "");
                    return;

                }
                if (listImgStr.size() == 2) {
                    rdao.requestRepairAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kindId,
                            et_repairs_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            listImgStr.get(1)+","+mGoodPicList.get(1).getZipImgUrl(),
                            "");
                    return;

                }
                if (listImgStr.size() == 3) {

                    rdao.requestRepairAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kindId,
                            et_repairs_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            listImgStr.get(1)+","+mGoodPicList.get(1).getZipImgUrl(),
                            listImgStr.get(2)+","+mGoodPicList.get(2).getZipImgUrl());
                    return;
                }

                if (listImgStr.size() == 0) {
                    rdao.requestRepairAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kindId,
                            et_repairs_add.getText().toString(),
                            "",
                            "",
                            "");

                }
            }
        });
        return true;
    }

}

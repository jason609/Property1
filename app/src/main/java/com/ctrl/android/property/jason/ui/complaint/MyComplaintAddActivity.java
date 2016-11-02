package com.ctrl.android.property.jason.ui.complaint;

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
import com.ctrl.android.property.jason.dao.ComplaintDao;
import com.ctrl.android.property.jason.dao.ImgDao;
import com.ctrl.android.property.jason.entity.GoodPic;
import com.ctrl.android.property.jason.entity.Kind;
import com.ctrl.android.property.jason.util.StrConstant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyComplaintAddActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_change)
    TextView tv_change;
    @InjectView(R.id.spinner_complaint_add)
    Spinner spinner_complaint_add;
    @InjectView(R.id.iv01_my_complaint_add)
    ImageView iv01_my_complaint_add;
    @InjectView(R.id.iv02_my_complaint_add)
    ImageView iv02_my_complaint_add;
    @InjectView(R.id.iv03_my_complaint_add)
    ImageView iv03_my_complaint_add;
    @InjectView(R.id.et_my_complaint_add)
    EditText et_my_complaint_add;
    @InjectView(R.id.tv_my_complaint_add_room)
    TextView tv_my_complaint_add_room;

    List<Kind>list=new ArrayList<>();
    List<GoodPic>mGoodPicList=new ArrayList<>();
    List<String>mGoodPicId=new ArrayList<>();
    List<String>mlist=new ArrayList<>();
    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private String[] items = new String[]{"本地图片", "拍照"};

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int CHOOSE_ROOM_CODE = 3;
    private static final String COMPLAINT_ADD_ACTIVITYID = "1";
    private ComplaintDao dao;
    private ClassDao mdao;
    private Kind kind;
    private ImgDao idao;
    private GoodPic goodPic;
    private int imageFlag=-1;
    private String kingId;
    private House house;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint_add);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        idao=new ImgDao(this);
        mdao=new ClassDao(this);
        mdao.requestData(StrConstant.COMPLAINT_IMAGE_APPKEY);
        listBitmap=new ArrayList<>();
        listImgStr=new ArrayList<>();
        listImg=new ArrayList<>();
        listImg.add(iv01_my_complaint_add);
        listImg.add(iv02_my_complaint_add);
        listImg.add(iv03_my_complaint_add);
        tv_change.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        iv01_my_complaint_add.setOnClickListener(this);
        iv02_my_complaint_add.setOnClickListener(this);
        iv03_my_complaint_add.setOnClickListener(this);
        tv_change.setOnClickListener(this);

                iv01_my_complaint_add.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (listImgStr.size() >= 1) {
                            imageFlag = 1;
                            showDelDialog(1);
                        }
                        return true;

                    }
                });

            iv02_my_complaint_add.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listImgStr.size()>=2) {
                        imageFlag = 2;
                        showDelDialog(2);
                    }
                    return true;
                }
            });
            iv03_my_complaint_add.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listImgStr.size()>=3) {
                        imageFlag = 3;
                        showDelDialog(3);
                    }
                    return true;
                }
            });

        spinner_complaint_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kingId=list.get(position).getId();
                android.util.Log.d("demo", "kindId: " + kingId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppHolder.getInstance().getHouse().getId() != null){
            //my_community_name.setText(S.getStr(AppHolder.getInstance().getHouse().getCommunityName()));
            tv_my_complaint_add_room.setText(S.getStr(AppHolder.getInstance().getHouse().getCommunityName())
                    + " " +AppHolder.getInstance().getHouse().getBuilding()
                    + "楼" + AppHolder.getInstance().getHouse().getUnit()
                    + "单元" + AppHolder.getInstance().getHouse().getRoom() + "室");
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==0){
            MessageUtils.showShortToast(this,"图片上传成功");
            showProgress(false);
            goodPic=idao.getGoodPic();
            listImgStr.add(goodPic.getOriginalImg());
            mGoodPicList.add(idao.getGoodPic());
            mGoodPicId.add(goodPic.getId());
           setBitmapImg();

        }
        if(requestCode==1){
            MessageUtils.showShortToast(this, "图片删除成功");
            if(imageFlag==1) {
                mGoodPicId.remove(0);
               // listImgStr.remove(0);
                delImg(1);
               // android.util.Log.d("demo", "mGoodPicList : " + mGoodPicId.size());

            }
            if(imageFlag==2) {
                mGoodPicId.remove(1);
                //listImgStr.remove(1);
                delImg(2);
               // android.util.Log.d("demo","mGoodPicList : " +mGoodPicId.size());

            }
            if(imageFlag==3){
                mGoodPicId.remove(2);
              //  listImgStr.remove(2);
                delImg(3);
                //android.util.Log.d("demo","mGoodPicList : " +mGoodPicId.size());
            }
        }
        if(requestCode==2){
            MessageUtils.showShortToast(this, "投诉发起成功");
            Intent intent=new Intent();
            setResult(4001,intent);
            finish();
        }

        if(requestCode==4){
            list=mdao.getData();
            for(int i=0;i<list.size();i++){
                kind=list.get(i);
                mlist.add(kind.getKindName());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mlist);
            spinner_complaint_add.setAdapter(adapter);
        }

    }

    private void showDelDialog(final int posititon) {
        new AlertDialog.Builder(this)
                .setTitle("确定删除吗？")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idao.requestDeleteImg(mGoodPicId.get(posititon - 1));
                        android.util.Log.d("demo","id : " +mGoodPicId.get(posititon - 1));
                      // delImg(posititon);
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
    public void onClick(View v) {
        if(v==tv_change){
           Intent intent= new Intent(MyComplaintAddActivity.this, HouseListActivity2.class);
            intent.addFlags(StrConstant.COMPLAINT_ROOM_CHANGE);
            startActivityForResult(intent, CHOOSE_ROOM_CODE);
        }
        if(v == iv01_my_complaint_add){
            if(listImgStr.size() >= 1){
                //
            } else {
                showDialog();
            }

            //setBitmapImg();
        }

        if(v == iv02_my_complaint_add){
            if(listImgStr.size() >= 2){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }

        if(v == iv03_my_complaint_add){
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
                    Log.i("tag","result code===="+resultCode);
                    if(resultCode==-1)
                    getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");
                    break;
          /*  case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;*/
                case CHOOSE_ROOM_CODE:
                    if (resultCode == RESULT_OK) {
                        Bundle bundle = data.getBundleExtra("bundle");
                        house = (House) bundle.getSerializable("house");

                        tv_my_complaint_add_room.setText(house.getCommunityName() + house.getBuilding()+"楼" + house.getUnit()+"单元" + house.getRoom()+"号房");
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
                idao.requestData(COMPLAINT_ADD_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
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
                idao.requestData(COMPLAINT_ADD_ACTIVITYID, photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
                //listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

       /* Bundle extras = data.getExtras();
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
               // listImgStr.add(photo);
                idao.requestData(flag+"", photo, StrConstant.COMPLAINT_IMAGE_APPKEY, "1");
                flag++;
                showProgress(true);
               // setBitmapImg();
            }
        }*/
    }
/*
* 按比例大小压缩图片
*
* */
    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
   /*
   * 按质量压缩图片
   * */
    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }



    private void setBitmapImg(){


        if(listImgStr != null){
            if(listImgStr.size() == 1){
                iv01_my_complaint_add.setVisibility(View.VISIBLE);
                iv02_my_complaint_add.setVisibility(View.VISIBLE);
                iv02_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);
                iv03_my_complaint_add.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params = iv01_my_complaint_add.getLayoutParams();
                int w=iv01_my_complaint_add.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
               // android.util.Log.d("demo", "height : " + params.height);
                iv01_my_complaint_add.setLayoutParams(params);

                //idao=new ImgDao(this);
                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                   // idao.requestData(null,listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY,"0");
                }

            }

            if(listImgStr.size() == 2){
                iv01_my_complaint_add.setVisibility(View.VISIBLE);
                iv02_my_complaint_add.setVisibility(View.VISIBLE);
                iv03_my_complaint_add.setVisibility(View.VISIBLE);
                iv03_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);

                ViewGroup.LayoutParams params = iv01_my_complaint_add.getLayoutParams();
                int w=iv01_my_complaint_add.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv01_my_complaint_add.setLayoutParams(params);
                iv02_my_complaint_add.setLayoutParams(params);

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }
            }

            if(listImgStr.size() == 3){
                iv01_my_complaint_add.setVisibility(View.VISIBLE);
                iv02_my_complaint_add.setVisibility(View.VISIBLE);
                iv03_my_complaint_add.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = iv01_my_complaint_add.getLayoutParams();
                int w=iv01_my_complaint_add.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv01_my_complaint_add.setLayoutParams(params);
                iv02_my_complaint_add.setLayoutParams(params);
                iv03_my_complaint_add.setLayoutParams(params);

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }
            }
        } else {
            iv01_my_complaint_add.setVisibility(View.VISIBLE);
            iv01_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);
            iv02_my_complaint_add.setVisibility(View.INVISIBLE);
            iv02_my_complaint_add.setVisibility(View.INVISIBLE);
        }
    }

    private void delImg(int imgFlg){
        if(listImgStr != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listImgStr.size() == 1){

                    //listImgStr.remove(0);
                    listImgStr.remove(0);
                    mGoodPicList.remove(0);

                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv01_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);
                    iv02_my_complaint_add.setVisibility(View.INVISIBLE);
                    iv03_my_complaint_add.setVisibility(View.INVISIBLE);

                    setBitmapImg();

                }

                if(listImgStr.size() == 2){
                    //listImgStr.remove(0);
                    listImgStr.remove(0);
                    mGoodPicList.remove(0);


                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);
                    iv03_my_complaint_add.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listImgStr.remove(0);
                    listImgStr.remove(0);
                    mGoodPicList.remove(0);


                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setVisibility(View.VISIBLE);
                    iv03_my_complaint_add.setVisibility(View.VISIBLE);
                    iv03_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);

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
                    //listImgStr.remove(1);
                    listImgStr.remove(1);
                    mGoodPicList.remove(1);


                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);
                    iv03_my_complaint_add.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listImgStr.remove(1);
                    listImgStr.remove(1);
                    mGoodPicList.remove(1);


                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setVisibility(View.VISIBLE);
                    iv03_my_complaint_add.setVisibility(View.VISIBLE);
                    iv03_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);

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
                    //listImgStr.remove(2);
                    listImgStr.remove(2);
                    mGoodPicList.remove(2);


                    iv01_my_complaint_add.setVisibility(View.VISIBLE);
                    iv02_my_complaint_add.setVisibility(View.VISIBLE);
                    iv03_my_complaint_add.setImageResource(R.drawable.green_add_img_icon);

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
        return "投诉详情";
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
                if (TextUtils.isEmpty(tv_my_complaint_add_room.getText().toString())) {
                    MessageUtils.showShortToast(MyComplaintAddActivity.this, "请选择房间");
                    return;
                }
                if (TextUtils.isEmpty(et_my_complaint_add.getText().toString())) {
                    MessageUtils.showShortToast(MyComplaintAddActivity.this, "内容为空");
                    return;
                }
                if (kind.getId() == null || kind.getId().equals("")) {
                    MessageUtils.showShortToast(MyComplaintAddActivity.this, "请选择类型");
                    return;
                }
                dao = new ComplaintDao(MyComplaintAddActivity.this);
              /*  int position=mlist.get();
                android.util.Log.d("demo", "spinner position: " + position);
                String kindId=list.get(spinner_complaint_add.getId()-1).getId();*/

                if (listImgStr.size() == 1) {
                    dao.requestComplaintAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kingId,
                            et_my_complaint_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            "",
                            "");
                }
                if (listImgStr.size() == 2) {
                    dao.requestComplaintAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kingId,
                            et_my_complaint_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            listImgStr.get(1)+","+mGoodPicList.get(1).getZipImgUrl(),
                            "");
                }
                if (listImgStr.size() == 3) {
                    dao.requestComplaintAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kingId,
                            et_my_complaint_add.getText().toString(),
                            listImgStr.get(0)+","+mGoodPicList.get(0).getZipImgUrl(),
                            listImgStr.get(1)+","+mGoodPicList.get(1).getZipImgUrl(),
                            listImgStr.get(2)+","+mGoodPicList.get(2).getZipImgUrl());
                }
                if (listImgStr.size() == 0) {

                    dao.requestComplaintAdd(AppHolder.getInstance().getProprietor().getProprietorId(),
                            AppHolder.getInstance().getCommunity().getId(),
                            AppHolder.getInstance().getHouse().getAddressId(),
                            kingId,
                            et_my_complaint_add.getText().toString(),
                            "",
                            "",
                            "");
                }
            }
        });
        return true;
    }

}

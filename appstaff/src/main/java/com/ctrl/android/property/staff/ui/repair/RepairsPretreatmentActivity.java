package com.ctrl.android.property.staff.ui.repair;

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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.dao.RepairDao;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.StrConstant;
import com.ctrl.android.property.staff.util.TimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*我的报修（处理前）activity*/

public class RepairsPretreatmentActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.tv_my_repairs_pretreament_progress)
    TextView tv_my_repairs_pretreament_progress;
    @InjectView(R.id.tv_my_repairs_pretreament_time)
    TextView tv_my_repairs_pretreament_time;
    @InjectView(R.id.tv_my_repairs_pretreament_type)
    TextView tv_my_repairs_pretreament_type;
    @InjectView(R.id.tv_my_repairs_pretreament_content)
    TextView tv_my_repairs_pretreament_content;
    @InjectView(R.id.tv_my_repairs_pretreament_wuye)
    TextView tv_my_repairs_pretreament_wuye;
    @InjectView(R.id.tv_my_repairs_pretreament_result)
    EditText tv_my_repairs_pretreament_result;
    @InjectView(R.id.iv01_my_repairs_pretreament)
    ImageView iv01_my_repairs_pretreament;
    @InjectView(R.id.iv02_my_repairs_pretreament)
    ImageView iv02_my_repairs_pretreament;
    @InjectView(R.id.iv03_my_repairs_pretreament)
    ImageView iv03_my_repairs_pretreament;
    @InjectView(R.id.iv04_my_repairs_pretreament)
    ImageView iv04_my_repairs_pretreament;
    @InjectView(R.id.iv05_my_repairs_pretreament)
    ImageView iv05_my_repairs_pretreament;
    @InjectView(R.id.iv06_my_repairs_pretreament)
    ImageView iv06_my_repairs_pretreament;
    @InjectView(R.id.ll_my_repairs_wuye)
    LinearLayout ll_my_repairs_wuye;
//    @InjectView(R.id.ll_my_repairs_pingjia)
//    LinearLayout ll_my_repairs_pingjia;
    @InjectView(R.id.tv_baoxiu_image)
    TextView tv_baoxiu_image;
    @InjectView(R.id.tv_wuye_image)
    TextView tv_wuye_image;


    @InjectView(R.id.sc_repair)//滚动布局
    ScrollView sc_repair;

    @InjectView(R.id.tv_repairs_accept)//我要接单
    TextView tv_repairs_accept;
    @InjectView(R.id.tv_repairs_chargeback)//我要拒单
    TextView tv_repairs_chargeback;

    @InjectView(R.id.tv_repairs_back)//我要退单
    TextView tv_repairs_back;

    @InjectView(R.id.ll_repair_selecte)//接单拒单布局
    LinearLayout ll_repair_selecte;

    @InjectView(R.id.tv_repairs_room)//报修房间
    TextView tv_repairs_room;

//    @InjectView(R.id.et_my_repairs_pretreament_pingjia)
//    TextView et_my_repairs_pretreament_pingjia;
    private RepairDao dao;

    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private String[] items = new String[]{"本地图片", "拍照"};
    private int imageFlag=-1;

    List<Img2>mGoodPicList=new ArrayList<>();
    List<String>mGoodPicId=new ArrayList<>();



    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final String REPAIRS_PRETREATMENT = "REPAIRS_PRETREATMENT";
    private ImgDao iDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_repairs_pretreatment);
        ButterKnife.inject(this);
        //隐藏输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
    }

    private void init() {

        iv01_my_repairs_pretreament.setOnClickListener(this);
        iv02_my_repairs_pretreament.setOnClickListener(this);
        iv03_my_repairs_pretreament.setOnClickListener(this);
        iv04_my_repairs_pretreament.setOnClickListener(this);
        iv05_my_repairs_pretreament.setOnClickListener(this);
        iv06_my_repairs_pretreament.setOnClickListener(this);
        tv_repairs_chargeback.setOnClickListener(this);
        tv_repairs_accept.setOnClickListener(this);

        listImg=new ArrayList<>();
        listImgStr=new ArrayList<>();
        listBitmap=new ArrayList<>();
        listImg.add(iv04_my_repairs_pretreament);
        listImg.add(iv05_my_repairs_pretreament);
        listImg.add(iv06_my_repairs_pretreament);
        dao=new RepairDao(this);
        iDao=new ImgDao(this);
        dao.requestRepair(getIntent().getStringExtra("repairDemandId"));

        if(getIntent().getStringExtra("progressState").equals("1")){

            iv04_my_repairs_pretreament.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.size() >= 1) {
                        imageFlag = 1;
                        showDelDialog(1);
                    }
                    return true;
                }
            });
            iv05_my_repairs_pretreament.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.size() >= 2) {
                        imageFlag = 2;
                        showDelDialog(2);
                    }
                    return true;
                }
            });
            iv06_my_repairs_pretreament.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.size() >= 3) {
                        imageFlag = 3;
                        showDelDialog(3);
                    }
                    return true;
                }
            });
    }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(requestCode==1){
            sc_repair.smoothScrollTo(0,0);
            if(getIntent().getStringExtra("progressState").equals("0")){
                ll_my_repairs_wuye.setVisibility(View.GONE);
                //ll_my_repairs_pingjia.setVisibility(View.GONE);
                tv_my_repairs_pretreament_progress.setText("报修进度：待处理");
                tv_my_repairs_pretreament_time.setText("报修时间："+ TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_repairs_room.setText("报修房间："+dao.getRepair().getCommunityName()+" "+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_type.setText("报修类型："+dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());
                if(dao.getRepairPicList().size()<1){
                tv_baoxiu_image.setVisibility(View.GONE);
                tv_wuye_image.setVisibility(View.GONE);
                iv01_my_repairs_pretreament.setVisibility(View.GONE);
                iv02_my_repairs_pretreament.setVisibility(View.GONE);
                iv03_my_repairs_pretreament.setVisibility(View.GONE);
                iv04_my_repairs_pretreament.setVisibility(View.GONE);
                iv05_my_repairs_pretreament.setVisibility(View.GONE);
                iv06_my_repairs_pretreament.setVisibility(View.GONE);
            }
                if (dao.getRepairPicList() != null) {
                    if (dao.getRepairPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
                    }
                }
        }
            if(getIntent().getStringExtra("progressState").equals("1")){
              /*  if(!Arad.preferences.getString("grade").equals("2")){
                    ll_my_repairs_wuye.setVisibility(View.GONE);
                }*/
                ll_repair_selecte.setVisibility(View.GONE);
                //ll_my_repairs_pingjia.setVisibility(View.GONE);
                tv_my_repairs_pretreament_progress.setText("报修进度：处理中");
                tv_my_repairs_pretreament_time.setText("报修时间："+TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_repairs_room.setText("报修房间："+dao.getRepair().getCommunityName()+" "+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_type.setText("报修类型："+dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());
                if(dao.getRepairPicList().size()<1){
                    tv_baoxiu_image.setVisibility(View.GONE);
                    iv01_my_repairs_pretreament.setVisibility(View.GONE);
                    iv02_my_repairs_pretreament.setVisibility(View.GONE);
                    iv03_my_repairs_pretreament.setVisibility(View.GONE);
                }
                if (dao.getRepairPicList() != null) {
                    if (dao.getRepairPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(0).getOriginalImg()).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(1).getOriginalImg()).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(2).getOriginalImg()).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
                    }
                }

            }
            if(getIntent().getStringExtra("progressState").equals("2")) {
                tv_my_repairs_pretreament_result.setEnabled(false);
                ll_repair_selecte.setVisibility(View.GONE);
                tv_repairs_back.setVisibility(View.GONE);
                if(dao.getRepairPicList().size()<1){
                    tv_baoxiu_image.setVisibility(View.GONE);
                    iv01_my_repairs_pretreament.setVisibility(View.GONE);
                    iv02_my_repairs_pretreament.setVisibility(View.GONE);
                    iv03_my_repairs_pretreament.setVisibility(View.GONE);
                }
                if(dao.getRepairResultPicList().size()<1){
                    tv_wuye_image.setVisibility(View.GONE);
                    iv04_my_repairs_pretreament.setVisibility(View.GONE);
                    iv05_my_repairs_pretreament.setVisibility(View.GONE);
                    iv06_my_repairs_pretreament.setVisibility(View.GONE);
                }
                tv_my_repairs_pretreament_progress.setText("报修进度：已处理");
                tv_my_repairs_pretreament_wuye.setText("物业处理：已处理");
                tv_my_repairs_pretreament_time.setText("报修时间："+TimeUtil.date(Long.parseLong(dao.getRepair().getCreateTime())));
                tv_repairs_room.setText("报修房间："+dao.getRepair().getCommunityName()+" "+dao.getRepair().getBuilding()+"-"+dao.getRepair().getUnit()+"-"+dao.getRepair().getRoom());
                tv_my_repairs_pretreament_type.setText("报修类型："+dao.getRepair().getRepairKindName());
                tv_my_repairs_pretreament_content.setText(dao.getRepair().getContent());
                tv_my_repairs_pretreament_result.setText(dao.getRepair().getResult());

                if (dao.getRepairPicList() != null) {
                    if (dao.getRepairPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(0).getOriginalImg() == null || dao.getRepairPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(0).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv01_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(1).getOriginalImg() == null || dao.getRepairPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(1).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv02_my_repairs_pretreament);
                    }
                    if (dao.getRepairPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairPicList().get(2).getOriginalImg() == null || dao.getRepairPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairPicList().get(2).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv03_my_repairs_pretreament);
                    }
                }
                if (dao.getRepairResultPicList() != null) {
                    if (dao.getRepairResultPicList().size() >= 1) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(0).getOriginalImg() == null || dao.getRepairResultPicList().get(0).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(0).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv04_my_repairs_pretreament);
                    }
                    if (dao.getRepairResultPicList().size() >= 2) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(1).getOriginalImg() == null || dao.getRepairResultPicList().get(1).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(1).getOriginalImg()).resize(300, 300).placeholder(R.drawable.default_image).into(iv05_my_repairs_pretreament);
                    }
                    if (dao.getRepairResultPicList().size() >= 3) {
                        Arad.imageLoader.load(dao.getRepairResultPicList().get(2).getOriginalImg() == null || dao.getRepairResultPicList().get(2).getOriginalImg().equals("") ? "aa" :
                                dao.getRepairResultPicList().get(2).getOriginalImg()).resize(300,300).placeholder(R.drawable.default_image).into(iv06_my_repairs_pretreament);
                    }
                }



                }


            }


        if(requestCode==2){
            MessageUtils.showShortToast(this,"接单成功");
            setResult(667);
            finish();
        }

        if(requestCode==3){
            MessageUtils.showShortToast(this, "处理提交成功");
            setResult(333);
            finish();
        }

        if(requestCode==101){
            showProgress(false);
            MessageUtils.showShortToast(this,"图片上传成功");
            listImgStr.add(iDao.getImg().getImgUrl());
            mGoodPicList.add(iDao.getImg());
            mGoodPicId.add(iDao.getImg().getImgId());
            setBitmapImg();

        }
        if(requestCode==102){
            MessageUtils.showShortToast(this,"图片删除成功");
            showProgress(false);
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
    }

    private void showDelDialog(final int posititon) {
        new AlertDialog.Builder(this)
                .setTitle("确定删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        iDao.requestDelImg(mGoodPicId.get(posititon-1));
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
        try {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    if (data != null) {
                        // startPhotoZoom(data.getData());

                        Uri uri = data.getData();
                        String thePath = com.ctrl.android.property.staff.base.Utils.getInstance().getPath(this, uri);
                        getImageToView1(thePath);

                        //getImageToView(data.getData());
                    }
                    break;
                case CAMERA_REQUEST_CODE:
//                    if (data != null) {
//                        Bundle bundle = data.getExtras();
//                        Bitmap bitmap = (Bitmap) bundle.get("data");
//                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, null, null));
//                        //startPhotoZoom(uri);
//                        getImageToView(uri);
                        if(resultCode==-1)
                        getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");

//                        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cxh.jpg")));
//                        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);

//                    }
                    break;

                case 1000:
                    if(RESULT_OK==resultCode){
                        finish();
                    }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                iDao.requestUploadImg(REPAIRS_PRETREATMENT, photo, StrConstant.REPAIRS_PRETREAMENT_IMAGE, "1");
                //imgDao.requestUploadImg(activityId, imgData, typeKey, optMode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 保存未裁剪的图片数据
     *
     * @param picturePath 路径
     */
    private void getImageToView2(String picturePath) {
        try {
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(uri,
//                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);  //获取照片路径
//            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);//压缩比例50%
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);
            Drawable drawable = new BitmapDrawable(bitmap);
            if (photo != null){
                iDao.requestUploadImg(REPAIRS_PRETREATMENT, photo, StrConstant.REPAIRS_PRETREAMENT_IMAGE, "1");
                listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * 保存未裁剪的图片数据
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
            if (photo != null){
                iDao.requestUploadImg(REPAIRS_PRETREATMENT, photo, StrConstant.REPAIRS_PRETREAMENT_IMAGE, "1");
                listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void setBitmapImg(){

        if(listImgStr != null){
            if(listImgStr.size() == 1){
                iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv05_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);
                iv06_my_repairs_pretreament.setVisibility(View.INVISIBLE);

                ViewGroup.LayoutParams params = iv04_my_repairs_pretreament.getLayoutParams();
                int w=iv04_my_repairs_pretreament.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv04_my_repairs_pretreament.setLayoutParams(params);

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }

            }

            if(listImgStr.size() == 2){
                iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv06_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv06_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);

                ViewGroup.LayoutParams params = iv04_my_repairs_pretreament.getLayoutParams();
                int w=iv04_my_repairs_pretreament.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv04_my_repairs_pretreament.setLayoutParams(params);
                iv05_my_repairs_pretreament.setLayoutParams(params);

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }
            }

            if(listImgStr.size() == 3){
                iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                iv06_my_repairs_pretreament.setVisibility(View.VISIBLE);


                ViewGroup.LayoutParams params = iv04_my_repairs_pretreament.getLayoutParams();
                int w=iv04_my_repairs_pretreament.getWidth();
                //android.util.Log.d("demo", "width : " + w);
                params.height=w;
                // android.util.Log.d("demo", "height : " + params.height);
                iv04_my_repairs_pretreament.setLayoutParams(params);
                iv05_my_repairs_pretreament.setLayoutParams(params);
                iv06_my_repairs_pretreament.setLayoutParams(params);

                for(int i = 0 ; i < listImgStr.size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //idao.requestData(null, listImgStr.get(i), StrConstant.COMPLAINT_IMAGE_APPKEY, "0");

                }
            }
        } else {
            iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
            iv04_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);
            iv05_my_repairs_pretreament.setVisibility(View.INVISIBLE);
            iv06_my_repairs_pretreament.setVisibility(View.INVISIBLE);
        }
    }

    private void delImg(int imgFlg){
        if(listBitmap != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listImgStr.size() == 1){

                    //listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv04_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);
                    iv05_my_repairs_pretreament.setVisibility(View.INVISIBLE);
                    iv06_my_repairs_pretreament.setVisibility(View.INVISIBLE);



                    setBitmapImg();

                }

                if(listImgStr.size() == 2){
                   // listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);
                    iv06_my_repairs_pretreament.setVisibility(View.INVISIBLE);


                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv06_my_repairs_pretreament.setVisibility(View.VISIBLE);

                    iv06_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);


                    setBitmapImg();
                }
            }

            /**长按 第二张图*/
            if(imgFlg == 2){
                if(listImgStr.size() == 1){


                }

                if(listImgStr.size() == 2){
                   // listBitmap.remove(1);
                    listImgStr.remove(1);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);
                    iv06_my_repairs_pretreament.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImgStr.size() == 3){
                    //listBitmap.remove(1);
                    listImgStr.remove(1);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv06_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv06_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
                }
            }


            /**长按 第三张图*/
            if(imgFlg == 3){
                if(listImgStr.size() == 1){


                }

                if(listImgStr.size() == 2){
                }

                if(listImgStr.size() == 3){
                    //listBitmap.remove(2);
                    listImgStr.remove(2);

                    iv04_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv05_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv06_my_repairs_pretreament.setVisibility(View.VISIBLE);
                    iv06_my_repairs_pretreament.setImageResource(R.drawable.green_add_img_icon);

                    setBitmapImg();
                }
            }

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
//                                Intent intentFromGallery = new Intent();
//                                intentFromGallery.setType("image/*"); // 设置文件类型
//                                intentFromGallery
//                                        .setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(intentFromGallery,
//                                        IMAGE_REQUEST_CODE);

                                Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
                                intentFromGallery.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);

                                break;
                            case CAMERA_REQUEST_CODE:

//                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);

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
    public void onClick(View v) {

        if(getIntent().getStringExtra("progressState").equals("1")) {
            if(v == iv04_my_repairs_pretreament){
                if(listImgStr.size() >= 1){
                    //
                } else {
                    showDialog();
                }

                //setBitmapImg();
            }

            if(v == iv05_my_repairs_pretreament){
                if(listImgStr.size() >= 2){
                    //
                } else {
                    showDialog();
                }
                //setBitmapImg();
            }

            if(v == iv06_my_repairs_pretreament){
                if(listImgStr.size() >= 3){
                    //
                } else {
                    showDialog();
                }
                //setBitmapImg();
            }


        }else{
            if(v==iv04_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=1){
                Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", dao.getRepairResultPicList().get(0).getOriginalImg());
                int[] location = new int[2];
                iv04_my_repairs_pretreament.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv04_my_repairs_pretreament.getWidth());
                intent.putExtra("height", iv04_my_repairs_pretreament.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            if(v==iv05_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=2){
                Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", dao.getRepairResultPicList().get(1).getOriginalImg());
                int[] location = new int[2];
                iv05_my_repairs_pretreament.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv05_my_repairs_pretreament.getWidth());
                intent.putExtra("height", iv05_my_repairs_pretreament.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            if(v==iv06_my_repairs_pretreament&&dao.getRepairResultPicList().size()>=3){
                Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", dao.getRepairResultPicList().get(2).getOriginalImg());
                int[] location = new int[2];
                iv06_my_repairs_pretreament.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv06_my_repairs_pretreament.getWidth());
                intent.putExtra("height", iv06_my_repairs_pretreament.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }



        }
        if(v==iv01_my_repairs_pretreament&&dao.getRepairPicList().size()>=1){
            Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(0).getOriginalImg());
            int[] location = new int[2];
            iv01_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv01_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv01_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv02_my_repairs_pretreament&&dao.getRepairPicList().size()>=2){
            Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(1).getOriginalImg());
            int[] location = new int[2];
            iv02_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv02_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv02_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(v==iv03_my_repairs_pretreament&&dao.getRepairPicList().size()>=3){
            Intent intent=new Intent(RepairsPretreatmentActivity.this, TestanroidpicActivity.class);
            intent.putExtra("imageurl", dao.getRepairPicList().get(2).getOriginalImg());
            int[] location = new int[2];
            iv03_my_repairs_pretreament.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            intent.putExtra("width", iv03_my_repairs_pretreament.getWidth());
            intent.putExtra("height", iv03_my_repairs_pretreament.getHeight());
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

        if(v==tv_repairs_accept){
            dao.requestRepairAssignType(getIntent().getStringExtra("repairDemandId"),
                    AppHolder.getInstance().getStaffInfo().getStaffId(),
                    "1",
                    "","");
        }

        if(v==tv_repairs_chargeback){
            Intent intent =new Intent(RepairsPretreatmentActivity.this,ChargeBackCauseActivity.class);
            intent.putExtra("repairDemandId",getIntent().getStringExtra("repairDemandId"));
            startActivity(intent);
            AnimUtil.intentSlidIn(RepairsPretreatmentActivity.this);
            finish();
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
        mRightText.setTextColor(Color.WHITE);
        mRightText.setText("完成");
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent=new Intent(MyRepairsPretreatmentActivity.this,MyRepairsAftertreatmentActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidOut(MyRepairsPretreatmentActivity.this);*/
                if (getIntent().getStringExtra("progressState").equals("0")) {
                    finish();
                }
                if (getIntent().getStringExtra("progressState").equals("1")) {
                    if (tv_my_repairs_pretreament_result.getText().toString().equals("")) {
                        if (listImgStr.size() == 0) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", "", "", "");
                        }
                        if (listImgStr.size() == 1) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "",
                                    listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(), "", "");
                        }
                        if (listImgStr.size() == 2) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                    listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(), "");
                        }
                        if (listImgStr.size() == 3) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                    listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                    listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl());
                        }
                    } else {
                        if (listImgStr.size() == 0) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                    tv_my_repairs_pretreament_result.getText().toString(), "", "", "");
                        }
                        if (listImgStr.size() == 1) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                    tv_my_repairs_pretreament_result.getText().toString(),
                                    listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(), "", "");
                        }
                        if (listImgStr.size() == 2) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                    tv_my_repairs_pretreament_result.getText().toString(),
                                    listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                    listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(), "");
                        }
                        if (listImgStr.size() == 3) {
                            dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                    tv_my_repairs_pretreament_result.getText().toString(),
                                    listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                    listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                    listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl());
                        }
                    }


                }
                if (getIntent().getStringExtra("progressState").equals("2")) {
                    finish();
                }

            }
        });

    /*    if(Arad.preferences.getString("grade").equals("2")) {
            if(getIntent().getStringExtra("progressState").equals("2")){
                mRightText.setText("");
            }else {
                mRightText.setText("完成");
            }
            mRightText.setTextColor(Color.WHITE);
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              *//*  Intent intent=new Intent(MyRepairsPretreatmentActivity.this,MyRepairsAftertreatmentActivity.class);
                startActivity(intent);
                AnimUtil.intentSlidOut(MyRepairsPretreatmentActivity.this);*//*
                    if (getIntent().getStringExtra("progressState").equals("0")) {
                        finish();
                    }
                    if (getIntent().getStringExtra("progressState").equals("1")) {
                        if (tv_my_repairs_pretreament_result.getText().toString().equals("")) {
                            if (listImgStr.size() == 0) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", "", "", "");
                            }
                            if (listImgStr.size() == 1) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "",
                                        listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(), "", "");
                            }
                            if (listImgStr.size() == 2) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                        listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(), "");
                            }
                            if (listImgStr.size() == 3) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"), "", listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                        listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                        listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl());
                            }
                        } else {
                            if (listImgStr.size() == 0) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                        tv_my_repairs_pretreament_result.getText().toString(), "", "", "");
                            }
                            if (listImgStr.size() == 1) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                        tv_my_repairs_pretreament_result.getText().toString(),
                                        listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(), "", "");
                            }
                            if (listImgStr.size() == 2) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                        tv_my_repairs_pretreament_result.getText().toString(),
                                        listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                        listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(), "");
                            }
                            if (listImgStr.size() == 3) {
                                dao.requestRepairFillResult(getIntent().getStringExtra("repairDemandId"),
                                        tv_my_repairs_pretreament_result.getText().toString(),
                                        listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                        listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                        listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl());
                            }
                        }


                    }

                }
            });
        }else {
            mRightText.setText("");
        }*/
        return true;
    }
}

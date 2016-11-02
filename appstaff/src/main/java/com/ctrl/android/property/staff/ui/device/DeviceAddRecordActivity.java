package com.ctrl.android.property.staff.ui.device;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ClassDao;
import com.ctrl.android.property.staff.dao.DeviceDao;
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.entity.DeviceDetail;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.entity.Kind;
import com.ctrl.android.property.staff.ui.adapter.ListItemAdapter2;
import com.ctrl.android.property.staff.util.S;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 添加养护记录 activity
 * Created by Eric on 2015/10/22
 */
public class DeviceAddRecordActivity extends AppToolBarActivity implements View.OnClickListener{

    //@InjectView(R.id.scroll_view)//滚动view
    //ScrollView scroll_view;

    @InjectView(R.id.device_name)//设备名称
    TextView device_name;
    @InjectView(R.id.device_locate)//设备位置
    TextView device_locate;
    @InjectView(R.id.device_time)//购置时间
    TextView device_time;
    @InjectView(R.id.device_cycle)//养护周期
    TextView device_cycle;
    @InjectView(R.id.device_man)//责任人
    TextView device_man;
    @InjectView(R.id.device_provider)//维护厂家
    TextView device_provider;
    @InjectView(R.id.device_tel)//厂家电话
    TextView device_tel;
    @InjectView(R.id.tel_btn)//电话按钮
    ImageView tel_btn;

    @InjectView(R.id.img_01)//上传图片1
    ImageView img_01;
    @InjectView(R.id.img_02)//上传图片2
    ImageView img_02;
    @InjectView(R.id.img_03)//上传图片3
    ImageView img_03;

    @InjectView(R.id.device_status_text)
    TextView device_status_text;
    @InjectView(R.id.device_content_text)
    TextView device_content_text;


    private List<ImageView> listImgView = new ArrayList<>();
    private List<Img2> listImg = new ArrayList<>();
    //private String[] items = new String[]{"本地图片", "拍照"};
    private String[] items = new String[]{"拍照"};
    /* 请求码*/
    //private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private ImgDao imgDao;
    private int delFlg = 0;

    private String nowDeviceStatusId = "";
    private List<Kind> listKind;


    private String deviceId;
    private DeviceDetail deviceDetail;

    private ClassDao classDao;

    //SparseArray<Fragment> fragments = new SparseArray<Fragment>();

    private DeviceDao deviceDao;
    private String TITLE = "设备养护";

    private View mMenuView;//显示pop的view
    private ListItemAdapter2 listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.device_add_record_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){

        device_status_text.setOnClickListener(this);

        deviceId = getIntent().getStringExtra("id");

        deviceDao = new DeviceDao(this);
        showProgress(true);
        deviceDao.requestDeviceDetail(deviceId);

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        listImgView.add(img_01);
        listImgView.add(img_02);
        listImgView.add(img_03);

        imgDao = new ImgDao(this);

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(2 == requestCode){
            deviceDetail = deviceDao.getDeviceDetail();

            device_name.setText(S.getStr(deviceDetail.getName()));
            device_locate.setText(S.getStr(deviceDetail.getLocation()));
            //device_time.setText(D.getDateStrFromStamp("yyyy-MM-dd", deviceDetail.getPurchaseTime()));
            device_time.setText(S.getStr(deviceDetail.getPurchaseTime()));
            device_cycle.setText(S.getStr(deviceDetail.getCuringCycle()));
            device_man.setText(S.getStr(deviceDetail.getManagerName()));
            device_provider.setText(S.getStr(deviceDetail.getFactoryName()));
            device_tel.setText(S.getStr(deviceDetail.getFactoryTelephone()));

            tel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!S.isNull(deviceDetail.getFactoryTelephone())) {
                        AndroidUtil.dial(DeviceAddRecordActivity.this, deviceDetail.getFactoryTelephone());
                    } else {
                        MessageUtils.showShortToast(DeviceAddRecordActivity.this, "未获得电话号码");
                    }
                }
            });

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

        if(999 == requestCode){
            listKind = classDao.getData();
            showStatusListPop();

        }

        if(4 == requestCode){
            MessageUtils.showShortToast(this,"提交成功");
            finish();
        }

    }

    @Override
    public void onClick(View v) {

        if(v == device_status_text){
            classDao = new ClassDao(this);
            showProgress(true);
            classDao.requestData("DS");
        }

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

    /**
     * 页面标题
     * */
    @Override
    public String setupToolBarTitle() {
        return TITLE;
    }

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText("完成");
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MessageUtils.showShortToast(DeviceAddRecordActivity.this,"完成");
                //String id = "";

                if(checkInput()){
                    String stateKindId = nowDeviceStatusId;
                    String content = device_content_text.getText().toString();
                    String equipmentId = deviceId;
                    String staffId = AppHolder.getInstance().getStaffInfo().getStaffId();
                    //String maintainTime = "";
                    String historyPicStr1 = "";
                    String historyPicStr2 = "";
                    String historyPicStr3 = "";

                    if(listImg != null && listImg.size() == 1 ){
                        historyPicStr1 = listImg.get(0).getImgUrl() + "," + listImg.get(0).getZipImgUrl();
                    } else {
                        historyPicStr1 = "";
                    }

                    if(listImg != null && listImg.size() == 2 ){
                        historyPicStr2 = listImg.get(1).getImgUrl() + "," + listImg.get(1).getZipImgUrl();
                    } else {
                        historyPicStr2 = "";
                    }

                    if(listImg != null && listImg.size() == 3 ){
                        historyPicStr3 = listImg.get(2).getImgUrl() + "," + listImg.get(2).getZipImgUrl();
                    } else {
                        historyPicStr3 = "";
                    }

                    showProgress(true);
                    deviceDao.requestAddDeviceRecord(stateKindId,content
                            ,equipmentId,staffId
                            ,historyPicStr1,historyPicStr2,historyPicStr3);
                }

                //} else {
                //    MessageUtils.showShortToast(DeviceAddRecordActivity.this,"内容不可为空");
                //}



            }
        });
        return true;
    }

    private boolean checkInput(){

        if(S.isNull(device_content_text.getText().toString())){
            MessageUtils.showShortToast(DeviceAddRecordActivity.this,"内容不可为空");
            return false;
        }

        if(S.isNull(nowDeviceStatusId)){
            MessageUtils.showShortToast(DeviceAddRecordActivity.this,"请选择设备状态");
            return false;
        }

        return true;
    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {

        Log.d("demo", "进入拍照");

        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "cxh.jpg")));
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);

//        new AlertDialog.Builder(this)
//                .setTitle("请选择")
//                .setItems(items, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
////                            case IMAGE_REQUEST_CODE:
////                                Intent intentFromGallery = new Intent();
////                                intentFromGallery.setType("image/*"); // 设置文件类型
////                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
////                                startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
////                                break;
//                            case CAMERA_REQUEST_CODE:
//                                Log.d("demo", "进入拍照");
//                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
//                                break;
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

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
                String typeKey = "MT";//设备养护记录
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
     * 显示年份的 popwindow
     * */
    private void showStatusListPop(){

        listItemAdapter = new ListItemAdapter2(this);
        listItemAdapter.setList(listKind);

        mMenuView = LayoutInflater.from(DeviceAddRecordActivity.this).inflate(R.layout.choose_list_pop, null);
        ListView listView = (ListView)mMenuView.findViewById(R.id.listView);
        listView.setAdapter(listItemAdapter);

        final PopupWindow pop = new PopupWindow();

        // 设置SelectPicPopupWindow的View
        pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pop.setWidth(device_status_text.getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        pop.setFocusable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MessageUtils.showShortToast(DeviceAddRecordActivity.this, listKind.get(position).getKindName());
                nowDeviceStatusId = listKind.get(position).getId();
                device_status_text.setText(listKind.get(position).getKindName());
                pop.dismiss();
            }
        });

        int[] location = new int[2];
        device_status_text.getLocationOnScreen(location);
        //Pop.showAtLocation(year_btn, Gravity.NO_GRAVITY, location[0], location[1] - Pop.getHeight());
        pop.showAsDropDown(device_status_text);
        //Pop.showAtLocation(mall_main_pop_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    /**
     * 测试 获取数据的方法
     * */
    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("owner", i + "业主名称");
            map.put("name", "中润世纪广场");
            map.put("time", "2015-10-22");
            map.put("status","" + (i%2));
            list.add(map);
        }
        return list;
    }



}

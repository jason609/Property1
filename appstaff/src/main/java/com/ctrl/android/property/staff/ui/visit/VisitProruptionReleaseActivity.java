package com.ctrl.android.property.staff.ui.visit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.dao.VisitDao;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
* 突发到访发布  activity
* */

public class VisitProruptionReleaseActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.et_visit_visitingpeople)//到访人姓名
    EditText et_visit_visitingpeople;
    @InjectView(R.id.et_visit_name)//拜访人姓名
    EditText et_visit_name;
    @InjectView(R.id.et_visit_count)//到访人数
    EditText et_visit_count;
    @InjectView(R.id.et_visit_car)//车牌号
    EditText et_visit_car;
    @InjectView(R.id.et_visit_tel)//拜访人电话
    EditText et_visit_tel;
    @InjectView(R.id.et_visit_stop)//预计停留时间
    EditText et_visit_stop;
    @InjectView(R.id.spinner_visit_building)//楼号
    Spinner spinner_visit_building;
    @InjectView(R.id.spinner_visit_unit)//单元号
    Spinner spinner_visit_unit;
    @InjectView(R.id.spinner_visit_room)//房间号
    Spinner spinner_visit_room;

    @InjectView(R.id.iv01_second_hand_transfer)//图片1
    ImageView iv01_second_hand_transfer;
    @InjectView(R.id.iv02_second_hand_transfer)//图片2
    ImageView iv02_second_hand_transfer;
    @InjectView(R.id.iv03_second_hand_transfer)//图片3
    ImageView iv03_second_hand_transfer;



    private List<String> buildStr=new ArrayList<>();
    private List<String> unitStr=new ArrayList<>();
    private List<String> roomStr=new ArrayList<>();

    List<String>spinnerlist=new ArrayList<>();
    List<Img2>mGoodPicList=new ArrayList<>();
    List<String>mGoodPicId=new ArrayList<>();

    private VisitDao vdao;
    private ArrayAdapter<String> adapter;
    private String build;
    private String unit;
    private String room;

    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private String[] items = new String[]{"本地图片", "拍照"};
    private int imageFlag=-1;


    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final String VISIT_REALEASE = "VISIT_REALEASE";
    private ImgDao iDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_proruption_release);
        // 隐藏输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        vdao=new VisitDao(this);
        iDao=new ImgDao(this);
        vdao.requestBuildingList(AppHolder.getInstance().getStaffInfo().getCommunityId());
        iv01_second_hand_transfer.setOnClickListener(this);
        iv02_second_hand_transfer.setOnClickListener(this);
        iv03_second_hand_transfer.setOnClickListener(this);
        listImg=new ArrayList<>();
        listImgStr=new ArrayList<>();
        listBitmap=new ArrayList<>();
        listImg.add(iv01_second_hand_transfer);
        listImg.add(iv02_second_hand_transfer);
        listImg.add(iv03_second_hand_transfer);

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

    }

    private void showDelDialog(final int posititon) {
        new AlertDialog.Builder(this)
                .setTitle("确定删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        iDao.requestDelImg(mGoodPicId.get(posititon - 1));
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

                        Uri uri = data.getData();
                        String thePath = com.ctrl.android.property.staff.base.Utils.getInstance().getPath(this, uri);
                        getImageToView1(thePath);

                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    getImageToView1(Environment.getExternalStorageDirectory() + "/cxh.jpg");
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
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
                Log.d("demo", "上传方法2");
                showProgress(true);
                iDao.requestUploadImg(VISIT_REALEASE, photo, StrConstant.VISIT_PRORUPTION_IMAGE, "1");
                listBitmap.add(bitmap);
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
                iDao.requestUploadImg(VISIT_REALEASE, photo, StrConstant.VISIT_PRORUPTION_IMAGE, "1");
                listBitmap.add(bitmap);
                showProgress(true);
                // setBitmapImg();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setBitmapImg(){

        if(listBitmap != null){
            ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
            int w=iv01_second_hand_transfer.getWidth();
            //android.util.Log.d("demo", "width : " + w);
            params.height=w;
            iv01_second_hand_transfer.setLayoutParams(params);
            iv02_second_hand_transfer.setLayoutParams(params);
            iv03_second_hand_transfer.setLayoutParams(params);
            // android.util.Log.d("demo", "height : " + params.height);
            if(listBitmap.size() == 1){
                iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                iv03_second_hand_transfer.setVisibility(View.INVISIBLE);


                for(int i = 0 ; i < listBitmap.size() ; i ++){
                    listImg.get(i).setImageBitmap(listBitmap.get(i));
                }


            }

            if(listBitmap.size() == 2){
                iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);


                for(int i = 0 ; i < listBitmap.size() ; i ++){
                    listImg.get(i).setImageBitmap(listBitmap.get(i));
                }

            }

            if(listBitmap.size() == 3) {
                iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                iv03_second_hand_transfer.setVisibility(View.VISIBLE);




                for(int i = 0 ; i < listBitmap.size() ; i ++){
                    listImg.get(i).setImageBitmap(listBitmap.get(i));
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
        if(listBitmap != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listBitmap.size() == 1){

                    listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv01_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv02_second_hand_transfer.setVisibility(View.INVISIBLE);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);



                    setBitmapImg();

                }

                if(listBitmap.size() == 2){
                    listBitmap.remove(0);
                    listImgStr.remove(0);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);


                    setBitmapImg();
                }

                if(listBitmap.size() == 3){
                    listBitmap.remove(0);
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
                if(listBitmap.size() == 1){


                }

                if(listBitmap.size() == 2){
                    listBitmap.remove(1);
                    listImgStr.remove(1);

                    iv01_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    iv02_second_hand_transfer.setImageResource(R.drawable.green_add_img_icon);
                    iv03_second_hand_transfer.setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listBitmap.size() == 3){
                    listBitmap.remove(1);
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
                if(listBitmap.size() == 1){
                }

                if(listBitmap.size() == 2){
                }

                if(listBitmap.size() == 3){
                    listBitmap.remove(2);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(101==requestCode){
            MessageUtils.showShortToast(this,"图片上传成功");
            listImgStr.add(iDao.getImg().getImgUrl());
            mGoodPicList.add(iDao.getImg());
            mGoodPicId.add(iDao.getImg().getImgId());
            setBitmapImg();
        }
        if(102==requestCode){
            MessageUtils.showShortToast(this,"图片删除成功");
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

        if(2 == requestCode){
            MessageUtils.showShortToast(this, "添加成功");
            finish();
        }

        if(10==requestCode){
            buildStr=vdao.getListBuilding();
            adapter = new ArrayAdapter<String>(VisitProruptionReleaseActivity.this, R.layout.spinner_layout, buildStr);
            //设置下拉列表的风格
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout. spinner_layout );
            spinner_visit_building.setAdapter(adapter);
            spinner_visit_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    build = buildStr.get(position);
                    vdao.requestUnitList(AppHolder.getInstance().getStaffInfo().getCommunityId(), build);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        if(11==requestCode){
            unitStr=vdao.getListUnit();
            adapter = new ArrayAdapter<String>(VisitProruptionReleaseActivity.this, R.layout.spinner_layout, unitStr);
            //设置下拉列表的风格
           // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout. spinner_layout );
            spinner_visit_unit.setAdapter(adapter);
            spinner_visit_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unit=unitStr.get(position);
                    vdao.requestRoomList(AppHolder.getInstance().getStaffInfo().getCommunityId(),build,unit);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(12==requestCode){
            roomStr=vdao.getListRoom();
            adapter = new ArrayAdapter<String>(VisitProruptionReleaseActivity.this, R.layout.spinner_layout, roomStr);
            //设置下拉列表的风格
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner_visit_room.setAdapter(adapter);
            spinner_visit_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    room = roomStr.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // room=roomStr.get(0);
                }
            });
        }
    }


    @Override
    public void onClick(View v) {

        if(v == iv01_second_hand_transfer){
            if(listImgStr.size() >= 1){
                //
            } else {
                showDialog();
            }

            //setBitmapImg();
        }

        if(v == iv02_second_hand_transfer) {
            if (listImgStr.size() >= 2){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }

        if (v == iv03_second_hand_transfer){
            if(listImgStr.size() >= 3){
                //
            } else {
                showDialog();
            }
            //setBitmapImg();
        }



    }

    @Override
    public String setupToolBarTitle() {
        return "突发到访";
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        leftButton.setImageResource(R.drawable.white_arrow_left_none);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if (checkInput()) {
                    vdao = new VisitDao(VisitProruptionReleaseActivity.this);
                    String communityId = AppHolder.getInstance().getStaffInfo().getCommunityId();//具体问题具体分析
                    String visitorName = S.getStr(et_visit_visitingpeople.getText().toString());
                    String memberName = S.getStr(et_visit_name.getText().toString());
                    String visitorMobile = S.getStr(et_visit_tel.getText().toString());
                    String numberPlates = S.getStr(et_visit_car.getText().toString());
                    String residenceTime = S.getStr(et_visit_stop.getText().toString());
                    String peopleNum = S.getStr(et_visit_count.getText().toString());
                    if (listImgStr.size() == 0) {
                        vdao.requestAddVisit(communityId, build, unit, room
                                , visitorName, memberName, visitorMobile, numberPlates
                                , residenceTime, peopleNum, "", "", "");

                    } else if (listImgStr.size() == 1) {
                        vdao.requestAddVisit(communityId, build, unit, room
                                , visitorName, memberName, visitorMobile, numberPlates
                                , residenceTime, peopleNum,
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(), "", "");
                    } else if (listImgStr.size() == 2) {
                        vdao.requestAddVisit(communityId, build, unit, room
                                , visitorName, memberName, visitorMobile, numberPlates
                                , residenceTime, peopleNum,
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(), "");
                    } else if (listImgStr.size() == 3) {
                        vdao.requestAddVisit(communityId, build, unit, room
                                , visitorName, memberName, visitorMobile, numberPlates
                                , residenceTime, peopleNum,
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl());
                    }

                    showProgress(true);
                }

            }
        });
        return true;
    }

    private boolean checkInput(){


        if(S.isNull(et_visit_visitingpeople.getText().toString())){
            MessageUtils.showShortToast(this,"到访人不可为空");
            return false;
        }
        if(S.isNull(et_visit_name.getText().toString())){
            MessageUtils.showShortToast(this,"拜访人不可为空");
            return false;
        }
//        if(S.isNull(et_visit_car.getText().toString())){
//            MessageUtils.showShortToast(this,"车牌号为空");
//            return false;
//        }
        if(S.isNull(et_visit_tel.getText().toString())){
            MessageUtils.showShortToast(this,"拜访人电话为空");
            return false;
        }
        if(S.isNull(et_visit_stop.getText().toString())){
            MessageUtils.showShortToast(this,"预计停留时间为空");
            return false;
        }

        if(S.isNull(build)){
            MessageUtils.showShortToast(this,"楼号为空");
            return false;
        }
        if(S.isNull(unit)){
            MessageUtils.showShortToast(this,"单元号为空");
            return false;
        }
        if(S.isNull(room)){
            MessageUtils.showShortToast(this,"房间号为空");
            return false;
        }

        if((et_visit_count.getText().toString() == null || et_visit_count.getText().toString().equals("") ? 0 : Integer.parseInt(et_visit_count.getText().toString())) <= 0 ){
            MessageUtils.showShortToast(this,"到访人数不可小于0");
            return false;
        }

        return true;
    }



}

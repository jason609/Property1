package com.ctrl.android.property.staff.ui.express;

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
import com.ctrl.android.property.staff.dao.ClassDao;
import com.ctrl.android.property.staff.dao.CommunityDao;
import com.ctrl.android.property.staff.dao.ExpressDao;
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.entity.Img2;
import com.ctrl.android.property.staff.entity.Kind;
import com.ctrl.android.property.staff.ui.qrcode.QrCodeActivity;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
/*
* 快递处理 activity
* */

public class ExpressHandleActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.et_visit_visitingpeople)//收件人
    EditText et_visit_visitingpeople;
    @InjectView(R.id.et_visit_name)//收件电话
    EditText et_visit_name;
    @InjectView(R.id.et_visit_car)//快递编号
    EditText et_visit_car;
    @InjectView(R.id.spinner_express_company)//快递公司
    Spinner spinner_express_company;
    @InjectView(R.id.spinner_visit_building)//楼号
            Spinner spinner_visit_building;
    @InjectView(R.id.spinner_visit_unit)//单元号
            Spinner spinner_visit_unit;
    @InjectView(R.id.spinner_visit_room)//房间号
            Spinner spinner_visit_room;

    @InjectView(R.id.iv_express_camera)//照相机
    ImageView iv_express_camera;

    @InjectView(R.id.iv01_second_hand_transfer)//图片1
    ImageView iv01_second_hand_transfer;
    @InjectView(R.id.iv02_second_hand_transfer)//图片2
    ImageView iv02_second_hand_transfer;
    @InjectView(R.id.iv03_second_hand_transfer)//图片31
    ImageView iv03_second_hand_transfer;

    private List<String> buildStr=new ArrayList<>();
    private List<String> unitStr=new ArrayList<>();
    private List<String> roomStr=new ArrayList<>();
    private List<String> companyStr=new ArrayList<>();
    private List<Kind> kindList=new ArrayList<>();
    List<Img2>mGoodPicList=new ArrayList<>();
    List<String>mGoodPicId=new ArrayList<>();
    private String build;
    private String unit;
    private String room;
    private ArrayAdapter<String> adapter;
    private CommunityDao cdao;
    private ClassDao cDao;
    private ArrayAdapter adapter1;
    private String kindId;

    private List<ImageView> listImg;
    private List<String> listImgStr;
    private List<Bitmap> listBitmap;
    private String[] items = new String[]{"本地图片", "拍照"};
    private int imageFlag=-1;


    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;

    private static final String EXPRESS_HANDLE = "EXPRESS_HANDLE";
    private ImgDao iDao;
    private ExpressDao edao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_express_handle);
        ButterKnife.inject(this);
        //隐藏输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
    }

    private void init() {
        iDao=new ImgDao(this);
        edao=new ExpressDao(this);
        cdao=new CommunityDao(this);
        cdao.requestBuildingList(AppHolder.getInstance().getStaffInfo().getCommunityId());
        cDao=new ClassDao(this);
        cDao.requestData(StrConstant.EXPRESS_COMPANY);
        iv_express_camera.setOnClickListener(this);
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

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(4==requestCode){
            MessageUtils.showShortToast(this, "快递添加成功");
            Intent intent=new Intent(ExpressHandleActivity.this,ExpressListActivity.class);
            startActivity(intent);
            finish();
        }
        if(101==requestCode){
            MessageUtils.showShortToast(this, "图片上传成功");
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
        if(999==requestCode){
            kindList=cDao.getData();
            for(int i=0;i<kindList.size();i++){
                companyStr.add(kindList.get(i).getKindName());
            }
            adapter1=new ArrayAdapter<>(ExpressHandleActivity.this,R.layout.spinner_layout,companyStr);
            adapter1.setDropDownViewResource(R.layout.spinner_layout);
            spinner_express_company.setAdapter(adapter1);
            spinner_express_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    kindId=kindList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(10==requestCode){
            buildStr=cdao.getListBuilding();
            adapter = new ArrayAdapter<String>(ExpressHandleActivity.this, R.layout.spinner_layout, buildStr);
            //设置下拉列表的风格
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.spinner_layout );
            spinner_visit_building.setAdapter(adapter);
            spinner_visit_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    build = buildStr.get(position);
                    cdao.requestUnitList(AppHolder.getInstance().getStaffInfo().getCommunityId(), build);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        if(11==requestCode){
            unitStr=cdao.getListUnit();
            adapter = new ArrayAdapter<String>(ExpressHandleActivity.this, R.layout.spinner_layout, unitStr);
            //设置下拉列表的风格
            // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout. spinner_layout );
            spinner_visit_unit.setAdapter(adapter);
            spinner_visit_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unit=unitStr.get(position);
                    cdao.requestRoomList(AppHolder.getInstance().getStaffInfo().getCommunityId(),build,unit);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(12==requestCode){
            roomStr=cdao.getListRoom();
            adapter = new ArrayAdapter<String>(ExpressHandleActivity.this, R.layout.spinner_layout, roomStr);
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
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
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
                iDao.requestUploadImg(EXPRESS_HANDLE, photo, StrConstant.EXPRESS_IMAGE, "1");
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
            // android.util.Log.d("demo", "height : " + params.height);
            iv01_second_hand_transfer.setLayoutParams(params);
            iv02_second_hand_transfer.setLayoutParams(params);
            iv03_second_hand_transfer.setLayoutParams(params);
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

            if(listBitmap.size() == 3){
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
    public String setupToolBarTitle() {
        return "快递处理";
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
        mRightText.setTextColor(Color.WHITE);
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    if (listImgStr.size() == 0) {
                        edao.requestExpressAdd(AppHolder.getInstance().getStaffInfo().getStaffId(),
                                AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                et_visit_visitingpeople.getText().toString(),
                                et_visit_name.getText().toString(),
                                build,
                                unit,
                                room,
                                kindId,
                                et_visit_car.getText().toString(),
                                "",
                                "",
                                "");

                    } else if (listImgStr.size() == 1) {
                        edao.requestExpressAdd(AppHolder.getInstance().getStaffInfo().getStaffId(),
                                AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                et_visit_visitingpeople.getText().toString(),
                                et_visit_name.getText().toString(),
                                build,
                                unit,
                                room,
                                kindId,
                                et_visit_car.getText().toString(),
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                "",
                                ""
                        );
                    } else if (listImgStr.size() == 2) {
                        edao.requestExpressAdd(AppHolder.getInstance().getStaffInfo().getStaffId(),
                                AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                et_visit_visitingpeople.getText().toString(),
                                et_visit_name.getText().toString(),
                                build,
                                unit,
                                room,
                                kindId,
                                et_visit_car.getText().toString(),
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                ""
                        );
                    } else if (listImgStr.size() == 3) {
                        edao.requestExpressAdd(AppHolder.getInstance().getStaffInfo().getStaffId(),
                                AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                et_visit_visitingpeople.getText().toString(),
                                et_visit_name.getText().toString(),
                                build,
                                unit,
                                room,
                                kindId,
                                et_visit_car.getText().toString(),
                                listImgStr.get(0) + "," + mGoodPicList.get(0).getZipImgUrl(),
                                listImgStr.get(1) + "," + mGoodPicList.get(1).getZipImgUrl(),
                                listImgStr.get(2) + "," + mGoodPicList.get(2).getZipImgUrl()
                        );

                    }
                    showProgress(true);
                }
            }

        });
        return  true;
    }

    private boolean checkInput(){
        if(S.isNull(et_visit_visitingpeople.getText().toString())){
            MessageUtils.showShortToast(this,"收件人不可为空");
            return false;
        }
        if(S.isNull(et_visit_name.getText().toString())){
            MessageUtils.showShortToast(this,"收件电话不可为空");
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
        if(S.isNull(et_visit_car.getText().toString())){
            MessageUtils.showShortToast(this,"快递编号不可为空");
            return false;
        }



        return true;
    }
    

    @Override
    public void onClick(View v) {
        if(iv_express_camera==v){
            Intent intent=new Intent(ExpressHandleActivity.this, QrCodeActivity.class);
            intent.addFlags(1054);
            startActivityForResult(intent, 1055);
        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1055==requestCode&&resultCode==StrConstant.EXPRESS_NUM){
            et_visit_car.setText(data.getStringExtra("expressNum"));
        }
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
                iDao.requestUploadImg(EXPRESS_HANDLE, photo, StrConstant.EXPRESS_IMAGE, "1");
                listBitmap.add(bitmap);
                showProgress(true);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}

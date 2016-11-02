package com.ctrl.android.property.staff.ui.patrol;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppHolder;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ImgDao;
import com.ctrl.android.property.staff.dao.PatrolDao;
import com.ctrl.android.property.staff.entity.Point;
import com.ctrl.android.property.staff.ui.CustomActivity.ListViewForScrollView;
import com.ctrl.android.property.staff.util.S;
import com.ctrl.android.property.staff.util.StrConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PatrolLineActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.lv_patrol)//下拉列表
    ListViewForScrollView lv_patrol;
    @InjectView(R.id.sc_patrol)
    ScrollView sc_patrol;
    @InjectView(R.id.tv_patrol_time)//任务时间
    TextView tv_patrol_time;
    @InjectView(R.id.tv_patrol_load)//巡更路线
    TextView tv_patrol_load;
    @InjectView(R.id.tv_patrol_name)//执行人
    TextView tv_patrol_name;

    private PatrolDao pdao;
    private String nextPoint;
    private static final int CAMERA_REQUEST_CODE = 1;

    private int mPosition;
    private List<Point> mData;
    private int num;
    private ImgDao iDao;
    private String PATROL_LINE_ACTIVITY="PATROL_LINE_ACTIVITY";
    private String url;
    private String zipUrl;
    private PatrolLineAdapter1 adapter;
    private Boolean isEnable=true;


    //private List<String> listImgStr;
    private List<String> listImgStr1;
    private List<String> listZipImgStr1;
    private Map<Integer,List<String>>listImgStr=new HashMap<>();
    private Map<Integer,List<String>>listImgId=new HashMap<>();
    private Map<Integer,List<String>>listZipImgStr=new HashMap<>();
    private Map<Integer,List<ImageView>>listImageview=new HashMap<>();
    private int imageFlag=-1;



    private ArrayList<String> listImgId1;
   // private ArrayList<ImageView> listImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_patrol_line);
        ButterKnife.inject(this);
        //隐藏输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        initData();
    }



    private void initData() {
        adapter = new PatrolLineAdapter1(this);
    }

    private void init() {
        showProgress(true);
        iDao=new ImgDao(this);
        pdao=new PatrolDao(this);
        pdao.requestPatrolDetail(getIntent().getStringExtra("patrolRouteStaffId"), getIntent().getStringExtra("routeStatus"), AppHolder.getInstance().getStaffInfo().getStaffId());

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
       // super.onRequestFaild(errorNo, errorMessage);
        isEnable=true;
        showProgress(false);
        if(errorNo.equals("018")){
            MessageUtils.showShortToast(this, "当前时间不在巡视时间内，不可继续巡视");
        }
    }

    public void setPos(int pos){
        this.mPosition=pos;
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(101==requestCode){
            MessageUtils.showShortToast(this,"图片上传成功");
            if(listImgStr.get(mPosition)==null){
                listImgStr1=new ArrayList<>();
                listZipImgStr1=new ArrayList<>();
            }else {
                listImgStr1=listImgStr.get(mPosition);
                listZipImgStr1=listZipImgStr.get(mPosition);
            }
            Log.i("tag","pos======"+mPosition);
            url=iDao.getImg().getImgUrl();
            zipUrl=iDao.getImg().getZipImgUrl();
            listImgStr1.add(iDao.getImg().getImgUrl());
            listImgId1.add(iDao.getImg().getImgId());
            listZipImgStr1.add(iDao.getImg().getZipImgUrl());
            listImgStr.put(mPosition, listImgStr1);
            listZipImgStr.put(mPosition, listZipImgStr1);
            listImgId.put(mPosition, listImgId1);

         //   adapter.setNum(num, zipUrl);
            setBitmapImg();
        }
        if(102==requestCode){
            MessageUtils.showShortToast(this, "图片删除成功");
            if(imageFlag==1){
                listImgId.get(mPosition).remove(0);
                delImg(1);
            }
            if(imageFlag==2){
                listImgId.get(mPosition).remove(1);
                delImg(2);
            }
            if (imageFlag==3){
                listImgId.get(mPosition).remove(2);
                delImg(3);
            }

        }
        if(2==requestCode){
            isEnable=true;
            showProgress(false);
            MessageUtils.showShortToast(this, "此巡查点完成");
            mData.get(mPosition).setHandleState("1");
          //  pdao.requestPatrolDetail(getIntent().getStringExtra("patrolRouteStaffId"), getIntent().getStringExtra("routeStatus"), AppHolder.getInstance().getStaffInfo().getStaffId());
            num++;
            adapter.setNum(num, null);
            if(num>pdao.getPointList().size()){
                   setResult(201);
                   finish();
               }
        }
        if(1==requestCode){
           tv_patrol_name.setText(pdao.getPatrolRoute().getStaffName());
           tv_patrol_time.setText(pdao.getPatrolRoute().getCreateTime());
           tv_patrol_load.setText(pdao.getPatrolRoute().getRouteName());
            nextPoint=pdao.getNextPoint();
            mData=pdao.getPointList();
            if(nextPoint==null||nextPoint.equals("")){
                //

                setResult(201);
               // finish();
            }else {
                for (int i=0;i< mData.size();i++){
                    String currentPoint = mData.get(i).getPatrolRoutePointId();
                    if(nextPoint.equals(currentPoint)){
                        num = mData.get(i).getSortNum();
                    }
                }
            }

            lv_patrol.setAdapter(adapter);
            sc_patrol.smoothScrollTo(0, 0);
            lv_patrol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            adapter.setData(mData);
            adapter.setNum(num, null);

        }
    }
 /*   private void setBitmapImg(){

        //MessageUtils.showLongToast(this, "size: " + listImgStr.size());

        if(listImgStr.get(mPosition) != null){
            if(listImgStr.get(mPosition).size() == 1){
                listImg.get(0).setVisibility(View.VISIBLE);
                listImg.get(1).setVisibility(View.VISIBLE);
                listImg.get(1).setImageResource(R.drawable.camera);
                listImg.get(2).setVisibility(View.INVISIBLE);

                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImg.get(i));
                    //listImg.get(i).setImageBitmap(listImgStr.get(i));
                }

            }

            if(listImgStr.get(mPosition).size() == 2){

                listImg.get(0).setVisibility(View.VISIBLE);
                listImg.get(1).setVisibility(View.VISIBLE);
                listImg.get(2).setVisibility(View.VISIBLE);
                listImg.get(2).setImageResource(R.drawable.camera);

                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(  listImg.get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                }
            }

            if(listImgStr.get(mPosition).size() == 3){

                listImg.get(0).setVisibility(View.VISIBLE);
                listImg.get(1).setVisibility(View.VISIBLE);
                listImg.get(2).setVisibility(View.VISIBLE);


                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(  listImg.get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                }
            }
        } else {

            listImg.get(0).setVisibility(View.VISIBLE);
            listImg.get(0).setImageResource(R.drawable.camera);
            listImg.get(1).setVisibility(View.INVISIBLE);
            listImg.get(2).setVisibility(View.INVISIBLE);
        }
    }*/
    private void setBitmapImg(){

        //MessageUtils.showLongToast(this, "size: " + listImgStr.size());

        if(listImgStr.get(mPosition) != null){
            if(listImgStr.get(mPosition).size() == 1){

                listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(1).setImageResource(R.drawable.camera);
                listImageview.get(mPosition).get(2).setVisibility(View.INVISIBLE);
                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into(listImageview.get(mPosition).get(i));
                    //listImg.get(i).setImageBitmap(listImgStr.get(i));

                }

            }

            if(listImgStr.get(mPosition).size() == 2){
                listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(2).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(2).setImageResource(R.drawable.camera);

                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into( listImageview.get(mPosition).get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                }
            }

            if(listImgStr.get(mPosition).size() == 3){

                listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                listImageview.get(mPosition).get(2).setVisibility(View.VISIBLE);


                for(int i = 0 ; i < listImgStr.get(mPosition).size() ; i ++){
                    Arad.imageLoader.load(S.getStr(listImgStr.get(mPosition).get(i)))
                            .placeholder(R.drawable.default_image)
                            .into( listImageview.get(mPosition).get(i));
                    //listImg.get(i).setImageBitmap(listBitmap.get(i));
                }
            }
        } else {

            listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
            listImageview.get(mPosition).get(0).setImageResource(R.drawable.camera);
            listImageview.get(mPosition).get(1).setVisibility(View.INVISIBLE);
            listImageview.get(mPosition).get(2).setVisibility(View.INVISIBLE);
        }
    }

    private void delImg(int imgFlg){
        if(listImgStr.get(mPosition) != null){

            /**长按 第一张图*/
            if(imgFlg == 1){
                if(listImgStr.get(mPosition).size() == 1){
                 //  listImg.remove(0);
                    //listBitmap.remove(0);
                    listImgStr.get(mPosition).remove(0);

                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(0).setImageResource(R.drawable.camera);
                    listImageview.get(mPosition).get(1).setVisibility(View.INVISIBLE);
                    listImageview.get(mPosition).get(2).setVisibility(View.INVISIBLE);
                    setBitmapImg();

                }

                if(listImgStr.get(mPosition).size() == 2){
                    //listBitmap.remove(0);
                    listImgStr.get(mPosition).remove(0);
                   // listImg.remove(0);

                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setImageResource(R.drawable.green_add_img_icon);
                    listImageview.get(mPosition).get(2).setVisibility(View.INVISIBLE);


                    setBitmapImg();
                }

                if(listImgStr.get(mPosition).size() == 3){
                    //listBitmap.remove(0);
                    listImgStr.get(mPosition).remove(0);
                 //   listImg.remove(0);
                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(2).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(2).setImageResource(R.drawable.camera);


                    setBitmapImg();
                }
            }

            /**长按 第二张图*/
            if(imgFlg == 2){
                if(listImgStr.get(mPosition).size() == 1){

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

                if(listImgStr.get(mPosition).size() == 2){
                    //listBitmap.remove(1);
                    listImgStr.get(mPosition).remove(1);
                //    listImg.remove(1);

                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setImageResource(R.drawable.camera);
                    listImageview.get(mPosition).get(2).setVisibility(View.INVISIBLE);

                    setBitmapImg();
                }

                if(listImgStr.get(mPosition).size() == 3){
                    //listBitmap.remove(1);
                    listImgStr.get(mPosition).remove(1);
                  //  listImg.remove(1);

                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(2).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(2).setImageResource(R.drawable.camera);

                    setBitmapImg();
                }
            }


            /**长按 第三张图*/
            if(imgFlg == 3){
                if(listImgStr.get(mPosition).size() == 1){

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

                if(listImgStr.get(mPosition).size() == 2){
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

                if(listImgStr.get(mPosition).size() == 3){
                    //listBitmap.remove(2);
                    listImgStr.get(mPosition).remove(2);
                  //  listImg.remove(2);
                    listImageview.get(mPosition).get(0).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setVisibility(View.VISIBLE);
                    listImageview.get(mPosition).get(1).setImageResource(R.drawable.camera);

                    setBitmapImg();
                }
            }

        }
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(resultCode==RESULT_CANCELED){return;}
            if (requestCode == CAMERA_REQUEST_CODE&&resultCode==-1) {
                    getImageToView1(Environment.getExternalStorageDirectory()+"/cxh.jpg");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getImageToView1(String path) {
        Bitmap bitmap ;
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeFile(path,options);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);

            String photo = new String(encode);
            if (photo != null){
               // Log.d("demo", "上传方法2");
                /**调用后台方法  将图片上传**/
                showProgress(true);
                iDao.requestUploadImg(PATROL_LINE_ACTIVITY, photo, StrConstant.PATROL_RESULT, "1");
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//压缩比例50%
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);
            Drawable drawable = new BitmapDrawable(bitmap);


            if (photo != null){
                iDao.requestUploadImg(PATROL_LINE_ACTIVITY, photo, StrConstant.PATROL_RESULT, "1");
                showProgress(true);
            }
        }catch (Exception e){
            e.printStackTrace();
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
        return "巡视巡查";
    }


    /**
     * 巡更巡查adapter
     * Created by Administrator on 2015/11/10.
     */
     class PatrolLineAdapter1 extends BaseAdapter {
        private Activity mActivity;
        List<Point> mData = new ArrayList<>();
        private int num;
        private String url=null;


        public PatrolLineAdapter1(Activity activity) {
            mActivity = activity;
            //listImg=new ArrayList<>();
         //   listImgStr=new HashMap<>();
            listImgId1=new ArrayList<>();
        }
        public void setData(List<Point> mData) {
            this.mData = mData;
            notifyDataSetChanged();
        }
        public void setNum(int num,String url) {
            this.num = num;
            this.url=url;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.j_adapter_partrol_line, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Point point = mData.get(position);
            holder.tv_point_1.setText(point.getPointName());
            holder.tv_describe.setText(point.getKeyMessage());
            holder.setPosition(position);
            if(position==0){
                holder.tv_line01.setVisibility(View.INVISIBLE);
            }else {
                holder.tv_line01.setVisibility(View.VISIBLE);
            }
         /*   if(position<num-1||num==0){
                holder.iv_patrol_disc_1.setImageResource(R.drawable.disc_green);
            }*/

            if(point.getHandleState()!=null) {
                if (point.getHandleState().equals("0")) {//未巡查
                    holder.iv_patrol_disc_1.setImageResource(R.drawable.ring_green);
                    holder.ll_patrol_3.setVisibility(View.VISIBLE);
                    holder.tv_patrol_end.setVisibility(View.VISIBLE);
                    holder.ll_camera.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_patrol_disc_1.setImageResource(R.drawable.disc_green);
                    holder.ll_patrol_3.setVisibility(View.GONE);
                    holder.tv_patrol_end.setVisibility(View.GONE);
                    holder.ll_camera.setVisibility(View.GONE);
                }

            }else {
                if(position<num-1||num==0){
                    holder.iv_patrol_disc_1.setImageResource(R.drawable.disc_green);
                }
                if(position==num-1){
                    holder.ll_camera.setVisibility(View.VISIBLE);
                    holder.ll_patrol_3.setVisibility(View.VISIBLE);
                    holder.tv_patrol_end.setVisibility(View.VISIBLE);
                }else{
                    holder.ll_patrol_3.setVisibility(View.GONE);
                    holder.tv_patrol_end.setVisibility(View.GONE);
                    holder.ll_camera.setVisibility(View.GONE);
                }
            }

         /*   if(position==num-1){
                holder.ll_patrol_3.setVisibility(View.VISIBLE);
                holder.tv_patrol_end.setVisibility(View.VISIBLE);
            }else{
                holder.ll_patrol_3.setVisibility(View.GONE);
                holder.tv_patrol_end.setVisibility(View.GONE);
            }*/


            holder.tv_patrol_end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listImgStr.get(position)==null||listImgStr.get(position).size()==0){
                        MessageUtils.showShortToast(PatrolLineActivity.this,"请拍照");
                    }else {
                        //Log.i("TAG","isEnbale====="+isEnable);
                        if (isEnable) {
                            showProgress(true);
                            isEnable=false;
                            setPos(position);
                            pdao.requestPatrolRoutePointAdd(AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                    pdao.getPatrolRoute().getPatrolRouteStaffId(),
                                    pdao.getPatrolRoute().getStaffId(),
                                    pdao.getPatrolRoute().getRouteId(),
                                    mData.get(position).getPointId(),
                                    holder.et_patrol_content.getText().toString(),
                                    getUrl(position)
                            );

                        }
                    }
                }
            });

            holder.iv01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if(listImg!=null){
                        listImg.clear();
                    }
                    listImg.add( holder.iv01);
                    listImg.add( holder.iv02);
                    listImg.add( holder.iv03);*/
                    List<ImageView>listImg=new ArrayList<>();
                    listImg.add(holder.iv01);
                    listImg.add(holder.iv02);
                    listImg.add(holder.iv03);
                    if(listImageview.get(position)==null){
                        listImageview.put(position,listImg);
                    }
                    if(listImgStr.get(position)==null){
                        gotoCapture(position);
                    }else if(listImgStr.get(position).size()>=1){
                        //
                    }else {
                        //
                    }

                }
            });

            holder.iv02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listImgStr.get(position).size()>=2){
                        //
                    }else {
                        gotoCapture(position);
                    }
                }
            });
            holder.iv03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listImgStr.get(position).size()>=3){
                        //
                    }else {
                        gotoCapture(position);
                    }
                }
            });

            holder.iv01.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.get(position).size() >= 1) {
                        imageFlag=1;
                        showDelDialog(imageFlag);
                    }
                    return true;
                }
            });
            holder.iv02.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.get(position).size()>= 1) {
                        imageFlag=2;
                        showDelDialog(imageFlag);
                    }
                    return true;
                }
            });
            holder.iv03.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listImgStr.get(position).size()>= 1) {
                        imageFlag=3;
                        showDelDialog(imageFlag);
                    }
                    return true;
                }
            });

        /*    if(url!=null){
                Arad.imageLoader.load(zipUrl).placeholder(R.drawable.default_image).into(holder.iv_patrol_camera);
            }else {
                holder.iv_patrol_camera.setImageResource(R.drawable.camera);
                holder.iv_patrol_camera.setScaleType(ImageView.ScaleType.CENTER);
            }*/

                return convertView;
        }

        private String getUrl(int position) {
            StringBuilder builder=new StringBuilder();
            String mUrl=null;
            for(int i=0;i<listImgStr.get(position).size();i++){
                mUrl=listImgStr.get(position).get(i)+","+listZipImgStr.get(position).get(i)+",";
                builder.append(mUrl);
            }
         //   Log.i("tag","url_fdfdf"+builder.toString());
            return builder.toString();
        }

        private void showDelDialog(final int posititon) {
            new AlertDialog.Builder(PatrolLineActivity.this)
                    .setTitle("确定删除吗？")
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            iDao.requestDelImg(listImgId.get(mPosition).get(posititon-1));
                        }
                    })

                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();


        }

        public  void gotoCapture(int pos){
          //  mPosition=pos;
            setPos(pos);
            Log.i("TAG","POS====="+pos);
            Log.i("TAG","mPosition====="+mPosition);
            Intent intentFromCapture = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "cxh.jpg")));
            startActivityForResult(intentFromCapture,
                    CAMERA_REQUEST_CODE);
        }

        class ViewHolder implements View.OnClickListener{
            @InjectView(R.id.iv01)
            ImageView iv01;
            @InjectView(R.id.iv02)
            ImageView iv02;
            @InjectView(R.id.iv03)
            ImageView iv03;

            @InjectView(R.id.tv_point_1)
            TextView tv_point_1;
            @InjectView(R.id.tv_describe)
            TextView tv_describe;
            @InjectView(R.id.tv_line01)//圆圈上方竖线
            TextView tv_line01;
            @InjectView(R.id.tv_line02)//圆圈下方竖线
            TextView tv_line02;
            @InjectView(R.id.et_patrol_content)//备注
                    EditText et_patrol_content;
            @InjectView(R.id.tv_patrol_end)//完成
                    TextView tv_patrol_end;
            @InjectView(R.id.iv_patrol_disc_1)//圆圈
                    ImageView iv_patrol_disc_1;
            @InjectView(R.id.ll_patrol_3)//内容布局
                    LinearLayout  ll_patrol_3;
            @InjectView(R.id.ll_camera)//照相布局
                    LinearLayout  ll_camera;
            private int mmposition;


            ViewHolder(View view) {
                ButterKnife.inject(this, view);
                tv_patrol_end.setOnClickListener(this);
                iv01.setOnClickListener(this);
                iv02.setOnClickListener(this);
                iv03.setOnClickListener(this);
            }



            public void setPosition(int position){
                this.mmposition = position;
            }



            @Override
            public void onClick(View v) {

                switch (v.getId()){
                  /*  case R.id.iv01:
                        position=mposition;
                        listImg.add(iv01);
                        listImg.add(iv02);
                        listImg.add(iv03);
                        if(listImgStr.size()>=1){
                            //
                        }else {
                            gotoCapture();
                        }
                        break;
                    case R.id.iv02:
                       // listImg.add(iv02);
                        if(listImgStr.size()>=2){
                            //
                        }else {
                            gotoCapture();
                        }
                        break;
                    case R.id.iv03:
                       // listImg.add(iv03);
                        if(listImgStr.size()>=3){
                            //
                        }else {
                            gotoCapture();
                        }
                        break;*/
                   /* case R.id.tv_patrol_end:
                        if(url==null||url.equals("")){
                            MessageUtils.showShortToast(PatrolLineActivity.this,"请拍照");

                        }else {
                            pdao.requestPatrolRoutePointAdd(AppHolder.getInstance().getStaffInfo().getCommunityId(),
                                    pdao.getPatrolRoute().getPatrolRouteStaffId(),
                                    pdao.getPatrolRoute().getStaffId(),
                                    pdao.getPatrolRoute().getRouteId(),
                                    mData.get(mmposition).getPointId(),
                                    et_patrol_content.getText().toString(),
                                    url+","+zipUrl
                            );
                        }
                        break;*/

                }

            }

        }
    }
}

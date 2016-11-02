package com.ctrl.android.property.staff.ui.express;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ExpressDao;
import com.ctrl.android.property.staff.entity.ExpressRecive;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.ui.CustomActivity.TestanroidpicActivity;
import com.ctrl.android.property.staff.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 快递详情
 * Created by Administrator on 2015/10/23.
 */
public class ExpressDetailActivity extends AppToolBarActivity implements View.OnClickListener{
    @InjectView(R.id.tv_express_name)//收件人姓名
    TextView mExpressName;
    @InjectView(R.id.tv_express_tel)//收件人电话
    TextView mExpressTel;
    @InjectView(R.id.tv_express_room)//收件房间号
    TextView mExpressRoom;
    @InjectView(R.id.tv_express_company)//快递公司名称
    TextView mExpressCompany;
    @InjectView(R.id.tv_express_number)//快递编号
    TextView mExpressNumber;
    @InjectView(R.id.tv_express_status)//快递领取状态
    TextView tv_express_status;
    @InjectView(R.id.ll_express_qrc)//快递图片布局
    LinearLayout ll_express_qrc;
    @InjectView(R.id.iv01_second_hand_transfer)//图片1
    ImageView iv01_second_hand_transfer;

    @InjectView(R.id.iv02_second_hand_transfer)//图片2
    ImageView iv02_second_hand_transfer;

    @InjectView(R.id.iv03_second_hand_transfer)//图片3
    ImageView iv03_second_hand_transfer;


    private String expressId;
    private String expressId2;
    private ExpressDao expressDao;

    private ExpressRecive expressRecive;
    private List<Img> listImg;
    private Bundle expressBundle;
    private boolean hasMeasured=false;
    private ArrayList<Img> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express_detail_activity);
        ButterKnife.inject(this);
        ViewTreeObserver mViewTreeObserve=iv01_second_hand_transfer.getViewTreeObserver();
        mViewTreeObserve.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    ViewGroup.LayoutParams params = iv01_second_hand_transfer.getLayoutParams();
                    int w=iv01_second_hand_transfer.getWidth();
                    //android.util.Log.d("demo", "width : " + w);
                    params.height=w;
                    // android.util.Log.d("demo", "height : " + params.height);
                    iv01_second_hand_transfer.setLayoutParams(params);
                    iv02_second_hand_transfer.setLayoutParams(params);
                    iv03_second_hand_transfer.setLayoutParams(params);
                    hasMeasured = true;
                }
                return true;
            }

        });
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init(){
        iv01_second_hand_transfer.setOnClickListener(this);
        iv02_second_hand_transfer.setOnClickListener(this);
        iv03_second_hand_transfer.setOnClickListener(this);


        tv_express_status.setOnClickListener(this);
        expressDao = new ExpressDao(this);
        if(getIntent().getFlags()==1025) {
            expressId = getIntent().getStringExtra("expressId");
            Log.i("TAG","expressId" + expressId);
            expressDao.requestQrCodeExpressDetail(expressId);
            showProgress(true);
        }

            expressBundle = getIntent().getBundleExtra("expressBundle");
        if(expressBundle!=null){
            expressId2 = S.getStr(expressBundle.getString("id"));
            mExpressName.setText(S.getStr(expressBundle.getString("name")));
            mExpressTel.setText(S.getStr(expressBundle.getString("tel")));
            mExpressRoom.setText(S.getStr(expressBundle.getString("building") + "-" + expressBundle.getString("unit") + "-" + expressBundle.getString("room")));
            mExpressCompany.setText(S.getStr(expressBundle.getString("company")));
            mExpressNumber.setText(S.getStr(expressBundle.getString("number")));
            if (expressBundle.getInt("status") == 0) {
                tv_express_status.setText("未领取");
                tv_express_status.setBackgroundResource(R.drawable.tv_shape_green);
            } else {
                tv_express_status.setText("已领取");
                tv_express_status.setBackgroundResource(R.drawable.tv_shape_gray);

            }
           list = (ArrayList<Img>)expressBundle.getSerializable("list");
            if (list != null) {

                if (list.size() >= 1) {
                    Arad.imageLoader.load(list.get(0).getZipImg() == null || list.get(0).getZipImg().equals("")
                            ? "aa" : list.get(0).getZipImg()).placeholder(R.drawable.default_image)
                            .into(iv01_second_hand_transfer);
                    Log.i("TAG","URL1"+list.get(0).getZipImg());
                }
                if (list.size() >= 2) {
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(list.get(1).getZipImg() == null || list.get(1).getZipImg().equals("")
                            ? "aa" : list.get(1).getZipImg()).placeholder(R.drawable.default_image)
                            .into(iv02_second_hand_transfer);
                }
                if (list.size() >= 3) {
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(list.get(2).getZipImg() == null || list.get(2).getZipImg().equals("")
                            ? "aa" : list.get(2).getZipImg()).placeholder(R.drawable.default_image)
                            .into(iv03_second_hand_transfer);
                }


            }else{
                ll_express_qrc.setVisibility(View.GONE);
            }
        }

            }



    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(3==requestCode){
            MessageUtils.showShortToast(this, "快递签收成功");
            tv_express_status.setText("已领取");
            tv_express_status.setBackgroundResource(R.drawable.visit_refused);
            Intent intent=new Intent();
            setResult(1001,intent);
            finish();
        }
        if(2 == requestCode){
            //MessageUtils.showShortToast(this,"请求成功");
            expressRecive = expressDao.getExpressRecive();
            mExpressName.setText(S.getStr(expressRecive.getRecipientName()));
            mExpressTel.setText(S.getStr(expressRecive.getMobile()));
            mExpressRoom.setText(S.getStr(expressRecive.getBuilding() + "-" + expressRecive.getUnit() + "-" + expressRecive.getRoom()));
            mExpressCompany.setText(S.getStr(expressRecive.getKindName()));
            mExpressNumber.setText(S.getStr(expressRecive.getLogisticsNum()));
            if(expressDao.getExpressRecive().getStatus()==0) {
                tv_express_status.setText("未领取");
                tv_express_status.setBackgroundResource(R.drawable.tv_shape_green);
            }else{
                tv_express_status.setText("已领取");
                tv_express_status.setBackgroundResource(R.drawable.tv_shape_gray);

            }
            if(expressDao.getListImg()!=null) {
                if (expressDao.getListImg().size() >= 1) {
                    Arad.imageLoader.load(expressDao.getListImg().get(0).getZipImg() == null ||
                            expressDao.getListImg().get(0).getZipImg().equals("") ? "aa" :
                            expressDao.getListImg().get(0).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv01_second_hand_transfer);
                  //  Log.i("TAG","URL"+expressDao.getListImg().get(0).getZipImg());
                }
                if (expressDao.getListImg().size() >= 2) {
                    iv02_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(expressDao.getListImg().get(1).getZipImg() == null ||
                            expressDao.getListImg().get(1).getZipImg().equals("") ? "aa" :
                            expressDao.getListImg().get(1).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv02_second_hand_transfer);
                  //  Log.i("TAG", "URL" + expressDao.getListImg().get(1).getZipImg());

                }
                if (expressDao.getListImg().size() >= 3) {
                    iv03_second_hand_transfer.setVisibility(View.VISIBLE);
                    Arad.imageLoader.load(expressDao.getListImg().get(2).getZipImg() == null ||
                            expressDao.getListImg().get(2).getZipImg().equals("") ? "aa" :
                            expressDao.getListImg().get(2).getZipImg())
                            .placeholder(R.drawable.default_image)
                            .into(iv03_second_hand_transfer);
                  //  Log.i("TAG", "URL" + expressDao.getListImg().get(2).getZipImg());

                }
            }else {
                    ll_express_qrc.setVisibility(View.GONE);
            }

        }

        }


    @Override
    public String setupToolBarTitle() {
        return "快递详情";
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
    public void onClick(View v) {
          if(getIntent().getFlags()==1025) {
              if (v == tv_express_status && expressDao.getExpressRecive().getStatus() == 0) {
                  expressDao.requestSignExpress(expressId);
              }
              if(v==iv01_second_hand_transfer&&expressDao.getListImg().size()>=1){
                  Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                  intent.putExtra("imageurl", expressDao.getListImg().get(0).getOriginalImg());
                  int[] location = new int[2];
                  iv01_second_hand_transfer.getLocationOnScreen(location);
                  intent.putExtra("locationX", location[0]);
                  intent.putExtra("locationY", location[1]);
                  intent.putExtra("width", iv01_second_hand_transfer.getWidth());
                  intent.putExtra("height", iv01_second_hand_transfer.getHeight());
                  startActivity(intent);
                  overridePendingTransition(0, 0);
              }
              if(v==iv02_second_hand_transfer&&expressDao.getListImg().size()>=2){
                  Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                  intent.putExtra("imageurl", expressDao.getListImg().get(1).getOriginalImg());
                  int[] location = new int[2];
                  iv02_second_hand_transfer.getLocationOnScreen(location);
                  intent.putExtra("locationX", location[0]);
                  intent.putExtra("locationY", location[1]);
                  intent.putExtra("width", iv02_second_hand_transfer.getWidth());
                  intent.putExtra("height", iv02_second_hand_transfer.getHeight());
                  startActivity(intent);
                  overridePendingTransition(0, 0);
              }
              if(v==iv03_second_hand_transfer&&expressDao.getListImg().size()>=3){
                  Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                  intent.putExtra("imageurl", expressDao.getListImg().get(2).getOriginalImg());
                  int[] location = new int[2];
                  iv03_second_hand_transfer.getLocationOnScreen(location);
                  intent.putExtra("locationX", location[0]);
                  intent.putExtra("locationY", location[1]);
                  intent.putExtra("width", iv03_second_hand_transfer.getWidth());
                  intent.putExtra("height", iv03_second_hand_transfer.getHeight());
                  startActivity(intent);
                  overridePendingTransition(0, 0);
              }


          }
        if(expressBundle!=null){
            if (v == tv_express_status && expressBundle.getInt("status")==0) {
                expressDao.requestSignExpress(expressId2);
            }

            if(v==iv01_second_hand_transfer&&list.size()>=1){
                Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", list.get(0).getOriginalImg());
                int[] location = new int[2];
                iv01_second_hand_transfer.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv01_second_hand_transfer.getWidth());
                intent.putExtra("height", iv01_second_hand_transfer.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            if(v==iv02_second_hand_transfer&&list.size()>=2){
                Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", list.get(1).getOriginalImg());
                int[] location = new int[2];
                iv02_second_hand_transfer.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv02_second_hand_transfer.getWidth());
                intent.putExtra("height", iv02_second_hand_transfer.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            if(v==iv03_second_hand_transfer&&list.size()>=3){
                Intent intent=new Intent(ExpressDetailActivity.this, TestanroidpicActivity.class);
                intent.putExtra("imageurl", list.get(2).getOriginalImg());
                int[] location = new int[2];
                iv03_second_hand_transfer.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", iv03_second_hand_transfer.getWidth());
                intent.putExtra("height", iv03_second_hand_transfer.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

        }



    }
}

package com.ctrl.android.property.eric.ui.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.VisitDao;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.entity.Visit;
import com.ctrl.android.property.eric.ui.widget.ImageZoomActivity;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 突发拜访
 * Created by Administrator on 2015/10/26.
 */
public class BurstVisitActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.visit_name)
    TextView visit_name;
    @InjectView(R.id.visit_host)
    TextView visit_host;
    @InjectView(R.id.visit_room)
    TextView visit_room;
    @InjectView(R.id.visit_count)
    TextView visit_count;
    @InjectView(R.id.visit_car)
    TextView visit_car;
    @InjectView(R.id.visit_tel)
    TextView visit_tel;
    @InjectView(R.id.visit_stop)
    TextView visit_stop;
    //@InjectView(R.id.visit_photo)
    //ImageView visit_photo;
    @InjectView(R.id.visit_refuse)
    TextView visit_refuse;
    @InjectView(R.id.visit_agree)
    TextView visit_agree;
    @InjectView(R.id.visit_no_home)
    TextView visit_no_home;

    @InjectView(R.id.img_01)//上传图片1
            ImageView img_01;
    @InjectView(R.id.img_02)//上传图片2
            ImageView img_02;
    @InjectView(R.id.img_03)//上传图片3
            ImageView img_03;

    private String communityVisitId;
    private Visit visitDetail;
    private VisitDao visitDao;

    private List<Img> listImg = new ArrayList<>();
    private ArrayList<String> imagelist;//传入到图片放大类 用
    private int position = 0;

    private int flg = 0;

    private String TITLE = StrConstant.BURST_VISIT_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burst_visit);
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        img_01.setOnClickListener(this);
        img_02.setOnClickListener(this);
        img_03.setOnClickListener(this);

        visit_refuse.setOnClickListener(this);
        visit_agree.setOnClickListener(this);
        visit_no_home.setOnClickListener(this);

        visit_refuse.setVisibility(View.GONE);
        visit_agree.setVisibility(View.GONE);
        visit_no_home.setVisibility(View.GONE);

        visitDao = new VisitDao(this);
        communityVisitId = getIntent().getStringExtra("communityVisitId");
        showProgress(true);
        visitDao.requestVisitDetail(communityVisitId);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(1 == requestCode){
            visitDetail = visitDao.getVisitDetail();
            listImg = visitDao.getListImg();

            visit_name.setText(S.getStr(visitDetail.getVisitorName()));
            visit_host.setText(S.getStr(visitDetail.getProprietorId()));
            visit_room.setText(S.getStr(visitDetail.getBuilding()) + "-" + S.getStr(visitDetail.getUnit()) + "-" + S.getStr(visitDetail.getRoom()));
            visit_count.setText(S.getStr(visitDetail.getPeopleNum()));
            visit_car.setText(S.getStr(visitDetail.getNumberPlates()));
            visit_tel.setText(S.getStr(visitDetail.getVisitorMobile()));
            visit_stop.setText(S.getStr(visitDetail.getResidenceTime()) + "小时");

            if(listImg != null && listImg.size() > 0){
                if(listImg.size() == 1){
                    Arad.imageLoader.load(listImg.get(0).getOriginalImg() == null || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.GONE);
                    img_03.setVisibility(View.GONE);
                }
                if(listImg.size() == 2){
                    Arad.imageLoader.load(listImg.get(0).getOriginalImg() == null || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
                    Arad.imageLoader.load(listImg.get(1).getOriginalImg() == null || (listImg.get(1).getOriginalImg()).equals("") ? "aa" : listImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_02);
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.VISIBLE);
                    img_03.setVisibility(View.GONE);
                }

                if(listImg.size() >= 3){
                    Arad.imageLoader.load(listImg.get(0).getOriginalImg() == null || (listImg.get(0).getOriginalImg()).equals("") ? "aa" : listImg.get(0).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_01);
                    Arad.imageLoader.load(listImg.get(1).getOriginalImg() == null || (listImg.get(1).getOriginalImg()).equals("") ? "aa" : listImg.get(1).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_02);
                    Arad.imageLoader.load(listImg.get(2).getOriginalImg() == null || (listImg.get(2).getOriginalImg()).equals("") ? "aa" : listImg.get(2).getOriginalImg())
                            .placeholder(R.drawable.default_image)
                            .into(img_03);
                    img_01.setVisibility(View.VISIBLE);
                    img_02.setVisibility(View.VISIBLE);
                    img_03.setVisibility(View.VISIBLE);
                }
            } else {
                img_01.setVisibility(View.GONE);
                img_02.setVisibility(View.GONE);
                img_03.setVisibility(View.GONE);
            }

            //返回状态（0：已预约 1：已结束 2：同意到访 3：其他 4：拒绝到访 5：业主不在家 6：显示拒绝到访、同意进入、我不在家）
            if(visitDetail.getReturnStatus() == 0){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("已预约");
            } else if(visitDetail.getReturnStatus() == 1){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("已结束");
            } else if(visitDetail.getReturnStatus() == 2){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("同意到访");
            } else if(visitDetail.getReturnStatus() == 3){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("其他");
            } else if(visitDetail.getReturnStatus() == 4){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("拒绝到访");
            } else if(visitDetail.getReturnStatus() == 5){
                visit_agree.setVisibility(View.VISIBLE);
                visit_agree.setClickable(false);
                visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
                visit_agree.setText("业主不在家");
            } else if(visitDetail.getReturnStatus() == 6){
                visit_refuse.setVisibility(View.VISIBLE);
                visit_agree.setVisibility(View.VISIBLE);
                visit_no_home.setVisibility(View.VISIBLE);
            }



        }


        if(3 == requestCode){
            if(flg == 1){
                MessageUtils.showShortToast(this, "同意到访");

            }

            if(flg == 2){
                MessageUtils.showShortToast(this,"拒绝到访");
            }

            if(flg == 3){
                MessageUtils.showShortToast(this,"我不在家");
            }

            visit_refuse.setClickable(false);
            visit_refuse.setBackgroundResource(R.drawable.gray_bg_shap);
            visit_agree.setClickable(false);
            visit_agree.setBackgroundResource(R.drawable.gray_bg_shap);
            visit_no_home.setClickable(false);
            visit_no_home.setBackgroundResource(R.drawable.gray_bg_shap);

        }
    }

    @Override
    public void onClick(View v) {

        if(v == img_01){
            imagelist = new ArrayList<String>();
            for(int i = 0 ; i < listImg.size() ; i ++){
                imagelist.add(listImg.get(i).getOriginalImg());
            }
            position = 0;
            Intent intent = new Intent(this, ImageZoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageList", imagelist);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(v == img_02){
            imagelist = new ArrayList<String>();
            for(int i = 0 ; i < listImg.size() ; i ++){
                imagelist.add(listImg.get(i).getOriginalImg());
            }
            position = 1;
            Intent intent = new Intent(this, ImageZoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageList", imagelist);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(v == img_03){
            imagelist = new ArrayList<String>();
            for(int i = 0 ; i < listImg.size() ; i ++){
                imagelist.add(listImg.get(i).getOriginalImg());
            }
            position = 2;
            Intent intent = new Intent(this, ImageZoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageList", imagelist);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(v == visit_refuse){
            flg = 2;
            //1：同意到访、2：拒绝到访、3：我不在家
            String handleStatus = "2";
            showProgress(true);
            visitDao.requestHandleVisit(communityVisitId,handleStatus);
        }

        if(v == visit_agree){
            flg = 1;
            //1：同意到访、2：拒绝到访、3：我不在家
            String handleStatus = "1";
            showProgress(true);
            visitDao.requestHandleVisit(communityVisitId,handleStatus);
        }

        if(v == visit_no_home){
            flg = 3;
            //1：同意到访、2：拒绝到访、3：我不在家
            String handleStatus = "3";
            showProgress(true);
            visitDao.requestHandleVisit(communityVisitId,handleStatus);
        }
    }


    @Override
    public String setupToolBarTitle() {
        return TITLE;
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


}

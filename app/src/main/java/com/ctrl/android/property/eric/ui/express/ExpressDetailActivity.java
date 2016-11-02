package com.ctrl.android.property.eric.ui.express;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.ExpressDao;
import com.ctrl.android.property.eric.entity.ExpressRecive;
import com.ctrl.android.property.eric.entity.Img;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 快递详情
 * Created by Administrator on 2015/10/23.
 */
public class ExpressDetailActivity extends AppToolBarActivity {
    @InjectView(R.id.iv_express_img)//快递图片
    ImageView mExpressImg;
    @InjectView(R.id.tv_express_name)//收件人姓名
    TextView mExpressName;
    @InjectView(R.id.tv_express_tel)//收件人电话
    TextView mExpressTel;
    @InjectView(R.id.tv_express_room)//收件房间号
    TextView mExpressRoom;
    @InjectView(R.id.tv_express_company)//快递名称
    TextView mExpressCompany;
    @InjectView(R.id.tv_express_number)//快递编号
    TextView mExpressNumber;
    @InjectView(R.id.iv_express_qrc)//快递二维码
    ImageView mExpressQrc;

    private String TITLE = StrConstant.EXPRESS_DETAIL_TITLE;
    private String expressId;
    private ExpressDao expressDao;

    private ExpressRecive expressRecive;
    private List<Img> listImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express_detail_activity);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        expressId = getIntent().getStringExtra("expressId");
        expressDao = new ExpressDao(this);
        showProgress(true);
        expressDao.requestExpressDetail(expressId);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if(1 == requestCode){
            //MessageUtils.showShortToast(this,"请求成功");
            expressRecive = expressDao.getExpressRecive();
            listImg = expressDao.getListImg();

            mExpressName.setText(S.getStr(expressRecive.getRecipientName()));
            mExpressTel.setText(S.getStr(expressRecive.getMobile()));
            mExpressRoom.setText(S.getStr(expressRecive.getBuilding() + "-" + expressRecive.getUnit() + "-" + expressRecive.getRoom()));
            mExpressCompany.setText(S.getStr(expressRecive.getLogisticsCompanyName()));
            mExpressNumber.setText(S.getStr(expressRecive.getLogisticsNum()));

//            Picasso.with(this)
//                    .load(S.getStr(listImg.get(0).getOriginalImg()))
//                    .placeholder(R.drawable.shop_icon_01)
//                    .into(mExpressImg);
//
//            Picasso.with(this)
//                    .load(S.getStr(expressRecive.getQrImgUrl()))
//                    .placeholder(R.drawable.shop_icon_01)
//                    .into(mExpressImg);

//            Arad.imageLoader
//                    .load(S.getStr(listImg.get(0).getOriginalImg()).equals("") ? "aa" : S.getStr(listImg.get(0).getOriginalImg()))
//                    .placeholder(R.drawable.default_express_img)
//                    .into(mExpressImg);

            if(listImg == null || listImg.size() < 1){
                Arad.imageLoader
                        .load("aa")
                        .placeholder(R.drawable.default_image)
                        .into(mExpressImg);
            } else {
                Arad.imageLoader
                        .load(S.getStr(listImg.get(0).getOriginalImg()).equals("") ? "aa" : S.getStr(listImg.get(0).getOriginalImg()))
                        .placeholder(R.drawable.default_image)
                        .into(mExpressImg);
            }

            Arad.imageLoader
                    .load(S.getStr(expressRecive.getQrImgUrl()).equals("") ? "aa" : S.getStr(expressRecive.getQrImgUrl()))
                    .placeholder(R.drawable.default_image)
                    .into(mExpressQrc);

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

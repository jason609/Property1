package com.ctrl.android.property.eric.ui.visit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.dao.VisitDao;
import com.ctrl.android.property.eric.entity.Visit;
import com.ctrl.android.property.eric.util.D;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import m.framework.utils.UIHandler;

/**
 * 我的到访详情
 * Created by Administrator on 2015/10/26.
 */
public class MyVisitDetail extends AppToolBarActivity implements View.OnClickListener, PlatformActionListener, Handler.Callback {

    @InjectView(R.id.visit_num)//到访编号
    TextView visit_num;
    @InjectView(R.id.visit_room)//拜访房间
    TextView visit_room;
    @InjectView(R.id.visit_name)//到  访  人
    TextView visit_name;
    @InjectView(R.id.visit_time)//到访时间
    TextView visit_time;
    @InjectView(R.id.visit_count)//到访人数
    TextView visit_count;
    @InjectView(R.id.visit_car_num)//车  牌  号
    TextView visit_car_num;
    @InjectView(R.id.visit_stop_time)//预计停留时间
    TextView visit_stop_time;
    @InjectView(R.id.visit_qrc)//二维码  地址
    ImageView visit_qrc;
    @InjectView(R.id.visit_qq)//QQ分享
    ImageView visit_qq;
    @InjectView(R.id.visit_wx)//微信分享
    ImageView visit_wx;

    private String communityVisitId;
    private Visit visitDetail;
    private VisitDao visitDao;
    private String TITLE = StrConstant.MY_VISIT_TITLE;

    protected PlatformActionListener paListener;

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        setContentView(R.layout.my_visit_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init(){

        visit_qq.setOnClickListener(this);
        visit_wx.setOnClickListener(this);

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

            visit_qrc.setLayoutParams(new LinearLayout.LayoutParams(AndroidUtil.getDeviceWidth(this) * 2 / 3,
                    AndroidUtil.getDeviceWidth(this) * 2 / 3));

            Arad.imageLoader
                    .load(visitDetail.getQrImgUrl() == null || visitDetail.getQrImgUrl().equals("") ? "aa" : visitDetail.getQrImgUrl())
                    .placeholder(R.drawable.default_image)
                    .into(visit_qrc);

            visit_num.setText(S.getStr(visitDetail.getVisitNum()));
            visit_room.setText(S.getStr(visitDetail.getCommunityName()) + " " + S.getStr(visitDetail.getBuilding()) + "-" + S.getStr(visitDetail.getUnit()) + "-" + S.getStr(visitDetail.getRoom()));
            visit_name.setText(S.getStr(visitDetail.getVisitorName()));
            visit_time.setText(D.getDateStrFromStamp(Constant.YYYY_MM_DD,visitDetail.getArriveTime()));
            visit_count.setText(S.getStr(visitDetail.getPeopleNum()));
            visit_car_num.setText(S.getStr(visitDetail.getNumberPlates()));
            visit_stop_time.setText(S.getStr(visitDetail.getResidenceTime()) + "小时");
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

    @Override
    public void onClick(View v) {
        if(v == visit_qq){
            MessageUtils.showLongToast(this, "正在启动QQ");
            //showProgress(true);
            Platform.ShareParams qqsp = new Platform.ShareParams();
            qqsp.setTitle("二维码");
            qqsp.setTitleUrl(visitDetail.getQrImgUrl());//分享后点击跳转链接
            qqsp.setText("请扫描二维码");
            qqsp.setImageUrl(visitDetail.getQrImgUrl());
            Platform qqPlatform = ShareSDK.getPlatform(this,QQ.NAME);
            qqPlatform.setPlatformActionListener(this);
            qqPlatform.share(qqsp);
        }

        if(v == visit_wx){
            MessageUtils.showLongToast(this, "正在启动微信");
            Platform.ShareParams wxsp = new Platform.ShareParams();
            //wxsp.setShareType(Platform.SHARE_IMAGE);
            wxsp.setTitle("二维码");
            wxsp.setTitleUrl(visitDetail.getQrImgUrl());//分享后点击跳转链接
            //wxsp.setTitleUrl("到访二维码");//分享后点击跳转链接
            wxsp.setText("请扫描二维码");
            wxsp.setImageUrl(visitDetail.getQrImgUrl());
            Platform wxPlatform = ShareSDK.getPlatform(this,Wechat.NAME);
            wxPlatform.setPlatformActionListener(this);
            wxPlatform.share(wxsp);
        }
    }


    @Override
    public void onCancel(Platform platform, int action) {
        //showProgress(false);
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
        //showProgress(false);
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg,this);

    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        // 失敗
        //打印错误信息,print the error msg
        t.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);

    }

    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                //Toast.makeText(this,"分享成功", 10000).show();
                //System.out.println("分享回调成功------------");
                MessageUtils.showShortToast(this,"分享成功");
            }
            break;
            case 2: {
                // 失败
                //Toast.makeText(MainActivity.this, "分享失败", 10000).show();
                MessageUtils.showShortToast(this,"分享失败");
            }
            break;
            case 3: {
                // 取消
                //Toast.makeText(MainActivity.this,"分享取消", 10000).show();
                MessageUtils.showShortToast(this,"分享取消");
            }
            break;
        }

        return false;

    }
}

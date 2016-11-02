package com.ctrl.android.property.staff.ui.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.VisitDao;
import com.ctrl.android.property.staff.util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


/*
* 预约到访详情  activity
* */
public class VisitOrderHandleDetailActivity extends AppToolBarActivity implements View.OnClickListener {
    @InjectView(R.id.visit_num)//到访编号
    TextView visit_num;
    @InjectView(R.id.visit_name)//到访人
    TextView visit_name;
    @InjectView(R.id.visit_time)//到访时间
    TextView visit_time;
    @InjectView(R.id.visit_count)//到访人数
    TextView visit_count;
    @InjectView(R.id.visit_car)//车牌号
    TextView visit_car;
    @InjectView(R.id.visit_stop_time)//预计停留时间
    TextView visit_stop_time;
    @InjectView(R.id.tv_order_status)//到访状态
    TextView tv_order_status;


    private VisitDao vdao;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_activity_visit_order_handle_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        tv_order_status.setOnClickListener(this);
        vdao=new VisitDao(this);
        if(getIntent().getFlags()==999) {
            vdao.requestVisitDetail("0", getIntent().getStringExtra("communityVisitId"));
        }

        bundle=getIntent().getBundleExtra("visitBundle");
        if(bundle!=null){
            visit_num.setText(bundle.getString("number"));
            visit_name.setText(bundle.getString("name"));
            visit_time.setText(bundle.getString("time"));
            visit_count.setText(bundle.getString("count"));
      visit_car.setText(bundle.getString("car"));
            visit_stop_time.setText(bundle.getString("stop"));

      if (bundle.getInt("status")==0) {
                tv_order_status.setText("已到访");
                tv_order_status.setBackgroundResource(R.drawable.visit_none);

            }
            if(bundle.getInt("status")==1) {
                tv_order_status.setText("已到访");
                tv_order_status.setBackgroundResource(R.drawable.visit_refused);

            }
        }
        
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if(4==requestCode){
            MessageUtils.showShortToast(this, "更改预约到访状态成功");
            tv_order_status.setText("已到访");
            tv_order_status.setBackgroundResource(R.drawable.visit_refused);
            Intent intent=new Intent();
            intent.setAction("visit_list_update");
            sendBroadcast(intent);
            setResult(1001,intent);
            finish();
        }
        if(1==requestCode){
            visit_num.setText(vdao.getVisitDetail().getVisitNum());
            visit_name.setText(vdao.getVisitDetail().getVisitorName());
            String arriveTime=vdao.getVisitDetail().getArriveTime();
            if(arriveTime!=null) {
                String time = TimeUtil.date(Long.parseLong(arriveTime));
                visit_time.setText(time);
            }else {
                visit_time.setText("");
            }
            visit_count.setText(vdao.getVisitDetail().getPeopleNum()+"");
            visit_car.setText(vdao.getVisitDetail().getNumberPlates());
            visit_stop_time.setText(vdao.getVisitDetail().getResidenceTime());
            if(vdao.getVisitDetail().getHandleStatus()==0){
                tv_order_status.setText("已到访");
                tv_order_status.setBackgroundResource(R.drawable.visit_none);
            }
            if(vdao.getVisitDetail().getHandleStatus()==1){
                tv_order_status.setText("已到访");
                tv_order_status.setBackgroundResource(R.drawable.visit_refused);
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(bundle!=null) {
            if (v == tv_order_status && bundle.getInt("status") == 0) {
                vdao.requestOrderVisitStatus(bundle.getString("communityVisitId"));
            }
        }else {
            if (v == tv_order_status && vdao.getVisitDetail().getHandleStatus() == 0) {
                vdao.requestOrderVisitStatus(getIntent().getStringExtra("communityVisitId"));
            }
        }


    }

    @Override
    public String setupToolBarTitle() {
        return "预约到访";
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

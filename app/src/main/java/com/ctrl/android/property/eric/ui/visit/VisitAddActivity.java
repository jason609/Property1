package com.ctrl.android.property.eric.ui.visit;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppHolder;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.dao.VisitDao;
import com.ctrl.android.property.eric.ui.house.HouseListActivity2;
import com.ctrl.android.property.eric.ui.widget.TimePicker;
import com.ctrl.android.property.eric.util.S;
import com.ctrl.android.property.eric.util.StrConstant;
import com.ctrl.android.property.jason.util.InputMethodUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 发起到访
 * Created by Administrator on 2015/10/26.
 */
public class VisitAddActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.activity_layout)//
    LinearLayout activity_layout;

    @InjectView(R.id.visit_room)//拜访房间
    TextView visit_room;
    @InjectView(R.id.room_change)//修改
    TextView room_change;
    @InjectView(R.id.visit_name)//到访人
    EditText visit_name;
    @InjectView(R.id.visit_time)//到访时间
    EditText visit_time;
    @InjectView(R.id.visit_count)//到访人数
    EditText visit_count;
    @InjectView(R.id.visit_car)//车牌号
    EditText visit_car;
    @InjectView(R.id.visit_stop)//预计停留时间
    EditText visit_stop;

    private View mMenuView;
    private TimePicker timePicker;
    private Button superman_ok_btn;
    private Button superman_cancel_btn;

    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String time_str;

    private String TITLE = StrConstant.START_VISIT_TITLE;

    private VisitDao visitDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_add);
        ButterKnife.inject(this);
        init();
    }

    private void init(){
        room_change.setOnClickListener(this);
        visit_time.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(AppHolder.getInstance().getHouse().getId() != null){
            visit_room.setText(AppHolder.getInstance().getHouse().getCommunityName()
                               + "  " + AppHolder.getInstance().getHouse().getBuilding()
                               + "-" + AppHolder.getInstance().getHouse().getUnit()
                               + "-" + AppHolder.getInstance().getHouse().getRoom());
        }

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);

        if(2 == requestCode){
            MessageUtils.showShortToast(this,"添加成功");
            finish();
        }
    }


    @Override
    public void onClick(View v) {

        if(v == room_change){
            Intent intent = new Intent(VisitAddActivity.this, HouseListActivity2.class);
            startActivity(intent);
            AnimUtil.intentSlidIn(VisitAddActivity.this);
        }

        if(v == visit_time){
            InputMethodUtils.hide(VisitAddActivity.this);
            showTimePickerPop(visit_time);
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
    public boolean setupToolBarRightText(TextView mRightText) {
        mRightText.setText(R.string.done);
        mRightText.setTextColor(getResources().getColor(R.color.text_white));
        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkInput()){
                    visitDao = new VisitDao(VisitAddActivity.this);
                    String communityId = AppHolder.getInstance().getCommunity().getId();//具体问题具体分析
                    String proprietorId = AppHolder.getInstance().getProprietor().getProprietorId();//具体问题具体分析
                    String addressId = AppHolder.getInstance().getHouse().getAddressId();//具体问题具体分析
                    String visitorName = S.getStr(visit_name.getText().toString());
                    String arriveTime = S.getStr(visit_time.getText().toString()) + ":00";
                    String peopleNum = S.getStr(visit_count.getText().toString());
                    String numberPlates = S.getStr(visit_car.getText().toString());
                    String residenceTime = S.getStr(visit_stop.getText().toString());
                    showProgress(true);
                    visitDao.requestAddVisit(communityId,proprietorId,addressId
                                                ,visitorName,arriveTime,peopleNum
                                                ,numberPlates,residenceTime);
                }

            }
        });
        return true;
    }

    private boolean checkInput(){

        if(S.isNull(visit_room.getText().toString())){
            MessageUtils.showShortToast(this,"拜访房间不可为空");
            return false;
        }

        if(S.isNull(visit_name.getText().toString())){
            MessageUtils.showShortToast(this,"到访人不可为空");
            return false;
        }

        if((visit_count.getText().toString() == null || visit_count.getText().toString().equals("") ? 0 : Integer.parseInt(visit_count.getText().toString())) <= 0 ){
            MessageUtils.showShortToast(this,"到访人数不可小于0");
            return false;
        }

        return true;
    }

    /**
     * 显示时间拾取器的 弹出
     * */
    private void showTimePickerPop(final EditText editText){
        //setRoomData();
        mMenuView = LayoutInflater.from(VisitAddActivity.this).inflate(R.layout.choose_time_bottom_pop, null);
        timePicker = (TimePicker)mMenuView.findViewById(R.id.timePicker);
        superman_ok_btn = (Button)mMenuView.findViewById(R.id.superman_ok_btn);
        superman_cancel_btn = (Button)mMenuView.findViewById(R.id.superman_cancel_btn);

        final PopupWindow Pop = new PopupWindow();


        // 设置SelectPicPopupWindow的View
        Pop.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        Pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        Pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        Pop.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        Pop.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        Pop.setBackgroundDrawable(dw);


        superman_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = timePicker.getYear();
                month = timePicker.getMonth();
                day = timePicker.getDay();
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                time_str = timePicker.getTime_string();
                Log.d("demo", "time_str: " + time_str);
                editText.setText(time_str);
                Pop.dismiss();
            }
        });

        superman_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop.dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        Pop.dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        Pop.setFocusable(true);
        Pop.showAtLocation(activity_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


}

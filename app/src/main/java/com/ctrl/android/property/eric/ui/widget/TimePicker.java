package com.ctrl.android.property.eric.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.ctrl.android.property.R;
import com.ctrl.android.property.eric.ui.widget.picker.ScrollerNumberPicker;
import com.ctrl.android.property.eric.ui.widget.picker.ScrollerNumberPicker.OnSelectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 城市Picker
 *
 * @author zd
 *
 */
public class TimePicker extends LinearLayout {
    /** 滑动控件 */
    private static ScrollerNumberPicker yearPicker;
    private static ScrollerNumberPicker monthPicker;
    private static ScrollerNumberPicker dayPicker;
    private static ScrollerNumberPicker hourPicker;
    private static ScrollerNumberPicker minutePicker;
    /** 选择监听 */
    private OnSelectingListener onSelectingListener;
    /** 刷新界面 */
    private static final int REFRESH_VIEW = 0x001;
    /** 临时日期 */
    private int tempYearIndex = -1;
    private int tempMonthIndex = -1;
    private int tempDayIndex = -1;

    private Context context;

    private ArrayList<String> listYear;
    private ArrayList<String> listMonth;
    private ArrayList<String> listDay;
    private ArrayList<String> listHour;
    private ArrayList<String> listMinute;

    private int year_flg;

    private String room_string;

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TimePicker(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.time_picker, this);

        // 获取控件引用
        yearPicker = (ScrollerNumberPicker) findViewById(R.id.Scroll1);
        monthPicker = (ScrollerNumberPicker) findViewById(R.id.Scroll2);
        dayPicker = (ScrollerNumberPicker) findViewById(R.id.Scroll3);
        hourPicker = (ScrollerNumberPicker) findViewById(R.id.Scroll4);
        minutePicker = (ScrollerNumberPicker) findViewById(R.id.Scroll5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yearStr = sdf.format(new Date());
        int year = Integer.parseInt(yearStr);

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        String monthStr = sdf1.format(new Date());
        int month = Integer.parseInt(monthStr);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
        String dayStr = sdf2.format(new Date());
        int day = Integer.parseInt(dayStr);

        SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
        String hourStr = sdf3.format(new Date());
        int hour = Integer.parseInt(hourStr);

        SimpleDateFormat sdf4 = new SimpleDateFormat("mm");
        String minuteStr = sdf4.format(new Date());
        int minute = Integer.parseInt(minuteStr);

        yearPicker.setData(getYears());
        yearPicker.setDefault(49);


        monthPicker.setData(getMonths());
        monthPicker.setDefault(month - 1);

        dayPicker.setData(getDays(year,month));
        dayPicker.setDefault(day - 1);

        hourPicker.setData(getHours());
        hourPicker.setDefault(hour);

        minutePicker.setData(getMinutes());
        minutePicker.setDefault(minute);

        yearPicker.setOnSelectListener(new OnSelectListener() {

            @Override
            public void endSelect(final int id, String text) {
                //System.out.println("id-->" + id + "text----->" + text);
                if (text.equals("") || text == null)
                    return;
                if (tempYearIndex != id) {
                    System.out.println("endselect");
                    String selectDay = monthPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = dayPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;

                    /******************************************************/
                    monthPicker.setData(getMonths());
                    monthPicker.setDefault(0);
                    dayPicker.setData(getDays(Integer.parseInt(text),1));
                    dayPicker.setDefault(0);
                    year_flg = Integer.parseInt(text);
                    /******************************************************/

                    int lastDay = Integer.valueOf(yearPicker.getListSize());
                    if (id > lastDay) {
                        yearPicker.setDefault(lastDay - 1);
                    }

                }
                tempYearIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }
        });
        monthPicker.setOnSelectListener(new OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub
                if (text.equals("") || text == null)
                    return;
                if (tempMonthIndex != id) {
                    String selectDay = yearPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = dayPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;

                    /************************************************/
                    dayPicker.setData(getDays(year_flg,Integer.parseInt(text)));
                    dayPicker.setDefault(0);
                    /************************************************/


                    int lastDay = Integer.valueOf(monthPicker.getListSize());
                    if (id > lastDay) {
                        monthPicker.setDefault(lastDay - 1);
                    }
                }
                tempMonthIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }
        });
        dayPicker.setOnSelectListener(new OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub

                if (text.equals("") || text == null)
                    return;
                if (tempDayIndex != id) {
                    String selectDay = yearPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    String selectMonth = monthPicker.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;

                    int lastDay = Integer.valueOf(dayPicker.getListSize());
                    if (id > lastDay) {
                        dayPicker.setDefault(lastDay - 1);
                    }
                }
                tempDayIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
                default:
                    break;
            }
        }

    };

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    public String getTime_string() {
        room_string = yearPicker.getSelectedText()
                + "-" + monthPicker.getSelectedText() + "-" + dayPicker.getSelectedText()
                + " " + hourPicker.getSelectedText() + ":" +minutePicker.getSelectedText();
        return room_string;
    }

    public static String getYear() {
        String str = yearPicker.getSelectedText();
        return str;
    }
    public static String getMonth() {
        String str = monthPicker.getSelectedText();
        return str;
    }
    public static String getDay() {
        String str = dayPicker.getSelectedText();
        return str;
    }

    public static String getHour() {
        String str = hourPicker.getSelectedText();
        return str;
    }

    public static String getMinute() {
        String str = minutePicker.getSelectedText();
        return str;
    }

    public interface OnSelectingListener {
        public void selected(boolean selected);
    }


    private ArrayList<String> getYears(){
        listYear = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String yearStr = sdf.format(new Date());
        int year = Integer.parseInt(yearStr);

        for(int i = 1 ; i <= 100 ; i ++){
            listYear.add(String.valueOf(((year - 50) + i)));
        }
        return listYear;
    }

    private ArrayList<String> getMonths(){
        listMonth = new ArrayList<>();
        for(int i = 1 ; i <= 12 ; i ++){
            if(i < 10){
                listMonth.add("0"+String.valueOf(i));
            } else {
                listMonth.add(String.valueOf(i));
            }

        }
        return listMonth;
    }

    private ArrayList<String> getDays(int year,int month){
        listDay = new ArrayList<>();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        String dayStr = sdf1.format(new Date());
        int day = Integer.parseInt(dayStr);

        if(2 == month){
            if(year % 4 == 0){
                for(int i = 1 ; i <= 29 ; i ++){
                    if(i < 10){
                        listDay.add("0"+String.valueOf(i));
                    } else {
                        listDay.add(String.valueOf(i));
                    }
                    //listDay.add(String.valueOf(i));
                }
            } else {
                for(int i = 1 ; i <= 28 ; i ++){
                    if(i < 10){
                        listDay.add("0"+String.valueOf(i));
                    } else {
                        listDay.add(String.valueOf(i));
                    }
                    //listDay.add(String.valueOf(i));
                }
            }
        }

        if(month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12 ){
            for(int i = 1 ; i <= 31 ; i ++){
                if(i < 10){
                    listDay.add("0"+String.valueOf(i));
                } else {
                    listDay.add(String.valueOf(i));
                }
                //listDay.add(String.valueOf(i));
            }
        }

        if(month == 4 || month == 6 || month == 9 || month == 11 ){
            for(int i = 1 ; i <= 30 ; i ++){
                if(i < 10){
                    listDay.add("0"+String.valueOf(i));
                } else {
                    listDay.add(String.valueOf(i));
                }
                //listDay.add(String.valueOf(i));
            }
        }


        return listDay;
    }


    private ArrayList<String> getHours(){
        listHour = new ArrayList<>();
        for(int i = 0 ; i <= 23 ; i ++){
            if(i < 10){
                listHour.add("0"+String.valueOf(i));
            } else {
                listHour.add(String.valueOf(i));
            }
            //listHour.add(String.valueOf(i));
        }
        return listHour;
    }

    private ArrayList<String> getMinutes(){
        listMinute = new ArrayList<>();
        for(int i = 0 ; i <= 59 ; i ++){
            if(i < 10){
                listMinute.add("0"+String.valueOf(i));
            } else {
                listMinute.add(String.valueOf(i));
            }
            //listMinute.add(String.valueOf(i));
        }
        return listMinute;
    }


}

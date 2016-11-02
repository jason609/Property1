package com.ctrl.android.property.jason.ui.secondmarket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.jason.dao.ClassDao;
import com.ctrl.android.property.jason.dao.UsedGoodsDao;
import com.ctrl.android.property.jason.ui.Fragment.SecondHandFragment;
import com.ctrl.android.property.jason.ui.adapter.JasonViewPagerAdapter;
import com.ctrl.android.property.jason.ui.adapter.SecondHandGridViewAdapter;
import com.ctrl.android.property.jason.util.StrConstant;
import com.ctrl.android.property.jason.util.jUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SecondHandActivity extends AppToolBarActivity implements View.OnClickListener {

    @InjectView(R.id.login_regest_layout)
    LinearLayout login_regest_layout;

    @InjectView(R.id.viewpager_secondhand)
    ViewPager viewpager_secondhand;
    @InjectView(R.id.tv_secong_hand_transfer)
    TextView tv_secong_hand_transfer;//转让
    @InjectView(R.id.tv_secong_hand_buy)
    TextView tv_secong_hand_buy;//求购
   // SparseArray<Fragment> fragmets=new SparseArray<>();
    List<Fragment>fragments=new ArrayList<>();
    private RelativeLayout rl_main;
    RelativeLayout rl_second_hand;
    private View mMenuView;
    private Button btn_second_hand_gridview;
    private PopupWindow popupWindowButtom;
    private PopupWindow popupWindow;
    private ClassDao dao;
    private SecondHandGridViewAdapter gvadapter;
    private GridView gv_second_hand;
    private List<String>mKindListName=new ArrayList<>();
    private List<String>mKindListId=new ArrayList<>();
    public static final int UPDATE_LIST=2000;
    public static final int UPDATE_LIST_BUY=2001;
    private String transactionType="0";
    private SecondHandFragment fragment01;
    private SecondHandFragment fragment02;
    private UsedGoodsDao udao;
    private int mposition;
    private JasonViewPagerAdapter adapter;
    private String kindId=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand);
        ButterKnife.inject(this);
        init();


    }

    private void init() {
        dao=new ClassDao(this);
        udao=new UsedGoodsDao(this);
        gvadapter=new SecondHandGridViewAdapter(this);
        login_regest_layout.setVisibility(View.VISIBLE);
        rl_second_hand=(RelativeLayout)findViewById(R.id.rl_second_hand);
        rl_main=(RelativeLayout)findViewById(R.id.rl_main);
        tv_secong_hand_transfer.setOnClickListener(this);
        tv_secong_hand_buy.setOnClickListener(this);
        fragment01=SecondHandFragment.newInstance(StrConstant.SECOND_HAND_TRANSFER,kindId);
        fragment02=SecondHandFragment.newInstance(StrConstant.SECOND_HAND_BUY,kindId);
        /*fragmets.put(0, fragment01);
        fragmets.put(1, fragment02);*/
        fragments.add(fragment01);
        fragments.add(fragment02);
        adapter=new JasonViewPagerAdapter(getSupportFragmentManager(),fragments);
        adapter.setOnReloadListener(new JasonViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragments = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(SecondHandFragment.newInstance(StrConstant.SECOND_HAND_TRANSFER,kindId));
                list.add(SecondHandFragment.newInstance(StrConstant.SECOND_HAND_BUY,kindId));
                adapter.setPagerItems(list);
            }
        });
        viewpager_secondhand.setAdapter(adapter);
        viewpager_secondhand.setCurrentItem(0);
        viewpager_secondhand.setOffscreenPageLimit(2);
        viewpager_secondhand.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        transactionType="0";
                        tv_secong_hand_transfer.setBackgroundResource(R.drawable.button_left_shap_checked);
                        tv_secong_hand_transfer.setTextColor(Color.WHITE);
                        tv_secong_hand_buy.setBackgroundResource(R.drawable.button_right_shap);
                        tv_secong_hand_buy.setTextColor(Color.GRAY);
                        break;
                    case 1:
                        transactionType="1";
                        tv_secong_hand_buy.setBackgroundResource(R.drawable.button_right_shap_checked);
                        tv_secong_hand_buy.setTextColor(Color.WHITE);
                        tv_secong_hand_transfer.setBackgroundResource(R.drawable.button_left_shap);
                        tv_secong_hand_transfer.setTextColor(Color.GRAY);
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_toolbar_register ://点击添加按钮
                showPopupWindow(v);//弹出下拉菜单
                break;
            case R.id.tv_toolbar_login://点击类别按钮
                dao.getData().clear();
                dao.requestData(StrConstant.SECOND_MARKET_TYPE_APPKEY);
                    //showPopupBottom();//底部弹出菜单
                break;
            case R.id.tv_secong_hand_transfer:
                tv_secong_hand_transfer.setBackgroundResource(R.drawable.button_left_shap_checked);
                tv_secong_hand_transfer.setTextColor(Color.WHITE);
                tv_secong_hand_buy.setBackgroundResource(R.drawable.button_right_shap);
                tv_secong_hand_buy.setTextColor(Color.GRAY);
                viewpager_secondhand.setCurrentItem(0);
                transactionType="0";
                break;
            case R.id.tv_secong_hand_buy:
                tv_secong_hand_buy.setBackgroundResource(R.drawable.button_right_shap_checked);
                tv_secong_hand_buy.setTextColor(Color.WHITE);
                tv_secong_hand_transfer.setBackgroundResource(R.drawable.button_left_shap);
                tv_secong_hand_transfer.setTextColor(Color.GRAY);
                viewpager_secondhand.setCurrentItem(1);
                transactionType="1";
                break;




    }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);

        if(requestCode==4){
           // MessageUtils.showShortToast(this, "获取类别成功");
           mKindListName.clear();
           mKindListId.clear();
            //dao.getData().clear();
            for(int i=0;i<dao.getData().size();i++){
                mKindListName.add(dao.getData().get(i).getKindName());
                mKindListId.add(dao.getData().get(i).getId());
            }
            mKindListName.add(0,"全部");
            mKindListId.add(0, null);
            /*Log.i("TAG","TAG"+mKindListId.size());
            Log.i("TAG","TAG"+mKindListId.get(0));
            Log.i("TAG", "TAG" + mKindListId.get(1));
            Log.i("TAG","TAG"+mKindListId.get(2));*/
            showPopupBottom();//底部弹出菜单
        }

        if(requestCode==2){
            MessageUtils.showShortToast(this, "分类列表");
        }
    }

    private void showPopupBottom() {
        mMenuView=LayoutInflater.from(this).inflate(R.layout.second_hand_gridview,null);
        gv_second_hand=(GridView)mMenuView.findViewById(R.id.gv_second_hand);
        btn_second_hand_gridview=(Button)mMenuView.findViewById(R.id.btn_second_hand_gridview);
        gvadapter.setList(mKindListName);
        gv_second_hand.setAdapter(gvadapter);
        jUtils.setListViewHeightBasedOnChildren(gv_second_hand);
        gvadapter.notifyDataSetChanged();
        gv_second_hand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mposition=position;
                gvadapter.setSeclection(position);
                gvadapter.notifyDataSetChanged();
                kindId=mKindListId.get(position);

            }
        });
        popupWindowButtom = new PopupWindow(mMenuView,
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置SelectPicPopupWindow的View
        popupWindowButtom.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        popupWindowButtom.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        popupWindowButtom.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        popupWindowButtom.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        popupWindowButtom.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindowButtom.setBackgroundDrawable(dw);

        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        /*
        * 设置popupwindow 点击自身消失
        * */
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindowButtom.isShowing()) {
                    popupWindowButtom.dismiss();
                }
            }
        });

        btn_second_hand_gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowButtom.dismiss();
                 adapter.reLoad();
                }
        });
        popupWindowButtom.setOutsideTouchable(true);
        popupWindowButtom.showAtLocation(rl_main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
   /*     popupWindowButtom.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindowButtom.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.edit_shap_green));*/



    }

    private void showPopupWindow(View view) {
        View contentView= LayoutInflater.from(this).inflate(R.layout.pop_window_secondhand,null);
        TextView tv01=(TextView)contentView.findViewById(R.id.tv_popwindow_transfer);
        TextView tv02=(TextView)contentView.findViewById(R.id.tv_popwindow_buy);
        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(SecondHandActivity.this,"我要转",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SecondHandActivity.this,TransferActivity.class);
                startActivityForResult(intent,UPDATE_LIST);
                AnimUtil.intentSlidIn(SecondHandActivity.this);
                popupWindow.dismiss();


            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(SecondHandActivity.this,"我要购",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SecondHandActivity.this,WantBuyActivity.class);
                startActivityForResult(intent, UPDATE_LIST_BUY);
                AnimUtil.intentSlidIn(SecondHandActivity.this);
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.edit_shap_green));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view,-140,60);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UPDATE_LIST:
                if(RESULT_OK==resultCode){
                    adapter.reLoad();

                }
                break;
            case UPDATE_LIST_BUY:
                if(RESULT_OK==resultCode){
                    adapter.reLoad();
                    viewpager_secondhand.setCurrentItem(1);
                }
                break;
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
                //MessageUtils.showShortToast(MallShopActivity.this, "AA");
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
        return "闲鱼市场";
    }


    /**
     * header 右侧按钮
     * */

    @Override
    public boolean setupToolBarRightText(TextView mRightText) {
        TextView mRightText01 =(TextView)findViewById(R.id.tv_toolbar_login);
        TextView mRightText02 =(TextView)findViewById(R.id.tv_toolbar_register);
        mRightText01.setText("类别");
        mRightText01.setVisibility(View.VISIBLE);
        mRightText02.setText("添加");
        mRightText02.setVisibility(View.VISIBLE);
        mRightText01.setOnClickListener(this);
        mRightText02.setOnClickListener(this);
        return true;
    }

    /**
     * 测试 获取数据的方法
     * */
/*    private List<Map<String,String>> getListMap(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("type","类型" +i);
            list.add(map);
        }
        return list;
    }*/

}

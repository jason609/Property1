package com.ctrl.android.property.eric.ui.mall;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.AppToolBarActivity;
import com.ctrl.android.property.eric.util.StrConstant;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 社区商城搜索 activity
 * Created by Eric on 2015/11/11.
 */
public class MallSearchActivity extends AppToolBarActivity implements View.OnClickListener{

    @InjectView(R.id.keyword_text)//关键字
    EditText keyword_text;
    @InjectView(R.id.search_btn)//搜索按钮
    Button search_btn;

    private String TITLE = StrConstant.SEARCH_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁止横屏
        setContentView(R.layout.mall_search_activity);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 初始化执行的 方法
     * */
    private void init(){
        search_btn.setOnClickListener(this);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
    }

    /**
     * 数据请求失败后
     * */
//    @Override
//    public void onRequestFaild(String errorNo, String errorMessage) {
//        showProgress(false);
//        //mPullToRefreshListView.onRefreshComplete();
//        MessageUtils.showShortToast(this, errorMessage);
//    }

    @Override
    public void onClick(View v) {
       if(v == search_btn){
           if(checkInput()){
               Intent intent = new Intent(MallSearchActivity.this, MallShopListActivity.class);
               intent.putExtra("keyword","" + keyword_text.getText().toString());
               startActivity(intent);
               AnimUtil.intentSlidIn(MallSearchActivity.this);
           }
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
        return TITLE;
    }


    /**
     * header 右侧按钮
     * */
//    @Override
//    public boolean setupToolBarRightButton(ImageView rightButton) {
//        rightButton.setImageResource(R.drawable.search_icon);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //toHomePage();
//                MessageUtils.showShortToast(MallSearchActivity.this, "搜索");
//            }
//        });
//        return true;
//    }

    private boolean checkInput(){
        if(keyword_text.getText().toString() == null || keyword_text.getText().toString().equals("")){
            MessageUtils.showShortToast(this,"请输入搜索内容");
            return false;
        }
        return true;
    }

}

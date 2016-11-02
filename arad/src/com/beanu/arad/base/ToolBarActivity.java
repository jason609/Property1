package com.beanu.arad.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.utils.AnimUtil;


/**
 * @author beanu
 */
public class ToolBarActivity extends BaseActivity implements ISetupToolBar {

    private TextView mTitle;
    private TextView mTime;
    private ImageView mLeftButton;
    private ImageView mRightButton;
    private TextView mCity;
    private TextView mRightText;
    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            mTitle = (TextView) findViewById(R.id.toolbar_title);
            mTime = (TextView) findViewById(R.id.toolbar_time);
            mLeftButton = (ImageView) findViewById(R.id.toolbar_leftbtn);
            mRightButton = (ImageView) findViewById(R.id.toolbar_rightbtn);
            mRightText = (TextView) findViewById(R.id.toolbar_rightText);
            mCity = (TextView)findViewById(R.id.toolbar_city);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mTitle != null && setupToolBarTitle() != null)
            mTitle.setText(setupToolBarTitle());


        if (mLeftButton != null) {
            if (setupToolBarLeftButton(mLeftButton)) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mLeftButton.setVisibility(View.GONE);
            }
        }

        if (mRightButton != null) {
            if (setupToolBarRightButton(mRightButton)) {
                mRightButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }

        if (mRightText != null) {
            if (setupToolBarRightText(mRightText)) {
                mRightText.setVisibility(View.VISIBLE);
            } else {
                mRightText.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        AnimUtil.intentSlidOut(this);
    }

    public void setupToolBarCity(String city){
        if (mCity != null){
            mCity.setVisibility(View.VISIBLE);
            mCity.setText(city);
        }
    }


    // 动态改变


    @Override
    public String setupToolBarTitle() {
        return null;
    }

    @Override
    public boolean setupToolBarLeftButton(ImageView leftButton) {
        return false;
    }

    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        return false;
    }

    public boolean setupToolBarRightText(TextView mRightText) {
        return false;
    }

    @Override
    public TextView getmTitle() {
        return mTitle;
    }

    public TextView getmTime() {
        return mTime;
    }

    @Override
    public ImageView getmLeftButton() {
        return mLeftButton;
    }

    @Override
    public ImageView getmRightButton() {
        return mRightButton;
    }
}

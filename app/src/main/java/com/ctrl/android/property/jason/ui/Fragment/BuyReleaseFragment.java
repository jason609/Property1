package com.ctrl.android.property.jason.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ctrl.android.property.R;

/**
 * 房屋出租 fragment
 * Created by jason on 2015/10/12.
 */
public class BuyReleaseFragment extends Fragment implements View.OnClickListener {

    public static BuyReleaseFragment newInstance(){
        BuyReleaseFragment fragment=new BuyReleaseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_release,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}

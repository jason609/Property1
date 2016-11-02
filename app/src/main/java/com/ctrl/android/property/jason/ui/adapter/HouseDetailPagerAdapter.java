package com.ctrl.android.property.jason.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.ctrl.android.property.jason.ui.Fragment.HouseDetailPagerFragment;

import java.util.List;

/**
 * 商品详细页 viewpager adapter
 * Created by jason on 2015/10/14
 */
public class HouseDetailPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> imageList;

    public HouseDetailPagerAdapter(FragmentManager fm, List<String> imageList) {
        super(fm);
        this.imageList = imageList;
        for(int i = 0 ; i < imageList.size() ; i ++){
            Log.d("demo","imageList( "+ i + "):" + imageList.get(i));
        }
    }

    public Fragment getItem(int position) {
        return HouseDetailPagerFragment.newInstance(imageList.get(position), imageList, position);
    }

    @Override
    public int getCount() {
        return imageList == null ? 0 : imageList.size();
    }
}

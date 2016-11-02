package com.ctrl.android.property.eric.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.ctrl.android.property.eric.ui.mall.ProductPicPagerFragment;

import java.util.List;

/**
 * 商品详细页 viewpager adapter
 * Created by Eric on 2015/10/8.
 */
public class ProductPicPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> imageList;

    public ProductPicPagerAdapter(FragmentManager fm, List<String> imageList) {
        super(fm);
        this.imageList = imageList;
        for(int i = 0 ; i < imageList.size() ; i ++){
            Log.d("demo","imageList( "+ i + "):" + imageList.get(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ProductPicPagerFragment.newInstance(imageList.get(position), imageList, position);
    }

    @Override
    public int getCount() {
        return imageList == null ? 0 : imageList.size();
    }
}

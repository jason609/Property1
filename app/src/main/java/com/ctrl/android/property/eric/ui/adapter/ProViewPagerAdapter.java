package com.ctrl.android.property.eric.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

/**
 * 商品页面 viewpager adapter
 * Created by Eric on 2015/6/24.
 */
public class ProViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> fragmentsList = new SparseArray<Fragment>();

    public ProViewPagerAdapter(FragmentManager fm, SparseArray<Fragment> fragmentsList){
        super(fm);
        this.fragmentsList = fragmentsList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList == null ? 0 : fragmentsList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}

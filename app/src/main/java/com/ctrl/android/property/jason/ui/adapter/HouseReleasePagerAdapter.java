package com.ctrl.android.property.jason.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

/**
 *  viewpager adapter
 * Created by jason on 2015/10/12.
 */
public class HouseReleasePagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> fragmentsList = new SparseArray<Fragment>();

    public HouseReleasePagerAdapter(FragmentManager fm, SparseArray<Fragment> fragmentsList){
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

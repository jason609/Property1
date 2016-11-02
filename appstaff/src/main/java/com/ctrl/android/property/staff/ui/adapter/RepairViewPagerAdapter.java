package com.ctrl.android.property.staff.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;

/**
 *  viewpager adapter
 * Created by jason on 2015/6/24.
 */
public class RepairViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * 页面内容集合
     */
    private SparseArray<Fragment> fgs = null;
    private FragmentManager mFragmentManager;
    /**
     * 当数据发生改变时的回调接口
     */
    private OnReloadListener mListener;

    public RepairViewPagerAdapter(FragmentManager fm, SparseArray<Fragment> fgs)
    {
        super(fm);
        this.fgs = fgs;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int index)
    {
        Log.i("TAG", "ITEM CREATED...");
        return fgs.get(index);
    }

    @Override
    public int getCount()
    {
        return fgs.size();// 返回选项卡总数
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    /**
     * 重新设置页面内容
     * @param items
     */
    public void setPagerItems(SparseArray<Fragment> items)
    {
        if (items != null)
        {
            for (int i = 0; i < fgs.size(); i++)
            {
                mFragmentManager.beginTransaction().remove(fgs.get(i)).commit();
            }
            fgs = items;
        }
    }
    /**
     *当页面数据发生改变时你可以调用此方法
     *
     * 重新载入数据，具体载入信息由回调函数实现
     */
    public void reLoad()
    {
        if(mListener != null)
        {
            mListener.onReload();
        }
        this.notifyDataSetChanged();
    }
    public void setOnReloadListener(OnReloadListener listener)
    {
        this.mListener = listener;
    }
    /**
     * @author Rowand jj
     *回调接口
     */
    public interface OnReloadListener
    {
        public void onReload();
    }
}

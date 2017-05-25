package com.bigdata.bigdatalib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * user:kun
 * Date:2017/5/24 or 下午4:33
 * email:hekun@gamil.com
 * Desc:
 */

public class MyViewPagerAdater extends FragmentStatePagerAdapter {
    
    public MyViewPagerAdater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}

package com.yanda.moduleapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yanda.moduleapplication.fragment.MyFragment;

/**
 * 作者：caibin
 * 时间：2021/5/18 11:53
 * 类说明：
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MyFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}

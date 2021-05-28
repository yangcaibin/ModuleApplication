package com.yanda.moduleapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yanda.moduleapplication.fragment.ChildFragment;

/**
 * 作者：caibin
 * 时间：2021/5/18 13:38
 * 类说明：
 */
public class ChildViewPagerAdapter extends FragmentStatePagerAdapter {
    public ChildViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ChildFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

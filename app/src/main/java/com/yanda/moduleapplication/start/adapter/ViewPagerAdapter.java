package com.yanda.moduleapplication.start.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 作者：caibin
 * 时间：2018/5/2 9:58
 * 类说明：引导页滑动的适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;

    public ViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

}

package com.yanda.moduleapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanda.module_base.base.BaseFragment;
import com.yanda.moduleapplication.R;

import butterknife.BindView;

/**
 * 作者：caibin
 * 时间：2021/5/18 13:32
 * 类说明：
 */
public class ChildFragment extends BaseFragment {

    @BindView(R.id.textView)
    TextView textView;

    public static ChildFragment newInstance(int currentPosition) {
        ChildFragment fragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currentPosition", currentPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void initView() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int currentPosition = arguments.getInt("currentPosition");
            textView.setText("我是第" + currentPosition + "个子fragment");
        }
    }

    @Override
    public void addOnClick() {

    }
}

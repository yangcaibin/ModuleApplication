package com.yanda.module_course;

import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.router.RouterActivityPath;
import com.yanda.module_base.utils.RouterUtil;

import butterknife.BindView;

/**
 * 作者：caibin
 * 时间：2021/3/12 18:23
 * 类说明：
 */
@Route(path = RouterActivityPath.Course.PAGER_COURSE)
public class CourseActivity extends BaseActivity {
    Button button;

    @Override
    protected int initContentView() {
        return R.layout.activity_aaa;
    }

    @Override
    public void initView() {
        button = findViewById(R.id.button);
    }

    @Override
    public void addOnClick() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.button) {
            RouterUtil.launchExam();
        }
    }
}

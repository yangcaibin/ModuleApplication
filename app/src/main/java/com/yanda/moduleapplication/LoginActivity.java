package com.yanda.moduleapplication;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.module_base.base.BaseActivity;
import com.yanda.module_base.router.RouterActivityPath;

/**
 * 作者：caibin
 * 时间：2021/4/29 16:27
 * 类说明：
 */
@Route(path = RouterActivityPath.Login.PAGER_LOGIN)
public class LoginActivity extends BaseActivity {
    @Override
    protected int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void addOnClick() {

    }
}

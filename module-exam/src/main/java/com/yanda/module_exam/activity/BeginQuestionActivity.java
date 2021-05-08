package com.yanda.module_exam.activity;

import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanda.module_base.entity.ExamEntity;
import com.yanda.module_base.router.RouterActivityPath;
import com.yanda.module_base.utils.RouterUtil;
import com.yanda.module_exam.R;
import com.yanda.module_exam.contract.BeginQuestionContract;
import com.yanda.module_exam.contract.BeginQuestionPresenter;

import java.util.Map;

/**
 * 作者：caibin
 * 时间：2021/4/29 15:29
 * 类说明：
 */
@Route(path = RouterActivityPath.Exam.PAGER_BEGIN)
public class BeginQuestionActivity extends BaseQuestionActivity<BeginQuestionPresenter> implements
        BeginQuestionContract.View {
    Button button;
    @Override
    public BeginQuestionPresenter getPresenter() {
        return new BeginQuestionPresenter(this);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_begin_question;
    }

    @Override
    public void initView() {
        button = findViewById(R.id.button);
        presenter.getQuestionIdsDataMethod("", "");
    }

    @Override
    public void addOnClick() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.button){
            RouterUtil.launchLogin();
        }
    }

    @Override
    public void setQuestionUserDataMethod(Map<Long, ExamEntity> examEntityMap) {

    }
}

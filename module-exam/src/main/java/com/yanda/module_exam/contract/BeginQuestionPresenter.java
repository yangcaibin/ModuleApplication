package com.yanda.module_exam.contract;

import com.yanda.module_base.base.BasePresenter;
import com.yanda.module_base.entity.ExamEntity;
import com.yanda.module_base.http.HttpObserver;
import com.yanda.module_base.http.HttpResult;
import com.yanda.module_base.http.RxScheduler;
import com.yanda.module_exam.model.BeginQuestionModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：caibin
 * 时间：2018/5/17 17:47
 * 类说明：做题的管理
 */
public class BeginQuestionPresenter extends BasePresenter<BeginQuestionContract.View> implements
        BeginQuestionContract.Presenter {
    private BeginQuestionModel model;

    public BeginQuestionPresenter(BeginQuestionContract.View view) {
        super(view);
        model = new BeginQuestionModel();
    }

    //根据试题Id字符串获取用户试题信息
    @Override
    public void getQuestionIdsDataMethod(String userId, String questionIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("questionIds", questionIds);
        map.put("userAnswer", 1);  //传参数不返回答案
        model.getQuestionIdsDataMethod(map)
                .compose(RxScheduler.Obs_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new HttpObserver<Map<Long, ExamEntity>>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(HttpResult<Map<Long, ExamEntity>> result) {
                        super.onNext(result);

                    }
                });
    }
}

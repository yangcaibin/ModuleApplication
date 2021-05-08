package com.yanda.module_exam.contract;

import com.yanda.module_base.base.BaseView;
import com.yanda.module_base.entity.ExamEntity;
import com.yanda.module_base.http.HttpResult;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 作者：caibin
 * 时间：2018/5/3 13:30
 * 类说明：做题或考试的契约类
 */
public interface BeginQuestionContract {

    interface Model {
        Observable<HttpResult<Map<Long, ExamEntity>>> getQuestionIdsDataMethod(Map<String, Object> map);
    }

    interface View extends BaseView {
        //获取用户试题数据成功
        void setQuestionUserDataMethod(Map<Long, ExamEntity> examEntityMap);
    }

    interface Presenter {
        //根据questionIds获取试题信息
        void getQuestionIdsDataMethod(String userId, String questionIds);
    }
}

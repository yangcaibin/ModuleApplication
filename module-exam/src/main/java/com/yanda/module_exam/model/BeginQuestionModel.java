package com.yanda.module_exam.model;

import com.yanda.module_base.entity.ExamEntity;
import com.yanda.module_base.http.HttpResult;
import com.yanda.module_base.http.RetrofitUtils;
import com.yanda.module_exam.contract.BeginQuestionContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 作者：caibin
 * 时间：2021/4/29 14:59
 * 类说明：
 */
public class BeginQuestionModel implements BeginQuestionContract.Model {
    @Override
    public Observable<HttpResult<Map<Long, ExamEntity>>> getQuestionIdsDataMethod(Map<String, Object> map) {
        return RetrofitUtils.getApiService().getQuestionIdsDataMethod(map);
    }
}

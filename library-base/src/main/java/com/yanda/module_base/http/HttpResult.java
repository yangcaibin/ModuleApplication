package com.yanda.module_base.http;

/**
 * 作者：caibin
 * 时间：2017/11/14 18:16
 * 类说明：基础网络数据封装
 */

public class HttpResult<T> {
    private String message = "";
    private boolean success;
    private T entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}

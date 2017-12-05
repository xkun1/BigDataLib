package com.bigdata.mylibrary.base.callback;

/**
 * user:kun
 * Date:03/07/2017 or 2:49 PM
 * email:hekun@gamil.com
 * Desc: 请求回调
 */

public interface CallBack<T> {
    void onSuccess(T t);

    void onFailure(int code, String msg);
}

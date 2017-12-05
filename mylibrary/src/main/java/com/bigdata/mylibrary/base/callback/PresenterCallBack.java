package com.bigdata.mylibrary.base.callback;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * user:kun
 * Date:03/07/2017 or 2:49 PM
 * email:hekun@gamil.com
 * Desc: 实现回调接口
 */

public class PresenterCallBack<T> implements CallBack<T> {

    private CallBack<T> mCallBack;

    public PresenterCallBack(CallBack<T> mCallBack) {
        Reference<CallBack<T>> reference = new WeakReference<>(mCallBack);
        this.mCallBack = mCallBack;
    }


    @Override
    public void onSuccess(T t) {
        mCallBack.onSuccess(t);
    }

    @Override
    public void onFailure(int code, String msg) {
        mCallBack.onFailure(code, msg);
    }
}

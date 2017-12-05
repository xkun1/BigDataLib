package com.bigdata.mylibrary.base.callback;

import com.bigdata.mylibrary.base.bean.NetResponse;
import com.bigdata.mylibrary.util.LogUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * user:kun
 * Date:03/07/2017 or 2:59 PM
 * email:hekun@gamil.com
 * Desc:
 */

public class NetCallBack<T extends NetResponse> implements Consumer<T> {

    private PresenterCallBack<T> mPresenterCallBack;

    private ViewCallBack<T> viewCallBack;

    /**
     * Mvp模式返回的结果
     *
     * @param mPresenterCallBack
     */
    public NetCallBack(PresenterCallBack<T> mPresenterCallBack) {
        Reference<PresenterCallBack<T>> reference = new WeakReference<>(mPresenterCallBack);
        this.mPresenterCallBack = reference.get();
    }

    public NetCallBack(ViewCallBack<T> viewCallBack) {
        Reference<ViewCallBack<T>> reference = new WeakReference<>(viewCallBack);
        this.viewCallBack = reference.get();
    }

    @Override
    public void accept(@NonNull T t) throws Exception {
        if (t.getCode().equals("200")) {
            if (mPresenterCallBack != null) {
                mPresenterCallBack.onSuccess(t);
            } else {
                LogUtils.e("PresenterCallBack is Null");
            }
            if (viewCallBack != null) {
                viewCallBack.onSuccess(t);
            } else {
                LogUtils.e("PresenterCallBack is Null");
            }
        } else {
            if (mPresenterCallBack != null) {
                mPresenterCallBack.onFailure(10006, t.getMsg());
            }
            if (viewCallBack != null) {
                viewCallBack.onFailure(10006, t.getMsg());
            }
        }
    }
}

package com.bigdata.mylibrary.net;


import android.content.Context;

import com.bigdata.mylibrary.util.LogUtils;
import com.bigdata.mylibrary.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * user:kun
 * Date:2017/5/19 or 下午2:15
 * email:hekun@gamil.com
 * Desc:封装rxjava+Retrofit返回结果集
 */

public class ResultSubScriber<T> implements Observer<T>, ProgressCancelListener {
    private Context mContext;
    private ProgressDialogHandler mHandler;
    private SubscriberOnNextListener<T> nextListener;
    private Disposable mDisposable;


    public ResultSubScriber(SubscriberOnNextListener<T> nextListener, Context mContext) {
        this.mContext = mContext;
        this.nextListener = nextListener;
        mHandler = new ProgressDialogHandler(mContext, this, true);
    }

    private void showProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
            mDisposable.dispose();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;

        LogUtils.d("开始获取数据=======");
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (nextListener != null) {
            if (t != null) {
                nextListener.onNext(t);
            }
        }
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.d("onError==" + e.toString());

        dismissProgressDialog();
        nextListener.onError();
        ToastUtils.showLong("网络异常，请稍后再试");
    }

    @Override
    public void onComplete() {
        LogUtils.d("数据获取完成=======");
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        mDisposable.dispose();
    }
}

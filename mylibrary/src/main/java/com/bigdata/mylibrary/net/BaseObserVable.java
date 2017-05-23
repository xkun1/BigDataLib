package com.bigdata.mylibrary.net;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * user:kun
 * Date:2017/5/19 or 下午3:40
 * email:hekun@gamil.com
 * Desc:  网络请求
 */

public class BaseObserVable<T> {
    private static class SingtonInstance {
        @SuppressLint("StaticFieldLeak")
        private static BaseObserVable instance = new BaseObserVable();
    }

    public static BaseObserVable getInstance() {
        return SingtonInstance.instance;
    }

    public void setObservale(Observable<T> observable, ResultSubScriber<T> resultSubscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultSubscriber);
    }
}

package com.bigdata.mylibrary.base.mvp;

import com.bigdata.mylibrary.base.bean.NetResponse;
import com.bigdata.mylibrary.base.callback.NetCallBack;
import com.bigdata.mylibrary.base.callback.NetExceptionCallBack;
import com.bigdata.mylibrary.base.callback.PresenterCallBack;
import com.bigdata.mylibrary.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * user:kun
 * Date:03/07/2017 or 2:27 PM
 * email:hekun@gamil.com
 * Desc: model处理数据业务通过
 */

public class BaseModel {

    //订阅管理
    private CompositeDisposable mCompositeDisposable;

    /**
     * 取消Rxjava订阅
     */
    public void onUnSubscribe() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        } else {
            LogUtils.e("CompositeDisposable is null");
        }
    }

    /**
     * 添加订阅
     *
     * @param mDisposable
     */
    public void onaddSubscribe(Disposable mDisposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable = new CompositeDisposable();
        } else {
            LogUtils.e("CompositeDisposable is null");
        }
        mCompositeDisposable.add(mDisposable);
    }

    public <T extends NetResponse> void addSubscription(Observable<T> observable,
                                                        PresenterCallBack<T> presenterCallBack) {

        if (mCompositeDisposable != null) {
            mCompositeDisposable = new CompositeDisposable();
        } else {
            LogUtils.e("CompositeDisposable is null");
        }
        mCompositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new NetCallBack<>(presenterCallBack),
                                new NetExceptionCallBack(presenterCallBack))
        );

    }

    public void detach() {
        onUnSubscribe();
    }

}

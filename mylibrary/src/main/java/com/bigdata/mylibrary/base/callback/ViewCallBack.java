package com.bigdata.mylibrary.base.callback;

import com.bigdata.mylibrary.base.mvp.BaseView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * user:kun
 * Date:03/07/2017 or 3:02 PM
 * email:hekun@gamil.com
 * Desc:
 */

public  abstract class ViewCallBack<T> implements CallBack<T> {

    private BaseView view;

    public ViewCallBack(BaseView view) {
        Reference<BaseView>reference=new WeakReference<>(view);
        this.view =reference.get();
    }

    @Override
    public void onFailure(int code, String msg) {
        view.onFailure(code, msg);
    }
}

package com.bigdata.mylibrary.base.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * user:kun
 * Date:03/07/2017 or 3:25 PM
 * email:hekun@gamil.com
 * Desc: p层将view和model关联起来
 */

public abstract class BasePresenter<V extends BaseView> {

    private V mView;

    public BasePresenter(V mView) {
        Reference<V>reference=new WeakReference<V>(mView);
        this.mView =reference.get();
    }
}

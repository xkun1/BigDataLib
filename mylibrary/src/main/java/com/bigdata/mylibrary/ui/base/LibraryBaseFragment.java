package com.bigdata.mylibrary.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bigdata.mylibrary.net.BaseObserVable;
import com.bigdata.mylibrary.net.RetroFactory;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/20 or 下午3:19
 * email:hekun@gamil.com
 * Desc:  打造通用的Fragment
 */

public abstract class LibraryBaseFragment extends Fragment {
    protected RetroFactory mRetroFactory;
    protected BaseObserVable mBaseObserVable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetroFactory = RetroFactory.getInstance();
        mBaseObserVable = BaseObserVable.getInstance();
        if (mBaseObserVable != null && mRetroFactory != null) {
            init();
        } else {
            LogUtils.d("初始化失败,请查看Application");
        }
    }

    protected abstract void init();

}

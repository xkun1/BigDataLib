package com.bigdata.mylibrary.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bigdata.mylibrary.chart.utils.Utils;
import com.bigdata.mylibrary.net.BaseObserVable;
import com.bigdata.mylibrary.net.RetroFactory;
import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/19 or 下午9:13
 * email:hekun@gamil.com
 * Desc: 打造通用的Activity
 */

public abstract class LibraryBaseActivity extends AppCompatActivity {
    protected RetroFactory mRetroFactory;
    protected BaseObserVable mBaseObserVable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetroFactory = RetroFactory.getInstance();
        mBaseObserVable = BaseObserVable.getInstance();
        setContentView(layoutId());
        Utils.init(this);
        if (mBaseObserVable != null && mRetroFactory != null) {
            init();
        } else {
            LogUtils.d("初始化失败,请查看Application");
        }
    }

    protected abstract int layoutId();

    protected abstract void init();


}

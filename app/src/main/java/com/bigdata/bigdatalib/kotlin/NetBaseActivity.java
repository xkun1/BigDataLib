package com.bigdata.bigdatalib.kotlin;

import android.annotation.SuppressLint;

import com.bigdata.bigdatalib.ApiServer;
import com.bigdata.mylibrary.ui.base.LibraryBaseActivity;

/**
 * user:kun
 * Date:19/01/2018 or 11:00 AM
 * email:hekun@gamil.com
 * Desc:
 */

@SuppressLint("Registered")
public abstract class NetBaseActivity extends LibraryBaseActivity {

    protected ApiServer mApiServer = null;

    @Override
    protected void init() {
        setContentView(setLayoutId());
        mApiServer = mRetroFactory.create(ApiServer.class);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApiServer = null;
    }

    protected abstract void initData();

    protected abstract int setLayoutId();
}

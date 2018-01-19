package com.bigdata.bigdatalib;

import android.annotation.SuppressLint;

import com.bigdata.bigdatalib.kotlin.NetBaseActivity;
import com.bigdata.bigdatalib.kotlin.SvcClEntity;
import com.bigdata.mylibrary.net.ResultSubScriber;
import com.bigdata.mylibrary.net.SubscriberOnNextListener;

/**
 * user:kun
 * Date:19/01/2018 or 11:03 AM
 * email:hekun@gamil.com
 * Desc:
 */

@SuppressLint("Registered")
public class NetActivityDemo extends NetBaseActivity {


    @Override
    protected void initData() {
        mBaseObserVable.setObservale(mApiServer.getSvcCl("1.0"), new ResultSubScriber<>(new SubscriberOnNextListener<SvcClEntity>() {
            @Override
            public void onNext(SvcClEntity svcClEntity) {

            }

            @Override
            public void onError() {

            }
        }, this));
    }

    @Override
    protected int setLayoutId() {
        return 0;
    }
}

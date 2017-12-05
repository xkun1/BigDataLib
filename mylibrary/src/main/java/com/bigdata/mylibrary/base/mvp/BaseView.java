package com.bigdata.mylibrary.base.mvp;

import android.content.Context;

/**
 * user:kun
 * Date:03/07/2017 or 3:03 PM
 * email:hekun@gamil.com
 * Desc:
 */

public interface BaseView {
    void showLoading();

    void dissLoading();

    void onFailure(int code,String str);

    Context getContext();
}

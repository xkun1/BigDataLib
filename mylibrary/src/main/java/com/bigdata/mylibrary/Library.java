package com.bigdata.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;

import com.bigdata.mylibrary.util.LogUtils;

/**
 * user:kun
 * Date:2017/5/19 or 上午9:25
 * email:hekun@gamil.com
 * Desc:网络库，image图库，Log库，文件下载库，Toast库，UI库
 */

public class Library {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String baseUrl;

    public static Context init(Context context) {
        Library.mContext = context.getApplicationContext();
        return getmContext();
    }

    public static String initBaseUrl(String baseUrl) {
        Library.baseUrl = baseUrl;
        return getBaseurl();
    }

    public static String getBaseurl() {
        if (baseUrl != null) {
            return baseUrl;
        }else {
            LogUtils.d("baseUrl...为空");
        }
        throw new NullPointerException("baseUrl...为空");
    }

    public static Context getmContext() {
        if (mContext != null) return mContext;
        throw new NullPointerException("u should init first");
    }
}

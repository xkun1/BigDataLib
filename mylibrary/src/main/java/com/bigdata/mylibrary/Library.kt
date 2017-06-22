package com.bigdata.mylibrary

import android.annotation.SuppressLint
import android.content.Context

import com.bigdata.mylibrary.util.LogUtils

/**
 * user:kun
 * Date:2017/5/19 or 上午9:25
 * email:hekun@gamil.com
 * Desc:网络库，image图库，Log库，文件下载库，Toast库，UI库
 * 和一些常用的工具，二维码生成和扫描，图表库
 */

object Library {
    @SuppressLint("StaticFieldLeak")
    var mContext: Context? = null
    var baseUrl: String? = null

    fun init(context: Context): Context {
        Library.mContext = context.applicationContext
        return getmContext()
    }

    fun initBaseUrl(baseUrl: String): String {
        Library.baseUrl = baseUrl
        return baseurl
    }

    val baseurl: String
        get() {
            if (baseUrl != null) {
                return baseUrl as String
            } else {
                LogUtils.d("baseUrl...为空")
            }
            throw NullPointerException("baseUrl...为空")
        }

    fun getmContext(): Context {
        if (mContext != null) return mContext as Context
        throw NullPointerException("context...为空")
    }
}

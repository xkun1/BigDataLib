package com.bigdata.bigdatalib.kotlin

import android.content.Intent
import com.bigdata.bigdatalib.ApiServer
import com.bigdata.mylibrary.ui.base.LibraryBaseActivity

/**
 * user:kun
 * Date:2017/5/19 or 下午4:49
 * email:hekun@gamil.com
 * Desc: 所有activity基类
 */

open class BaseActivity :LibraryBaseActivity() {

    var mApiServer: ApiServer? = null

    override fun init() {
        mApiServer = mRetroFactory!!.create(ApiServer::class.java)
    }


    open fun onActivityResult(requestCode: Int?, resultCode: Int?, data: Intent?) {}
}

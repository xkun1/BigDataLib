package com.bigdata.bigdatalib.kotlin

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.bigdata.bigdatalib.R
import com.bigdata.mylibrary.ui.webView.LibraryWebView

/**
 * user:kun
 * Date:2017/5/22 or 上午10:33
 * email:hekun@gamil.com
 * Desc:
 */

class WebViewActivity : BaseActivity() {

    var libraryWebView: LibraryWebView? = null


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        libraryWebView = findViewById(R.id.webView) as LibraryWebView
        libraryWebView!!.setmContext(this@WebViewActivity)
        libraryWebView!!.initWebView()
//        libraryWebView!!.loadUrl("file:///android_asset/default_jt.htm?c=000002.sz&fid=F10&istophide=0")
        libraryWebView!!.loadUrl("file:///android_asset/default_jt.htm?c=000002.sz&cid=-3&fid=F10&istophide=1&sid=#")
        //        libraryWebView.loadUrl("file:///android_asset/MyHtml.html");
    }

}
